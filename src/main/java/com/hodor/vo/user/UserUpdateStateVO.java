package com.hodor.vo.user;

/**
 * @Author limingli006
 * @Date 2021/10/24
 */
public class UserUpdateStateVO {

    private Long id;

    private Integer state;

    public Long getId() {
        return id;
    }

    public UserUpdateStateVO setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getState() {
        return state;
    }

    public UserUpdateStateVO setState(Integer state) {
        this.state = state;
        return this;
    }
}
