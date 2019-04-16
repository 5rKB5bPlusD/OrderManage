<jsp:useBean id="order" scope="request" type="com.graduationDesign.model.vo.OrderComplaint"/>
<%--
  Created by IntelliJ IDEA.
  User: 5rKB5bPlusD
  Date: 2018/12/11
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<html lang="en">
<head>
    <%@ page isELIgnored="false" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <title>Title</title>
</head>
<body>
<div style="padding: 20px">
    <style>
        .complaintTable {
            width: 80%;
            border-color: #aaaaaa;
        }

        .complaintTable td {
            padding: 10px 5px;
            width: 16%;
            height: 40px;
            word-break: break-word;
            text-align: center;
        }

        .processTable, .resultTable {
            width: 80%;
        }

        .processTable td:first-child {
            padding: 5px;
            text-align: center;
        }

        .resultTable td:first-child {
            padding: 5px;
            text-align: center;
        }
    </style>
    <c:if test="${order.lv == 0&&order.rank == 1||order.rank == 0&&order.lv!=7}">
        <div style="width:92%; position: absolute; top: 60px;">
            <a style="float: left" href="javascript:void(0)" class="easyui-linkbutton" onclick="addProcess()">增加处理记录</a>
            <a style="float: right; right: 20px" href="javascript:void(0)" class="easyui-linkbutton"
               onclick="confirm()">处理完成</a>
        </div>
        <div id="process" class="easyui-window" title="添加处理记录"
             data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,maximizable:false"
             style="width:600px;height:350px;padding:5px;">
            <div class="easyui-layout" data-options="fit:true">
                <div data-options="region:'center'" style="padding:10px;">
                    <form id="formProcess" method="post">
                        <input type="text" name="id" hidden value="${order.id}">
                        <table class="processTable" cellpadding="5" align="center">
                            <tr>
                                <td>处理人</td>
                                <td>
                                    <input class="easyui-textbox" type="text" name="handler" style="width: 50%"
                                           disabled>
                                </td>
                            </tr>
                            <tr>
                                <td>处理时间</td>
                                <td>
                                    <input class="easyui-textbox" type="date" name="processingTime" style="width: 50%">
                                </td>
                            </tr>
                            <tr>
                                <td>沟通方式</td>
                                <td>
                                    <input class="easyui-textbox" type="text" name="communicationMode"
                                           style="width: 100%">
                                </td>
                            </tr>
                            <tr>
                                <td>处理过程</td>
                                <td>
                                    <input class="easyui-textbox" type="text" name="treatmentProcess"
                                           data-options="multiline:true"
                                           style="height:60px;width: 100%">
                                </td>
                            </tr>
                            <tr>
                                <td>处理结果</td>
                                <td>
                                    <input class="easyui-textbox" type="text" name="processingResult"
                                           style="width: 100%">
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
                       onclick="submitProcess()" style="width:80px">提交</a>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
                       onclick="cancelProcess()" style="width:80px">取消</a>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${order.rank >= 2&&order.rank == order.lv&&order.lv!=7||order.lv==0&&order.rank==4||order.lv==2&&order.rank==5||order.lv==3&&order.rank==6}">
        <div style="width:92%; position: absolute; top: 60px;">
            <a style="float: left" href="javascript:void(0)" class="easyui-linkbutton" onclick="editResult()">填写报表</a>
        </div>
        <div id="result" class="easyui-window" title="填写报表"
             data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,maximizable:false"
             style="width:600px;height:350px;padding:5px;">
            <div class="easyui-layout" data-options="fit:true">
                <div data-options="region:'center'" style="padding:10px;">
                    <form id="formResult" method="post">
                        <input type="text" name="id" hidden value="${order.id}">
                        <table class="resultTable" cellpadding="5" align="center">

                        </table>
                    </form>
                </div>
                <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
                       onclick="submitResult()" style="width:80px">提交</a>
                    <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
                       onclick="cancelResult()" style="width:80px">取消</a>
                </div>
            </div>
        </div>
    </c:if>
    <h3 style="text-align: center">${order.title}</h3>
    <h5 style="text-align: center">编号：${order.id}</h5>
    <table class="complaintTable" align="center" border="1" cellspacing="0" cellpadding="0">
        <tr>
            <td>客户姓名</td>
            <td>${order.customerName}</td>
            <td>联系电话</td>
            <td>${order.customerTel}</td>
            <td>工单号</td>
            <td>${order.eomsId}</td>
        </tr>
        <tr>
            <td>投诉类型</td>
            <td>${order.complaintType}</td>
            <td>受理人</td>
            <td>${order.acceptPerson}</td>
            <td>受理时间</td>
            <td>${order.acceptanceTime}</td>
        </tr>
        <tr>
            <td rowspan="2" colspan="4" style="text-align: left">
                投诉内容：
                ${order.complaintContent}
            </td>
            <td>责成部门</td>
            <td>${order.responsibleDepartment}</td>
        </tr>
        <tr>
            <td>责任人</td>
            <td>${order.responsiblePerson}</td>
        </tr>
        <tr>
            <td colspan="4" style="text-align: left;">
                客户要求：
                ${order.customerRequirements}
            </td>
            <td>要求完成时间</td>
            <td>${order.timeRequired}</td>
        </tr>
        <c:forEach items="${order.process}" var="p" varStatus="status">
            <tr>
                <td>第${status.count}次处理时间</td>
                <td>${p.processingTime}</td>
                <td>沟通方式</td>
                <td>${p.communicationMode}</td>
                <td>处理人</td>
                <td>${p.handler}</td>
            </tr>
            <tr>
                <td rowspan="2">处理过程</td>
                <td rowspan="2" colspan="4" style="text-align: left;">${p.treatmentProcess}</td>
                <td>处理结果</td>
            </tr>
            <tr>
                <td>${p.processingResult}</td>
            </tr>
        </c:forEach>
        <tr>
            <td rowspan="2">客服部处理意见</td>
            <td rowspan="2" colspan="4" style="text-align: left;">${order.serviceAdvice}</td>
            <td>部门负责人</td>
        </tr>
        <tr>
            <td>${order.serviceManager}</td>
        </tr>
        <tr>
            <td rowspan="2">分管部处理意见</td>
            <td rowspan="2" colspan="4" style="text-align: left;">${order.chargeAdvice}</td>
            <td>部门负责人</td>
        </tr>
        <tr>
            <td>${order.chargeManager}</td>
        </tr>
        <tr>
            <td rowspan="2">处理方案</td>
            <td rowspan="2" colspan="3" style="text-align: left;">${order.finalPlan}</td>
            <td>签名</td>
            <td>日期</td>
        </tr>
        <tr>
            <td>${order.planSign}</td>
            <td>${order.planDate}</td>
        </tr>
        <tr>
            <td rowspan="2">改进措施</td>
            <td rowspan="2" colspan="3" style="text-align: left;">${order.improvementMeasures}</td>
            <td>签名</td>
            <td>日期</td>
        </tr>
        <tr>
            <td>${order.measuresSign}</td>
            <td>${order.measuresDate}</td>
        </tr>
        <tr>
            <td>客服回访反馈</td>
            <td colspan="5" style="text-align: left;">${order.feedback}</td>
        </tr>
        <tr>
            <td>结案审批</td>
            <td colspan="5" style="text-align: left;">${order.closingOpinion}</td>
        </tr>
    </table>
