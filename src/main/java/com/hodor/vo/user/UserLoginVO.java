package com.hodor.vo.user;

import jdk.jfr.DataAmount;

/**
 * @Author limingli006
 * @Date 2021/10/23
 */
public class UserLoginVO {

    private Long id;

    private Long power;

    private String nickName;

    private String token;

    public UserLoginVO() {
    }

    public UserLoginVO(Long id, Long power, String nickName, String token) {
        this.id = id;
        this.power = power;
        this.nickName = nickName;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
