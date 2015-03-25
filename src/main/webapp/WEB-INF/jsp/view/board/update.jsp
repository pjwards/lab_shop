<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 2015-03-24 | 오전 11:37
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>글 수정</title>
</head>
<body>
글을 수정했습니다.
<br/>
<a href="list.do?s=${param.s}&p=${param.p}">목록보기</a>
<a href="read.do?s=${param.s}&p=${param.p}&boardNumber=${boardVO.number}">게시글 읽기</a>

</body>
</html>
