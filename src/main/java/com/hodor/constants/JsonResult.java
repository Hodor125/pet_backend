package com.hodor.constants;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
public class JsonResult<T> {

    private T data;

    private Meta meta;

    public T getData() {
        return data;
    }

    public JsonResult setData(T data) {
        this.data = data;
        return this;
    }

    public Meta getMeta() {
        return meta;
    }

    public JsonResult setMeta(Meta meta) {
        this.meta = meta;
        return this;
    }
}
