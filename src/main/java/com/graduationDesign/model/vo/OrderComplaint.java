package com.graduationDesign.model.vo;

import java.util.Date;
import java.util.List;

public class OrderComplaint {

    private int lv;
    private int rank;
    private int id;
    private String eomsId;
    private String title;
    private String customerName;
    private String customerTel;
    private String complaintType;
    private String acceptanceTime;
    private String acceptPerson;
    private String complaintContent;
    private String customerRequirements;
    private String responsibleDepartment;
    private String responsiblePerson;
    private String timeRequired;
    private List<OrderComplaintProcessVO> process;
    private String serviceAdvice;
    private String serviceManager;
    private String chargeAdvice;
    private String chargeManager;
    private String finalPlan;
    private String planSign;
    private String planDate;
    private String improvementMeasures;
    private String measuresSign;
    private String measuresDate;
    private String feedback;
    private String closingOpinion;

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEomsId() {
        return eomsId;
    }

    public void setEomsId(String eomsId) {
        this.eomsId = eomsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getAcceptanceTime() {
        return acceptanceTime;
    }

    public void setAcceptanceTime(String acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
    }

    public String getAcceptPerson() {
        return acceptPerson;
    }

    public void setAcceptPerson(String acceptPerson) {
        this.acceptPerson = acceptPerson;
    }

    public String getComplaintContent() {
        return complaintContent;
    }

    public void setComplaintContent(String complaintContent) {
        this.complaintContent = complaintContent;
    }

    public String getCustomerRequirements() {
        return customerRequirements;
    }

    public void setCustomerRequirements(String customerRequirements) {
        this.customerRequirements = customerRequirements;
    }

    public String getResponsibleDepartment() {
        return responsibleDepartment;
    }

    public void setResponsibleDepartment(String responsibleDepartment) {
        this.responsibleDepartment = responsibleDepartment;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getTimeRequired() {
        return timeRequired;
    }

    public void setTimeRequired(String timeRequired) {
        this.timeRequired = timeRequired;
    }

    public List<OrderComplaintProcessVO> getProcess() {
        return process;
    }

    public void setProcess(List<OrderComplaintProcessVO> process) {
        this.process = process;
    }

    public String getServiceAdvice() {
        return serviceAdvice;
    }

    public void setServiceAdvice(String serviceAdvice) {
        this.serviceAdvice = serviceAdvice;
    }

    public String getServiceManager() {
        return serviceManager;
    }

    public void setServiceManager(String serviceManager) {
        this.serviceManager = serviceManager;
    }

    public String getChargeAdvice() {
        return chargeAdvice;
    }

    public void setChargeAdvice(String chargeAdvice) {
        this.chargeAdvice = chargeAdvice;
    }

    public String getChargeManager() {
        return chargeManager;
    }

    public void setChargeManager(String chargeManager) {
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

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
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

    public String getMeasuresDate() {
        return measuresDate;
    }

    public void setMeasuresDate(String measuresDate) {
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
