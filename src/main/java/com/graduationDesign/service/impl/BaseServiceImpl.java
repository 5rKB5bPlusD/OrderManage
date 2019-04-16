package com.graduationDesign.service.impl;

import com.google.gson.Gson;
import com.graduationDesign.dao.IMenuDao;
import com.graduationDesign.dao.IOrderDao;
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
        if (typeId == 1) {//工单
            for (Group g : menuList) {
                List<Item> items = getMenuItem(g.getId());
                if (items.size() != 0) {
                    MenuVO unfinished = new MenuVO();
                    MenuVO finished = new MenuVO();
                    MenuVO temp = new MenuVO();

                    if (g.getId() == 1) { //投诉工单
                        int lv = (int) role.getOrderLv().get("1");
                        String src = items.get(0).getSrc();
                        List<OrderComplaintAccept> list = orderService.getOrderComplaintAcceptAll();
                        for (OrderComplaintAccept o : list) {
                            if (o.getRankLv() == lv
                                    || (lv == 0 && o.getRankLv() == 1) || (lv == 0 && o.getRankLv() == 4)
                                    || (lv == 2 && o.getRankLv() == 5) || (lv == 3 && o.getRankLv() == 6)) {
                                unfinished.add(typeId, o.getId(), o.getTitle(), src);
                            } else if (o.getRankLv() > lv) {
                                finished.add(typeId, o.getId(), o.getTitle(), src);
                            }
                        }
                    } else if (g.getId() == 9) {
                        int lv = (int) role.getOrderLv().get("2");
                        String src = items.get(0).getSrc();
                        List<OrderApplyPO> list = orderService.selectOrderApplyAll();
                        for (OrderApplyPO o : list) {
                            if (o.getType() == lv && o.getStatus() == 0) {
                                unfinished.add(typeId, o.getOrderId(), o.getTitle(), src);
                            } else if (o.getType() == lv) {
                                finished.add(typeId, o.getOrderId(), o.getTitle(), src);
                            }
                        }
                    } else if (g.getId() == 10) {//工单管理
                        List<Integer> commonLv = Arrays.stream(role.getCommonLv()).boxed().collect(Collectors.toList());
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
                        return menu.getMenu();
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
                List<Integer> commonLv = Arrays.stream(role.getCommonLv()).boxed().collect(Collectors.toList());//数组转列表
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

//    @Override
//    public int upgradeOrder(int itemId) {
//        return menuDao.upgradeOrder(itemId);
//    }

}
