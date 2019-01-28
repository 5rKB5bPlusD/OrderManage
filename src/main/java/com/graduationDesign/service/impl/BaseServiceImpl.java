package com.graduationDesign.service.impl;

import com.google.gson.Gson;
import com.graduationDesign.dao.IMenuDao;
import com.graduationDesign.model.po.*;
import com.graduationDesign.model.vo.MenuVO;
import com.graduationDesign.model.vo.RoleVO;
import com.graduationDesign.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BaseServiceImpl implements IBaseService {

    @Autowired
    private IMenuDao menuDao;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public List<Group> getMenuGroup(int typeId, int[] commonLv) {
        Map<String, Object> map = new HashMap<>();
        map.put("typeId", typeId);
        map.put("commonLv", commonLv);
        return menuDao.getMenuGroup(map);
    }

    @Override
    public Group getMenuGroup(int groupId) {
        return menuDao.getMenuGroupById(groupId);
    }

    @Override
    public List<Item> getMenuItem(int groupId) {
        return menuDao.getMenuItem(groupId);
    }

    @Override
    public List<Map<String, Object>> getMenu(User user, int typeId) {
        RoleVO role = userService.getRole(user.getRoleId());
        List<Group> menuList = getMenuGroup(typeId, role.getCommonLv());
        MenuVO menu = new MenuVO();
        if (typeId == 1) {
            for (Group g : menuList) {
                List<Item> items = getMenuItem(g.getId());
                if (items.size() != 0) {
                    MenuVO unfinished = new MenuVO();
                    MenuVO finished = new MenuVO();
                    MenuVO temp = new MenuVO();

                    if (g.getId() == 1) {
                        int lv = (int) role.getOrderLv().get("1");
                        for (Item i : items) {
                            if (i.getRank() == lv
                                    || (lv == 0 && i.getRank() == 1) || (lv == 0 && i.getRank() == 4)
                                    || (lv == 2 && i.getRank() == 5) || (lv == 3 && i.getRank() == 6)) {
                                unfinished.add(typeId, i.getId(), i.getName(), i.getSrc());
                            } else if (i.getRank() > lv) {
                                finished.add(typeId, i.getId(), i.getName(), i.getSrc());
                            }
                        }
                    }

                    temp.add(0, "待处理", unfinished.getMenu().size() == 0, unfinished.getMenu())
                            .add(1, "已完成", finished.getMenu().size() == 0, finished.getMenu());
                    menu.add(g.getId(), g.getName(), temp.getMenu().size() == 0, temp.getMenu());
                }
            }
        } else if (typeId == 2 || typeId == 3 || typeId == 4) {
            for (Group g : menuList) {
                List<Item> items = getMenuItem(g.getId());
                MenuVO temp = new MenuVO();
                List<Integer> commonLv = Arrays.stream(role.getCommonLv()).boxed().collect(Collectors.toList());
                if (items.size() > 0) {
                    boolean empty = true;
                    for (Item i : items) {
                        if (commonLv.contains(i.getRank())) {
                            temp.add(typeId, i.getId(), i.getName(), i.getSrc());
                            empty = false;
                        }
                    }
                    if (empty) {
                        menu.add(g.getId(), g.getName(), true, temp.getMenu());
                    } else {
                        menu.add(g.getId(), g.getName(), false, temp.getMenu());
                    }
                } else {
                    menu.add(g.getId(), g.getName(), true, temp.getMenu());
                }
            }
        }
        return menu.getMenu();
    }

    @Override
    public String getType(int roleId) {
        int[] lvs = userService.getRole(roleId).getCommonLv();
        StringBuilder res = new StringBuilder();
        for (int l : lvs) {
            MenuType mt = menuDao.getMenuType(l);
            if (mt != null) {
                res.append(mt.getType()).append(",");
            }
        }
        res.deleteCharAt(res.lastIndexOf(","));
        return res.toString();
    }

    @Override
    public int upgradeOrder(int itemId) {
        return menuDao.upgradeOrder(itemId);
    }

}
