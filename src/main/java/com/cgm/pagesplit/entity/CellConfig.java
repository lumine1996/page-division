package com.cgm.pagesplit.entity;

/**
 * @author cgm
 */
public class CellConfig {
    private Integer index;
    private String title;
    private String src;
    private Integer upperLeft;
    private Integer lowerRight;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getUpperLeft() {
        return upperLeft;
    }

    public void setUpperLeft(Integer upperLeft) {
        this.upperLeft = upperLeft;
    }

    public Integer getLowerRight() {
        return lowerRight;
    }

    public void setLowerRight(Integer lowerRight) {
        this.lowerRight = lowerRight;
    }
}
