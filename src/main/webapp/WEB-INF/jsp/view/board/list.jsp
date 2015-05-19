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
    <c:if test="${pagingVO.totalPageCount > 0}">
        <tr>
            <td colspan="5">
                    ${pagingVO.firstRow}-${pagingVO.endRow}
                [${pagingVO.requestPage}/${pagingVO.totalPageCount}]
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
                        <c:set var="query" value="s=${param.s}&p=${pagingVO.requestPage}&boardNumber=${list.number}"/>
                        <a href="<c:url value="read.do?${query}"/> ">
                                ${list.title} (${list.commentCount})
                        </a>
                    </td>
                    <td>${list.userEmail}</td>
                    <td><fmt:formatDate value="${list.postingDate}" pattern="yyyy-MM-dd"/></td>
                    <td>${list.readCount}</td>
                </tr>
            </c:forEach>

            <%-- Paging --%>
            <tr>
                <td colspan="5">
                    <c:if test="${pagingVO.beginPage > 10}">
                        <a href="<c:url value="list.do?s=${param.s}&p=${pagingVO.beginPage-1}"/> ">이전</a>
                    </c:if>
                    <c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
                        <a href="<c:url value="list.do?s=${param.s}&p=${pno}"/> ">[${pno}]</a>
                    </c:forEach>
                    <c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
                        <a href="<c:url value="list.do?s=${param.s}&p=${pagingVO.endPage + 1}"/> ">다음</a>
                    </c:if>
                </td>
            </tr>
        </c:otherwise>
    </c:choose>

    <tr>
        <td colspan="5">
            <a href="write.do?s=${param.s}">글쓰기</a>
        </td>
    </tr>
</table>
<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</body>
</html>