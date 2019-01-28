package com.graduationDesign.service;

import com.graduationDesign.model.po.PermissionPO;
import com.graduationDesign.model.po.Role;
import com.graduationDesign.model.po.User;
import com.graduationDesign.model.vo.RankVO;
import com.graduationDesign.model.vo.RoleVO;
import com.graduationDesign.model.vo.UserDetailVO;
import com.graduationDesign.model.vo.UserVO;

import java.util.List;

public interface IUserService {

    public List<User> selectUser(String username);

    public User getUser(int id);

    public RoleVO getRole(int roleId);

    public List<Role> getAllRole();

    public int addRole(String roleName, String orderType, String order, String common);

    public int deleteRole(int roleId);

    public List<PermissionPO> getAllPermission();

    public List<UserVO> getAllUser();

    public UserDetailVO getUserDetail(int userId);

    public int setRole(int userId, int roleId);

    public int createUser(String username, String password);

    public int deleteUser(int userId);
}
