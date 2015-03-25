<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 2015-03-24 | 오후 8:15
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>답변글 작성</title>
</head>
<body>
답변글을 등록했습니다.
<br/>
<a href="<c:url value="list.do?s=${param.s}" />">목록보기</a>
<a href="<c:url value="read.do?s=${param.s}&boardNumber=${postedBoardVO.number}"/> ">게시글 읽기</a>

</body>
</html>