package com.example.droolsdemo.model;

import java.util.List;
import java.util.Map;

/**
 * @author shenxuehai
 * @description
 * @Date 2022-07-22-17:53
 **/
public class ApiProperty {

    private String type;

    private List<String> required;

    private Map<String, Property> properties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }

    public Map<String, Property> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Property> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "ApiProperty{" +
                "type='" + type + '\'' +
                ", required=" + required +
                ", properties=" + properties +
                '}';
    }
}
