package com.its.onlinestore.model;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private String id;
    private String title;
    private Float price;
    private String description;
    private List<String> tags;
    private List<String> categories;
    private List<String> feature_images = new ArrayList<>();
    private Long created_at;
    private Float discount;
    private int hit;
    private String userId;


    public Product(String title, Float price, String description, List<String> tags, List<String> categories, List<String> feature_images, Float discount,String userId) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.tags = tags;
        this.categories = categories;
        this.feature_images = feature_images;
        this.discount = discount;
        this.userId = userId;
    }

    public Product() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setFeature_images(List<String> feature_images) {
        this.feature_images = feature_images;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<String> getFeature_images() {
        return feature_images;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public Float getDiscount() {
        return discount;
    }

    public int getHit() {
        return hit;
    }


    public void  clearFeatureImage(){
        this.feature_images.clear();
    }
    public void addImageUrl(String url){
        feature_images.add(url);
    }
}
