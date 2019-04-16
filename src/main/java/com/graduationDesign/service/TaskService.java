package com.graduationDesign.service;

import com.alibaba.fastjson.JSON;
import com.graduationDesign.model.po.OrderApplyPO;
import com.graduationDesign.model.po.OrderComplaintAccept;
import com.graduationDesign.service.impl.OrderServiceImpl;
import com.graduationDesign.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskService {

    @Autowired
    private OrderServiceImpl orderService;

    @Scheduled(cron = "0 0/10 * * * ?")
//    @Scheduled(cron = "0/5 * * * * ?")
    public void sync() {
        System.out.println("工单同步开始");
        String dataOrderComplaintAccept = HttpUtils.doGet("http://localhost:8080/sync/fakeOrderComplaintSync", "");
        List<OrderComplaintAccept> remoteOrderComplaintAccept = JSON.parseArray(dataOrderComplaintAccept, OrderComplaintAccept.class);
        List<OrderComplaintAccept> localOrderComplaintAccept = orderService.getOrderComplaintAcceptAll();
        List<OrderComplaintAccept> resultOrderComplaintAccept = new ArrayList<>(remoteOrderComplaintAccept);
        for (int i = 0; i < remoteOrderComplaintAccept.size(); i++) {
            for (int j = 0; j < localOrderComplaintAccept.size(); j++) {
                if (remoteOrderComplaintAccept.get(i).getEomsId().equals(localOrderComplaintAccept.get(j).getEomsId())) {
                    resultOrderComplaintAccept.remove(remoteOrderComplaintAccept.get(i));
                    break;
                }
            }
        }
        for (OrderComplaintAccept o : resultOrderComplaintAccept) {
            orderService.insertOrderComplaintAccept(o);
            System.out.println("同步投诉工单中，EOMS编号：" + o.getEomsId());
        }

        String dataOrderApply = HttpUtils.doGet("http://localhost:8080/sync/fakeOrderApplySync", "");
        List<OrderApplyPO> remoteOrderApply = JSON.parseArray(dataOrderApply, OrderApplyPO.class);
        List<OrderApplyPO> localOrderApply = orderService.selectOrderApplyAll();
        List<OrderApplyPO> resultOrderApply = new ArrayList<>(remoteOrderApply);
        for (int i = 0; i < remoteOrderApply.size(); i++) {
            for (int j = 0; j < localOrderApply.size(); j++) {
                if (remoteOrderApply.get(i).getEomsId().equals(localOrderApply.get(j).getEomsId())) {
                    resultOrderApply.remove(remoteOrderApply.get(i));
                    break;
                }
            }
        }
        for (OrderApplyPO o : resultOrderApply) {
            orderService.insertOrderApply(o);
            System.out.println("同步申请工单中，EOMS编号：" + o.getEomsId());
        }

        System.out.println("工单同步完成，已同步投诉工单数量：" + resultOrderComplaintAccept.size() +
                "，已同步申请工单数量：" + resultOrderApply.size());
    }
}
