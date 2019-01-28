function err(target, message) {
    let t = $(target);
    if (t.hasClass('textbox-text')) {
        t = t.parent();
    }
    let m = t.next('.error-message');
    if (!m.length) {
        m = $('<div class="error-message"></div>').insertAfter(t);
    }
    m.html(message);
}

$("#formLogin").form({
    url: "/auth/checkLogin",
    onSubmit: function () {
        return $(this).form('validate');
    },
    success: function (result) {
        let res = eval('(' + result + ')');
        if (res.success) {
            sessionStorage.setItem("username", res.data);
            window.location.href = "/user/index";
        } else if (!res.success) {
            alert(res.message);
        }
    }
});

$("#showNotice").datagrid({
    url: "/notice/allNotice",
    title: "公告",
    width: '100%',
    height: '250px',
    striped: true,
    singleSelect: true,
    columns: [[
        {field: 'noticeId', title: '编号', width: 50, hidden: true},
        {field: 'date', title: '日期', width: '20%'},
        {
            field: 'title', title: '标题', width: '80%',
            formatter: function (value, row, index) {
                return "<a href='/notice/showNotice?noticeId=" + row.noticeId + "' target='_blank'>" + value + "</a>"
            }
        }
    ]]
});