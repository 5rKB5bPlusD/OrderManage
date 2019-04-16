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
<link rel="stylesheet" type="text/css" href="/static/plugins/kindeditor/themes/simple/simple.css">
<div style="padding: 5px">
    <div style="margin-left: 20px">
        <h3>编辑公告</h3>
        <form id="addNotice" method="post">
            <h4>标题</h4>
            <input name="title" class="easyui-textbox" data-options="prompt:'填写标题'" style="width:40%;height:32px">
            <h4>公告内容</h4>
            <textarea id="editorNotice" name="content" style="width:90%;height:500px"></textarea>
            <p>您当前输入了 <span class="showNum1"></span> 个文字。（字数统计包含HTML代码。）</p>
            <p>您当前输入了 <span class="showNum2"></span> 个文字。（字数统计包含纯文本、IMG、EMBED，不包含换行符，IMG和EMBED算一个文字。）</p>
            <p class="showNum3"></p>
            <div style="margin-top: 20px;padding-bottom: 50px;">
                <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 60px" onclick="publish()">发布</a>
            </div>
        </form>
    </div>
</div>
<script charset="utf-8" src="/static/plugins/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="/static/plugins/kindeditor/lang/zh-CN.js"></script>
<script type="application/javascript">
    var editor1;

    kindeditor("#editorNotice");

    function kindeditor(kindeditor) {
        editor1 = KindEditor.create(kindeditor, {
            items: ['source', '|', 'undo', 'redo', '|', 'preview', 'code', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', 'clearhtml', 'quickformat', 'selectall', '/',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'insertfile',
                'table', 'hr',
                'anchor', 'link', 'unlink'],
            allowImageUpload: true,
            uploadJson: '/file/upload',
            fileManagerJson: "/file/manage",
            allowFileManager : true,
            minHeight: "500",
            afterBlur: function () {
                this.sync();
            },
            afterCreate: function () {
                this.sync();
            },
            afterChange: function () {
                $('.showNum1').html(this.count()); //字数统计包含HTML代码
                $('.showNum2').html(this.count('text'));  //字数统计包含纯文本、IMG、EMBED，不包含换行符，IMG和EMBED算一个文字
                var limitNum = 4000;  //设定限制字数
                var pattern = '还可以输入' + limitNum + '字';
                $('.showNum3').html(pattern); //输入显示
                if (this.count('text') > limitNum) {
                    pattern = ("<span style='color: red'>字数超过限制，请适当删除部分内容</span>");
                }
                if (this.count() > limitNum + 1000) {
                    alert("<span style='color: red'>字数超过限制，请适当删除部分内容</span>");
                } else {
                    //计算剩余字数
                    var result = limitNum - this.count('text');
                    pattern = '还可以输入' + result + '字';
                }
                $('.showNum3').html(pattern); //输入显示
            }
        });
    }

    function publish() {
        $("#addNotice").form("submit", {
            url: "/notice/addNotice",
            onSubmit: function (param) {
                param.content = editor1.html();
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
                    $("#addNotice").form("clear");
                    editor1.html("");
                }
            }
        });
    }
</script>
</body>
</html>
