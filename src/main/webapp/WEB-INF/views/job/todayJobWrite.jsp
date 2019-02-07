<%--
  Created by IntelliJ IDEA.
  User: 5rKB5bPlusD
  Date: 2019/1/27
  Time: 22:56
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
        .jobList {
            width: 90%;
            font-size: 18px;
        }

        .jobList td {
            border-bottom: 1px solid #000000;
            height: 65px;
            padding: 10px 5px;
        }

        .jobList td:first-child {
            width: 60%;
        }
    </style>
    <div style="margin-left: 20px;">
        <h3>今日工作(<span id="todayDate"></span>)</h3>
        <div style="display:none;">
            查看成员今日工作情况（选择成员）&nbsp;&nbsp;&nbsp;&nbsp;<input id="members">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'"
               onclick='clearMembers()'>清除</a>
        </div>
        <hr>
        <table class="jobList" cellspacing="0" cellpadding="0">
            <tr>
                <td>
                    <input class="easyui-textbox" type="text" name="jobContent" style="width: 100%;height: 40px;"
                           data-options="prompt:'工作内容'">
                </td>
                <td width="100px" style="text-align: right">
                    <label>进度&nbsp;&nbsp;&nbsp;&nbsp;</label>
                </td>
                <td>
                    <input class="easyui-slider" style="width:80%" name="jobSpeed"
                           data-options="showTip:true,rule: [0,'|',25,'|',50,'|',75,'|',100]">
                </td>
            </tr>
        </table>
        <div id="jobButtons" style="margin-top: 20px;margin-bottom: 50px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'"
               onclick="add()">添加</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
               onclick="remove()">删除</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"
               onclick="submitJob()">提交</a>
        </div>
    </div>
