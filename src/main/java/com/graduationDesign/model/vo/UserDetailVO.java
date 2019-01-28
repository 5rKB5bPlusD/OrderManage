package com.graduationDesign.model.vo;

import java.util.Map;

public class UserDetailVO {
    private int userId;
    private String username;
    private String roleName;
    private Map order;
    private String[] common;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Map getOrder() {
        return order;
    }

    public void setOrder(Map order) {
        this.order = order;
    }

    public String[] getCommon() {
        return common;
    }

    public void setCommon(String[] common) {
        this.common = common;
    }
}
