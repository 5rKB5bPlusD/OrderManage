package com.graduationDesign.dao;

import com.graduationDesign.model.po.PermissionPO;
import com.graduationDesign.model.po.RankPO;
import com.graduationDesign.model.po.Role;
import com.graduationDesign.model.po.User;
import com.graduationDesign.model.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IUserDao {

    public List<User> selectUser(String username);

    public User getUser(int id);

    public Role getRole(int roleId);

    public List<Role> getAllRole();

    public List<PermissionPO> getAllPermission();

    public int addRole(Map map);

    public int deleteRole(int roleId);

    public List<User> getAllUser();

    public int setRole(@Param("userId") int userId, @Param("roleId") int roleId);

    public int createUser(@Param("username") String username, @Param("password") String password);

    public int deleteUser(int userId);
}
