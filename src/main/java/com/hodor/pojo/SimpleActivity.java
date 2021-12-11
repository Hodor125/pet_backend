package com.hodor.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author limingli006
 * @Date 2021/11/28
 */
@Data
public class SimpleActivity {
    private Long id;

    private String content;

    private Date starttime;

    private Date endtime;

    /**
     * 0代表活动已结束，1活动未结束， 2表示进行中
     */
    private Integer state;
}
