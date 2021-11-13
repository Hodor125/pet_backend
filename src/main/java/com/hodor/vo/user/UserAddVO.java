package com.hodor.vo.user;

import lombok.Data;

/**
 * @Author limingli006
 * @Date 2021/10/23
 */
@Data
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
}
