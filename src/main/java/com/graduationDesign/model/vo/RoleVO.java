package com.graduationDesign.model.vo;

import java.util.Map;

public class RoleVO {
    private int id;
    private String roleName;
    private Map orderLv;
    private int[] commonLv;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Map getOrderLv() {
        return orderLv;
    }

    public void setOrderLv(Map orderLv) {
        this.orderLv = orderLv;
    }

    public int[] getCommonLv() {
        return commonLv;
    }

    public void setCommonLv(int[] commonLv) {
        this.commonLv = commonLv;
    }
}
