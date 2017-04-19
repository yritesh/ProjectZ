package com.example.friends.projectz;

import android.graphics.Bitmap;


public class ProductDetailPojo {

    private String productName;
    private String productAdress;
    private Bitmap productImage;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAdress() {
        return productAdress;
    }

    public void setProductAdress(String productAdress) {
        this.productAdress = productAdress;
    }

    public Bitmap getProductImage() {
        return productImage;
    }

    public void setProductImage(Bitmap productImage) {
        this.productImage = productImage;
    }
}
