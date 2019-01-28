<%--
  Created by IntelliJ IDEA.
  User: 5rKB5bPlusD
  Date: 2018/12/29
  Time: 4:40
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
    <div style="margin-left: 20px">
        <h3>编辑公告</h3>
        <form id="addNotice" method="post">
            <h4>标题</h4>
            <input name="title" class="easyui-textbox" data-options="prompt:'填写标题'" style="width:40%;height:32px">
            <h4>公告内容</h4>
            <input name="content" class="easyui-textbox" data-options="multiline:true,prompt:'填写内容'" style="width:80%;height:300px">
            <div style="margin-top: 20px;padding-bottom: 50px;">
                <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 60px" onclick="publish()">发布</a>
            </div>
        </form>
    </div>
</div>
<script type="application/javascript">
    function publish() {
        $("#addNotice").form("submit", {
            url: "/notice/addNotice",
            success: function (result) {
                let res = eval('(' + result + ')');
                $.messager.show({
                    title: '提示',
                    msg: '<h3>' + res.message + '</h3>',
                    timeout: 1000,
                    showType: 'slide'
                });
                if (res.success) {
                    $("#addNotice").form("clear");
                }
            }
        });
    }
</script>
</body>
</html>
