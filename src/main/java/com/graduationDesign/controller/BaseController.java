package com.graduationDesign.controller;

import com.graduationDesign.model.po.User;
import com.graduationDesign.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"base"})
public class BaseController {

    @Autowired
    private BaseServiceImpl baseService;

    @RequestMapping("/menu")
    @ResponseBody
    public List<Map<String, Object>> menu(HttpServletRequest request, HttpServletResponse response) {
        int typeId = 0;
        try {
            typeId = Integer.parseInt(request.getParameter("type"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return baseService.getMenu((User) request.getSession().getAttribute("user"), typeId);
    }
}
