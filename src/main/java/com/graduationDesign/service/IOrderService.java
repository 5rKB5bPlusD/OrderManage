package com.graduationDesign.service;

import com.graduationDesign.model.po.*;
import com.graduationDesign.model.vo.OrderComplaint;
import com.graduationDesign.model.vo.RankVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IOrderService {
    public OrderComplaintSimple getOrderComplaintSimple(int itemId);

    public OrderComplaintAccept getOrderComplaintAccept(int itemId);

    public List<OrderComplaintProcess> getOrderComplaintProcess(int orderId);

    public OrderComplaintResult getOrderComplaintResult(int orderId);

    public OrderComplaint getOrderComplaint(int lv, int itemId);

    public Object getOrder(User user, String src, int itemId);

    public void addProcess(User user, int orderId, String processingTime, String communicationMode, String treatmentProcess, String processingResult);

    public int upgradeComplaint(User user, int orderId);

    public int updateComplaintResult(User user, int orderId, String text1, String text2, String sign, String date);

    public List<RankVO> getAllRank(int groupId);

    public List<Group> getAllOrderType();

    public List<OrderComplaintAccept> getOrderComplaintAcceptFake();

    public int insertOrderComplaintAccept(OrderComplaintAccept orderComplaintAccept);

    public List<OrderComplaintAccept> getOrderComplaintAcceptAll();
}
