package com.example.store.Fragment;

import static com.example.store.ListDBSQL.getSanPham;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.store.Adapter.SanPham.SanPhamAdapter;
import com.example.store.Adapter.SanPham.SanPhamListAdapter;
import com.example.store.DatabaseHandler;
import com.example.store.Modal.Product;
import com.example.store.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FragmentHomeActivity extends Fragment {

    private SanPhamAdapter sanPhamAdapter;
    private SanPhamListAdapter sanPhamListAdapter;
    private ArrayList<Product> sanPhamList;
    private GridView gridView;
    private ListView listView;
    private DatabaseReference mDatabase;
    DatabaseHandler db;
    private boolean isListIcon = true;
    private ImageButton btnHoanDoi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        gridView = view.findViewById(R.id.gridSanPhamManHinhChinh);
        listView = view.findViewById(R.id.listSanPhamManHinhChinh);
         btnHoanDoi = view.findViewById(R.id.btnHoanDoi);
        sanPhamList = new ArrayList<>();
//        sanPhamAdapter = new SanPhamAdapter(getContext(), sanPhamList);
        gridView.setAdapter(sanPhamAdapter);

        db = new DatabaseHandler(requireActivity());

        // Copy database to SD Card
        db.copyDB2SDCard();
        setBtnHoanDoi();
        db2ListViewSanPham();
        // Get the count of records
//        int sbg = db.GetCount("SELECT * FROM qly_sanpham");
//        Toast.makeText(getContext(), "số bản ghi:" + sbg, Toast.LENGTH_SHORT).show();
        return view;
    }

    private void setBtnHoanDoi() {
        btnHoanDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isListIcon) {
                    btnHoanDoi.setImageResource(R.drawable.baseline_grid_view_24);
                    db2GridViewSanPham();
                    gridView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                } else {
                    btnHoanDoi.setImageResource(R.drawable.baseline_list_alt_24);
                    db2ListViewSanPham();
                    gridView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
                isListIcon = !isListIcon;
            }
        });
    }


    public void db2GridViewSanPham() {
        sanPhamList = new ArrayList<Product>();
        Product row;
        Cursor cursor = db.getCursor(getSanPham);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            row = new Product();
            row.productId = cursor.getString( 0 );
            row.tenMatHang = cursor.getString( 1 );
            row.giaBan = String.valueOf(cursor.getInt( 2 ));
            row.soLuong = String.valueOf(cursor.getInt( 3 ));
            row.donViTinh = cursor.getString( 4 );
            row.imgProduct = cursor.getString( 5 );
            row.ghiChu = cursor.getString( 6 );
            System.out.println("row.imgProduct "+ row.imgProduct);
            sanPhamList.add( row );
            cursor.moveToNext();
        }
        sanPhamAdapter = new SanPhamAdapter(getContext(), sanPhamList);
        gridView.setAdapter( sanPhamAdapter );
        sanPhamAdapter.notifyDataSetChanged();
        cursor.close();
    }

    public void db2ListViewSanPham() {
        sanPhamList = new ArrayList<Product>();
        Product row;
        Cursor cursor = db.getCursor( getSanPham);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            row = new Product();
            row.productId = cursor.getString( 0 );
            row.tenMatHang = cursor.getString( 1 );
            row.giaBan = String.valueOf(cursor.getInt( 2 ));
            row.soLuong = String.valueOf(cursor.getInt( 3 ));
            row.donViTinh = cursor.getString( 4 );
            row.imgProduct = cursor.getString( 5 );
            row.ghiChu = cursor.getString( 6 );
            System.out.println("row.imgProduct "+ row.imgProduct);
            sanPhamList.add( row );
            cursor.moveToNext();
        }
        sanPhamListAdapter = new SanPhamListAdapter(getContext(), sanPhamList);
        listView.setAdapter( sanPhamListAdapter );
        sanPhamListAdapter.notifyDataSetChanged();
        cursor.close();
    }
}
