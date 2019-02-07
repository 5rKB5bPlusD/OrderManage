package com.graduationDesign.service.impl;

import com.graduationDesign.dao.IUserDao;
import com.graduationDesign.model.po.PermissionPO;
import com.graduationDesign.model.po.User;
import com.graduationDesign.model.po.UserTeamPO;
import com.graduationDesign.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public User checkLogin(String username, String password) {
        List<User> userList = userService.selectUser(username);
        for (User u : userList) {
            if (u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public boolean getAuth(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }

    @Override
    public List<PermissionPO> getAllPermission() {
        return userService.getAllPermission();
    }

    @Override
    public boolean checkAuth(User user, int permission) {
        int[] common = userService.getRole(user.getRoleId()).getCommonLv();
        for (int i : common) {
            if (i == permission) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int checkLeader(User user) {
        UserTeamPO userTeamPO = userService.selectUserTeamByLeaderId(user.getUserId());
        if (userTeamPO != null) {
            return userTeamPO.getTeamId();
        }
        return -1;
    }
}
