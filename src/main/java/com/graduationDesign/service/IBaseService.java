package com.graduationDesign.service;

import com.graduationDesign.model.po.Item;
import com.graduationDesign.model.po.Group;
import com.graduationDesign.model.po.User;

import java.util.List;
import java.util.Map;

public interface IBaseService {

    public List<Group> getMenuGroup(int typeId, int[] commonLv);

    public Group getMenuGroup(int groupId);

    public List<Item> getMenuItem(int groupId);

    public List<Map<String, Object>> getMenu(User user, int typeId);

    public String getType(int roleId);

    public int upgradeOrder(int itemId);
}
