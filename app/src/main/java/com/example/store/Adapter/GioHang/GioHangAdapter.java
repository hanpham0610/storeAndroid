package com.example.store.Adapter.GioHang;

import static com.example.store.VMCrop.getSanPham;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.store.DatabaseHandler;
import com.example.store.Modal.Product;
import com.example.store.R;
import com.example.store.VMCrop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GioHangAdapter extends ArrayAdapter<Product> {

    private Context context;
    private ArrayList<Product> items;

    public GioHangAdapter(Context context, ArrayList<Product> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    public void setItems(ArrayList<Product> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_gio_hang, parent, false);
        }

        Product item = items.get(position);
        if (item != null) {
            ImageView imageView = view.findViewById(R.id.imvHinhGioHang);
            TextView textViewSrc = view.findViewById(R.id.tvTenSPGioHang);
            TextView textViewCaption = view.findViewById(R.id.tvGiaSpGioHang);
            TextView textViewSl = view.findViewById(R.id.tvGioHangSoLuong);
            TextView btnTru = view.findViewById(R.id.btnGioHangTru);
            TextView btnCong = view.findViewById(R.id.btnGioHangCong);

            textViewSrc.setText(item.getTenMatHang());
            textViewCaption.setText(VMCrop.setFormatMoney(Integer.parseInt(item.getGiaBan())));
            textViewSl.setText(item.getSoLuongHienTai());

            if (item.getImgProduct().startsWith("https://")) {
                Picasso.get().load(item.getImgProduct()).into(imageView);
            } else if (item.getImgProduct() != null && !item.getImgProduct().isEmpty()) {
                Bitmap bitmap = decodeBase64(item.getImgProduct());
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.ic_launcher_background);
            }

            // Xử lý sự kiện click cho btnTru
            btnTru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int soLuong = Integer.parseInt(item.getSoLuongHienTai());
                    if (soLuong > 1) {
                        soLuong--;
                        item.setSoLuongHienTai(String.valueOf(soLuong));
                        textViewSl.setText(String.valueOf(soLuong));
                        // Cập nhật giỏ hàng trong cơ sở dữ liệu
                        updateCartItem(item);
                    }
                }
            });

            // Xử lý sự kiện click cho btnCong
            btnCong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int soLuong = Integer.parseInt(item.getSoLuongHienTai());
                    soLuong++;
                    item.setSoLuongHienTai(String.valueOf(soLuong));
                    textViewSl.setText(String.valueOf(soLuong));
                    // Cập nhật giỏ hàng trong cơ sở dữ liệu
                    updateCartItem(item);
                }
            });
        }

        return view;
    }

    // Phương thức chuyển đổi chuỗi Base64 thành bitmap
    private Bitmap decodeBase64(String base64Str) {
        byte[] decodedBytes = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    // Phương thức cập nhật giỏ hàng trong cơ sở dữ liệu
    private void updateCartItem(Product product) {
        DatabaseHandler dbHelper = new DatabaseHandler(context);
        dbHelper.updateProductQuantity(product.getProductId(), product.getSoLuongHienTai());
        Cursor cursor = dbHelper.getCursor(getSanPham);
        cursor.moveToFirst();

    }
}
