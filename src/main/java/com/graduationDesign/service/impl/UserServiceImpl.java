package com.graduationDesign.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.graduationDesign.dao.IUserDao;
import com.graduationDesign.model.po.Group;
import com.graduationDesign.model.po.PermissionPO;
import com.graduationDesign.model.po.Role;
import com.graduationDesign.model.po.User;
import com.graduationDesign.model.vo.RoleVO;
import com.graduationDesign.model.vo.UserDetailVO;
import com.graduationDesign.model.vo.UserVO;
import com.graduationDesign.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private BaseServiceImpl baseService;

    @Override
    public List<User> selectUser(String username) {
        return userDao.selectUser(username);
    }

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public RoleVO getRole(int roleId) {
        RoleVO roleVO = new RoleVO();
        Role role = userDao.getRole(roleId);
        if (role == null) {
            return null;
        }
        roleVO.setId(role.getId());
        roleVO.setRoleName(role.getRoleName());

        Gson gson = new Gson();
        Map<String, String> orderLv = new HashMap<String, String>();
        orderLv = gson.fromJson(role.getOrderLv(), orderLv.getClass());
        Map<String, Integer> lv = new HashMap<>();
        if (orderLv != null) {
            for (Map.Entry<String, String> entry : orderLv.entrySet()) {
                lv.put(entry.getKey(), Integer.parseInt(entry.getValue()));
            }
        }

        String[] arr = role.getCommonLv().split(",");
        int[] commonLv = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            commonLv[i] = Integer.parseInt(arr[i]);
        }

        roleVO.setOrderLv(lv);
        roleVO.setCommonLv(commonLv);

        return roleVO;
    }

    @Override
    public List<Role> getAllRole() {
        return userDao.getAllRole();
    }

    @Override
    public int addRole(String roleName, String orderType, String order, String common) {
        Map<String, String> orderLv = new HashMap<String, String>();
        String[] type = orderType.split(",");
        String[] lv = orderType.split(",");
        for (int i = 0; i < type.length; i++) {
            orderLv.put(type[i], lv[i]);
        }
        Map<String, String> map = new HashMap<>();
        map.put("roleName", roleName);
        map.put("orderLv", orderType.equals("") ? "" : JSON.toJSON(orderLv).toString());
        map.put("commonLv", common);
        return userDao.addRole(map);
    }

    @Override
    public int deleteRole(int roleId) {
        return userDao.deleteRole(roleId);
    }

    @Override
    public List<PermissionPO> getAllPermission() {
        return userDao.getAllPermission();
    }

    @Override
    public List<UserVO> getAllUser() {
        List<UserVO> list = new ArrayList<>();
        List<User> users = userDao.getAllUser();
        for (User user : users) {
            UserVO userVO = new UserVO();
            userVO.setUserId(user.getUserId());
            userVO.setUsername(user.getUsername());

            Role role = userDao.getRole(user.getRoleId());
            if (role != null) {
                userVO.setRoleId(role.getId());
                userVO.setRoleName(role.getRoleName());
                userVO.setOrderLv(role.getOrderLv());
                userVO.setCommonLv(role.getCommonLv());
            }
            list.add(userVO);
        }
        return list;
    }

    @Override
    public UserDetailVO getUserDetail(int userId) {
        UserDetailVO userDetailVO = new UserDetailVO();
        Map<String, String> order = new HashMap<String, String>();
        User user = getUser(userId);
        userDetailVO.setUserId(userId);
        userDetailVO.setUsername(user.getUsername());

        RoleVO roleVO = getRole(user.getRoleId());
        if (roleVO != null) {
            String[] common = new String[roleVO.getCommonLv().length];
            List<PermissionPO> list = getAllPermission();
            Map<String, String> orderLv = roleVO.getOrderLv();


            userDetailVO.setRoleName(roleVO.getRoleName());

            for (Map.Entry<String, String> entry : orderLv.entrySet()) {
                Group group = baseService.getMenuGroup(Integer.parseInt(entry.getKey()));
                order.put(group.getName(), entry.getValue());
            }

            for (int i = 0; i < roleVO.getCommonLv().length; i++) {
                for (PermissionPO p : list) {
                    if (roleVO.getCommonLv()[i] == p.getPermissionId()) {
                        common[i] = p.getPermissionName();
                    }
                }
            }
            userDetailVO.setOrder(order);
            userDetailVO.setCommon(common);
        }

        return userDetailVO;
    }

    @Override
    public int setRole(int userId, int roleId) {
        return userDao.setRole(userId, roleId);
    }

    @Override
    public int createUser(String username, String password) {
        return userDao.createUser(username, password);
    }

    @Override
    public int deleteUser(int userId) {
        return userDao.deleteUser(userId);
    }
}
