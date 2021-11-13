package com.hodor.vo.activity;

import lombok.Data;

/**
 * @Author limingli006
 * @Date 2021/11/7
 */
@Data
public class ActivityVO {

    private Long id;

    private String Content;

    private String img;

    public ActivityVO() {
    }

    public ActivityVO(Long id, String content, String img) {
        this.id = id;
        Content = content;
        this.img = img;
    }
}
