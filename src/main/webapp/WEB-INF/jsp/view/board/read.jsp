<%--
  Created by IntelliJ IDEA.
  User: Donghyun Seo (egaoneko@naver.com)
  Date: 2015-03-23
  Time: 오전 10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>글 읽기</title>
</head>
<body>
<table>
    <tr>
        <td>제목</td>
        <td>${boardVO.title}</td>
    </tr>
    <tr>
        <td>작성자</td>
        <td>${boardVO.userEmail}</td>
    </tr>
    <tr>
        <td>작성일</td>
        <td><fmt:formatDate value="${boardVO.postingDate}" pattern="yyyy-MM-dd"/></td>
    </tr>
    <tr>
        <td>내용</td>
        <td>
            <pre><c:out value="${boardVO.content}"/></pre>
        </td>
    </tr>

    <tr>
        <td colspan="2">
            <a href="list.do?p=${param.p}">목록보기</a>
            <a href="replyForm.do?boardNumber=${boardVO.number}&p=${param.p}">답변하기</a>
            <a href="updateForm.do?boardNumber=${boardVO.number}&p=${param.p}">수정하기</a>
            <a href="deleteForm.do?boardNumber=${boardVO.number}">삭제하기</a>
        </td>
    </tr>
</table>

<!-- 덧글 구현부 -->

<!-- jsp:include page="/view/comment/list.jsp"/ -->

</body>
</html>
