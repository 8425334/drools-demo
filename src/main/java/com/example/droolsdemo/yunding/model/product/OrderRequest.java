package com.example.droolsdemo.yunding.model.product;

/**
 * @author shenxuehai
 * @description
 * @Date 2022-08-15-16:43
 **/
public class OrderRequest {

    private String id;

    private int type_status = 1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType_status() {
        return type_status;
    }

    public void setType_status(int type_status) {
        this.type_status = type_status;
    }
}