</div>
<script type="application/javascript">
    function addProcess() {
        $("#process").window("open");
        $('#formProcess').form('load', {
            handler: sessionStorage.getItem("username")
        });
    }

    function cancelProcess() {
        $('#formProcess').form("clear");
        $("#process").window("close");
    }

    function submitProcess() {
        $('#formProcess').form("submit", {
            url: "/order/addProcess",
            success: function (result) {
                let res = eval('(' + result + ')');
                $("#process").window("close");
                if (res.success) {
                    $.messager.show({
                        title: '提示',
                        msg: '<h3>提交成功</h3>',
                        timeout: 1000,
                        showType: 'slide'
                    });
                    let tab = $('#orderRight').tabs('getSelected');
                    let url = $(tab.panel('options')).attr('href');
                    tab.panel('refresh', url);
                    $('#formProcess').form("clear");
                }
            }
        });
    }

    function confirm() {
        $.messager.confirm('确认', '确认吗？确认后完成处理环节，转交下一部门', function (r) {
            if (r) {
                $.ajax({
                    type: "get",
                    url: "/order/upgradeComplaint",
                    dataType: "json",
                    data: {
                        id: ${order.id}
                    },
                    success: function (result) {
                        let mes = result.message;
                        $.messager.show({
                            title: '提示',
                            msg: '<h3>' + mes + '</h3>',
                            timeout: 1000,
                            showType: 'slide'
                        });
                        $('#orderMenuList').tree({
                            url: '/base/menu?type=1',
                            method: 'get',
                            animate: false
                        });
                    }
                });
            }
        });
    }
