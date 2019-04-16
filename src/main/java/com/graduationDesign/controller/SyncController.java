package com.graduationDesign.controller;

import com.alibaba.fastjson.JSON;
import com.graduationDesign.model.po.OrderApplyPO;
import com.graduationDesign.model.po.OrderComplaintAccept;
import com.graduationDesign.service.impl.SyncServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("sync")
public class SyncController {

    @Autowired
    private SyncServiceImpl syncService;

    @RequestMapping(value = "/fakeOrderComplaintSync", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String fakeOrderComplaintSync(HttpServletRequest request, HttpServletResponse response) {
        List<OrderComplaintAccept> data = syncService.fakeOrderComplaintSync();
        return JSON.toJSONString(data);
    }

    @RequestMapping(value = "/fakeOrderApplySync", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String fakeOrderApplySync(HttpServletRequest request, HttpServletResponse response) {
        List<OrderApplyPO> data = syncService.fakeOrderApplySync();
        return JSON.toJSONString(data);
    }
}
