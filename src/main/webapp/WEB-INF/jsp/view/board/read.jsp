<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 2015-03-23 | 오전 10:07
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>글 읽기</title>
   
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/bpopup.css" type="text/css" charset="utf-8"/>
    <script src="${pageContext.request.contextPath}/resource/js/jquery-2.1.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/js/board/comment.js"></script>
    <script type="text/javascript">
        contextPath = "${pageContext.request.contextPath}";
        boardNumber = "${boardVO.number}";

        var isWriting = false;		// 덧글 쓰기 중일 경우 true
        var isReplying = false;		// 답글 쓰기 중일 경우 true
        var isUpdating = false;		// 수정 중일 경우 true
        var cs1 = 'review';
        var cs2 = 'qna';

        var loading_comment =
                function() {
                    $("#read_comment").html("<table><tr><td><br/><img src='" + contextPath + "/resource/img/board/loader-comm.gif'/><br/><br /></td></tr></table>");
                };

        // 상품 리스트 로딩.
        function showGoods() {
            if ( $("#goods_list").length == 0 ) { return; }

            $("#goods_list").load(
                    contextPath + "/goods/listByBoard.do",
                    {
                        boardNumber:boardNumber
                    }
            )
        }

        $(document).ready(function(){
            showComment(1, cs1);
            showComment(1, cs2);
            showGoods();
        });
    </script>
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
            <c:if test="${param.s == 'product'}">
                <div id="goods_list"></div>
            </c:if>
            ${boardVO.content}
        </td>
    </tr>

    <tr>
        <td colspan="2">
            <input type="button" onclick="location.href='list.do?s=${param.s}&p=${param.p}'" value="목록보기"/>
            <c:if test="${param.s != 'product'}">
                <input type="button" onclick="location.href='reply.do?s=${param.s}&p=${param.p}&parentBoardNumber=${boardVO.number}'" value="답변하기"/>
            </c:if>
            <input type="button" onclick="location.href='update.do?s=${param.s}&p=${param.p}&boardNumber=${boardVO.number}'" value="수정하기"/>
            <form action="<c:url value="/board/delete.do" />" method="post" style="display: inline;">
                <input type="hidden" name="p" value="${param.p}"/>
                <input type="hidden" name="s" value="${param.s}"/>
                <input type="hidden" name="boardNumber" value="${boardVO.number}"/>
                <input type="submit" value="삭제하기" >
            </form>
        </td>
    </tr>

</table>

<!-- 댓글 구현부 -->
<div id="review"></div>
<div id="qna"></div>

</body>
</html>
