package com.example.store.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.store.Adapter.SanPhamAdapter;
import com.example.store.DatabaseHandler;
import com.example.store.Modal.Product;
import com.example.store.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentHomeActivity extends Fragment {

    private SanPhamAdapter sanPhamAdapter;
    private ArrayList<Product> sanPhamList;
    private GridView listView;
    private DatabaseReference mDatabase;
    DatabaseHandler db = new DatabaseHandler( getActivity() );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listView = view.findViewById(R.id.listSanPhamManHinhChinh);
        sanPhamList = new ArrayList<>();
//        sanPhamAdapter = new SanPhamAdapter(getContext(), sanPhamList);
        listView.setAdapter(sanPhamAdapter);

        // Initialize Firebase Database

        db.copyDB2SDCard();
        int sbg = db.GetCount( "SELECT * FROM qlySanPham" );
        Toast.makeText( getContext(), "số bản ghi:" + sbg, Toast.LENGTH_SHORT ).show();
        return view;
    }

    private void fetchProductsFromFirebase() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sanPhamList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product sanPham = snapshot.getValue(Product.class);
                    System.out.println("sanPham "+ sanPham);
                    sanPhamList.add(sanPham);
                }
                sanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
}
