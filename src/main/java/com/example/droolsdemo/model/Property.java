package com.example.droolsdemo.model;

import java.util.Map;

/**
 * @author shenxuehai
 * @description
 * @Date 2022-07-22-17:54
 **/
public class Property {

    private String type;

    private String description;

    private ApiProperty items;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApiProperty getItems() {
        return items;
    }

    public void setItems(ApiProperty items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Property{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", items=" + items +
                '}';
    }
}
