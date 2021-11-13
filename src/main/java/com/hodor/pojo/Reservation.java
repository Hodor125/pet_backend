package com.hodor.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 预约信息
 * @Author limingli006
 * @Date 2021/11/7
 */
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
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date time;

    /**
     * false 不通过 true已通过 null待审核
     * 0           1         2
     */
    private Integer state;

    /**
     * false 不通过 true已通过 null待审核
     * 0           1         2
     */
    private Integer adopt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public Long getU_id() {
        return u_id;
    }

    public void setU_id(Long u_id) {
        this.u_id = u_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getAdopt() {
        return adopt;
    }

    public void setAdopt(Integer adopt) {
        this.adopt = adopt;
    }
}
