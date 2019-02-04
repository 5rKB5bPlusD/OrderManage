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
    <style>
        .teamAdd {
            width: 100%;
        }

        .teamAdd td {
            height: 40px;
            padding: 10px 5px;
        }

        .teamAdd td:first-child {
            width: 20%;
        }
    </style>
    <div id="addTeam" class="easyui-window" title="团队"
         data-options="modal:true,closed:true,iconCls:'icon-edit',minimizable:false,maximizable:false"
         style="width:600px;height:400px;padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" style="padding:10px;">
                <form id="formTeamAdd" method="post">
                    <table class="teamAdd">
                        <tr>
                            <td>编号（只读）</td>
                            <td>
                                <input id="teamId" class="easyui-textbox" type="text" name="teamId"
                                       data-options="readonly:'true'">
                            </td>
                        </tr>
                        <tr>
                            <td>团队名称</td>
                            <td>
                                <input id="teamName" class="easyui-textbox" type="text" name="teamName"
                                       style="width: 100%;height: 40px;"
                                       data-options="prompt:'团队名称'">
                            </td>
                        </tr>
                        <tr>
                            <td>团队描述</td>
                            <td>
                                <input id="teamDescribe" class="easyui-textbox" type="text" name="teamDescribe"
                                       style="width: 100%;height: 100px;"
                                       data-options="prompt:'团队描述',multiline:true">
                            </td>
                        </tr>
                        <tr>
                            <td>负责人</td>
                            <td>
                                <input id="leaderName" class="easyui-combogrid" style="width:50%" name="leaderName"
                                       value="请选择负责人" editable="false"
                                       data-options="
                                    panelWidth: 300,
                                    idField: 'userId',
                                    textField: 'username',
                                    url: '/user/allUser',
                                    method: 'get',
                                    columns: [[
                                        {field:'userId',title:'编号',width:40,hidden:true},
                                        {field:'username',title:'用户名',width:100},
                                        {field:'roleName',title:'角色',width:100},
                                    ]],
                                    fitColumns: true
                                ">
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
                   onclick="confirmTeamAdd()" style="width:80px">确定</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
                   onclick="cancelTeamAdd()" style="width:80px">取消</a>
            </div>
        </div>
    </div>
    <div style="margin-left: 20px">
        <h3>已有团队列表</h3>
        <div>
            <table id="showTeam" align="center" style="height: 250px;width: 700px"></table>
        </div>
    </div>
    <div id="teamAddBar" style="text-align: right">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="deleteTeam()">解散团队</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="addTeam()">新增团队</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="editTeam()">编辑团队</a>
    </div>
</div>
<script type="application/javascript">
    $('#showTeam').datagrid({
        url: '/user/allTeam',
        striped: true,
        singleSelect: true,
        toolbar: "#teamAddBar",
        columns: [[
            {field: 'teamId', title: '编号', width: 80},
            {field: 'teamName', title: '团队名称', width: 200},
            {field: 'teamDescribe', title: '团队描述', width: 200},
            {field: 'leaderName', title: '负责人', width: 200},
        ]]
    });

    function addTeam() {
        $('#formTeamAdd').form('clear');
        $("#addTeam").window("open");
    }

    function editTeam() {
        let select = $("#showTeam").datagrid("getSelected");
        if (select !== null) {
            $('#formTeamAdd').form('load', {
                teamId: select.teamId,
                teamName: select.teamName,
                teamDescribe: select.teamDescribe
            });
            $("#leaderName").combogrid("setValue", select.leaderId);
            $("#addTeam").window("open");
        }
    }

    function deleteTeam() {
        let select = $("#showTeam").datagrid("getSelected");
        let index = $("#showTeam").datagrid("getRowIndex", select);
        if (select === null) {
            return;
        }
        $.messager.confirm('确认', '确认解散该团队：' + select.teamName + "?", function (r) {
            if (r) {
                $.ajax({
                    type: "get",
                    url: "/user/deleteTeam",
                    data: {
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
                            $("#showTeam").datagrid("deleteRow", index);
                        }
                    }
                });
            }
        });
    }

    function confirmTeamAdd() {
        $('#formTeamAdd').form('submit', {
            url: $("#teamId").val() === "" ? "/user/addTeam" : "/user/editTeam",
            onSubmit: function (param) {
                param.leaderId = $("#leaderName").combogrid("getValue");
            },
            success: function (result) {
                let res = eval('(' + result + ')');
                $.messager.show({
                    title: '提示',
                    msg: '<h3>' + res.message + '</h3>',
                    timeout: 1000,
                    showType: 'slide'
                });
                if (res.success) {
                    $("#addTeam").window("close");
                    let tab = $('#userRight').tabs('getSelected');
                    let url = $(tab.panel('options')).attr('href');
                    tab.panel('refresh', url);
                }
            }
        });
    }

    function cancelTeamAdd() {
        $('#formTeamAdd').form('clear');
        $("#addTeam").window("close");
    }
</script>
</body>
</html>
