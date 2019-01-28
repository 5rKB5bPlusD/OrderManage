package com.graduationDesign.controller;

import com.graduationDesign.model.po.Group;
import com.graduationDesign.model.po.User;
import com.graduationDesign.model.vo.ActionResult;
import com.graduationDesign.model.vo.RankVO;
import com.graduationDesign.service.impl.BaseServiceImpl;
import com.graduationDesign.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private BaseServiceImpl baseService;

    @Autowired
    private OrderServiceImpl orderService;

    @RequestMapping("/load")
    public ModelAndView load(HttpServletRequest request, HttpServletResponse response) {
        String src = request.getParameter("src");
        String itemId = request.getParameter("itemId");
        User user = (User) request.getSession().getAttribute("user");
        Object order = orderService.getOrder(user, src, Integer.parseInt(itemId));

        return new ModelAndView(src, "order", order);
    }

    @RequestMapping(value = "/addProcess", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult addProcess(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        String processingTime = request.getParameter("processingTime");
        String communicationMode = request.getParameter("communicationMode");
        String treatmentProcess = request.getParameter("treatmentProcess");
        String processingResult = request.getParameter("processingResult");
        orderService.addProcess(user, id, processingTime, communicationMode, treatmentProcess, processingResult);
        return new ActionResult(true, "", "");
    }

    @RequestMapping("/upgradeComplaint")
    @ResponseBody
    public ActionResult upgradeComplaint(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        int num = orderService.upgradeComplaint(user, id);
        if (num == 1) {
            return new ActionResult(true, "", "提交成功");
        } else if (num == 0) {
            return new ActionResult(false, "", "提交失败，无权限");
        } else {
            return new ActionResult(false, "", "出现未知异常");
        }
    }

    @RequestMapping("/editResult")
    @ResponseBody
    public ActionResult editResult(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        String text1 = request.getParameter("text1");
        String text2 = request.getParameter("text2");
        String sign = request.getParameter("sign");
        String date = request.getParameter("date");
        int num = orderService.updateComplaintResult(user, id, text1, text2, sign, date);
        if (num == 1) {
            return new ActionResult(true, "", "提交成功");
        } else if (num == 0) {
            return new ActionResult(false, "", "提交失败，无权限");
        } else {
            return new ActionResult(false, "", "出现未知异常");
        }
    }

    @RequestMapping("/allRank")
    @ResponseBody
    public List<RankVO> allRank(HttpServletRequest request, HttpServletResponse response){
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        return orderService.getAllRank(groupId);
    }

    @RequestMapping("/allOrderType")
    @ResponseBody
    public List<Group> allOrderType(HttpServletRequest request, HttpServletResponse response){
        return orderService.getAllOrderType();
    }
}