</div>
<script type="application/javascript">

    let date = new Date();
    let month = date.getMonth() + 1;
    let dateStr = date.getFullYear() + "-" + month + "-" + date.getDate();
    $("#todayDate").html(dateStr);
    checkDate(dateStr);
    checkLeader();

    function checkLeader() {
        $.ajax({
            type: "get",
            url: "/auth/checkLeader",
            success: function (result) {
                if(result.success){
                    $('#members').parent().css("display", "block");
                    $('#members').combobox({
                        url: '/user/getMember?teamId=' + result.data,
                        valueField: 'userId',
                        textField: 'username'
                    });
                }
            }
        })
    }

    function clearMembers() {
        $(".jobList").empty();
        $("#jobButtons").show();
        $('#members').combobox("clear");
        checkDate(dateStr);
    }

    $('#members').combobox({
        onSelect: function (record) {
            $.ajax({
                type: "get",
                url: "/job/showMemberJob",
                data: {
                    userId: record.userId,
                    date: dateStr
                },
                success: function (result) {
                    $(".jobList").empty();
                    if (result.success) {
                        $.each(result.data, function (index, value) {
                            let text = '<tr>' +
                                '                <td>' +
                                '                    <input class="easyui-textbox" type="text" name="jobContent" value="' + value.jobName + '" style="width: 100%;height: 40px;"' +
                                '                           data-options="readonly:\'true\'">' +
                                '                </td>' +
                                '                <td width="100px" style="text-align: right">' +
                                '                    <label>进度&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
                                '                </td>' +
                                '                <td>' +
                                '                    <div class="easyui-progressbar" data-options="value:' + value.speed + '" style="width:100%"></div>' +
                                '                </td>' +
                                '            </tr>';
                            $(".jobList").append(text);
                        });
                        $("#jobButtons").hide();
                        $.parser.parse(".jobList");
                    } else {
                        $(".jobList").append("<h3>" + result.message + "</h3>");
                    }
                }
            })
        }
    });

    function checkDate(date) {
        $.ajax({
            type: "get",
            url: "/job/showJob",
            data: {
                date: date
            },
            success: function (result) {
                $(".jobList").empty();
                if (result.success) {
                    $.each(result.data, function (index, value) {
                        let text = '<tr>' +
                            '                <td>' +
                            '                    <input class="easyui-textbox" type="text" name="jobContent" value="' + value.jobName + '" style="width: 100%;height: 40px;"' +
                            '                           data-options="readonly:\'true\'">' +
                            '                </td>' +
                            '                <td width="100px" style="text-align: right">' +
                            '                    <label>进度&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
                            '                </td>' +
                            '                <td>' +
                            '                    <div class="easyui-progressbar" data-options="value:' + value.speed + '" style="width:100%"></div>' +
                            '                </td>' +
                            '            </tr>';
                        $(".jobList").append(text);
                    });
                    $("#jobButtons").hide();
                    $.parser.parse(".jobList");
                    $.messager.alert('提示', '今日工作记录已完成');
                } else {
                    checkUnfinished();
                }
            }
        })
    }

    function checkUnfinished() {
        $.ajax({
            type: "get",
            url: "/job/unfinished",
            success: function (result) {
                $.each(result.data, function (index, value) {
                    let text = '<tr>' +
                        '                <td>' +
                        '                    <input name="jobId" value="' + value.id + '" hidden>' +
                        '                    <input class="easyui-textbox" type="text" name="jobContent" value="' + value.jobName + '" style="width: 100%;height: 40px;"' +
                        '                           data-options="readonly:\'true\'">' +
                        '                </td>' +
                        '                <td width="100px" style="text-align: right">' +
                        '                    <label>进度&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
                        '                </td>' +
                        '                <td>' +
                        '                    <input class="easyui-slider" style="width:80%" name="jobSpeed" value="' + value.speed + '"' +
                        '                           data-options="showTip:true,rule: [0,\'|\',25,\'|\',50,\'|\',75,\'|\',100]">' +
                        '                </td>' +
                        '            </tr>';
                    $(".jobList").append(text);
                });
                $.parser.parse(".jobList");
            }
        })
    }

    function add() {
        let text = '<tr>' +
            '                <td>' +
            '                    <input name="jobId" value="-1" hidden>' +
            '                    <input class="easyui-textbox" type="text" name="jobContent" style="width: 100%;height: 40px;"' +
            '                           data-options="prompt:\'工作内容\'">' +
            '                </td>' +
            '                <td width="100px" style="text-align: right">' +
            '                    <label>进度&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
            '                </td>' +
            '                <td>' +
            '                    <input class="easyui-slider" style="width:80%" name="jobSpeed"' +
            '                           data-options="showTip:true,rule: [0,\'|\',25,\'|\',50,\'|\',75,\'|\',100]">' +
            '                </td>' +
            '            </tr>';
        $(".jobList").append(text);
        $.parser.parse(".jobList");
    }

    function remove() {
        $(".jobList tr:last").remove();
    }

    function submitJob() {
        let jobContent = "";
        let jobSpeed = "";
        let jobId = "";
        $("input[name='jobId']").each(function () {
            jobId = jobId + $(this).val() + ',';
        });
        $("input[name='jobContent']").each(function () {
            jobContent = jobContent + $(this).val() + ',';
        });
        $("input[name='jobSpeed']").each(function () {
            jobSpeed = jobSpeed + $(this).val() + ',';
        });

        $.ajax({
            type: "get",
            url: "/job/recordingJob",
            data: {
                jobId: jobId,
                jobName: encodeURI(jobContent),
                speed: encodeURI(jobSpeed)
            },
            success: function (result) {
                $.messager.show({
                    title: '提示',
                    msg: '<h3>' + result.message + '</h3>',
                    timeout: 1000,
                    showType: 'slide'
                });
                if(result.success){
                    let tab = $('#jobRight').tabs('getSelected');
                    let url = $(tab.panel('options')).attr('href');
                    tab.panel('refresh', url);
                }
            }
        });
    }
</script>
</body>
</html>
