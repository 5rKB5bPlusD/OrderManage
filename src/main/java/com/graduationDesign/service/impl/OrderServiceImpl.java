package com.graduationDesign.service.impl;

import com.graduationDesign.dao.IOrderDao;
import com.graduationDesign.model.po.*;
import com.graduationDesign.model.vo.*;
import com.graduationDesign.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BaseServiceImpl baseService;

    @Override
    public List<OrderComplaintProcess> getOrderComplaintProcess(int orderId) {
        return orderDao.getOrderComplaintProcess(orderId);
    }

    @Override
    public OrderComplaintResult getOrderComplaintResult(int orderId) {
        return orderDao.getOrderComplaintResult(orderId);
    }

    @Override
    public OrderComplaint getOrderComplaint(int lv, int orderId) {
        OrderComplaintAccept complaintAccept = orderDao.getOrderComplaintAccept(orderId);
        OrderComplaint orderComplaint = new OrderComplaint();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        orderComplaint.setLv(lv);
        orderComplaint.setRank(complaintAccept.getRankLv());
        orderComplaint.setId(complaintAccept.getId());
        orderComplaint.setEomsId(complaintAccept.getEomsId());
        orderComplaint.setTitle(complaintAccept.getTitle());
        orderComplaint.setCustomerName(complaintAccept.getCustomerName());
        orderComplaint.setCustomerTel(complaintAccept.getCustomerTel());
        orderComplaint.setComplaintType(complaintAccept.getComplaintType());
        orderComplaint.setAcceptanceTime(sdf.format(complaintAccept.getAcceptanceTime()));
        orderComplaint.setAcceptPerson(complaintAccept.getAcceptPerson());
        orderComplaint.setComplaintContent(complaintAccept.getComplaintContent());
        orderComplaint.setCustomerRequirements(complaintAccept.getCustomerRequirements());
        orderComplaint.setResponsibleDepartment(complaintAccept.getResponsibleDepartment());
        orderComplaint.setResponsiblePerson(complaintAccept.getResponsiblePerson());
        orderComplaint.setTimeRequired(sdf.format(complaintAccept.getTimeRequired()));

        List<OrderComplaintProcess> processes = getOrderComplaintProcess(complaintAccept.getId());
        List<OrderComplaintProcessVO> processVOS = new ArrayList<>();
        for (OrderComplaintProcess o : processes) {
            OrderComplaintProcessVO temp = new OrderComplaintProcessVO();
            temp.setProcessingTime(sdf.format(o.getProcessingTime()));
            temp.setCommunicationMode(o.getCommunicationMode());
            temp.setHandler(userService.getUser(o.getHandler()).getUsername());
            temp.setTreatmentProcess(o.getTreatmentProcess());
            temp.setProcessingResult(o.getProcessingResult());
            processVOS.add(temp);
        }
        orderComplaint.setProcess(processVOS);

        OrderComplaintResult complaintResult = getOrderComplaintResult(orderComplaint.getId());
        if (complaintResult != null) {
            orderComplaint.setServiceAdvice(complaintResult.getServiceAdvice() == null ? "" : complaintResult.getServiceAdvice());
            if (complaintResult.getServiceManager() != 0) {
                orderComplaint.setServiceManager(userService.getUser(complaintResult.getServiceManager()).getUsername());
            } else {
                orderComplaint.setServiceManager("");
            }
            orderComplaint.setChargeAdvice(complaintResult.getChargeAdvice() == null ? "" : complaintResult.getChargeAdvice());
            if (complaintResult.getChargeManager() != 0) {
                orderComplaint.setChargeManager(userService.getUser(complaintResult.getChargeManager()).getUsername());
            } else {
                orderComplaint.setChargeManager("");
            }
            orderComplaint.setFinalPlan(complaintResult.getFinalPlan() == null ? "" : complaintResult.getFinalPlan());
            orderComplaint.setPlanSign(complaintResult.getPlanSign() == null ? "" : complaintResult.getPlanSign());
            if (complaintResult.getPlanDate() != null) {
                orderComplaint.setPlanDate(sdf.format(complaintResult.getPlanDate()));
            } else {
                orderComplaint.setPlanDate("");
            }
            orderComplaint.setImprovementMeasures(complaintResult.getImprovementMeasures() == null ? "" : complaintResult.getImprovementMeasures());
            orderComplaint.setMeasuresSign(complaintResult.getMeasuresSign() == null ? "" : complaintResult.getMeasuresSign());
            if (complaintResult.getMeasuresDate() != null) {
                orderComplaint.setMeasuresDate(sdf.format(complaintResult.getMeasuresDate()));
            } else {
                orderComplaint.setMeasuresDate("");
            }
            orderComplaint.setFeedback(complaintResult.getFeedback() == null ? "" : complaintResult.getFeedback());
            orderComplaint.setClosingOpinion(complaintResult.getClosingOpinion() == null ? "" : complaintResult.getClosingOpinion());
        }

        return orderComplaint;
    }

    @Override
    public Object getOrder(User user, String src, int orderId) {
        if (src.contains("orderComplaint")) {
            int lv = (int) userService.getRole(user.getRoleId()).getOrderLv().get("1");
            return getOrderComplaint(lv, orderId);
        } else if (src.contains("orderApply")) {
            int lv = (int) userService.getRole(user.getRoleId()).getOrderLv().get("2");
            return getOrderApply(lv, orderId);
        }

        return null;
    }

    @Override
    public void addProcess(User user, int orderId, String processingTime, String communicationMode,
                           String treatmentProcess, String processingResult) {
        List<OrderComplaintProcess> list = orderDao.getOrderComplaintProcess(orderId);
        if (list.size() == 0) {
            orderDao.upgradeComplaint(orderId, 0);
//            baseService.upgradeOrder(orderDao.getOrderComplaintAccept(orderId).getItemId());
        }
        Map<String, java.io.Serializable> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("processingTime", processingTime);
        map.put("communicationMode", communicationMode);
        map.put("handler", user.getUserId());
        map.put("processingResult", processingResult);
        map.put("treatmentProcess", treatmentProcess);
        orderDao.addProcess(map);
    }

    @Override
    public int upgradeComplaint(User user, int orderId) {
        OrderComplaintAccept orderComplaintAccept = orderDao.getOrderComplaintAccept(orderId);
        int lv = (int) userService.getRole(user.getRoleId()).getOrderLv().get("1");
        if (orderComplaintAccept.getRankLv() == lv
                || (lv == 0 && orderComplaintAccept.getRankLv() == 1)
                || (lv == 0 && orderComplaintAccept.getRankLv() == 4)
                || (lv == 2 && orderComplaintAccept.getRankLv() == 5)
                || (lv == 3 && orderComplaintAccept.getRankLv() == 6)) {
//            baseService.upgradeOrder(orderDao.getOrderComplaintAccept(orderId).getItemId());
            if (orderComplaintAccept.getRankLv() == 6) {
                return orderDao.upgradeComplaint(orderId, 1);
            }
            return orderDao.upgradeComplaint(orderId, 0);
        }
        return 0;
    }

    @Override
    public int updateComplaintResult(User user, int orderId, String text1, String text2, String sign, String date) {
        OrderComplaintAccept orderComplaintAccept = orderDao.getOrderComplaintAccept(orderId);
        OrderComplaintResult orderComplaintResult = orderDao.getOrderComplaintResult(orderId);
        if (orderComplaintResult == null) {
            orderDao.insertOrderComplaintResult(orderId);
        }
        Map<String, java.io.Serializable> map = new HashMap<>();
        map.put("orderId", orderId);
        RoleVO roleVO = userService.getRole(user.getUserId());
        int lv = (int) roleVO.getOrderLv().get("1");
        if (lv == 2) {
            if (orderComplaintAccept.getRankLv() == 5) {
                map.put("feedback", text1);
            } else {
                map.put("serviceAdvice", text1);
                map.put("serviceManager", user.getUserId());
            }
        } else if (lv == 3) {
            if (orderComplaintAccept.getRankLv() == 6) {
                map.put("closingOpinion", text1);
            } else {
                map.put("chargeAdvice", text1);
                map.put("chargeManager", user.getUserId());
            }
        } else if (lv == 0 && orderComplaintAccept.getRankLv() == 4) {
            map.put("finalPlan", text1);
            map.put("improvementMeasures", text2);
            map.put("planSign", sign);
            map.put("measuresSign", sign);
            map.put("planDate", date);
            map.put("measuresDate", date);
        } else {
            return 0;
        }
        upgradeComplaint(user, orderId);
        return orderDao.updateComplaintResult(map);
    }

    @Override
    public List<RankVO> getAllRank(int groupId) {
        List<RankPO> rankPOS = orderDao.getAllRank(groupId);
        List<RankVO> rankVOS = new ArrayList<>();
        for (RankPO p : rankPOS) {
            String orderGroup = baseService.getMenuGroup(p.getOrderGroup()).getName();
            RankVO rankVO = new RankVO();
            rankVO.setId(p.getId());
            rankVO.setLv(p.getLv());
            rankVO.setLvMean(p.getLvMean());
            rankVO.setRoleMean(p.getRoleMean());
            rankVO.setOrderGroup(p.getOrderGroup());
            rankVO.setOrderGroupStr(orderGroup);
            rankVOS.add(rankVO);
        }

        return rankVOS;
    }

    @Override
    public List<Item> getAllOrderType() {
        List<Item> list = orderDao.getAllOrderType();
        for (Item item : list) {
            item.setRank(baseService.getMenuGroup(item.getGroupId()).getRank());
        }
        return list;
    }

    @Override
    public List<OrderComplaintAccept> getOrderComplaintAcceptFake() {
        return orderDao.getOrderComplaintAcceptFake();
    }

    @Override
    public int insertOrderComplaintAccept(OrderComplaintAccept orderComplaintAccept) {
        return orderDao.insertOrderComplaintAccept(orderComplaintAccept);
    }

    @Override
    public List<OrderComplaintAccept> getOrderComplaintAcceptAll() {
        return orderDao.getOrderComplaintAcceptAll();
    }

    @Override
    public List<OrderApplyPO> selectOrderApplyAll() {
        return orderDao.selectOrderApplyAll();
    }

    @Override
    public OrderApplyVO getOrderApply(int lv, int orderId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        OrderApplyPO orderApplyPO = orderDao.selectOrderApplyByOrderId(orderId);
        OrderApplyVO orderApplyVO = new OrderApplyVO();
        orderApplyVO.setOrderId(orderApplyPO.getOrderId());
        orderApplyVO.setTitle(orderApplyPO.getTitle());
        orderApplyVO.setApplicant(orderApplyPO.getApplicant());
        orderApplyVO.setApplyDate(sdf.format(orderApplyPO.getApplyDate()));
        orderApplyVO.setType(orderApplyPO.getType());
        orderApplyVO.setTypeStr(orderApplyPO.getType() == 1 ? "采购" : "库存");
        orderApplyVO.setPurpose(orderApplyPO.getPurpose());
        List<Map<String, String>> list = new ArrayList<>();
        String[] m = orderApplyPO.getMaterial().split(";");
        for (String s : m) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", s.split(",")[0]);
            map.put("num", s.split(",")[1]);
            list.add(map);
        }
        orderApplyVO.setMaterial(list);
        orderApplyVO.setPurchase(orderApplyPO.getPurchase() == null ? "" : orderApplyPO.getPurchase());
        orderApplyVO.setPurchaseDate(orderApplyPO.getPurchaseDate() == null ? "" : sdf.format(orderApplyPO.getPurchaseDate()));
        orderApplyVO.setWarehouse(orderApplyPO.getWarehouse() == null ? "" : orderApplyPO.getWarehouse());
        orderApplyVO.setWarehouseDate(orderApplyPO.getWarehouseDate() == null ? "" : sdf.format(orderApplyPO.getWarehouseDate()));
        String status = "";
        orderApplyVO.setStatus(orderApplyPO.getStatus());
        if (orderApplyPO.getStatus() == 0) {
            status = "未处理";
        } else if (orderApplyPO.getStatus() == 1) {
            status = "已通过";
        } else if (orderApplyPO.getStatus() == 2) {
            status = "未通过";
        }
        orderApplyVO.setStatusStr(status);
        orderApplyVO.setLv(lv);
        return orderApplyVO;
    }

    @Override
    public int approvalOrderApply(int orderId, int status, int type, String approvalValue) {
        Map<String, java.io.Serializable> map = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        map.put("orderId", orderId);
        if (type == 1) {
            map.put("purchase", approvalValue);
            map.put("purchaseDate", sdf.format(date));
        } else if (type == 2) {
            map.put("warehouse", approvalValue);
            map.put("warehouseDate", sdf.format(date));
        }
        map.put("status", status);
        return orderDao.updateOrderApplyByOrderId(map);
    }

    @Override
    public List<RankVO> showEditOrderLv(int roleId) {
        List<RankVO> list = new ArrayList<>();
        RoleVO role = userService.getRole(roleId);
        Map<String, Integer> map = role.getOrderLv();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            int orderGroup = 0;
            if (entry.getKey().equals("1")) {
                orderGroup = 1;
            } else if (entry.getKey().equals("2")) {
                orderGroup = 9;
            }
            RankPO rankPO = orderDao.selectRankByLvAndGroup(entry.getValue(), orderGroup);
            String orderGroupName = baseService.getMenuGroup(rankPO.getOrderGroup()).getName();
            RankVO rankVO = new RankVO();
            rankVO.setId(rankPO.getId());
            rankVO.setLv(rankPO.getLv());
            rankVO.setLvMean(rankPO.getLvMean());
            rankVO.setRoleMean(rankPO.getRoleMean());
            rankVO.setOrderGroup(rankPO.getOrderGroup());
            rankVO.setOrderGroupStr(orderGroupName);
            list.add(rankVO);
        }
        return list;
    }

    @Override
    public List<Map> searchOrder(String eomsId, String title, int orderType, int lv) {
        List<Map> list = new ArrayList<>();
        if (orderType == 1 || eomsId.contains("OC")) {
            Item item = baseService.getMenuItem(1).get(0);
            if (!"".equals(eomsId)) {
                OrderComplaintAccept orderComplaintAccept = orderDao.selectOrderComplaintAcceptByEomsId(eomsId);
                Map map = new HashMap();
                map.put("orderId", orderComplaintAccept.getId());
                map.put("eomsId", orderComplaintAccept.getEomsId());
                map.put("type", item.getName());
                map.put("title", orderComplaintAccept.getTitle());
                map.put("status", orderDao.selectRankByLvAndGroup(orderComplaintAccept.getRankLv(), 1).getLvMean());
                map.put("src", item.getSrc());
                list.add(map);
            } else {
                List<OrderComplaintAccept> list1 = orderDao.selectOrderComplaintAcceptByTitleAndRankLv(title, lv);
                for (OrderComplaintAccept o : list1) {
                    Map map = new HashMap();
                    map.put("orderId", o.getId());
                    map.put("eomsId", o.getEomsId());
                    map.put("type", item.getName());
                    map.put("title", o.getTitle());
                    map.put("status", orderDao.selectRankByLvAndGroup(o.getRankLv(), 1).getLvMean());
                    map.put("src", item.getSrc());
                    list.add(map);
                }
            }
        } else if (orderType == 9 || eomsId.contains("OA")) {
            Item item = baseService.getMenuItem(9).get(0);
            if (!"".equals(eomsId)) {
                OrderApplyPO orderApplyPO = orderDao.selectOrderApplyByEomsId(eomsId);
                Map map = new HashMap();
                map.put("orderId", orderApplyPO.getOrderId());
                map.put("eomsId", orderApplyPO.getEomsId());
                map.put("type", item.getName());
                map.put("title", orderApplyPO.getTitle());
                map.put("status", orderApplyPO.getStatus() == 1 ? "完成" : "未处理");
                map.put("src", item.getSrc());
                list.add(map);
            } else {
                List<OrderApplyPO> list1 = orderDao.selectOrderApplyByTitleAndStatus(title, lv);
                for (OrderApplyPO o : list1) {
                    Map map = new HashMap();
                    map.put("orderId", o.getOrderId());
                    map.put("eomsId", o.getEomsId());
                    map.put("type", item.getName());
                    map.put("title", o.getTitle());
                    map.put("status", o.getStatus() == 1 ? "完成" : "未处理");
                    map.put("src", item.getSrc());
                    list.add(map);
                }
            }
        } else {
            Item item1 = baseService.getMenuItem(1).get(0);
            List<OrderComplaintAccept> list1 = orderDao.selectOrderComplaintAcceptByTitleAndRankLv(title, lv);
            for (OrderComplaintAccept o : list1) {
                Map map = new HashMap();
                map.put("orderId", o.getId());
                map.put("eomsId", o.getEomsId());
                map.put("type", item1.getName());
                map.put("title", o.getTitle());
                map.put("status", orderDao.selectRankByLvAndGroup(o.getRankLv(), 1).getLvMean());
                map.put("src", item1.getSrc());
                list.add(map);
            }
            Item item2 = baseService.getMenuItem(9).get(0);
            List<OrderApplyPO> list2 = orderDao.selectOrderApplyByTitleAndStatus(title, lv);
            for (OrderApplyPO o : list2) {
                Map map = new HashMap();
                map.put("orderId", o.getOrderId());
                map.put("eomsId", o.getEomsId());
                map.put("type", item2.getName());
                map.put("title", o.getTitle());
                map.put("status", o.getStatus() == 1 ? "完成" : "未处理");
                map.put("src", item2.getSrc());
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public int insertOrderApply(OrderApplyPO orderApplyPO) {
        return orderDao.insertOrderApply(orderApplyPO);
    }

    @Override
    public List<OrderApplyPO> getOrderApplyFake() {
        return orderDao.selectOrderApplyFakeAll();
    }
}
