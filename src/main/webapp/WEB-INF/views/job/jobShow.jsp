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
    <style>
        .jobShowList {
            width: 90%;
            font-size: 18px;
        }

        .jobShowList td {
            border-bottom: 1px solid #000000;
            height: 65px;
            padding: 10px 5px;
        }

        .jobShowList td:first-child {
            width: 60%;
        }
    </style>
    <div style="margin-left: 20px;">
        <h3>
            <span id="todayDate2"></span>工作记录&nbsp;&nbsp;&nbsp;&nbsp;
            <input id="jobDate" type="text" class="easyui-datebox">
        </h3>
        <hr>
        <table class="jobShowList" cellspacing="0" cellpadding="0">
            <tr>
                <td>
                    <input class="easyui-textbox" type="text" name="jobContent" style="width: 100%;height: 40px;"
                           data-options="readonly:'true'">
                </td>
                <td width="100px" style="text-align: right">
                    <label>进度&nbsp;&nbsp;&nbsp;&nbsp;</label>
                </td>
                <td>
                    <div class="easyui-progressbar" data-options="value:60" style="width:100%"></div>
                </td>
            </tr>
        </table>
    </div>
</div>
<script type="application/javascript">

    let date = new Date();
    let month = date.getMonth() + 1;
    let dateStr = date.getFullYear() + "-" + month + "-" + date.getDate();
    $("#todayDate2").html(dateStr);
    submitDate(dateStr);

    function submitDate(date) {
        $.ajax({
            type: "get",
            url: "/job/showJob",
            data: {
                date: date
            },
            success: function (result) {
                $(".jobShowList").empty();
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
                        $(".jobShowList").append(text);
                        $.parser.parse(".jobShowList");
                    });
                } else {
                    $(".jobShowList").append("<h3 >" + result.message + "</h3>");
                }

            }
        })
    }

    $('#jobDate').datebox({
        onSelect: function (date) {
            let d = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
            $("#todayDate2").html(d);
            submitDate(d);
        }
    });
</script>
</body>
</html>
