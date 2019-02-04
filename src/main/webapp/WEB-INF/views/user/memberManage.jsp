<%--
  Created by IntelliJ IDEA.
  User: 5rKB5bPlusD
  Date: 2019/1/30
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <%@ page isELIgnored="false" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <title>Title</title>
</head>
<body>
<div style="padding: 5px">
    <div id="showMember" class="easyui-window" title="成员列表"
         data-options="modal:true,closed:true,iconCls:'icon-edit',minimizable:false,maximizable:false"
         style="width:600px;height:400px;padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" style="padding:10px;">
                <table id="memberList" cellpadding="5" style="width: 100%;height: 100%"></table>
                <div id="showMemberBar" style="text-align: right">
                    <input id="userSearch" class="easyui-combobox" panelHeight="auto" style="width:200px"
                           value="选择用户"></select>
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true"
                       onclick="addMember()">增加成员</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true"
                       onclick="removeMember()">移除成员</a>
                </div>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
                   onclick="closeMember()" style="width:80px">关闭</a>
            </div>
        </div>
    </div>
    <div style="margin-left: 20px">
        <h3>已有团队列表</h3>
        <div>
            <table id="teamList" align="center" style="height: 250px;width: 700px"></table>
            <div id="memberManageBar" style="text-align: right">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true"
                   onclick="showMember()">查看成员</a>
            </div>
        </div>
    </div>
</div>
<script>
    $('#teamList').datagrid({
        url: '/user/allTeam',
        striped: true,
        singleSelect: true,
        toolbar: "#memberManageBar",
        columns: [[
            {field: 'teamId', title: '编号', width: 80},
            {field: 'teamName', title: '团队名称', width: 200},
            {field: 'teamName', title: '团队描述', width: 200},
            {field: 'leaderName', title: '负责人', width: 200},
        ]]
    });

    function showMember() {
        let select = $("#teamList").datagrid("getSelected");
        if (select !== null) {
            $('#userSearch').combobox({
                url: '/user/allUser',
                valueField: 'userId',
                textField: 'username'
            });

            $('#memberList').datagrid({
                url: '/user/getMember?teamId=' + select.teamId,
                striped: true,
                singleSelect: true,
                toolbar: "#showMemberBar",
                columns: [[
                    {field: 'userId', title: '编号', width: 100},
                    {field: 'username', title: '用户名', width: 200},
                    {field: 'roleName', title: '角色', width: 200}
                ]]
            });
            $("#showMember").window("open");
        }
    }

    function closeMember() {
        $("#showMember").window("close");
    }

    function addMember() {
        let userId = $("#userSearch").combobox("getValue");
        let username = $("#userSearch").combobox("getText");
        let select = $("#teamList").datagrid("getSelected");
        if (userId !== null) {
            $.ajax({
                type: "get",
                url: "/user/addMember",
                data: {
                    userId: userId,
                    teamId: select.teamId
                },
                success: function (result) {
                    $.messager.show({
                        title: '提示',
                        msg: '<h3>' + result.message + '</h3>',
                        timeout: 1000,
                        showType: 'slide'
                    });
                    if (result.success) {
                        $("#memberList").datagrid("insertRow", {
                            index: 0,
                            row: {
                                userId: userId,
                                username: username,
                                roleName: result.data
                            }
                        });
                    }
                }
            });
        }
    }

    function removeMember() {
        let select = $("#memberList").datagrid("getSelected");
        let index = $("#memberList").datagrid("getRowIndex", select);
        if (select === null) {
            return;
        }
        $.messager.confirm('确认', '确认移除该成员：' + select.username + "?", function (r) {
            if (r) {
                $.ajax({
                    type: "get",
                    url: "/user/removeMember",
                    data: {
                        userId: select.userId
                    },
                    success: function (result) {
                        $.messager.show({
                            title: '提示',
                            msg: '<h3>' + result.message + '</h3>',
                            timeout: 1000,
                            showType: 'slide'
                        });
                        if (result.success) {
                            $("#memberList").datagrid("deleteRow", index);
                        }
                    }
                });
            }
        });
    }
</script>
</body>
</html>
