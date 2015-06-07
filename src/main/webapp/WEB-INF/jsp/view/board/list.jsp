<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 2015-03-21 | 오후 7:16
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
	
    <title>게시글 목록</title>

    <script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function(){
            $("#search_btn").click(function(){
                if($("#q").val() == ''){
                    alert("Enter Keyword");
                    $("#q").focus();
                    return false;
                }else{
                    var act = 'list.do?s=${param.s}&q='+$("#q").val();
                    $("#search").attr('action',act).submit();
                }
            });
        });

        function search_enter(form){
            var keycode = window.event.keyCode;
            if(keycode == 13) $("#search_btn").click();
        }
    </script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
    <c:if test="${pagingVO.totalPageCount > 0}">
        <tr>
            <td colspan="5">
                    ${pagingVO.firstRow}-${pagingVO.endRow}
                [${pagingVO.requestPage}/${pagingVO.totalPageCount}]
            </td>
        </tr>
    </c:if>

<table id="box-table-a" class="table table-hover">
    <tr>
        <th scope="col">글 번호</th>
        <th scope="col">제목</th>
        <th scope="col">작성자</th>
        <th scope="col">작성일</th>
        <th scope="col">조회수</th>
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
                	<c:if test="${param.s == 'product'}">
                	<td><img alt="" src="<%=request.getContextPath()%>/resource/upload/${list.imagePath}" height="42" ></td>
                	</c:if>
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
                <td colspan="5" align="center">
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

    <div id="search_div">
        <form id="search" method="post">
            <input type="text" name="search_word" id="q" onkeypress="search_enter(document.q);" autocomplete="off"/>
            <input type="button" value="search" id="search_btn"/>
        </form>
    </div>
</div>

</body>
</html>