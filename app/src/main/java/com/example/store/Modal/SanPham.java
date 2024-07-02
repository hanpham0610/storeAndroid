package com.example.store.Modal;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;

public class SanPham implements Serializable, Parcelable {

    public int giaThanh;
    public int sl;
    public String maSp;
    public String tenSp;
    public String DD;
    public int slBanRa;


    protected SanPham(Parcel in) {
        giaThanh = in.readInt();
        sl = in.readInt();
        maSp = in.readString();
        tenSp = in.readString();
        DD = in.readString();
        slBanRa = in.readInt();
    }

    public static final Creator<SanPham> CREATOR = new Creator<SanPham>() {
        @Override
        public SanPham createFromParcel(Parcel in) {
            return new SanPham( in );
        }

        @Override
        public SanPham[] newArray(int size) {
            return new SanPham[size];
        }
    };

    public SanPham(String masp, int sl, int gia) {

    }

    public SanPham(int giaThanh, int sl, String maSp, String tenSp, String DD, int slBanRa, DecimalFormat formatter) {
        this.giaThanh = giaThanh;
        this.sl = sl;
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.DD = DD;
        this.slBanRa = slBanRa;
        this.formatter = formatter;
    }

    public SanPham(String maSp, String tenSp) {
    }


    public SanPham(String productId, String tenMatHang, String maVach, String giaBan, String soLuong, String donViTinh) {

    }

    public int getGiaThanh() {
        return giaThanh;
    }

    public void setGiaThanh(int giaThanh) {
        this.giaThanh = giaThanh;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getDD() {
        return DD;
    }

    public void setDD(String DD) {
        this.DD = DD;
    }

    public int getSlBanRa() {
        return slBanRa;
    }

    public void setSlBanRa(int slBanRa) {
        this.slBanRa = slBanRa;
    }

    public DecimalFormat getFormatter() {
        return formatter;
    }

    public void setFormatter(DecimalFormat formatter) {
        this.formatter = formatter;
    }

    DecimalFormat formatter = new DecimalFormat( "###,###,###" );
    @Override
    public String toString() {
        return "Mã cây: " + DD + "\nTên cây: " + tenSp + "\nGía bán: " + formatter.format(giaThanh) + "\nSố lượng:" + sl;
    }

    public String getJsonObject() {

        JSONObject giohang = new JSONObject();//trao đổi dữ liệu
        try {
            giohang.put( "Masp", maSp );
            giohang.put( "Tensp", tenSp );
            giohang.put( "GiaBan", giaThanh );
            giohang.put( "Giatri", sl );
        } catch (Exception e) {
        }
        return giohang.toString();
    }


    public SanPham(String masp, String tensp, String DD, Integer sl, Integer gt) {
        this.maSp = masp;
        this.tenSp = tensp;
        this.DD = DD;
        this.sl = sl;
        this.giaThanh = gt;

    }

    public SanPham() {
    }

    public void setActive(boolean b) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt( giaThanh );
        parcel.writeInt( sl );
        parcel.writeString( maSp );
        parcel.writeString( tenSp );
        parcel.writeString( DD );
        parcel.writeInt( slBanRa );
    }
}

