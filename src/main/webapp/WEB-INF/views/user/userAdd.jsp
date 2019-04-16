<%--
  Created by IntelliJ IDEA.
  User: 5rKB5bPlusD
  Date: 2018/12/27
  Time: 19:36
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
    <div style="margin-left: 20px">
        <h3>添加用户</h3>
        <form id="createUser" method="post">
            <input name="username" class="easyui-textbox" data-options="prompt:'角色名'"
                   style="width:20%;height:32px">
            <input name="password" class="easyui-textbox" data-options="prompt:'密码'"
                   style="width:20%;height:32px">
            <a style="margin-left: 20px;width: 60px" href="javascript:void(0)" class="easyui-linkbutton"
               onclick="createUser()">创建</a>
        </form>
        <h3>用户列表</h3>
        <div>
            <table id="showUser" align="center" style="height: 250px;width: 700px"></table>
        </div>
    </div>
    <div id="deleteUser" style="text-align: right">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="resetUser()">重置用户密码</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="deleteUser()">删除用户</a>
    </div>
</div>
<script type="application/javascript">
    $("#showUser").datagrid({
        url: '/user/allUser',
        striped: true,
        singleSelect: true,
        width: '80%',
        toolbar: "#deleteUser",
        columns: [[
            {field: 'userId', title: '编号', width: 50},
            {field: 'username', title: '用户名称', width: 100},
            {field: 'roleId', title: '角色编号', width: 50, hidden: true},
            {field: 'roleName', title: '角色名称', width: 200},
            {field: 'orderLv', title: '工单等级', width: 200},
            {field: 'commonLv', title: '权限列表', width: 200}
        ]]
    });

    function createUser() {
        $("#createUser").form("submit", {
            url: "/user/createUser",
            success: function (result) {
                let res = eval('(' + result + ')');
                $.messager.show({
                    title: '提示',
                    msg: '<h3>' + res.message + '</h3>',
                    timeout: 1000,
                    showType: 'slide'
                });
                if (res.success) {
                    let tab = $('#userRight').tabs('getSelected');
                    let url = $(tab.panel('options')).attr('href');
                    tab.panel('refresh', url);
                }
            }
        });
    }

    function resetUser() {
        let select = $("#showUser").datagrid("getSelected");
        $.messager.confirm('确认', '确认重置用户：' + select.username + "的密码?", function (r) {
            if (r) {
                $.ajax({
                    type: "get",
                    url: "/user/resetUser",
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
                    }
                });
            }
        })
    }

    function deleteUser() {
        let select = $("#showUser").datagrid("getSelected");
        let index = $("#showUser").datagrid("getRowIndex", select);
        if (select !== undefined) {
            $.messager.confirm('确认', '确认删除用户：' + select.username + "?", function (r) {
                if (r) {
                    $.ajax({
                        type: "get",
                        url: "/user/deleteUser",
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
                                $("#showUser").datagrid("deleteRow", index);
                            }
                        }
                    });
                }
            });
        }
    }
</script>
</body>
</html>
