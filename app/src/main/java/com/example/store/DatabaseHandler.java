package com.example.store;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.store.Modal.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private Context dbContext;
    private static final String dbPath = "/data/data/com.example.store/databases/store.sqlite";
    private static final String dbName = "store.sqlite";
    private static final int dbVersion = 1;

    private SQLiteDatabase db;
    private static final String TABLE_PRODUCTS = "qly_sanpham";
    private static final String TABLE_CART = "qly_card";

    public DatabaseHandler(Context context) {
        super(context, dbName, null, dbVersion);
        this.dbContext = context;
       db = this.getWritableDatabase();
        db.enableWriteAheadLogging(); // Enable WAL mode
    }

    public void copyDB2SDCard() {
        boolean check = false;
        try {
            File file = new File(dbPath);
            check = file.exists();

            if (check) {
                this.close();
            } else {
                this.getReadableDatabase();
                InputStream myInput = dbContext.getAssets().open(dbName);
                String outFileName = dbPath;
                OutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        } catch (Exception ex) {
            throw new Error("Lỗi không copy được database", ex);
        }
    }

    void openDB() {
        db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    void closeDB() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public int GetCount(String sql) {
        openDB();
        Cursor cur = db.rawQuery(sql, null);
        int count = cur.getCount();
        cur.close();
        closeDB();
        return count;
    }

    public Cursor getCursor(String strSelect) {
        openDB();
        return db.rawQuery(strSelect, null);
    }

    public void executeSQL(String strSQL) {
        openDB();
        db.execSQL(strSQL);
        closeDB();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + "productId TEXT PRIMARY KEY,"
                + "tenMatHang TEXT,"
                + "giaBan TEXT,"
                + "soLuong TEXT,"
                + "donViTinh TEXT,"
                + "imgProduct TEXT,"
                + "ghiChu TEXT,"
                + "soLuongHienTai TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_PRODUCTS_TABLE);

        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + "productId TEXT PRIMARY KEY,"
                + "tenMatHang TEXT,"
                + "giaBan TEXT,"
                + "soLuong TEXT,"
                + "donViTinh TEXT,"
                + "imgProduct TEXT,"
                + "ghiChu TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(sqLiteDatabase);
    }

    public void addProductToCart(Product product) {
        SQLiteDatabase db = null;
        int retries = 5;
        while (retries > 0) {
            try {
                db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("productId", product.productId);
                values.put("tenMatHang", product.tenMatHang);
                values.put("giaBan", product.giaBan);
                values.put("soLuong", 1); // Mỗi lần thêm vào giỏ hàng là 1 đơn vị
                values.put("donViTinh", product.donViTinh);
                values.put("imgProduct", product.imgProduct);
                values.put("ghiChu", product.ghiChu);

                String selectQuery = "SELECT * FROM " + TABLE_CART + " WHERE productId = ? AND tenMatHang = ?";
                Cursor cursor = db.rawQuery(selectQuery, new String[]{product.productId, product.tenMatHang});
                if (cursor.moveToFirst()) {
                    @SuppressLint("Range") int currentQuantity = cursor.getInt(cursor.getColumnIndex("soLuong"));
                    values.put("soLuong", currentQuantity + 1); // Tăng số lượng lên 1
                    db.update(TABLE_CART, values, "productId = ? AND tenMatHang = ?", new String[]{product.productId, product.tenMatHang});
                } else {
                    db.insert(TABLE_CART, null, values);
                }
                cursor.close();

                // Cập nhật soLuong trong bảng qly_SanPham, trừ đi 1 đơn vị
                String updateQuery = "UPDATE " + TABLE_PRODUCTS + " SET soLuong = soLuong - 1 WHERE productId = ?";
                db.execSQL(updateQuery, new Object[]{product.productId});

                db.close();
                break; // Thoát vòng lặp khi thành công
            } catch (SQLiteDatabaseLockedException e) {
                retries--;
                try {
                    Thread.sleep(50); // Đợi trước khi thử lại
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
                break;
            }
        }
    }



    @SuppressLint("Range")
    public ArrayList<Product> getCartProducts() {
        ArrayList<Product> cartProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.productId = cursor.getString(cursor.getColumnIndex("productId"));
                product.tenMatHang = cursor.getString(cursor.getColumnIndex("tenMatHang"));
                product.giaBan = cursor.getString(cursor.getColumnIndex("giaBan"));
                product.soLuong = cursor.getString(cursor.getColumnIndex("soLuong"));
                product.donViTinh = cursor.getString(cursor.getColumnIndex("donViTinh"));
                product.imgProduct = cursor.getString(cursor.getColumnIndex("imgProduct"));
                product.ghiChu = cursor.getString(cursor.getColumnIndex("ghiChu"));

                // Lấy số lượng hiện tại từ bảng qly_SanPham
                String productQuery = "SELECT soLuong FROM " + TABLE_PRODUCTS + " WHERE productId = ?";
                Cursor productCursor = db.rawQuery(productQuery, new String[]{product.productId});
                if (productCursor.moveToFirst()) {
                    product.soLuongHienTai = productCursor.getString(productCursor.getColumnIndex("soLuong"));
                }
                productCursor.close();

                cartProducts.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cartProducts;
    }

    public void updateProductQuantity(String productId, String soLuong) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soLuong", soLuong);
        db.update(TABLE_PRODUCTS, values, "productId = ?", new String[]{productId});
        db.close();
    }
}
