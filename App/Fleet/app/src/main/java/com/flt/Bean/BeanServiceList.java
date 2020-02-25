package com.flt.Bean;

public class BeanServiceList {
    String Id;
    String ParentId;
    String Name;
    //String Class;
    String Image;
    String Price;
    String Discount;
    String FeatureId;
    String UserId;
    String ItemAddedTime;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    /*@Override
    public String getClass() {
        return Class;
    }

    public void setClass(String aClass) {
        Class = aClass;
    }
*/
    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getFeatureId() {
        return FeatureId;
    }

    public void setFeatureId(String featureId) {
        FeatureId = featureId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getItemAddedTime() {
        return ItemAddedTime;
    }

    public void setItemAddedTime(String itemAddedTime) {
        ItemAddedTime = itemAddedTime;
    }
}
