package com.graduationDesign.model.vo;

public class RankVO {
    private int id;
    private int lv;
    private String lvMean;
    private String roleMean;
    private String orderGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public String getLvMean() {
        return lvMean;
    }

    public void setLvMean(String lvMean) {
        this.lvMean = lvMean;
    }

    public String getRoleMean() {
        return roleMean;
    }

    public void setRoleMean(String roleMean) {
        this.roleMean = roleMean;
    }

    public String getOrderGroup() {
        return orderGroup;
    }

    public void setOrderGroup(String orderGroup) {
        this.orderGroup = orderGroup;
    }
}
