package com.hodor.dto;

import lombok.Data;

import java.util.Date;

/**
 * @Author limingli006
 * @Date 2021/10/23
 */
@Data
public class UserAddDTO {

    private String name;

    private String password;

    private String nick_name;

    private String tel;

    private String p_id;

    private String p_img;

    private String address;

    public UserAddDTO() {
    }
}
