package com.cgm.pagesplit.entity;

import java.util.List;

/**
 * @author cgm
 */
public class PageConfig {
    private String configName;
    private Integer divX;
    private Integer divY;
    private List<CellConfig> list;

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public Integer getDivX() {
        return divX;
    }

    public void setDivX(Integer divX) {
        this.divX = divX;
    }

    public Integer getDivY() {
        return divY;
    }

    public void setDivY(Integer divY) {
        this.divY = divY;
    }

    public List<CellConfig> getList() {
        return list;
    }

    public void setList(List<CellConfig> list) {
        this.list = list;
    }
}
