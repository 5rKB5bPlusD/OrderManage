package com.graduationDesign.service;

import com.graduationDesign.model.po.PermissionPO;
import com.graduationDesign.model.po.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IAuthService {

    public User checkLogin(String username, String password);

    public boolean getAuth(HttpServletRequest request);

    public List<PermissionPO> getAllPermission();

    public boolean checkAuth(User user, int permission);
}
