package com.hodor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author limingli006
 * @Date 2021/11/27
 */
@Data
public class ChangePwdDTO {

    private Long id;

    private String password;

    private String newpassword;

    private String confirmpassword;
}
