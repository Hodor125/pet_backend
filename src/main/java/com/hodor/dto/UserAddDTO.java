package com.hodor.dto;

import java.util.Date;

/**
 * @Author limingli006
 * @Date 2021/10/23
 */
public class UserAddDTO {

    private String name;

    private String password;

    private String nick_name;

    private String tel;

    private String p_id;

    private String p_image;

    private String address;

    public UserAddDTO() {
    }

    public UserAddDTO(String name, String password, String nick_name, String tel, String p_id, String p_image, String address) {
        this.name = name;
        this.password = password;
        this.nick_name = nick_name;
        this.tel = tel;
        this.p_id = p_id;
        this.p_image = p_image;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_image() {
        return p_image;
    }

    public void setP_image(String p_image) {
        this.p_image = p_image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
