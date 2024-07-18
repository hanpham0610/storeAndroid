package com.example.store;

import java.text.NumberFormat;
import java.util.Locale;

public class VMCrop {
public  static String  keySanPham = "qly_sanpham";
    public static String getSanPham = "SELECT * FROM qly_sanpham";

    public static final String KeyMeta = "SELECT * FROM  android_metadata";

    private static final String DATABASE_NAME = "storeManager";
    private static final String TABLE_PRODUCTS = "products";
    private static  String TABLE_CART = "qly_card";

    String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + "productId TEXT PRIMARY KEY,"
            + "tenMatHang TEXT,"
            + "giaBan TEXT,"
            + "soLuong TEXT,"
            + "donViTinh TEXT,"
            + "imgProduct TEXT,"
            + "ghiChu TEXT" + ")";

    String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
            + "productId TEXT PRIMARY KEY,"
            + "tenMatHang TEXT,"
            + "giaBan TEXT,"
            + "soLuong TEXT,"
            + "donViTinh TEXT,"
            + "imgProduct TEXT,"
            + "ghiChu TEXT" + ")";
    public static String setFormatMoney(int amount){
        Locale vietnamLocale = new Locale("vi", "VN");
        NumberFormat vietnamFormat = NumberFormat.getCurrencyInstance(vietnamLocale);
        String vietnamFormattedAmount = vietnamFormat.format(amount);
        return vietnamFormattedAmount;
//        System.out.println("Vietnam locale: " + vietnamFormattedAmount);
    }
}
