package com.hodor.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 预约信息
 * @Author limingli006
 * @Date 2021/11/7
 */
@Data
public class Reservation {

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
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date time;

    /**
     * false 不通过 true已通过 null待审核
     * 0           1         2
     */
    private Integer state;

    /**
     * 取消预约 false 不通过 true已通过 null待审核
     *   -1        0           1         2
     */
    private Integer adopt;
}
