package com.example.ourfarming.dataclass;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DetailProduct implements Serializable {
    public static String Title;
    public static String ProductId;
    public static String SellerId;
    public static String SellerPhone;
    public static String Quantity;
    public static String Description;
    public static String Amount;
    public static String MFD;
    public static String EXP;
    public static String Location;
    public static String DeliverableLocation;
    public static String FirstImageUrl;
    public static String SecondImageUrl;
    public static String ThirdImageUrl;
    public static String FourthImageUrl;
    public static ArrayList<String> images;

    public DetailProduct() {
        // Default constructor required
    }

    public DetailProduct(String ProductId, String SellerId, String Title, String Description, String Amount, String Quantity, String MFD, String EXP, String Location, String DeliverableLocation,String SellerPhone,String FirstImageUrl, String SecondImageUrl, String ThirdImageUrl, String FourthImageUrl) {
        this.ProductId = ProductId;
        this.SellerId = SellerId;
        this.SellerPhone=SellerPhone;
        this.Title = Title;
        this.Quantity = Quantity;
        this.Description = Description;
        this.Amount = Amount;
        this.MFD = MFD;
        this.EXP = EXP;
        this.Location=Location;
        this.DeliverableLocation=DeliverableLocation;
        this.FirstImageUrl = FirstImageUrl;
        this.SecondImageUrl = SecondImageUrl;
        this.ThirdImageUrl = ThirdImageUrl;
        this.FourthImageUrl = FourthImageUrl;
    }

    public static String getSellerPhone() {
        return SellerPhone;
    }

    public static void setSellerPhone(String sellerPhone) {
        SellerPhone = sellerPhone;
    }

    public static String getDeliverableLocation() {
        return DeliverableLocation;
    }

    public static void setDeliverableLocation(String deliverableLocation) {
        DeliverableLocation = deliverableLocation;
    }

    public static String getTitle() {
        return Title;
    }

    public static void setTitle(String title) {
        Title = title;
    }

    public static String getProductId() {
        return ProductId;
    }

    public static void setProductId(String productId) {
        ProductId = productId;
    }

    public static String getSellerId() {
        return SellerId;
    }

    public static void setSellerId(String sellerId) {
        SellerId = sellerId;
    }

    public static String getQuantity() {
        return Quantity;
    }

    public static void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public static String getDescription() {
        return Description;
    }

    public static void setDescription(String description) {
        Description = description;
    }

    public static String getAmount() {
        return Amount;
    }

    public static void setAmount(String amount) {
        Amount = amount;
    }

    public static String getMFD() {
        return MFD;
    }

    public static void setMFD(String MFD) {
        DetailProduct.MFD = MFD;
    }

    public static String getEXP() {
        return EXP;
    }

    public static void setEXP(String EXP) {
        DetailProduct.EXP = EXP;
    }

    public static String getLocation() {
        return Location;
    }

    public static void setLocation(String location) {
        Location = location;
    }

    public static String getFirstImageUrl() {
        return FirstImageUrl;
    }

    public static void setFirstImageUrl(String firstImageUrl) {
        FirstImageUrl = firstImageUrl;
    }

    public static String getSecondImageUrl() {
        return SecondImageUrl;
    }

    public static void setSecondImageUrl(String secondImageUrl) {
        SecondImageUrl = secondImageUrl;
    }

    public static String getThirdImageUrl() {
        return ThirdImageUrl;
    }

    public static void setThirdImageUrl(String thirdImageUrl) {
        ThirdImageUrl = thirdImageUrl;
    }

    public static String getFourthImageUrl() {
        return FourthImageUrl;
    }

    public static void setFourthImageUrl(String fourthImageUrl) {
        FourthImageUrl = fourthImageUrl;
    }

    public static ArrayList<String> getImages() {
        return images;
    }

    public static void setImages(ArrayList<String> images) {
        DetailProduct.images = images;
    }
}