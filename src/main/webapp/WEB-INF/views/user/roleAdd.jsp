<%--
  Created by IntelliJ IDEA.
  User: 5rKB5bPlusD
  Date: 2018/12/23
  Time: 21:52
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
        <h3>添加角色（工单等级从低到高分配）</h3>
        <input id="roleName" class="easyui-textbox" data-options="prompt:'角色名'"
               style="width:20%;height:32px">
        <input id="orderType" class="easyui-combogrid" style="width:15%" value="工单种类" editable="false" data-options="
			panelWidth: 300,
			idField: 'id',
			textField: 'name',
			url: '/order/allOrderType',
			method: 'get',
			columns: [[
				{field:'id',title:'编号',width:40,hidden:true},
				{field:'name',title:'工单种类',width:100},
				{field:'rank',title:'要求权限编号',width:100},
			]],
			fitColumns: true
		">
        <input id="order" class="easyui-combogrid" style="width:30%" value="工单等级" editable="false">
        <input id="common" class="easyui-combogrid" style="width:15%" value="赋予权限" editable="false" data-options="
			panelWidth: 200,
			multiple: true,
			idField: 'permissionId',
			textField: 'permissionName',
			url: '/auth/allPermission',
			method: 'get',
			columns: [[
			    {field:'ck',checkbox:true},
				{field:'permissionId',title:'编号',width:40},
				{field:'permissionName',title:'权限名称',width:100},
			]],
			fitColumns: true
		">
        <a style="margin-left: 20px;width: 60px" href="javascript:void(0)" class="easyui-linkbutton" onclick="setUp()">创建</a>
        <h3>已有角色列表</h3>
        <div>
            <table id="showRole" align="center" style="height: 250px;width: 700px"></table>
        </div>
    </div>
    <div id="roleAddBar" style="text-align: right">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="deleteRole()">删除角色</a>
    </div>
</div>
<script type="application/javascript">
    $("#orderType").combogrid({
        onChange: function (n, o) {
            $('#order').combogrid({
                panelWidth: 500,
                idField: 'lv',
                textField: 'lvMean',
                url: '/order/allRank?groupId=' + n,
                method: 'get',
                columns: [[
                    {field: 'lv', title: '等级', width: 40},
                    {field: 'lvMean', title: '等级含义', width: 100},
                    {field: 'roleMean', title: '适用角色', width: 100},
                    {field: 'orderGroup', title: '工单类别', width: 100},
                ]],
                fitColumns: true
            });
        }
    });

    $('#showRole').datagrid({
        url: '/user/allRole',
        striped: true,
        singleSelect: true,
        toolbar: "#roleAddBar",
        columns: [[
            {field: 'id', title: '编号', width: 100},
            {field: 'roleName', title: '角色名称', width: 200},
            {field: 'orderLv', title: '工单级别', width: 200},
            {field: 'commonLv', title: '权限列表', width: 100}
        ]]
    });

    function setUp() {
        $.ajax({
            type: "post",
            url: "/user/addRole",
            data: {
                roleName: $('#roleName').val(),
                orderType: $('#orderType').combogrid('getValue') === "工单种类" ? "" : $('#orderType').combogrid('getValue'),
                order: $('#order').combogrid('getValue') === "工单等级" ? "" : $('#order').combogrid('getValue'),
                common: $('#common').combogrid('getValues').toString() === "赋予权限" ? "" : $('#common').combogrid('getValues').toString()
            },
            success: function (result) {
                console.log(result);
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
    }

    function deleteRole() {
        let select = $("#showRole").datagrid("getSelected");
        let index = $("#showRole").datagrid("getRowIndex", select);
        $.messager.confirm('确认', '确认删除角色：' + select.roleName + "?", function (r) {
            if (r) {
                $.ajax({
                    type: "get",
                    url: "/user/deleteRole",
                    data: {
                        roleId: select.id
                    },
                    success: function (result) {
                        $.messager.show({
                            title: '提示',
                            msg: '<h3>' + result.message + '</h3>',
                            timeout: 1000,
                            showType: 'slide'
                        });
                        if (result.success) {
                            $("#showRole").datagrid("deleteRow", index);
                        }
                    }
                });
            }
        });
    }
</script>
</body>
</html>
