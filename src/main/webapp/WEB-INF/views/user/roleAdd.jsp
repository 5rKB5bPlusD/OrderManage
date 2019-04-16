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
    <div id="editPermissionWindow" class="easyui-dialog" title="修改权限" style="width:800px;height:600px;padding: 10px"
         data-options="
                closed: true,
                cache: false,
                draggable: false,
                modal: true,
				iconCls: 'icon-save',
				buttons: [{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function(){
						$('#editPermissionWindow').dialog('close');
						let tab = $('#userRight').tabs('getSelected');
                        let url = $(tab.panel('options')).attr('href');
                        tab.panel('refresh', url);
					}
				}]
			">
        <h3>工单权限</h3>
        <input id="editOrderType" class="easyui-combogrid" style="width:20%" value="工单种类" editable="false"
               data-options="
			panelWidth: 300,
			idField: 'groupId',
			textField: 'name',
			url: '/order/allOrderType',
			method: 'get',
			columns: [[
				{field:'name',title:'工单种类',width:100},
				{field:'rank',title:'要求权限编号',width:100}
			]],
			fitColumns: true
		">
        <input id="editOrderLv" class="easyui-combogrid" style="width:40%" value="工单等级" editable="false">
        <a style="margin-left: 20px" href="javascript:void(0)" class="easyui-linkbutton" onclick="addOrderLv()">添加</a>
        <a style="margin-left: 20px" href="javascript:void(0)" class="easyui-linkbutton" onclick="removeOrderLv()">移除选中权限</a>
        <table id="orderPermission" class="easyui-datagrid" style="width:90%;height:200px"
               data-options="fitColumns:true,singleSelect:true">
            <thead>
            <tr>
                <th data-options="field:'orderGroupStr'" style="width: 40%">类别</th>
                <th data-options="field:'roleMean'" style="width: 40%">适用角色</th>
                <th data-options="field:'lv'" style="width: 20%">等级</th>
            </tr>
            </thead>
        </table>
        <h3>功能权限</h3>
        <a style="float: right" href="javascript:void(0)" class="easyui-linkbutton"
           onclick="confirmEditCommonPermission()">确认修改</a>
        <table id="editCommonPermission" class="easyui-datagrid" style="width:90%;height:300px"
               data-options="url:'/auth/allPermission',fitColumns:true,singleSelect:false,checkOnSelect:true,selectOnCheck:true,idField:'permissionId'">
            <thead>
            <tr>
                <th data-options="field:'ck',checkbox:true"></th>
                <th data-options="field:'permissionId'" style="width: 20%">编号</th>
                <th data-options="field:'permissionName'" style="width: 60%">权限名称</th>
            </tr>
            </thead>
        </table>
    </div>
    <div style="margin-left: 20px">
        <h3>添加角色（工单等级从低到高分配，新建时只能选择一个工单权限）</h3>
        <input id="roleName" class="easyui-textbox" data-options="prompt:'角色名'"
               style="width:20%;height:32px">
        <input id="orderType" class="easyui-combogrid" style="width:15%" value="工单种类" editable="false" data-options="
			panelWidth: 300,
			idField: 'groupId',
			textField: 'name',
			url: '/order/allOrderType',
			method: 'get',
			columns: [[
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
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="editPermission()">修改权限</a>
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
                    {field: 'orderGroupStr', title: '工单类别', width: 100},
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
            {field: 'commonLv', title: '权限列表', width: 150}
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

    function editPermission() {
        $("#editCommonPermission").datagrid("uncheckAll");
        let select = $("#showRole").datagrid("getSelected");
        $("#orderPermission").datagrid({
            url: "/order/showEditOrderLv?roleId=" + select.id
        });
        let arr = select.commonLv.split(",");
        $.each(arr, function (index, value) {
            $("#editCommonPermission").datagrid("selectRecord", value);
        });
        $("#editPermissionWindow").dialog("open");

    }

    $("#editOrderType").combogrid({
        onChange: function (n, o) {
            $('#editOrderLv').combogrid({
                panelWidth: 500,
                idField: 'lv',
                textField: 'lvMean',
                url: '/order/allRank?groupId=' + n,
                method: 'get',
                columns: [[
                    {field: 'lv', title: '等级', width: 40},
                    {field: 'lvMean', title: '等级含义', width: 100},
                    {field: 'roleMean', title: '适用角色', width: 100},
                    {field: 'orderGroupStr', title: '工单类别', width: 100},
                ]],
                fitColumns: true
            });
        }
    });

    function addOrderLv() {
        let select = $("#showRole").datagrid("getSelected");
        let groupId = $("#editOrderType").combogrid("getValue") === "工单种类" ? "-1" : $("#editOrderType").combogrid("getValue");
        let orderLv = $("#editOrderLv").combogrid("getValue") === "工单等级" ? "-1" : $("#editOrderLv").combogrid("getValue");
        $.ajax({
            type: "get",
            url: "/user/addOrderLv",
            data: {
                roleId: select.id,
                groupId: groupId,
                orderLv: orderLv
            },
            success: function (result) {
                $.messager.show({
                    title: '提示',
                    msg: '<h3>' + result.message + '</h3>',
                    timeout: 1000,
                    showType: 'slide'
                });
                if (result.success) {
                    $("#orderPermission").datagrid("reload");
                }
            }
        });
    }

    function removeOrderLv() {
        let select = $("#showRole").datagrid("getSelected");
        let select2 = $("#orderPermission").datagrid("getSelected");
        $.ajax({
            type: "get",
            url: "/user/removeOrderLv",
            data: {
                roleId: select.id,
                groupId: select2.orderGroup
            },
            success: function (result) {
                $.messager.show({
                    title: '提示',
                    msg: '<h3>' + result.message + '</h3>',
                    timeout: 1000,
                    showType: 'slide'
                });
                if (result.success) {
                    $("#orderPermission").datagrid("reload");
                }
            }
        });
    }

    function confirmEditCommonPermission() {
        let select = $("#showRole").datagrid("getSelected");
        let selections = $("#editCommonPermission").datagrid("getSelections");
        let commonLv = "";
        $.each(selections, function (index, value) {
            commonLv += value.permissionId + ",";
        });
        $.ajax({
            type: "get",
            url: "/user/editCommonPermission",
            data: {
                roleId: select.id,
                commonLv: commonLv
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
</script>
</body>
</html>
