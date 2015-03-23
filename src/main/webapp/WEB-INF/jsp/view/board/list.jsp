<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 2015-03-21 | 오후 7:16
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>게시글 목록</title>
</head>
<body>
<table border="1">
    <c:if test="${totalPageCount > 0}">
        <tr>
            <td colspan="5">
                    ${startRow}-${endRow}
                [${requestPage}/${totalPageCount}]
            </td>
        </tr>
    </c:if>

    <tr>
        <td>글 번호</td>
        <td>제목</td>
        <td>작성자</td>
        <td>작성일</td>
        <td>조회수</td>
    </tr>

    <c:choose>
        <c:when test="${hasBoard == false}">
            <tr>
                <td colspan="5">
                    게시글이 없습니다.
                </td>
            </tr>
        </c:when>

        <c:otherwise>
            <c:forEach var="list" items="${boardVOList}">
                <tr>
                    <td>${list.number}</td>
                    <td>
                        <c:if test="${list.level > 0}">
                            <c:forEach begin="1" end="${list.level}">-</c:forEach>&gt;
                        </c:if>
                        <c:set var="query" value="boardNumber=${list.number}&p=${requestPage}"/>
                        <a href="<c:url value="read.do?${query}"/> ">
                                ${list.title} (${list.commentCount})
                        </a>
                    </td>
                    <td>${list.userEmail}</td>
                    <td><fmt:formatDate value="${list.postingDate}" pattern="yyyy-MM-dd"/></td>
                    <td>${list.readCount}</td>
                </tr>
            </c:forEach>

            <tr>
                <td colspan="5">
                    <c:if test="${beginPage > 10}">
                        <a href="<c:url value="list.do?p=${beginPage-1}"/> ">이전</a>
                    </c:if>
                    <c:forEach var="pno" begin="${beginPage}" end="${endPage}">
                        <a href="<c:url value="list.do?p=${pno}"/> ">[${pno}]</a>
                    </c:forEach>
                    <c:if test="${endPage < totalPageCount}">
                        <a href="<c:url value="list.do?p=${endPage + 1}"/> ">다음</a>
                    </c:if>
                </td>
            </tr>
        </c:otherwise>
    </c:choose>

    <tr>
        <td colspan="5">
            <a href="write.do">글쓰기</a>
        </td>
    </tr>
</table>

</body>
</html>