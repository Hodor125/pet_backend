package com.hodor.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Author limingli006
 * @Date 2021/11/6
 */
@Data
public class Pet {

    private Long id;

    private String name;

    private Integer age;

    private Double weight;

    private String sex;

    private String kind;

    private String img;

    private String immunity;

    private String healthy;

    private String character;

    private Integer state;

    private String worm;

    private Date createTime;
}
