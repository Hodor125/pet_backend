package com.hodor.vo.activity;

/**
 * @Author limingli006
 * @Date 2021/11/7
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
