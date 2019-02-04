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
        <hr>
        <table class="jobList" cellspacing="0" cellpadding="0">
            <tr>
                <td>
                    <input class="easyui-textbox" type="text" name="name" style="width: 100%;height: 40px;"
                           data-options="prompt:'工作内容'">
                </td>
                <td width="100px" style="text-align: right">
                    <label>进度&nbsp;&nbsp;&nbsp;&nbsp;</label>
                </td>
                <td>
                    <input class="easyui-slider" style="width:80%"
                           data-options="showTip:true,rule: [0,'|',25,'|',50,'|',75,'|',100]">
                </td>
            </tr>
        </table>
        <div style="margin-top: 20px;margin-bottom: 50px">
            <button onclick="add()">添加</button>
            <button onclick="remove()">删除</button>
        </div>
    </div>
</div>
<script type="application/javascript">
    function add() {
        let text = '<tr>' +
            '                <td>' +
            '                    <input class="easyui-textbox" type="text" name="name" style="width: 100%;height: 40px;"' +
            '                           data-options="prompt:\'工作内容\'">' +
            '                </td>' +
            '                <td width="100px" style="text-align: right">' +
            '                    <label>进度&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
            '                </td>' +
            '                <td>' +
            '                    <input id="slider" class="easyui-slider" style="width:80%"' +
            '                           data-options="showTip:true,rule: [0,\'|\',25,\'|\',50,\'|\',75,\'|\',100]">' +
            '                </td>' +
            '            </tr>';
        $(".jobList").append(text);
        $.parser.parse();
    }

    function remove() {
        $(".jobList tr:last").remove();
    }
</script>
<script type="application/javascript">
    let date = new Date();
    $("#todayDate").html(date.getFullYear() + "-" + date.getMonth() + 1 + "-" + date.getDate());
</script>
</body>
</html>
