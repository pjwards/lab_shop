<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 2015-03-24 | 오후 9:15
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>삭제하기</title>
</head>
<body>

<form action="<c:url value="/board/delete.do" />" method="post">
    <input type="hidden" name="s" value="${param.s}"/>
    <input type="hidden" name="boardNumber" value="${param.boardNumber}"/>
    <input type="submit" value="삭제"/>
</form>

</body>
</html>