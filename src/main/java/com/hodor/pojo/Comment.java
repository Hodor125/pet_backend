package com.hodor.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author limingli006
 * @Date 2021/11/28
 */
@Data
public class Comment {

    private Long id;

    private String content;

    private Long u_id;

    private Long parentId;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createTime;
}
