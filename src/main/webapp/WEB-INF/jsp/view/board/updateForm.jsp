<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 2015-03-24 | 오전 11:37
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>수정하기</title>
</head>
<body>

<form action="<c:url value="update.do" />" method="post">
    <input type="hidden" name="boardNumber" value="${boardVO.number}"/>
    <input type="hidden" name="s" value="${param.s}"/>
    <input type="hidden" name="p" value="${param.p}"/>
    제목 : <input type="text" name="title" size="20" value="${boardVO.title}"/><br/>
    작성자 : <input type="text" name="memberId"/><br/>
    글내용 : <br/>
    <textarea name="content" cols="40" rows="5" >${boardVO.content}</textarea>
    <br/>
    <input type="submit" value="전송"/>
</form>

</body>
</html>
