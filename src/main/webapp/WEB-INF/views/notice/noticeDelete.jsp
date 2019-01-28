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
<div style="padding: 5px">
    <div id="noticeEdit" class="easyui-window" title="编辑"
         data-options="modal:true,closed:true,iconCls:'icon-edit',minimizable:false,maximizable:false"
         style="width:800px;height:400px;padding:5px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center'" style="padding:10px;">
                <form id="editNotice" method="post">
                    <h4>标题</h4>
                    <input id="title" name="title" class="easyui-textbox" style="width:50%;height:32px">
                    <h4>正文</h4>
                    <input id="content" name="content" class="easyui-textbox" data-options="multiline:true"
                           style="width:100%;height:250px;margin-top: 20px">
                </form>
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
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-more" plain="true"
           onclick="editNotice()">编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="deleteNotice()">删除</a>
    </div>
</div>
<script type="application/javascript">
    $("#noticeList").datagrid({
        url: "/notice/allNotice",
        striped: true,
        singleSelect: true,
        width: '80%',
        toolbar: '#noticeDeleteBar',
        columns: [[
            {field: 'noticeId', title: '编号', width: '5%'},
            {field: 'title', title: '公告标题', width: '20%'},
            {field: 'date', title: '公告日期', width: '15%'},
            {field: 'content', title: '公告内容', width: '60%'}
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
                content: $("#content").val()
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
                            title: result.data.title,
                            content: result.data.content
                        });
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
