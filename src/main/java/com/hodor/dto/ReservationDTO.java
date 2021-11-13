package com.hodor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 预约信息
 * @Author limingli006
 * @Date 2021/11/7
 */
@Data
public class ReservationDTO {

    private Long id;

    /**
     * 宠物id
     */
    private Long p_id;

    /**
     * 用户id
     */
    private Long u_id;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date time;

    /**
     * false 不通过 true已通过 null待审核
     * 0           1         2
     */
    private Boolean state;

    /**
     * false 不通过 true已通过 null待审核
     * 0           1         2
     */
    private Boolean adopt;
}
