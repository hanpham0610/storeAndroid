package com.example.store.Modal;


    public class Product {
        private  String productId;
        private String tenMatHang;
        private String maVach;
        private String giaBan;
        private String soLuong;
        private String donViTinh;
        private boolean apDungThue;
        private String imgProduct; // Thêm trường này để lưu URL ảnh

        public Product() {
            // Default constructor required for calls to DataSnapshot.getValue(Product.class)
        }

        public Product(String productId,String tenMatHang, String giaBan, String soLuong, String donViTinh, String imgProduct) {
          this.productId = productId;
            this.tenMatHang = tenMatHang;
            this.giaBan = giaBan;
            this.soLuong = soLuong;
            this.donViTinh = donViTinh;
            this.imgProduct = imgProduct;
        }

        // Getter và Setter cho imgProduct
        public String getImgProduct() {
            return imgProduct;
        }

        public void setImgProduct(String imgProduct) {
            this.imgProduct = imgProduct;
        }

        // Getter và Setter cho các trường khác
        public String getTenMatHang() {
            return tenMatHang;
        }

        public void setTenMatHang(String tenMatHang) {
            this.tenMatHang = tenMatHang;
        }

        public String getMaVach() {
            return maVach;
        }

        public void setMaVach(String maVach) {
            this.maVach = maVach;
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

        public boolean isApDungThue() {
            return apDungThue;
        }

        public void setApDungThue(boolean apDungThue) {
            this.apDungThue = apDungThue;
        }

}
