package com.graduationDesign.service;

import com.graduationDesign.model.po.OrderApplyPO;
import com.graduationDesign.model.po.OrderComplaintAccept;

import java.util.List;

public interface SyncService {

    public List<OrderComplaintAccept> fakeOrderComplaintSync();

    public List<OrderApplyPO> fakeOrderApplySync();
}
