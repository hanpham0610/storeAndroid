package com.example.store.Fragment;

import static com.example.store.VMCrop.KeyMeta;
import static com.example.store.VMCrop.getSanPham;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.store.Adapter.SanPham.SanPhamAdapter;
import com.example.store.Adapter.SanPham.SanPhamListAdapter;
import com.example.store.Controller.GioHang.CardController;
import com.example.store.DatabaseHandler;
import com.example.store.Modal.Product;
import com.example.store.R;

import java.util.ArrayList;

public class FragmentHomeActivity extends Fragment {

    private SanPhamAdapter sanPhamAdapter;
    private SanPhamListAdapter sanPhamListAdapter;
    private ArrayList<Product> sanPhamList;
    private GridView gridView;
    private ListView listView;
    private DatabaseHandler db;
    private boolean isListIcon = true;
    private ImageButton btnHoanDoi, btnCard;
    private TextView cartItemCount;
    private SearchView searchView;
    private Button btnCart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        gridView = view.findViewById(R.id.gridSanPhamManHinhChinh);
        listView = view.findViewById(R.id.listSanPhamManHinhChinh);
        btnHoanDoi = view.findViewById(R.id.btnHoanDoi);
        btnCard = view.findViewById(R.id.btnCard);
        cartItemCount = view.findViewById(R.id.cartItemCount);
        searchView = view.findViewById(R.id.idSearchProduct);
        btnCard = view.findViewById(R.id.btnCard);

        sanPhamList = new ArrayList<>();
        db = new DatabaseHandler(requireActivity());
        db.copyDB2SDCard();

        int sbg = db.GetCount("select * from qly_sanpham");
         Toast.makeText( getActivity(), "số bản ghi:" + sbg, Toast.LENGTH_SHORT ).show();
        setBtnHoanDoi();
        db2ListViewSanPham();
        setCard();
        updateCartItemCount();

        // Đặt on item click listeners cho gridView và listView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = sanPhamList.get(position);
                addToLocalStore(selectedProduct);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = sanPhamList.get(position);
                addToLocalStore(selectedProduct);
            }
        });

        return view;
    }

    private void setCard() {
        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CardController.class);
                startActivity(intent);
            }
        });


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
        sanPhamAdapter = new SanPhamAdapter(getContext(), sanPhamList);
        gridView.setAdapter(sanPhamAdapter);
        sanPhamAdapter.notifyDataSetChanged();
        cursor.close();
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
        sanPhamListAdapter = new SanPhamListAdapter(getContext(), sanPhamList);
        listView.setAdapter(sanPhamListAdapter);
        sanPhamListAdapter.notifyDataSetChanged();
        cursor.close();
    }

    private void addToLocalStore(Product product) {
        // Lưu sản phẩm vào bảng qly_card trong SQLite
        db.addProductToCart(product);
        db2ListViewSanPham();
//        db2GridViewSanPham();
        // Cập nhật số lượng sản phẩm trong giỏ hàng


        // Hiển thị thông báo
        Toast.makeText(getContext(), "Đã thêm " + product.tenMatHang + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
    }

    private void updateCartItemCount() {
        int sbg = db.GetCount("SELECT * FROM qly_card");
//        Toast.makeText( this, "số bản ghi:" + sbg, Toast.LENGTH_SHORT ).show();
        System.out.println("sbg " + sbg);

        if (sbg > 0) {
            cartItemCount.setVisibility(View.VISIBLE);
            cartItemCount.setText(String.valueOf(sbg));
        } else {
            cartItemCount.setVisibility(View.GONE);
        }
        cartItemCount.setText(String.valueOf(sbg));
//        SQLiteDatabase database = db.getReadableDatabase();
//        Cursor cursor = database.rawQuery("SELECT SUM(soLuong) FROM qly_card", null);
//        if (cursor.moveToFirst()) {
//            int itemCount = cursor.getInt(0);
//            if (itemCount > 0) {
//                cartItemCount.setVisibility(View.VISIBLE);
//                cartItemCount.setText(String.valueOf(itemCount));
//            } else {
//                cartItemCount.setVisibility(View.GONE);
//            }
//        }
//        cursor.close();
    }
}
