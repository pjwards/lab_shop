<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 15. 4. 5. | 오후 6:07
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
    <input type="hidden" name="parentCommentNumber" value="${param.parentCommentNumber}" />
    <input type="hidden" name="boardNumber" value="${param.boardNumber}" />
    <input type="hidden" name="s" value="${param.s}"/>
    <input type="hidden" name="p" value="${param.p}"/>
    작성자 : <input type="text" name="memberId"/><br/>
    글내용 : <br/>
    <textarea name="content" cols="40" rows="5" ></textarea>
    <br/>
    <input type="submit" value="전송"/>
</form>

</body>
</html>
