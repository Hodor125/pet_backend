package com.hodor.vo.user;

import lombok.Data;

/**
 * @Author limingli006
 * @Date 2021/11/13
 */
@Data
public class UserRegisterVO {

        private Long id;

        /**
         * 权限
         * 1为普通管理员，查看0权限的普通用户，2为超级管理员
         */
        private Long power;

        public UserRegisterVO(Long id, Long power) {
                this.id = id;
                this.power = power;
        }
}
