package com.example.store.Controller.SanPham.QuanLy;

import static com.example.store.VMCrop.getSanPham;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.store.Adapter.SanPham.QuanLySanPhamAdapter.QuanLySanPhamAdapter;
import com.example.store.Adapter.SanPham.SanPhamListAdapter;
import com.example.store.Controller.SanPham.CreateProductController;
import com.example.store.DatabaseHandler;
import com.example.store.Modal.Product;
import com.example.store.R;

import java.util.ArrayList;

public class QuanLySanPhamController extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Product> sanPhamList;
    private QuanLySanPhamAdapter quanLySanPhamAdapter;
    private DatabaseHandler db;
    private ImageButton btnThemSanPham;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //back tren ActionBar

        setContentView(R.layout.activity_quan_ly_san_pham_controller);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setDisplayShowHomeEnabled( true );
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarQlySanPham);
        //Toolbar will now take on default Action Bar characteristics
        setActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        this.setTitle("Quản lý");
        btnThemSanPham = findViewById(R.id.btnThemSanPham);
        listView = findViewById(R.id.lvQuanLyGianHang);
        sanPhamList = new ArrayList<>();
        db = new DatabaseHandler(this);
        db.copyDB2SDCard();
        db2ListViewSanPham();
        ChuyenTrang();
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Intent intent = new Intent( QuanLySanPhamController.this, UpdateProductController.class );
                intent.putExtra( "idProduct", sanPhamList.get( i ).getProductId().toString() );
                startActivity( intent );

            }
        } );
    }

    private void ChuyenTrang() {
        btnThemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLySanPhamController.this, CreateProductController.class);
                startActivity(intent);
            }
        });
    }

    public void db2ListViewSanPham() {
        sanPhamList = new ArrayList<>();
        Product row;
        Cursor cursor = db.getCursor(getSanPham);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            row = new Product();
            row.productId = cursor.getString(0);
            row.tenMatHang = cursor.getString(1);
            row.giaBan = String.valueOf(cursor.getInt(2));
            row.soLuong = String.valueOf(cursor.getInt(3));
            row.donViTinh = cursor.getString(4);
            row.imgProduct = cursor.getString(5);
            row.ghiChu = cursor.getString(6);
            sanPhamList.add(row);
            cursor.moveToNext();
        }
        quanLySanPhamAdapter = new QuanLySanPhamAdapter(QuanLySanPhamController.this, sanPhamList);
        listView.setAdapter(quanLySanPhamAdapter);
        quanLySanPhamAdapter.notifyDataSetChanged();
        cursor.close();
    }
}