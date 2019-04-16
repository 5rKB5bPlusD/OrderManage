<%--
  Created by IntelliJ IDEA.
  User: 5rKB5bPlusD
  Date: 2019/2/11
  Time: 15:47
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
    <h3>管理工单（双击查看工单）</h3>
    <table id="orderManage"></table>
    <div id="orderManageBar" style="padding:5px;height:auto">
        <div>
            条件: &nbsp;&nbsp;
            <input id="eomsIdSearch" class="easyui-textbox" style="width:200px" data-options="prompt:'工单EOMS编号'">
            <input id="orderTitleSearch" class="easyui-textbox" style="width:200px" data-options="prompt:'工单名称'">
            <input id="orderTypeSearch" class="easyui-combobox" editable="false" value="工单种类">
            <input id="statusSearch" class="easyui-combobox" editable="false" value="选择状态">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick='clearSearch()'>清除</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="searchOrder()">查找工单</a>
        </div>
    </div>
</div>
<script type="application/javascript">
    $("#orderTypeSearch").combobox({
        url: '/order/allOrderType',
        valueField: 'groupId',
        textField: 'name',
        onSelect: function (record) {
            $('#statusSearch').combobox({
                url: '/order/allRank?groupId=' + record.groupId,
                valueField: 'lv',
                textField: 'lvMean'
            });
        }
    });

    function clearSearch() {
        $("#eomsIdSearch").textbox("clear");
        $("#orderTitleSearch").textbox("clear");
        $("#orderTypeSearch").combobox("reset");
        $("#statusSearch").combobox("reset");
    }

    function searchOrder() {
        let eomsId = $("#eomsIdSearch").textbox("getText");
        let title = $("#orderTitleSearch").textbox("getText");
        let orderType = $("#orderTypeSearch").combobox("getValue") === "工单种类" || $("#orderTypeSearch").combobox("getValue") === "" ? "-1" : $("#orderTypeSearch").combobox("getValue");
        let lv = $("#statusSearch").combobox("getValue") === "选择状态" || $("#statusSearch").combobox("getValue") === "" ? "-1" : $("#statusSearch").combobox("getValue");
        $("#orderManage").datagrid({
            url: '/order/searchOrder',
            striped: true,
            singleSelect: true,
            toolbar: "#orderManageBar",
            queryParams: {
                eomsId: eomsId,
                title: title,
                orderType: orderType,
                lv: lv
            },
            columns: [[
                {field: 'orderId', title: '编号', width: 100},
                {field: 'eomsId', title: 'EOMS编号', width: 200},
                {field: 'type', title: '工单种类', width: 200},
                {field: 'title', title: '工单名称', width: 200},
                {field: 'status', title: '当前状态', width: 200}
            ]],
            onDblClickRow: function (rowIndex, rowData) {
                window.open("/order/load?src=" + rowData.src + "&orderId=" + rowData.orderId);
            }
        })
    }
</script>
</body>
</html>
