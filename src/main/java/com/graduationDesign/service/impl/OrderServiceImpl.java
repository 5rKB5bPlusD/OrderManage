package com.graduationDesign.service.impl;

import com.graduationDesign.dao.IOrderDao;
import com.graduationDesign.model.po.*;
import com.graduationDesign.model.vo.OrderComplaint;
import com.graduationDesign.model.vo.OrderComplaintProcessVO;
import com.graduationDesign.model.vo.RankVO;
import com.graduationDesign.model.vo.RoleVO;
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

    @Autowired
    private AuthServiceImpl authService;

    @Override
    public OrderComplaintSimple getOrderComplaintSimple(int itemId) {
        return orderDao.getOrderComplaintSimple(itemId);
    }

    @Override
    public OrderComplaintAccept getOrderComplaintAccept(int itemId) {
        return orderDao.getOrderComplaintAcceptByItem(itemId);
    }

    @Override
    public List<OrderComplaintProcess> getOrderComplaintProcess(int orderId) {
        return orderDao.getOrderComplaintProcess(orderId);
    }

    @Override
    public OrderComplaintResult getOrderComplaintResult(int orderId) {
        return orderDao.getOrderComplaintResult(orderId);
    }

    @Override
    public OrderComplaint getOrderComplaint(int lv, int itemId) {
        OrderComplaintAccept complaintAccept = getOrderComplaintAccept(itemId);
        OrderComplaint orderComplaint = new OrderComplaint();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        orderComplaint.setLv(lv);
        orderComplaint.setRank(complaintAccept.getRank());
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
    public Object getOrder(User user, String src, int itemId) {
        if (src.contains("orderComplaint")) {
            int lv = (int) userService.getRole(user.getRoleId()).getOrderLv().get("1");
            return getOrderComplaint(lv, itemId);
        }

        return null;
    }

    @Override
    public void addProcess(User user, int orderId, String processingTime, String communicationMode,
                           String treatmentProcess, String processingResult) {
        if (orderDao.getOrderComplaintProcess(orderId) == null) {
            orderDao.upgradeComplaint(0);
            baseService.upgradeOrder(orderDao.getOrderComplaintAccept(orderId).getItemId());
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
        if (orderComplaintAccept.getRank() == lv || (lv == 0 && orderComplaintAccept.getRank() == 1)
                || (lv == 2 && orderComplaintAccept.getRank() == 5) || (lv == 3 && orderComplaintAccept.getRank() == 6)) {
            baseService.upgradeOrder(orderDao.getOrderComplaintAccept(orderId).getItemId());
            if (orderComplaintAccept.getRank() == 6) {
                return orderDao.upgradeComplaint(1);
            }
            return orderDao.upgradeComplaint(0);
        }
        return 0;
    }

    @Override
    public int updateComplaintResult(User user, int orderId, String text1, String text2, String sign, String date) {
        OrderComplaintAccept orderComplaintAccept = orderDao.getOrderComplaintAccept(orderId);
        Map<String, java.io.Serializable> map = new HashMap<>();
        map.put("orderId", orderId);
        RoleVO roleVO = userService.getRole(user.getUserId());
        int lv = (int) roleVO.getOrderLv().get("1");
        if (lv == 2) {
            if (orderComplaintAccept.getRank() == 5) {
                map.put("feedback", text1);
            } else {
                map.put("serviceAdvice", text1);
                map.put("serviceManager", user.getUserId());
            }
        } else if (lv == 3) {
            if (orderComplaintAccept.getRank() == 6) {
                map.put("closingOpinion", text1);
            } else {
                map.put("chargeAdvice", text1);
                map.put("chargeManager", user.getUserId());
            }
        } else if (lv == 0 && orderComplaintAccept.getRank() == 4) {
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
        for(RankPO p: rankPOS){
            String orderGroup = baseService.getMenuGroup(p.getOrderGroup()).getName();
            RankVO rankVO = new RankVO();
            rankVO.setId(p.getId());
            rankVO.setLv(p.getLv());
            rankVO.setLvMean(p.getLvMean());
            rankVO.setRoleMean(p.getRoleMean());
            rankVO.setOrderGroup(orderGroup);
            rankVOS.add(rankVO);
        }

        return rankVOS;
    }

    @Override
    public List<Group> getAllOrderType() {
        return orderDao.getAllOrderType();
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
}
