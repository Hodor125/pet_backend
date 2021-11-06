package com.hodor.vo.pet;

/**
 * @Author limingli006
 * @Date 2021/11/6
 */
public class PetUpdateVO {

    private Long id;

    private Integer state;

    public PetUpdateVO() {
    }

    public PetUpdateVO(Long id, Integer state) {
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
