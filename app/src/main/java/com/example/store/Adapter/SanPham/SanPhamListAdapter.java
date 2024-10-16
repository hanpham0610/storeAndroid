package com.example.store.Adapter.SanPham;

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
import com.example.store.VMCrop;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SanPhamListAdapter extends ArrayAdapter<Product> {

    private Context context;
    private ArrayList<Product> items;

    public SanPhamListAdapter(Context context, ArrayList<Product> items) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_listsanpham, parent, false);
        }

        Product item = items.get(position);
        if (item != null) {
            ImageView imageView = view.findViewById(R.id.imgProduct);
            TextView textViewSrc = view.findViewById(R.id.tvTenSp);
            TextView textViewCaption = view.findViewById(R.id.tvGiaSp);
            TextView textViewSl = view.findViewById(R.id.tvSoLuong);

            textViewSrc.setText(item.getTenMatHang());
            textViewCaption.setText("Số lượng : "+ item.getSoLuong()+ " "+ item.getDonViTinh());
            textViewSl.setText("Giá bán: "+ VMCrop.setFormatMoney(Integer.parseInt(item.getGiaBan())));
            System.out.println("item img"+ item.getImgProduct());
//            if(item.getImgProduct().startsWith("https://")){
//                Picasso.get().load(item.getImgProduct()).into(imageView);
//            }else
//            // Chuyển đổi chuỗi Base64 thành bitmap
//            if (item.getImgProduct() != null && !item.getImgProduct().isEmpty()) {
//                Bitmap bitmap = decodeBase64(item.getImgProduct());
//                imageView.setImageBitmap(bitmap);
//            } else {
//                imageView.setImageResource(R.drawable.ic_launcher_background); // Ảnh mặc định khi không có URL
//            }
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            System.out.println("storageRef "+ storageRef);
            StorageReference imageRef = storageRef.child("images/" + item.getImgProduct() + ".jpg");
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                Picasso.get().load(uri).into(imageView);
            }).addOnFailureListener(e -> {
                System.out.println("Failed to load image URL");
            });
        }

        return view;
    }

    // Phương thức chuyển đổi chuỗi Base64 thành bitmap
    private Bitmap decodeBase64(String base64Str) {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
