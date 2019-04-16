package com.graduationDesign.dao;

import com.graduationDesign.model.po.Item;
import com.graduationDesign.model.po.Group;
import com.graduationDesign.model.po.MenuType;

import java.util.List;
import java.util.Map;

public interface IMenuDao {

    public List<Group> getMenuGroup(Map map);

    public Group getMenuGroupById(int groupId);

    public List<Item> getMenuItem(int groupId);

    public MenuType getMenuType(int rank);

//    public int upgradeOrder(int itemId);
}
