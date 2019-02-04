<%--
  Created by IntelliJ IDEA.
  User: 5rKB5bPlusD
  Date: 2018/12/27
  Time: 4:25
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
<style>
    .tableMore {
        margin-left: 20px;
        width: 90%;
    }

    .tableMore td {
        width: 60%;
        text-align: left;
    }

    .tableMore td:first-child {
        width: 30%;
        text-align: center;
    }
</style>
<div style="padding: 5px">
    <div id="more" class="easyui-window" title="详情"
         data-options="modal:true,closed:true,iconCls:'icon-edit',minimizable:false,maximizable:false"
         style="width:500px;height:300px;padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <table class="tableMore" cellpadding="5">

            </table>
        </div>
    </div>
    <div id="assignRole" class="easyui-window" title="分配"
         data-options="modal:true,closed:true,iconCls:'icon-edit',minimizable:false,maximizable:false"
         style="width:600px;height:300px;padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" style="padding:10px;">
                <table id="tableAssignRole" cellpadding="5" align="center">

                </table>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
                   onclick="confirmAssign()" style="width:80px">确定</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
                   onclick="cancelAssign()" style="width:80px">取消</a>
            </div>
        </div>
    </div>
    <div style="margin-left: 20px">
        <h3>用户列表</h3>
        <table id="userList"></table>
    </div>
    <div id="roleAssignBar" style="text-align: right">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-more" plain="true"
           onclick="more()">详情</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="assignRole()">分配角色</a>
    </div>
</div>
<script>
    $('#userList').datagrid({
        url: '/user/allUser',
        striped: true,
        singleSelect: true,
        width: '80%',
        toolbar: '#roleAssignBar',
        columns: [[
            {field: 'userId', title: '编号', width: 50},
            {field: 'username', title: '用户名称', width: 100},
            {field: 'roleId', title: '角色编号', width: 50, hidden: true},
            {field: 'roleName', title: '角色名称', width: 200},
            {field: 'orderLv', title: '工单等级', width: 200},
            {field: 'commonLv', title: '权限列表', width: 200}
        ]]
    });

    function tableAssignRole() {
        $("#tableAssignRole").datagrid({
            url: '/user/allRole',
            striped: true,
            width: '100%',
            singleSelect: true,
            columns: [[
                {field: 'id', title: '编号', width: 50},
                {field: 'roleName', title: '角色名称', width: 200},
                {field: 'orderLv', title: '工单级别', width: 200},
                {field: 'commonLv', title: '权限列表', width: 100}
            ]]
        });
    }

    tableAssignRole();

    function more() {
        let select = $("#userList").datagrid("getSelected");
        if (select !== undefined) {
            $.ajax({
                type: "get",
                url: "/user/userDetail",
                data: {
                    userId: select.userId
                },
                success: function (result) {
                    if (result.success && result.data.roleName != null) {
                        let res = result.data;
                        let html = '<tr>' +
                            '                    <td>用户名</td>' +
                            '                    <td>' + res.username + '</td>' +
                            '                </tr>' +
                            '                <tr>' +
                            '                    <td>角色名</td>' +
                            '                    <td>' + res.roleName + '</td>' +
                            '                </tr>';
                        let order = res.order;
                        for (let key in order) {
                            html += '<tr>' +
                                '                    <td>' + key + '等级</td>' +
                                '                    <td>' + order[key] + '</td>' +
                                '                </tr>';
                        }

                        html += '<tr>' +
                            '                    <td>拥有权限</td>' +
                            '                    <td>' + res.common + '</td>' +
                            '                </tr>';

                        $(".tableMore").html(html);
                        $("#more").window("open");
                    }
                }
            });
        }
    }

    function assignRole() {
        let select = $("#userList").datagrid("getSelected");
        if (select !== undefined) {
            tableAssignRole();
            $("#assignRole").window("open");
        }
    }

    function confirmAssign() {
        let select = $("#userList").datagrid("getSelected");
        if (select !== undefined) {
            let role = $("#tableAssignRole").datagrid("getSelected");
            $.ajax({
                type: "get",
                url: "/user/setRole",
                data: {
                    userId: select.userId,
                    roleId: role.id
                },
                success: function (result) {
                    $("#assignRole").window("close");
                    $.messager.show({
                        title: '提示',
                        msg: '<h3>' + result.message + '</h3>',
                        timeout: 1000,
                        showType: 'slide'
                    });
                    if (result.success) {
                        let tab = $('#userRight').tabs('getSelected');
                        let url = $(tab.panel('options')).attr('href');
                        tab.panel('refresh', url);
                    }
                }
            });
            $("#tableAssignRole").datagrid("unselectAll");
        }
    }

    function cancelAssign() {
        $("#tableAssignRole").datagrid("unselectAll");
        $("#assignRole").window("close");
    }
</script>
</body>
</html>
