package com.graduationDesign.controller;

import com.graduationDesign.model.po.PermissionPO;
import com.graduationDesign.model.vo.ActionResult;
import com.graduationDesign.model.po.User;
import com.graduationDesign.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult checkLogin(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = authService.checkLogin(username, password);
        if (null != user) {
            request.getSession().setAttribute("user", user);
            return new ActionResult(true, username, "成功");
        } else {
            return new ActionResult(false, "", "用户名或密码不正确");
        }
    }

    @RequestMapping("/allPermission")
    @ResponseBody
    public List<PermissionPO> allPermission(HttpServletRequest request, HttpServletResponse response){
        return authService.getAllPermission();
    }

}
