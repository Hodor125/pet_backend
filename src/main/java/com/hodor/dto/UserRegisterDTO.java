package com.hodor.dto;

import lombok.Data;

/**
 * @Author limingli006
 * @Date 2021/12/6
 */
@Data
public class UserRegisterDTO {

    private String name;

    private String password;

    private String nick_name;

    private String tel;

    private String p_id;
}
