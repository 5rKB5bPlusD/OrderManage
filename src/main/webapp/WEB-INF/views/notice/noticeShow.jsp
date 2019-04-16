<%--
  Created by IntelliJ IDEA.
  User: 5rKB5bPlusD
  Date: 2018/12/30
  Time: 3:13
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <%@ page isELIgnored="false" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <title>${notice.title}</title>
</head>
<body style="padding: 5px">
<h2 style="text-align: center">${notice.title}</h2>
<h3 style="text-align: center">${notice.date}</h3>
<div>
    ${notice.content}
</div>
</body>
</html>
