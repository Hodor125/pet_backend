package com.hodor.vo.reserve;

/**
 * @Author limingli006
 * @Date 2021/11/7
 */
public class ReserveUpdateVO {

    private Long id;

    private Integer state;

    public ReserveUpdateVO() {
    }

    public ReserveUpdateVO(Long id, Integer state) {
        this.id = id;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public ReserveUpdateVO setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getState() {
        return state;
    }

    public ReserveUpdateVO setState(Integer state) {
        this.state = state;
        return this;
    }
}
