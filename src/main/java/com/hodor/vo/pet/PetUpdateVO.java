package com.hodor.vo.pet;

import lombok.Data;

/**
 * @Author limingli006
 * @Date 2021/11/6
 */
@Data
public class PetUpdateVO {

    private Long id;

    private Integer state;

    public PetUpdateVO() {
    }

    public PetUpdateVO(Long id, Integer state) {
        this.id = id;
        this.state = state;
    }
}
