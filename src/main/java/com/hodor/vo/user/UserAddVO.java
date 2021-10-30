package com.hodor.vo.user;

/**
 * @Author limingli006
 * @Date 2021/10/23
 */
public class UserAddVO {

    private Long id;

    private String nick_name;

    private Integer age;

    private String sex;

    public UserAddVO() {
    }

    public UserAddVO(Long id, String nick_name, Integer age, String sex) {
        this.id = id;
        this.nick_name = nick_name;
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
}
