<%--
  Created by IntelliJ IDEA.
  User: 5rKB5bPlusD
  Date: 2019/1/27
  Time: 22:57
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
    <div style="margin-left: 20px;">
        <h3>工作管理</h3>
        <table id="jobManage"></table>
        <div id="jobManageBar" style="padding:5px;height:auto">
            <div>
                条件: &nbsp;&nbsp;
                <input id="jobNameSearch" class="easyui-textbox" style="width:200px" data-options="prompt:'工作名称'">
                <input id="teamSearch" class="easyui-combobox" editable="false" value="选择团队">
                <input id="userSearch" class="easyui-combobox" editable="false" value="选择用户">
                <input id="dateSearch" type="text" class="easyui-datebox" editable="false"
                       data-options="formatter:myformatter,parser:myparser">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick='clearSearch()'>清除</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchJob()">查看成员</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchJobLeader()">查看负责人</a>
            </div>
            <div style="margin-bottom:5px">
                <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="selectAll()">全选</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="unSelectAll()">取消全选</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" onclick="deleteSelect()">删除选中</a>
            </div>
        </div>
    </div>
</div>
<script>
    $('#teamSearch').combobox({
        url: '/user/allTeam',
        valueField: 'teamId',
        textField: 'teamName',
        onSelect: function (record) {
            $('#userSearch').combobox({
                url: '/user/getMember?teamId=' + record.teamId,
                valueField: 'userId',
                textField: 'username'
            });
        }
    });

    function selectAll() {
        $("#jobManage").datagrid("selectAll");
    }

    function unSelectAll() {
        $("#jobManage").datagrid("unselectAll");
    }

    function clearSearch() {
        $("#jobNameSearch").textbox("clear");
        $("#teamSearch").combobox("reset");
        $("#userSearch").combobox("reset");
        $("#dateSearch").datebox("reset");
    }

    function deleteSelect() {
        let selections = $("#jobManage").datagrid("getSelections");
        let jobId = "";
        $.each(selections, function (index, value) {
            jobId += value.jobId + ",";
        });
        $.ajax({
            type: "get",
            url: "/job/deleteJob",
            data: {
                jobId: jobId
            },
            success: function (result) {
                $.messager.show({
                    title: '提示',
                    msg: '<h3>' + result.message + '</h3>',
                    timeout: 1000,
                    showType: 'slide'
                });
                if (result.success) {
                    searchJob();
                }
            }
        })
    }

    function searchJob() {
        let jobName = $("#jobNameSearch").textbox("getText");
        let teamId = $("#teamSearch").combobox("getValue") === "选择团队" || $("#teamSearch").combobox("getValue") === "" ? "-1" : $("#teamSearch").combobox("getValue");
        let userId = $("#userSearch").combobox("getValue") === "选择用户" || $("#userSearch").combobox("getValue") === "" ? "-1" : $("#userSearch").combobox("getValue");
        let date = $("#dateSearch").datebox("getValue");

        $("#jobManage").datagrid({
            url: '/job/searchJob',
            striped: true,
            singleSelect: false,
            toolbar: "#jobManageBar",
            queryParams: {
                jobName: jobName,
                teamId: teamId,
                userId: userId,
                date: date
            },
            columns: [[
                {field: 'jobId', title: '编号', width: 100},
                {field: 'jobName', title: '工作名称', width: 200},
                {
                    field: 'speed', title: '进度', width: 200,
                    formatter: function (value, row, index) {
                        return '<div class="easyui-progressbar" data-options="value:' + value + '" style="width:100%"></div>';
                    }
                },
                {field: 'date', title: '日期', width: 200},
                {field: 'username', title: '用户名', width: 200},
                {field: 'teamName', title: '所属团队及角色', width: 200}
            ]],
            onLoadSuccess:function (data) {
                $.parser.parse("#jobRight");
            }
        });
    }

    function searchJobLeader() {
        let jobName = $("#jobNameSearch").textbox("getText");
        let teamId = $("#teamSearch").combobox("getValue") === "选择团队" ? "-1" : $("#teamSearch").combobox("getValue");
        let date = $("#dateSearch").datebox("getValue");

        $("#jobManage").datagrid({
            url: '/job/searchJobLeader',
            striped: true,
            singleSelect: false,
            toolbar: "#jobManageBar",
            queryParams: {
                jobName: jobName,
                teamId: teamId,
                date: date
            },
            columns: [[
                {field: 'jobId', title: '编号', width: 100},
                {field: 'jobName', title: '工作名称', width: 200},
                {field: 'date', title: '日期', width: 200},
                {field: 'username', title: '用户名', width: 200},
                {field: 'teamName', title: '所属团队及角色', width: 200}
            ]]
        })
    }

    function myformatter(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    }

    function myparser(s) {
        if (!s) return new Date();
        var ss = (s.split('-'));
        var y = parseInt(ss[0], 10);
        var m = parseInt(ss[1], 10);
        var d = parseInt(ss[2], 10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
            return new Date(y, m - 1, d);
        } else {
            return new Date();
        }
    }
</script>
</body>
</html>
