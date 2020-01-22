package com.its.onlinestore.model;

import java.util.List;

public class Category {

    private String id;
    private String name;
    private String icon;
    private List<String> sub_category;


    public Category() {
    }

    public Category(String id, String name, String icon, List<String> sub_category) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.sub_category = sub_category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public List<String> getSub_category() {
        return sub_category;
    }
}
