package com.hodor.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author limingli006
 * @Date 2021/11/6
 */
@Data
public class Activity {

    private Long id;

    private String title;

    private String img;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date starttime;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date endtime;

    private String content;

    private Integer num;

    /**
     * 0代表活动已结束，1活动未结束， 2表示进行中
     */
    private Integer state;

    private Integer signed;

    private Long userId;

    private List<ActivityPerson> person;
}
