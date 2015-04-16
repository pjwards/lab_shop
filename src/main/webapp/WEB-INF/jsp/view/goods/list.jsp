<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 4/16/15 | 11:54 AM
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>상품 목록</title>
</head>
<body>
<table border="1">
    <c:if test="${pagingVO.totalPageCount > 0}">
        <tr>
            <td colspan="13">
                    ${pagingVO.firstRow}-${pagingVO.endRow}
                [${pagingVO.requestPage}/${pagingVO.totalPageCount}]
            </td>
        </tr>
    </c:if>

    <tr>
        <td>상품 번호</td>
        <td>상품 명</td>
        <td>크기</td>
        <td>소재</td>
        <td>구성</td>
        <td>옵션</td>
        <td>제조/수입</td>
        <td>제조국</td>
        <td>상품 설명</td>
        <td>가격</td>
        <td>재고</td>
        <td>등록자</td>
        <td>등록일</td>
    </tr>

    <c:choose>
        <c:when test="${hasGoods == false}">
            <tr>
                <td colspan="13">
                    상품이 없습니다.
                </td>
            </tr>
        </c:when>

        <c:otherwise>
            <c:forEach var="list" items="${goodsVOList}">
                <tr>
                    <td>${list.number}</td>
                    <td>
                        <c:set var="query" value="p=${pagingVO.requestPage}&goodsNumber=${list.number}"/>
                        <a href="<c:url value="read.do?${query}"/> ">
                                ${list.name}
                        </a>
                    </td>
                    <td>${list.size}</td>
                    <td>${list.material}</td>
                    <td>${list.component}</td>
                    <td>${list.options}</td>
                    <td>${list.manufacturer}</td>
                    <td>${list.madein}</td>
                    <td>${list.description}</td>
                    <td>${list.price}</td>
                    <td>${list.stock}</td>
                    <td>${list.userEmail}</td>
                    <td><fmt:formatDate value="${list.createdDate}" pattern="yyyy-MM-dd"/></td>
                </tr>
            </c:forEach>

            <%-- Paging --%>
            <tr>
                <td colspan="13">
                    <c:if test="${pagingVO.beginPage > 10}">
                        <a href="<c:url value="list.do?p=${pagingVO.beginPage-1}"/> ">이전</a>
                    </c:if>
                    <c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
                        <a href="<c:url value="list.do?p=${pno}"/> ">[${pno}]</a>
                    </c:forEach>
                    <c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
                        <a href="<c:url value="list.do?p=${pagingVO.endPage + 1}"/> ">다음</a>
                    </c:if>
                </td>
            </tr>
        </c:otherwise>
    </c:choose>

    <tr>
        <td colspan="13">
            <a href="write.do">상품 등록</a>
        </td>
    </tr>
</table>

</body>
</html>