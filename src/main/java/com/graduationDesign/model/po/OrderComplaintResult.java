package com.graduationDesign.model.po;

import java.util.Date;

public class OrderComplaintResult {
    private int id;
    private int orderId;
    private String serviceAdvice;
    private int serviceManager;
    private String chargeAdvice;
    private int chargeManager;
    private String finalPlan;
    private String planSign;
    private Date planDate;
    private String improvementMeasures;
    private String measuresSign;
    private Date measuresDate;
    private String feedback;
    private String closingOpinion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getServiceAdvice() {
        return serviceAdvice;
    }

    public void setServiceAdvice(String serviceAdvice) {
        this.serviceAdvice = serviceAdvice;
    }

    public int getServiceManager() {
        return serviceManager;
    }

    public void setServiceManager(int serviceManager) {
        this.serviceManager = serviceManager;
    }

    public String getChargeAdvice() {
        return chargeAdvice;
    }

    public void setChargeAdvice(String chargeAdvice) {
        this.chargeAdvice = chargeAdvice;
    }

    public int getChargeManager() {
        return chargeManager;
    }

    public void setChargeManager(int chargeManager) {
        this.chargeManager = chargeManager;
    }

    public String getFinalPlan() {
        return finalPlan;
    }

    public void setFinalPlan(String finalPlan) {
        this.finalPlan = finalPlan;
    }

    public String getPlanSign() {
        return planSign;
    }

    public void setPlanSign(String planSign) {
        this.planSign = planSign;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getImprovementMeasures() {
        return improvementMeasures;
    }

    public void setImprovementMeasures(String improvementMeasures) {
        this.improvementMeasures = improvementMeasures;
    }

    public String getMeasuresSign() {
        return measuresSign;
    }

    public void setMeasuresSign(String measuresSign) {
        this.measuresSign = measuresSign;
    }

    public Date getMeasuresDate() {
        return measuresDate;
    }

    public void setMeasuresDate(Date measuresDate) {
        this.measuresDate = measuresDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getClosingOpinion() {
        return closingOpinion;
    }

    public void setClosingOpinion(String closingOpinion) {
        this.closingOpinion = closingOpinion;
    }
}
