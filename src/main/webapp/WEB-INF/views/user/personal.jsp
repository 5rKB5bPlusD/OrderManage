<jsp:useBean id="user" scope="request" type="com.graduationDesign.model.po.User"/>
<%--
  Created by IntelliJ IDEA.
  User: 5rKB5bPlusD
  Date: 2019/2/10
  Time: 12:26
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
        .personalTable {
            width: 60%;
        }

        .personalTable td:first-child {
            padding: 10px 5px;
            width: 25%;
            height: 40px;
            word-break: break-word;
            text-align: center;
        }
    </style>
    <h3 style="margin-left: 20px">个人信息</h3>
    <table class="personalTable" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td><b>用户名</b></td>
            <td><input class="easyui-textbox" data-options="readonly:true" type="text" style="width: 60%" value="${user.username}"></td>
            <td></td>
        </tr>
        <tr>
            <td><b>密码</b></td>
            <td><input id="passwordEdit" class="easyui-passwordbox" prompt="Password" iconWidth="28" value="000000000"
                       data-options="readonly:true" style="width: 90%"></td>
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editPassword()">修改密码</a>
                <a id="confirmEditPassword" href="javascript:void(0)" class="easyui-linkbutton"
                   data-options="disabled:true"
                   onclick="confirmEditPassword()">确认</a>
            </td>
        </tr>
    </table>
</div>
<script type="application/javascript">
    function editPassword() {
        $("#passwordEdit").textbox('readonly', false);
        $("#passwordEdit").textbox("clear");
        $("#confirmEditPassword").linkbutton('enable');
    }

    function confirmEditPassword() {
        $.ajax({
            type: "post",
            url: "/user/changePassword",
            data: {
                password: $("#passwordEdit").textbox("getValue")
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
        $("#passwordEdit").textbox('readonly', true);
        $("#confirmEditPassword").linkbutton('disable');
    }
</script>
</body>
</html>
