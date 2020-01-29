package com.its.onlinestore.model;

import java.util.List;

public class Product {

    private String id;
    private String title;
    private Float price;
    private String description;
    private List<String> tags;
    private List<String> categories;
    private List<String> feature_images;
    private Long created_at;
    private Float discount;
    private int hit;


    public Product(String title, Float price, String description, List<String> tags, List<String> categories, List<String> feature_images, Float discount) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.tags = tags;
        this.categories = categories;
        this.feature_images = feature_images;
        this.discount = discount;
    }

    public Product() {
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
}
