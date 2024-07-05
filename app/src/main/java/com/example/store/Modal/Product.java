package com.example.store.Modal;


    public class Product {
        public   String productId;
        public String tenMatHang;
        public String giaBan;
        public String soLuong;
        public String donViTinh;
        public String imgProduct; // Thêm trường này để lưu URL ảnh
        public String ghiChu;

        public Product() {
            // Default constructor required for calls to DataSnapshot.getValue(Product.class)
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getTenMatHang() {
            return tenMatHang;
        }

        public void setTenMatHang(String tenMatHang) {
            this.tenMatHang = tenMatHang;
        }

        public String getGiaBan() {
            return giaBan;
        }

        public void setGiaBan(String giaBan) {
            this.giaBan = giaBan;
        }

        public String getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(String soLuong) {
            this.soLuong = soLuong;
        }

        public String getDonViTinh() {
            return donViTinh;
        }

        public void setDonViTinh(String donViTinh) {
            this.donViTinh = donViTinh;
        }

        public String getImgProduct() {
            return imgProduct;
        }

        public void setImgProduct(String imgProduct) {
            this.imgProduct = imgProduct;
        }

        public String getGhiChu() {
            return ghiChu;
        }

        public void setGhiChu(String ghiChu) {
            this.ghiChu = ghiChu;
        }
    }
