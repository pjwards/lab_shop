<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 2015-03-24 | 오후 8:16
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>답변쓰기</title>
</head>
<body>

<form action="<c:url value="reply.do" />" method="post">
    <input type="hidden" name="parentBoardNumber" value="${param.parentBoardNumber}" />
    <input type="hidden" name="s" value="${param.s}"/>
    제목 : <input type="text" name="title" size="20" value="re: "/><br/>
    글내용 : <br/>
    <textarea name="content" cols="40" rows="5" ></textarea>
    <br/>
    <input type="submit" value="전송"/>
</form>

</body>
</html>