</script>
<script>
    $(document).ready(function () {
        if (${(order.rank == 2||order.rank == 3) && order.rank == order.lv}) {
            $(".resultTable").html('<tr>' +
                '                                <td>部门管理员</td>' +
                '                                <td><input class="easyui-textbox" type="text" name="manager" style="width: 50%"' +
                '                                           disabled>' +
                '                                </td>' +
                '                            </tr>' +
                '                            <tr>' +
                '                                <td>意见</td>' +
                '                                <td>' +
                '                                    <input class="easyui-textbox" type="date" name="text1"' +
                '                                           data-options="multiline:true" style="height:100px;width: 100%">' +
                '                                </td>' +
                '                            </tr>');
        } else if (${order.rank == 4 && order.lv == 0}) {
            $(".resultTable").html('<tr>' +
                '                                <td>日期</td>' +
                '                                <td><input class="easyui-textbox" type="date" name="date" style="width: 50%"></td>' +
                '                            </tr>' +
                '                            <tr>' +
                '                                <td>处理方案</td>' +
                '                                <td>' +
                '                                    <input class="easyui-textbox" type="text" name="text1"' +
                '                                           data-options="multiline:true"' +
                '                                           style="height:60px;width: 100%">' +
                '                                </td>' +
                '                            </tr>' +
                '                            <tr>' +
                '                                <td>改进措施</td>' +
                '                                <td>' +
                '                                    <input class="easyui-textbox" type="text" name="text2"' +
                '                                           data-options="multiline:true"' +
                '                                           style="height:60px;width: 100%">' +
                '                                </td>' +
                '                            </tr>' +
                '                            <tr>\n' +
                '                                <td>签名</td>' +
                '                                <td><input class="easyui-textbox" type="text" name="sign" style="width: 50%"></td>' +
                '                            </tr>');
        } else if (${(order.rank == 5||order.rank == 6) && order.rank == order.lv||order.lv==2&&order.rank==5||order.lv==3&&order.rank==6}) {
            $(".resultTable").html('<tr>' +
                '                                <td>内容</td>' +
                '                                <td>' +
                '                                    <input class="easyui-textbox" type="date" name="text1" ' +
                '                                           data-options="multiline:true" style="height:100px;width: 100%">' +
                '                                </td>' +
                '                            </tr>');
        }
    });

    function cancelResult() {
        $('#formResult').form("clear");
        $("#result").window("close");
    }

    function editResult() {
        $("#result").window("open");
        $('#formResult').form('load', {
            manager: sessionStorage.getItem("username")
        });
    }

    function submitResult() {
        $('#formResult').form("submit", {
            url: "/order/editResult",
            success: function (result) {
                let res = eval('(' + result + ')');
                $("#result").window("close");
                if (res.success) {
                    $.messager.show({
                        title: '提示',
                        msg: '<h3>提交成功</h3>',
                        timeout: 1000,
                        showType: 'slide'
                    });
                    $('#orderMenuList').tree({
                        url: '/base/menu?type=1',
                        method: 'get',
                        animate: false
                    });
                    let tab = $('#orderRight').tabs('getSelected');
                    var url = $(tab.panel('options')).attr('href');
                    tab.panel('refresh', url);
                    $('#formResult').form("clear");
                }
            }
        });
    }
</script>
</body>
</html>
