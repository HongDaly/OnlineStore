package com.its.onlinestore.model;

import java.io.Serializable;

public class ProductImage implements Serializable {
    private String product_id;
    private String image_url;

    public ProductImage(String product_id, String image_url) {
        this.product_id = product_id;
        this.image_url = image_url;
    }

    public ProductImage() {
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
