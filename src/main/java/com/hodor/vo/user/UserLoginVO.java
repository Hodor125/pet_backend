package com.hodor.vo.user;

import jdk.jfr.DataAmount;

/**
 * @Author limingli006
 * @Date 2021/10/23
 */
public class UserLoginVO {

    private Long id;

    private Long power;

    private String name;

    private String token;

    public UserLoginVO() {
    }

    public UserLoginVO(Long id, Long power, String name, String token) {
        this.id = id;
        this.power = power;
        this.name = name;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPower() {
        return power;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
