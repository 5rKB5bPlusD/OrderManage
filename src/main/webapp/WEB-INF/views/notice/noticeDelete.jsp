<%--
  Created by IntelliJ IDEA.
  User: 5rKB5bPlusD
  Date: 2018/12/30
  Time: 5:29
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
    <div id="noticeEdit" class="easyui-window" title="编辑"
         data-options="modal:true,closed:true,iconCls:'icon-edit',minimizable:false,maximizable:false"
         style="width:800px;height:400px;padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" style="padding:10px;">
                <form id="editNotice" method="post">
                    <h4>标题</h4>
                    <input id="title" name="title" class="easyui-textbox" style="width:50%;height:32px">
                </form>
                <h4>正文</h4>
                <textarea id="editContent" name="content"
                          style="width:100%;height:400px;margin-top: 20px"></textarea>
                <p>您当前输入了 <span class="showNum1"></span> 个文字。（字数统计包含HTML代码。）</p>
                <p>您当前输入了 <span class="showNum2"></span> 个文字。（字数统计包含纯文本、IMG、EMBED，不包含换行符，IMG和EMBED算一个文字。）</p>
                <p class="showNum3"></p>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
                   onclick="confirmEdit()" style="width:80px">确定</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
                   onclick="cancelEdit()" style="width:80px">取消</a>
            </div>
        </div>
    </div>
    <div style="margin-left: 20px">
        <h3>公告列表</h3>
        <table id="noticeList"></table>
    </div>
    <div id="noticeDeleteBar" style="text-align: right">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="editNotice()">编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="deleteNotice()">删除</a>
    </div>
</div>
<script charset="utf-8" src="/static/plugins/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="/static/plugins/kindeditor/lang/zh-CN.js"></script>
<script type="application/javascript">
    var editor2;

    function kindeditor(kindeditor) {
        editor2 = KindEditor.create(kindeditor, {
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
            allowFileManager: true,
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


    $("#noticeList").datagrid({
        url: "/notice/allNotice",
        striped: true,
        singleSelect: true,
        width: '80%',
        toolbar: '#noticeDeleteBar',
        columns: [[
            {field: 'noticeId', title: '编号', width: '10%'},
            {field: 'title', title: '公告标题', width: '40%'},
            {field: 'date', title: '公告日期', width: '20%'}
        ]]
    });

    function cancelEdit() {
        $("#noticeEdit").window("close");
    }

    function confirmEdit() {
        let select = $("#noticeList").datagrid("getSelected");
        $.ajax({
            type: "post",
            url: "/notice/updateNotice",
            dataType: "json",
            data: {
                noticeId: select.noticeId,
                title: $("#title").val(),
                content: editor2.html()
            },
            success: function (result) {
                $.messager.show({
                    title: '提示',
                    msg: '<h3>' + result.message + '</h3>',
                    timeout: 1000,
                    showType: 'slide'
                });
                if (result.success) {
                    $("#noticeEdit").window("close");
                    let tab = $('#noticeRight').tabs('getSelected');
                    let url = $(tab.panel('options')).attr('href');
                    tab.panel('refresh', url);
                }
            }
        });
    }

    function editNotice() {
        let select = $("#noticeList").datagrid("getSelected");
        if (select !== undefined) {
            $.ajax({
                type: "get",
                url: "/notice/getNotice",
                data: {
                    noticeId: select.noticeId
                },
                success: function (result) {
                    if (result.success) {
                        $("#editNotice").form("load", {
                            title: result.data.title
                        });
                        if (editor2 === undefined) {
                            kindeditor("#editContent");
                        }
                        editor2.html(result.data.content.toString());
                        $("#noticeEdit").window("open");
                    } else {
                        $.messager.show({
                            title: '提示',
                            msg: '<h3>' + result.message + '</h3>',
                            timeout: 1000,
                            showType: 'slide'
                        });
                    }
                }
            });
        }
    }

    function deleteNotice() {
        let select = $("#noticeList").datagrid("getSelected");
        let index = $("#noticeList").datagrid("getRowIndex", select);
        if (select !== undefined) {
            $.messager.confirm('确认', '确认删除公告：' + select.title + "?", function (r) {
                if (r) {
                    $.ajax({
                        type: "get",
                        url: "/notice/deleteNotice",
                        data: {
                            noticeId: select.noticeId
                        },
                        success: function (result) {
                            $.messager.show({
                                title: '提示',
                                msg: '<h3>' + result.message + '</h3>',
                                timeout: 1000,
                                showType: 'slide'
                            });
                            if (result.success) {
                                $("#noticeList").datagrid("deleteRow", index);
                            }
                        }
                    });
                }
            });
        }
    }
</script>
</body>
</html>
