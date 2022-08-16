package com.example.droolsdemo.yunding.model.product;

/**
 * @author shenxuehai
 * @description
 * @Date 2022-08-15-15:43
 **/
public class ProductRequest {

    private int page = 1;

    private int limit = 500;

    private String type = "1";

    private String brand_id = "";

    private String sort_data = "timedesc";

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getSort_data() {
        return sort_data;
    }

    public void setSort_data(String sort_data) {
        this.sort_data = sort_data;
    }
}
