<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 2015-03-22 | 오후 2:29
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>글쓰기</title>
</head>
<body>

<form action="<c:url value="write.do" />" method="post">
    <input type="hidden" name="s" value="${param.s}"/>
    제목 : <input type="text" name="title" size="100"/><br/>
    <!--카테고리 : <input type="text" name="separator"/><br/>-->
    글내용: <br/>
    <textarea name="content" cols="40" rows="5"></textarea>
    <br/>
    <input type="submit" value="전송">
</form>

</body>
</html>
