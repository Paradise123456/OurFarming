package com.example.ourfarming.dataclass;

import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ListProductMarketPrice {
    String productName;
    String productPrice;
    String productImage;

    public ListProductMarketPrice() {
    }

    public ListProductMarketPrice(String productName, String productPrice, String productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productUnit) {
        this.productImage = productUnit;
    }
}
