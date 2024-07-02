package com.example.store.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.store.Modal.Product;
import com.example.store.R;

import java.util.ArrayList;

public class SanPhamAdapter extends ArrayAdapter<Product> {

    private Context context;
    private ArrayList<Product> items;

    public SanPhamAdapter(Context context, ArrayList<Product> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    public void setItems(ArrayList<Product> items) {
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_san_pham, parent, false);
        }

        Product item = items.get(position);
        if (item != null) {
            ImageView imageView = view.findViewById(R.id.imgProduct);
            TextView textViewSrc = view.findViewById(R.id.tvTenSp);
            TextView textViewCaption = view.findViewById(R.id.tvGiaSp);

            textViewSrc.setText(item.getTenMatHang());
            textViewCaption.setText(item.getGiaBan());

            // Chuyển đổi chuỗi Base64 thành bitmap
            if (item.getImgProduct() != null && !item.getImgProduct().isEmpty()) {
                Bitmap bitmap = decodeBase64(item.getImgProduct());
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.ic_launcher_background); // Ảnh mặc định khi không có URL
            }
        }

        return view;
    }

    // Phương thức chuyển đổi chuỗi Base64 thành bitmap
    private Bitmap decodeBase64(String base64Str) {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
