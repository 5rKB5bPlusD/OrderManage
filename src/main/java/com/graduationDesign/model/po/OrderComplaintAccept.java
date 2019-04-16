package com.graduationDesign.model.po;

import java.io.Serializable;
import java.util.Date;

public class OrderComplaintAccept implements Serializable {
    private int id;
    private String eomsId;
    private String title;
    private int finished;
    private String customerName;
    private String customerTel;
    private String complaintType;
    private Date acceptanceTime;
    private String acceptPerson;
    private String complaintContent;
    private String customerRequirements;
    private String responsibleDepartment;
    private String responsiblePerson;
    private Date timeRequired;
    private int rankLv;

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

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
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

    public Date getAcceptanceTime() {
        return acceptanceTime;
    }

    public void setAcceptanceTime(Date acceptanceTime) {
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

    public Date getTimeRequired() {
        return timeRequired;
    }

    public void setTimeRequired(Date timeRequired) {
        this.timeRequired = timeRequired;
    }

    public String getCustomerRequirements() {
        return customerRequirements;
    }

    public void setCustomerRequirements(String customerRequirements) {
        this.customerRequirements = customerRequirements;
    }

    public int getRankLv() {
        return rankLv;
    }

    public void setRankLv(int rankLv) {
        this.rankLv = rankLv;
    }
}
