package com.example.droolsdemo.model;

/**
 * @author shenxuehai
 * @description
 * @Date 2022-07-22-18:36
 **/
public class Field {

    private String fieldName;

    private String fieldCode;

    private String typeOfData;

    private Integer bizLine;

    private String development;

    private String remark;

    private String fieldTypeId;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getTypeOfData() {
        return typeOfData;
    }

    public void setTypeOfData(String typeOfData) {
        this.typeOfData = typeOfData;
    }

    public Integer getBizLine() {
        return bizLine;
    }

    public void setBizLine(Integer bizLine) {
        this.bizLine = bizLine;
    }

    public String getDevelopment() {
        return development;
    }

    public void setDevelopment(String development) {
        this.development = development;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFieldTypeId() {
        return fieldTypeId;
    }

    public void setFieldTypeId(String fieldTypeId) {
        this.fieldTypeId = fieldTypeId;
    }

    @Override
    public String toString() {
        return "Field{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldCode='" + fieldCode + '\'' +
                ", typeOfData='" + typeOfData + '\'' +
                ", bizLine=" + bizLine +
                ", development='" + development + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
