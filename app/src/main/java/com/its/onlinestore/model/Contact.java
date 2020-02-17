package com.its.onlinestore.model;

public class Contact {
    private String id;
    private String phone;
    private String address;
    private String imageUrl;
    private String store;
    private String user_id;


    public Contact(String id, String phone, String address, String store, String user_id) {
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.store = store;
        this.user_id = user_id;
    }

    public Contact() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
