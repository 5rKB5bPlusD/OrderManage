<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ page isELIgnored="false" %>
    <%@ page contentType="text/html; charset=UTF-8" %>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="/static/plugins/jquery-easyui-1.6.7/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/plugins/jquery-easyui-1.6.7/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/static/css/login.css">
</head>
<body class="easyui-layout" data-options="border:false">
<div data-options="region:'north',border:false" style="height:100px;background:#B3DFDA;padding:10px;opacity: 0.8">
    <h1 style="text-align: center">工单管理系统接口模块</h1>
</div>
<div class="notice" data-options="region:'west',split:false,border:false">
    <div style="width:80%;margin-left: 15%">
        <table id="showNotice"></table>
    </div>
</div>
<div class="login" data-options="region:'center',split:false,border:false">
    <div class="easyui-panel" title="登录" style="width:60%;padding: 20px 40px;">
        <form id="formLogin" method="post">
            <div style="margin-bottom:20px">
                <label for="username" class="label-top">用户名</label>
                <input id="username" name="username" class="easyui-validatebox tb" type="text"
                       data-options="required:true,missingMessage:'必填',validateOnCreate:true,err:err">
            </div>
            <div style="margin-bottom:20px">
                <label for="password" class="label-top">密码</label>
                <input id="password" name="password" class="easyui-validatebox tb" type="password"
                       data-options="required:true,missingMessage:'必填',validateOnCreate:false,err:err">
            </div>
            <div style="margin-left: 50px;margin-bottom: 20px">
                <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 100px"
                   onclick="$('#formLogin').submit()">登录</a>
            </div>
        </form>
    </div>
</div>
</body>
<script type="application/javascript" src="/static/plugins/jquery-easyui-1.6.7/jquery.min.js"></script>
<script type="application/javascript" src="/static/plugins/jquery-easyui-1.6.7/jquery.easyui.min.js"></script>
<script type="application/javascript" src="/static/js/login.js"></script>
</html>