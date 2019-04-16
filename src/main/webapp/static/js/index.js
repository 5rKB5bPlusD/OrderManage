let tabs = [[], [], [], []];//0工单填报,1用户管理,2公告管理,3工作日志
let index = [0, 0, 0, 0];//0工单填报,1用户管理,2公告管理,3工作日志

//增加tab页
function addPanel(typeId, itemId, title, src) {
    if (typeId === 1) {
        if (exist(typeId, itemId)) {
            $('#orderRight').tabs('select', tabs[0].indexOf(itemId));
            return;
        }
        $('#orderRight').tabs('add', {
            title: title,
            href: "/order/load?src=" + src + "&orderId=" + itemId,
            closable: true
        });
    } else if (typeId === 2) {
        if (exist(typeId, itemId)) {
            $('#userRight').tabs('select', tabs[1].indexOf(itemId));
            return;
        }
        $('#userRight').tabs('add', {
            title: title,
            href: "/user/load?src=" + src,
            closable: true
        });
    } else if (typeId === 3) {
        if (exist(typeId, itemId)) {
            $('#noticeRight').tabs('select', tabs[2].indexOf(itemId));
            return;
        }
        $('#noticeRight').tabs('add', {
            title: title,
            href: "/notice/load?src=" + src,
            closable: true
        });
    } else if (typeId === 4) {
        if (exist(typeId, itemId)) {
            $('#jobRight').tabs('select', tabs[3].indexOf(itemId));
            return;
        }
        $('#jobRight').tabs('add', {
            title: title,
            href: "/job/load?src=" + src,
            closable: true
        });
    }
}

//判断tab是否已存在
function exist(typeId, itemId) {
    if (tabs[typeId - 1].indexOf(itemId) < 0) {
        tabs[typeId - 1].splice(index[typeId - 1], 0, itemId);
        index[typeId - 1]++;
        return false;
    } else {
        return true;
    }
}

//工单填报删除tab页同时移除对应id
$('#orderRight').tabs({
    onBeforeClose: function (title, tabsIndex) {
        tabs[0].splice(tabsIndex, 1);
        index[0]--;
    }
});

//用户管理删除tab页同时移除对应id
$('#userRight').tabs({
    onBeforeClose: function (title, tabsIndex) {
        tabs[1].splice(tabsIndex, 1);
        index[1]--;
    }
});

//通知管理删除tab页同时移除对应id
$('#noticeRight').tabs({
    onBeforeClose: function (title, tabsIndex) {
        tabs[2].splice(tabsIndex, 1);
        index[2]--;
    }
});

//工作日志删除tab页同时移除对应id
$('#jobRight').tabs({
    onBeforeClose: function (title, tabsIndex) {
        tabs[3].splice(tabsIndex, 1);
        index[3]--;
    }
});

//登出
function logout() {
    sessionStorage.removeItem("username");
    window.location.href = "/user/logout";
}

//显示通知
function showNotice() {
    $("#showNotice").datagrid({
        url: "/notice/allNotice",
        width: '100%',
        height: '100%',
        striped: true,
        singleSelect: true,
        columns: [[
            {field: 'noticeId', title: '编号', width: 50, hidden: true},
            {field: 'date', title: '日期', width: '20%'},
            {
                field: 'title', title: '标题', width: '80%',
                formatter: function (value, row, index) {
                    return "<a href='/notice/showNotice?noticeId=" + row.noticeId + "' target='_blank'>" + value + "</a>"
                }
            }
        ]]
    });
    $("#noticeWindow").dialog("open");
}