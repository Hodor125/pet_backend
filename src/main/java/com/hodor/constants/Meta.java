package com.hodor.constants;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
public class Meta {

    private String msg;

    private Long code;

    public Meta() {

    }

    public Meta(String msg, Long code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
