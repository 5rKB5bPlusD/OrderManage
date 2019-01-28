package com.graduationDesign.model.po;

public class Role {
    private int id;
    private String roleName;
    private String orderLv;
    private String commonLv;

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

    public String getOrderLv() {
        return orderLv;
    }

    public void setOrderLv(String orderLv) {
        this.orderLv = orderLv;
    }

    public String getCommonLv() {
        return commonLv;
    }

    public void setCommonLv(String commonLv) {
        this.commonLv = commonLv;
    }
}
