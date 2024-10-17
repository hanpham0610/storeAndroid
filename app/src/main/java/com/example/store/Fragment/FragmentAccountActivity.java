package com.example.store.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.store.Controller.SanPham.QuanLy.QuanLySanPhamController;
import com.example.store.R;

public class FragmentAccountActivity extends Fragment {
    private LinearLayout btnTaiKhoan, btnGianHang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acc, container, false);

        btnTaiKhoan = view.findViewById(R.id.btnTaiKhoan);
        btnGianHang = view.findViewById(R.id.btnGianHang);

        btnTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent intent = new Intent(getActivity(), TaiKhoanActivity.class);
                // startActivity(intent);
            }
        });

        // Set an OnClickListener for btnGianHang (if needed)
        btnGianHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity(), QuanLySanPhamController.class);
              startActivity(intent);            }
        });

        return view;
    }
}