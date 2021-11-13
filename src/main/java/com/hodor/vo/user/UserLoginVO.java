package com.hodor.vo.user;

import jdk.jfr.DataAmount;
import lombok.Data;

/**
 * @Author limingli006
 * @Date 2021/10/23
 */
@Data
public class UserLoginVO {

    private Long id;

    private Long power;

    private String name;

    private String token;

    public UserLoginVO() {
    }

    public UserLoginVO(Long id, Long power, String name, String token) {
        this.id = id;
        this.power = power;
        this.name = name;
        this.token = token;
    }
}
