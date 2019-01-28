package com.graduationDesign.dao;

import com.graduationDesign.model.po.*;

import java.util.List;
import java.util.Map;

public interface IOrderDao {
    public OrderComplaintSimple getOrderComplaintSimple(int itemId);

    public OrderComplaintAccept getOrderComplaintAcceptByItem(int itemId);

    public OrderComplaintAccept getOrderComplaintAccept(int orderId);

    public List<OrderComplaintProcess> getOrderComplaintProcess(int orderId);

    public OrderComplaintResult getOrderComplaintResult(int orderId);

    public void addProcess(Map map);

    public int upgradeComplaint(int finished);

    public int updateComplaintResult(Map map);

    public List<RankPO> getAllRank(int groupId);

    public List<Group> getAllOrderType();

    public List<OrderComplaintAccept> getOrderComplaintAcceptFake();

    public int insertOrderComplaintAccept(OrderComplaintAccept orderComplaintAccept);

    public List<OrderComplaintAccept> getOrderComplaintAcceptAll();
}
