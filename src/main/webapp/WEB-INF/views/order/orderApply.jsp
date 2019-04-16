<jsp:useBean id="order" scope="request" type="com.graduationDesign.model.vo.OrderApplyVO"/>
<%--
  Created by IntelliJ IDEA.
  User: 5rKB5bPlusD
  Date: 2019/2/8
  Time: 17:11
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
<div style="padding: 20px">
    <style>
        .applyTable {
            width: 80%;
            border-color: #aaaaaa;
        }

        .applyTable td {
            padding: 10px 5px;
            width: 25%;
            height: 40px;
            word-break: break-word;
            text-align: center;
        }
    </style>
    <c:if test="${order.lv==order.type&&order.status==0}">
        <div style="position: absolute; top: 60px;">
            <a style="float: left" href="javascript:void(0)" class="easyui-linkbutton" onclick="approval()">审批</a>
        </div>
        <div id="approvalWindow" class="easyui-dialog" title="审批" style="width:450px;height:310px;padding:10px"
             data-options="
                closed: true,
                cache: false,
                modal: true,
				iconCls: 'icon-save',
				buttons: [{
					text:'同意',
					iconCls:'icon-ok',
					handler:function(){
						confirm(1);
					}
				},{
					text:'驳回',
					iconCls:'icon-cancel',
					handler:function(){
						confirm(2);
					}
				}]
			">
            <form id="formApproval" method="post">
                <input class="easyui-textbox" data-options="multiline:true,prompt:'审批意见'" type="text"
                       style="height: 200px;width: 100%" name="approvalValue">
            </form>
        </div>
    </c:if>
    <h3 align="center">物资申请表</h3>
    <h5 align="center">编号：${order.orderId}</h5>
    <table class="applyTable" align="center" border="1" cellspacing="0" cellpadding="0">
        <tr>
            <td><b>项目</b></td>
            <td>${order.title}</td>
            <td><b>申请人</b></td>
            <td>${order.applicant}</td>
        </tr>
        <tr>
            <td><b>申请日期</b></td>
            <td>${order.applyDate}</td>
            <td><b>采购或库存</b></td>
            <td>${order.typeStr}</td>
        </tr>
        <tr>
            <td style="height: 100px"><b>物资用途</b></td>
            <td colspan="3" style="text-align: left">${order.purpose}</td>
        </tr>
        <tr>
            <td rowspan="${order.material.size()+1}"><b>物资详单</b></td>
            <td><b>编号</b></td>
            <td><b>物品名称</b></td>
            <td><b>数量</b></td>
        </tr>
        <c:forEach items="${order.material}" var="m" varStatus="status">
            <tr>
                <td>${status.count}</td>
                <td>${m.name}</td>
                <td>${m.num}</td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="2" style="height: 100px;position: relative;">
                <span style="position: absolute;left: 10px;top: 10px">
                    采购意见：${order.purchase}
                </span>
                <span style="position: absolute;right: 10px;bottom: 10px">
                    日期：
                    <c:if test="${empty order.purchaseDate}">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </c:if>
                    <c:if test="${not empty order.purchaseDate}">
                        ${order.purchaseDate}
                    </c:if>
                </span>
            </td>
            <td colspan="2" style="position: relative">
                <span style="position: absolute;left: 10px;top: 10px">
                    仓管意见：${order.warehouse}
                </span>
                <span style="position: absolute;right: 10px;bottom: 10px">
                    日期：
                    <c:if test="${empty order.warehouseDate}">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </c:if>
                    <c:if test="${not empty order.warehouseDate}">
                        ${order.warehouseDate}
                    </c:if>
                </span>
            </td>
        </tr>
        <tr>
            <td><b>状态</b></td>
            <td colspan="3">${order.statusStr}</td>
        </tr>
    </table>
</div>
<script type="application/javascript">
    function approval() {
        $("#approvalWindow").dialog("open");
    }

    function confirm(value) {
        $("#formApproval").form("submit", {
            url: "/order/applyApproval",
            onSubmit: function (param) {
                param.orderId = ${order.orderId};
                param.status = value;
                param.type = ${order.type};
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
                    $("#approvalWindow").dialog("close");
                    $('#orderMenuList').tree({
                        url: '/base/menu?type=1',
                        method: 'get',
                        animate: false
                    });
                    let tab = $('#orderRight').tabs('getSelected');
                    let url = $(tab.panel('options')).attr('href');
                    tab.panel('refresh', url);
                }
            }
        })
    }
</script>
</body>
</html>
