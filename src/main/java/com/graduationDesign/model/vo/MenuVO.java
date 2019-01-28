package com.graduationDesign.model.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuVO {

    private List<Map<String, Object>> menu;

    public MenuVO() {
        this.menu = new ArrayList<>();
    }

    public MenuVO(List<Map<String, Object>> list) {
        this.menu = list;
    }

    public MenuVO add(int typeId, int itemId, String text, String src) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", itemId);
        text = "<span onclick=\"addPanel(" + typeId + "," + itemId + ",'" + text + "','" + src + "')\">" + text + "</span>";
        map.put("text", text);
        menu.add(map);
        return this;
    }

    public MenuVO add(int groupId, String text, boolean state) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", groupId);
        map.put("text", text);
        map.put("state", state(state));
        menu.add(map);
        return this;
    }

    public MenuVO add(int groupId, String text, boolean state, List<Map<String, Object>> children) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", groupId);
        map.put("text", text);
        map.put("state", state(state));
        map.put("children", children);
        menu.add(map);
        return this;
    }

    public List<Map<String, Object>> getMenu() {
        return this.menu;
    }

    private String state(boolean state) {
        if (state) {
            return "open";
        } else {
            return "closed";
        }
    }
}
