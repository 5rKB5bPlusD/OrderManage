package com.graduationDesign.service.impl;

import com.graduationDesign.model.po.OrderComplaintAccept;
import com.graduationDesign.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncServiceImpl implements SyncService {

    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public List<OrderComplaintAccept> fakeOrderComplaintSync() {
        return orderService.getOrderComplaintAcceptFake();
    }
}
