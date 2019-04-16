package com.graduationDesign.service;

import com.graduationDesign.model.po.PermissionPO;
import com.graduationDesign.model.po.Role;
import com.graduationDesign.model.po.User;
import com.graduationDesign.model.po.UserTeamPO;
import com.graduationDesign.model.vo.*;

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

    public int resetUser(int userId);

    public int deleteUser(int userId);

    public List<UserTeamVO> selectUserTeamAll();

    public UserTeamVO selectUserTeamByTeamId(int teamId);

    public UserTeamPO selectUserTeamByLeaderId(int leaderId);

    public int deleteTeam(int teamId);

    public int addTeam(String teamName, String teamDescribe, int leaderId);

    public int editTeam(int teamId, String teamName, String teamDescribe, int leaderId);

    public List<UserVO> getMember(int teamId);

    public int addMember(int userId, int teamId);

    public int removeMember(int userId);

    public int addOrderLv(int roleId,String groupId,String orderLv);

    public int removeOrderLv(int roleId,String groupId);

    public int editCommonPermission(int roleId,String commonLv);

    public int changePassword(int userId, String password);
}
