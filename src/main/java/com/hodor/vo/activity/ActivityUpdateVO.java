package com.hodor.vo.activity;

import lombok.Data;

/**
 * @Author limingli006
 * @Date 2021/11/7
 */
@Data
public class ActivityUpdateVO {

    private Long id;

    private Integer state;

    public ActivityUpdateVO() {
    }

    public ActivityUpdateVO(Long id, Integer state) {
        this.id = id;
        this.state = state;
    }
}
