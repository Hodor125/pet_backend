package com.hodor.vo.pet;

import lombok.Data;

/**
 * @Author limingli006
 * @Date 2021/11/6
 */
@Data
public class PetAddVO {

    private Long id;

    private String name;

    private String img;

    public PetAddVO() {
    }

    public PetAddVO(Long id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }
}
