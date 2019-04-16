package com.graduationDesign.model.vo;

import java.util.List;
import java.util.Map;

public class OrderApplyVO {
    private int orderId;
    private String eomsId;
    private String title;
    private String applicant;
    private String applyDate;
    private int type;
    private String typeStr;
    private String purpose;
    private List<Map<String, String>> material;
    private String purchase;
    private String purchaseDate;
    private String warehouse;
    private String warehouseDate;
    private int status;
    private String statusStr;
    private int lv;

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

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public List<Map<String, String>> getMaterial() {
        return material;
    }

    public void setMaterial(List<Map<String, String>> material) {
        this.material = material;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getWarehouseDate() {
        return warehouseDate;
    }

    public void setWarehouseDate(String warehouseDate) {
        this.warehouseDate = warehouseDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }
}
