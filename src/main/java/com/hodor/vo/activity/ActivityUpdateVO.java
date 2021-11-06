package com.hodor.vo.activity;

/**
 * @Author limingli006
 * @Date 2021/11/7
 */
public class ActivityUpdateVO {

    private Long id;

    private Integer state;

    public ActivityUpdateVO() {
    }

    public ActivityUpdateVO(Long id, Integer state) {
        this.id = id;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
