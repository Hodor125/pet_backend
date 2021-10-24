package com.hodor.vo.user;

import java.util.Date;

/**
 * @Author limingli006
 * @Date 2021/10/23
 */
public class UserListVO {

    private Long id;

    private String nick_name;

    private String psw;

    private String name;

    private String p_id;

    private String p_image;

    private String tel;

    private String address;

    private Integer age;

    private String sex;

    private Boolean state;

    public UserListVO() {
    }

    public UserListVO(Long id, String nick_name, String psw, String name, String p_id, String p_image,
                      String tel, String address, Integer age, String sex) {
        this.id = id;
        this.nick_name = nick_name;
        this.psw = psw;
        this.name = name;
        this.p_id = p_id;
        this.p_image = p_image;
        this.tel = tel;
        this.address = address;
        this.age = age;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
