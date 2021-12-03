package com.hodor.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@Data
public class User {

    private Long id;

    private String nickName;

    private String password;

    /**
     * 权限
     * 1为普通管理员，查看0权限的普通用户，2为超级管理员
     */
    private Long power;

    private String email;

    private String tel;

    private Date createTime;

    private String pId;

    /**
     * 身份证反面
     */
    private String pImg0;

    /**
     * 身份证正面
     */
    private String pImg1;

    private String address;

    private Integer age;

    private String sex;

    private String name;

    private Integer state;
}
