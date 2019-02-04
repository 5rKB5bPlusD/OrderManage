package com.graduationDesign.model.po;

public class UserTeamPO {
    private int teamId;
    private String teamName;
    private String teamDescribe;
    private int leaderId;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamDescribe() {
        return teamDescribe;
    }

    public void setTeamDescribe(String teamDescribe) {
        this.teamDescribe = teamDescribe;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }
}
