package com.graduationDesign.dao;

import com.graduationDesign.model.po.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IOrderDao {

    public OrderComplaintAccept getOrderComplaintAccept(int orderId);

    public List<OrderComplaintProcess> getOrderComplaintProcess(int orderId);

    public OrderComplaintResult getOrderComplaintResult(int orderId);

    public int insertOrderComplaintResult(int orderId);

    public void addProcess(Map map);

    public int upgradeComplaint(@Param("orderId") int orderId, @Param("finished") int finished);

    public int updateComplaintResult(Map map);

    public List<RankPO> getAllRank(int groupId);

    public List<Item> getAllOrderType();

    public List<OrderComplaintAccept> getOrderComplaintAcceptFake();

    public int insertOrderComplaintAccept(OrderComplaintAccept orderComplaintAccept);

    public List<OrderComplaintAccept> getOrderComplaintAcceptAll();

    public List<OrderApplyPO> selectOrderApplyAll();

    public OrderApplyPO selectOrderApplyByOrderId(int orderId);

    public int updateOrderApplyByOrderId(Map map);

    public RankPO selectRankByLvAndGroup(@Param("lv") int lv, @Param("orderGroup") int orderGroup);

    public OrderApplyPO selectOrderApplyByEomsId(String eomsId);

    public List<OrderApplyPO> selectOrderApplyByTitleAndStatus(@Param("title") String title, @Param("status") int status);

    public OrderComplaintAccept selectOrderComplaintAcceptByEomsId(String eomsId);

    public List<OrderComplaintAccept> selectOrderComplaintAcceptByTitleAndRankLv(@Param("title") String title, @Param("rankLv") int rankLv);

    public int insertOrderApply(OrderApplyPO orderApplyPO);

    public List<OrderApplyPO> selectOrderApplyFakeAll();
}
