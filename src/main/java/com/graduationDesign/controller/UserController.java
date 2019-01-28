package com.graduationDesign.controller;

import com.graduationDesign.model.po.PermissionPO;
import com.graduationDesign.model.po.Role;
import com.graduationDesign.model.po.User;
import com.graduationDesign.model.vo.ActionResult;
import com.graduationDesign.model.vo.RankVO;
import com.graduationDesign.model.vo.UserDetailVO;
import com.graduationDesign.model.vo.UserVO;
import com.graduationDesign.service.impl.AuthServiceImpl;
import com.graduationDesign.service.impl.BaseServiceImpl;
import com.graduationDesign.service.impl.UserServiceImpl;
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
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BaseServiceImpl baseService;

    @RequestMapping("/login")
    @ResponseBody
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        return index(request, response);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        return new ModelAndView("user/login");
    }

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        if (authService.getAuth(request)) {
            Map<String, Object> data = new HashMap<String, Object>();
            User user = (User) request.getSession().getAttribute("user");
            data.put("username", user.getUsername());
            data.put("rank", baseService.getType(user.getRoleId()));
            return new ModelAndView("index/index", "data", data);
        }
        return new ModelAndView("user/login");
    }

    @RequestMapping("/load")
    public ModelAndView load(HttpServletRequest request, HttpServletResponse response) {
        String src = request.getParameter("src");
        return new ModelAndView(src);
    }

    @RequestMapping("/allRole")
    @ResponseBody
    public List<Role> allRole(HttpServletRequest request, HttpServletResponse response) {
        return userService.getAllRole();
    }

    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult newRole(HttpServletRequest request, HttpServletResponse response) {
        String roleName = request.getParameter("roleName");
        String orderType = request.getParameter("orderType");
        String order = request.getParameter("order");
        String common = request.getParameter("common");
        if (roleName == null || "".equals(roleName)) {
            return new ActionResult(false, null, "名称不能为空，操作取消");
        }
        if ("".equals(common)) {
            return new ActionResult(false, null, "未赋予权限，操作取消");
        }
        if (!authService.checkAuth((User) request.getSession().getAttribute("user"), 4)) {
            return new ActionResult(false, null, "没有权限");
        }
        userService.addRole(roleName, orderType, order, common.replace(",赋予权限", ""));
        return new ActionResult(true, null, "成功");
    }

    @RequestMapping("/deleteRole")
    @ResponseBody
    public ActionResult deleteRole(HttpServletRequest request, HttpServletResponse response) {
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        int num = userService.deleteRole(roleId);
        if (!authService.checkAuth((User) request.getSession().getAttribute("user"), 4)) {
            return new ActionResult(false, null, "没有权限");
        }
        if (num == 1) {
            return new ActionResult(true, num, "删除成功");
        } else {
            return new ActionResult(false, null, "未知异常");
        }
    }

    @RequestMapping("/allUser")
    @ResponseBody
    public List<UserVO> allUser(HttpServletRequest request, HttpServletResponse response) {
        return userService.getAllUser();
    }

    @RequestMapping("/userDetail")
    @ResponseBody
    public ActionResult userDetail(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        UserDetailVO userDetailVO = userService.getUserDetail(userId);
        return new ActionResult(true, userDetailVO, "");
    }

    @RequestMapping("/setRole")
    @ResponseBody
    public ActionResult setRole(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        int num = userService.setRole(userId, roleId);
        if (num == 1) {
            return new ActionResult(true, num, "成功");
        } else {
            return new ActionResult(false, null, "未知异常");
        }
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult createUser(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int num = userService.createUser(username, password);
        if (num == 1) {
            return new ActionResult(true, num, "成功");
        } else {
            return new ActionResult(false, null, "未知异常");
        }
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public ActionResult deleteUser(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int num = userService.deleteUser(userId);
        if (num == 1) {
            return new ActionResult(true, num, "成功");
        } else {
            return new ActionResult(false, null, "未知异常");
        }
    }
}
