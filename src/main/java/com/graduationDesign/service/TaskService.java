package com.graduationDesign.service;

import com.alibaba.fastjson.JSON;
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
    public void sync() {
        System.out.println("工单同步开始");
        String data = HttpUtils.doGet("http://localhost:8080/sync/fakeOrderComplaintSync", "");
        List<OrderComplaintAccept> remote = JSON.parseArray(data, OrderComplaintAccept.class);
        List<OrderComplaintAccept> local = orderService.getOrderComplaintAcceptAll();
        List<OrderComplaintAccept> result = new ArrayList<>(remote);
        for (int i = 0; i < remote.size(); i++) {
            for (int j = 0; j < local.size(); j++) {
                if (remote.get(i).getEomsId().equals(local.get(j).getEomsId())) {
                    result.remove(remote.get(i));
                    break;
                }
            }
        }
        for (OrderComplaintAccept o : result) {
            o.setFinished(0);
            o.setRank(0);
            orderService.insertOrderComplaintAccept(o);
            System.out.println("同步工单中，EOMS编号：" + o.getEomsId());
        }
        System.out.println("工单同步完成，已同步数量：" + result.size());
    }
}
