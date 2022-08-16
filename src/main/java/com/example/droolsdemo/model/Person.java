package com.example.droolsdemo.model;

import lombok.Data;

import java.util.List;

/**
 * @author shenxuehai
 * @description
 * @Date 2022-07-06-18:25
 **/
@Data
public class Person {

    private String name;

    private String gender;

    private int age;

    private List<String> hobby;
}
