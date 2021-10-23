package com.hodor.vo.user;

/**
 * @Author limingli006
 * @Date 2021/10/23
 */
public class UserAddVO {

    private Long id;

    private String nick_name;

    public UserAddVO() {
    }

    public UserAddVO(Long id, String nick_name) {
        this.id = id;
        this.nick_name = nick_name;
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
}