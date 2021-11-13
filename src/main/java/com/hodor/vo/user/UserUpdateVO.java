package com.hodor.vo.user;

import lombok.Data;

/**
 * @Author limingli006
 * @Date 2021/10/31
 */
@Data
public class UserUpdateVO {

    private Long id;

    private String nick_name;

    public UserUpdateVO() {
    }

    public UserUpdateVO(Long id, String nick_name) {
        this.id = id;
        this.nick_name = nick_name;
    }
}
