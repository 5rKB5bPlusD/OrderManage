package com.graduationDesign.model.po;

import java.util.Date;

public class OrderApplyPO {
    private int orderId;
    private String eomsId;
    private String title;
    private String applicant;
    private Date applyDate;
    private int type;
    private String purpose;
    private String material;
    private String purchase;
    private Date purchaseDate;
    private String warehouse;
    private Date warehouseDate;
    private int status;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Date getWarehouseDate() {
        return warehouseDate;
    }

    public void setWarehouseDate(Date warehouseDate) {
        this.warehouseDate = warehouseDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
