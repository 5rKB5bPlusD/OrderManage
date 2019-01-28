package com.graduationDesign.model.vo;

public class UserVO {
    private int userId;
    private String username;
    private int roleId;
    private String roleName;
    private String orderLv;
    private String commonLv;

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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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
