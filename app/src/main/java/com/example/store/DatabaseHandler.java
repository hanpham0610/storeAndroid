package com.example.store;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHandler extends SQLiteOpenHelper {
        private Context dbContext;
        private SQLiteDatabase db;
        private static final String dbPath = "/data/data/com.example.store/databases/cuahang.sqlite";
        private static final String dbName = "cuahang.sqlite";
        private static final int dbVersion = 1;

        // Phương thức 1: Phương thức khởi dựng
        public DatabaseHandler(Context context) {
            super(context, dbName, null, dbVersion);
            // TODO Auto-generated constructor stub
            this.dbContext = context;
        }

        //Phương thức 2:
        //Kiểm tra sự tồn tại của CSDL và copy CSDL sang SD Card
        //Nếu CSDL SQLite ĐÃ CÓ trên SD Card rồi thì thôi
        //Nếu CSDL SQLite CHƯA CÓ trên SD Card thì copy sang
        public void copyDB2SDCard() {
            boolean check = false;
            try {
                File file = new File(dbPath);
                check = file.exists();
                if (check) {
                    this.close();
                } else if (!check) {
                    this.getReadableDatabase();
                    //
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
                // throw new Error("Lỗi không copy được database");
            }
        }

        //Phương thức 3: Mở CSDL
        void openDB() {
            db = SQLiteDatabase.openDatabase(dbPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }

        //Phương thức 4: Đóng CSDL
        void closeDB() {
            db.close();
        }

        // Phương thức 5: Lấy về tổng số bản ghi, kiểm tra mã trùng
        public int GetCount(String sql) {
            //B1
            openDB();
            //B2
            Cursor cur = db.rawQuery(sql, null);
            int count = cur.getCount();
            //B3
            closeDB();
            //B4
            return count;
        }

        //Phương thức 6: Trả về đối tượng con trỏ đọc DB
        public Cursor getCursor(String strSelect) {
            //B1
            openDB();
            //B2
            Cursor cursor = db.rawQuery(strSelect, null);
            //B3
            return cursor;
        }

        //Phương thức 7: Thực thi câu lệnh SQL
        public void executeSQL(String strSQL) {
            //B1
            openDB();
            //B2
            db.execSQL(strSQL);
            //B3
            closeDB();
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        public void excuteSQL(String s) {
        }
    }

