package com.example.store.Controller.GioHang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.store.Adapter.GioHang.GioHangAdapter;
import com.example.store.DatabaseHandler;
import com.example.store.Modal.Product;
import com.example.store.R;

import java.util.ArrayList;

public class CardController extends AppCompatActivity {
    private DatabaseHandler db;
    private ArrayList<Product> sanPhamList;
    private GioHangAdapter gioHangAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_controller);
        listView = findViewById(R.id.lvGioHang);
        db = new DatabaseHandler(this);
        db.copyDB2SDCard();

        sanPhamList = db.getCartProducts();
        gioHangAdapter = new GioHangAdapter(this, sanPhamList);
        listView.setAdapter(gioHangAdapter);
    }


}