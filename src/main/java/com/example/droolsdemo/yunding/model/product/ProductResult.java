package com.example.droolsdemo.yunding.model.product;

import java.util.List;

/**
 * @author shenxuehai
 * @description
 * @Date 2022-08-15-15:50
 **/
public class ProductResult {

    private int total;

    private String per_page;

    private int lastPage;

    private List<Product> data;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ProductResult{" +
                "total=" + total +
                ", per_page='" + per_page + '\'' +
                ", lastPage=" + lastPage +
                ", data=" + data +
                '}';
    }
}
