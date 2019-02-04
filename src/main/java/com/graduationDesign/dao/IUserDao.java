package com.graduationDesign.dao;

import com.graduationDesign.model.po.*;
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

    public int updateUserByUserId(Map map);

    public List<UserTeamPO> selectUserTeamAll();

    public UserTeamPO selectUserTeamByTeamId(int teamId);

    public int insertUserTeam(Map map);

    public int updateUserTeamByTeamId(Map map);

    public int deleteUserTeamById(int teamId);

    public List<User> selectUserByTeamId(int teamId);
}
