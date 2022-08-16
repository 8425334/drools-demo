package com.example.droolsdemo.yunding.model.product;

/**
 * @author shenxuehai
 * @description
 * @Date 2022-08-15-15:51
 **/
public class Product {

    private long id;

    private String status;

    private String type;

    private String name;

    private String worksimage;

    private String author_id;

    private String author_name;

    private String author_logo;

    private String label;

    private String bktype;

    private String sellprice;

    private int version;

    private int castnum;

    private int version_name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorksimage() {
        return worksimage;
    }

    public void setWorksimage(String worksimage) {
        this.worksimage = worksimage;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_logo() {
        return author_logo;
    }

    public void setAuthor_logo(String author_logo) {
        this.author_logo = author_logo;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBktype() {
        return bktype;
    }

    public void setBktype(String bktype) {
        this.bktype = bktype;
    }

    public String getSellPrice() {
        return sellprice;
    }

    public void setSellPrice(String sellprice) {
        this.sellprice = sellprice;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getCastnum() {
        return castnum;
    }

    public void setCastnum(int castnum) {
        this.castnum = castnum;
    }

    public int getVersion_name() {
        return version_name;
    }

    public void setVersion_name(int version_name) {
        this.version_name = version_name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", worksimage='" + worksimage + '\'' +
                ", author_id='" + author_id + '\'' +
                ", author_name='" + author_name + '\'' +
                ", author_logo='" + author_logo + '\'' +
                ", label='" + label + '\'' +
                ", bktype='" + bktype + '\'' +
                ", sellprice='" + sellprice + '\'' +
                ", version=" + version +
                ", castnum=" + castnum +
                ", version_name=" + version_name +
                '}';
    }
}
