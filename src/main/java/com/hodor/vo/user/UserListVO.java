package com.hodor.vo.user;

import lombok.Data;

import java.util.Date;

/**
 * @Author limingli006
 * @Date 2021/10/23
 */
@Data
public class UserListVO {

    private Long id;

    private String nick_name;

    private String password;

    private String name;

    private String p_id;

    private String p_img0;

    private String p_img1;

    private String tel;

    private String address;

    private Integer age;

    private String sex;

    private Boolean state;
}
