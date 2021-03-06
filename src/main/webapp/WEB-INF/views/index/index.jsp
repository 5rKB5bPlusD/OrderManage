<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@ page isELIgnored="false" %>
    <%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>主页</title>
    <link rel="stylesheet" type="text/css" href="/static/plugins/jquery-easyui-1.6.7/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/plugins/jquery-easyui-1.6.7/themes/icon.css">
</head>
<body class="easyui-layout" data-options="border:false">
<div id="noticeWindow" class="easyui-dialog" title="公告" style="width:600px;height:300px;padding: 10px"
     data-options="
                closed: true,
                cache: false,
                draggable: false,
                modal: true,
				buttons: [{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function(){
						$('#noticeWindow').dialog('close');
					}
				}]
			">
    <table id="showNotice"></table>
</div>
<div data-options="region:'north',border:false" style="height:100px;background: #F0F8FF;">
    <h1 style="text-align: center;font-family: KaiTi">工单管理系统接口模块</h1>
    <span id="username" style="position: absolute;right: 20px;bottom: 10px;">
        欢迎登陆&nbsp;&nbsp;
        <span style="color: darkred; cursor: pointer">${data.username}</span>
        <a style="color: blue; cursor: pointer;margin-left: 20px" href="javascript:void(0)" onclick="showNotice()">通知</a>
        <a href="javascript:void(0);" style="margin-left: 10px" onclick="logout()">安全登出</a>
    </span>
</div>
<div data-options="region:'center',border:false">
    <div class="easyui-tabs" style="width:100%;height:100%;">
        <c:if test="${fn:contains(data.rank,'orderEdit')}">
            <div title="工单填报">
                <div class="easyui-layout" data-options="border:false" style="width:100%;height: 100%">
                    <div data-options="region:'west',split:false,border:true" style="width:20%">
                        <div style="height: 100%;overflow-y: auto">
                            <ul id="orderMenuList" class="easyui-tree"
                                data-options="url:'/base/menu?type=1',method:'get',animate:false"></ul>
                        </div>
                    </div>
                    <div data-options="region:'east',split:false,border:true" style="width:80%">
                        <div id="orderRight" class="easyui-tabs"
                             style="width:100%;height:100%;background-image: url('/static/img/bg2.jpg')"></div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:contains(data.rank,'userManage')}">
            <div title="人员管理">
                <div class="easyui-layout" data-options="border:false" style="width:100%;height: 100%">
                    <div data-options="region:'west',split:false,border:true" style="width:20%">
                        <ul class="easyui-tree"
                            data-options="url:'/base/menu?type=2',method:'get',animate:false"></ul>
                    </div>
                    <div data-options="region:'east',split:false,border:true" style="width:80%">
                        <div id="userRight" class="easyui-tabs"
                             style="width:100%;height:100%;background-image: url('/static/img/bg2.jpg')"></div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:contains(data.rank,'noticeManage')}">
            <div title="通知管理">
                <div class="easyui-layout" data-options="border:false" style="width:100%;height: 100%">
                    <div data-options="region:'west',split:false,border:true" style="width:20%">
                        <ul class="easyui-tree"
                            data-options="url:'/base/menu?type=3',method:'get',animate:false"></ul>
                    </div>
                    <div data-options="region:'east',split:false,border:true" style="width:80%">
                        <div id="noticeRight" class="easyui-tabs"
                             style="width:100%;height:100%;background-image: url('/static/img/bg2.jpg')"></div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:contains(data.rank,'jobDiary')}">
            <div title="工作日志">
                <div class="easyui-layout" data-options="border:false" style="width:100%;height: 100%">
                    <div data-options="region:'west',split:false,border:true" style="width:20%">
                        <ul class="easyui-tree"
                            data-options="url:'/base/menu?type=4',method:'get',animate:false"></ul>
                    </div>
                    <div data-options="region:'east',split:false,border:true" style="width:80%">
                        <div id="jobRight" class="easyui-tabs"
                             style="width:100%;height:100%;background-image: url('/static/img/bg2.jpg')"></div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</div>
</body>
<script type="application/javascript" src="/static/plugins/jquery-easyui-1.6.7/jquery.min.js"></script>
<script type="application/javascript" src="/static/plugins/jquery-easyui-1.6.7/jquery.easyui.min.js"></script>
<script type="application/javascript" src="/static/js/index.js"></script>
</html>