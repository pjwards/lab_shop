<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 2015-03-24 | 오전 11:37
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap-theme.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/daumeditor/css/editor.css" type="text/css" charset="utf-8"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bpopup.css" type="text/css" charset="utf-8"/>
<script src="<%=request.getContextPath()%>/resource/daumeditor/js/editor_loader.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/jquery.bpopup.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/board/editor.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/board/popup.js"></script>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>

    <title>글 수정</title>

    <style type="text/css">
        textarea {
            resize: none;
        }
    </style>

    <script type="text/javascript">
        contextPath = "${pageContext.request.contextPath}";
    </script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<!-- start editor -->
<div>
    <form id="frm" name="frm" action="<c:url value="/board/update.do" />" method="post" accept-charset="utf-8">

        <input type="hidden" name="boardNumber" value="${boardVO.number}"/>
        <input type="hidden" name="s" value="${param.s}"/>
        <input type="hidden" name="p" value="${param.p}"/>

        제목 : <input type="text" name="title" size="100" value="${boardVO.title}"/><br/>

        <c:if test="${param.s == 'product'}">
            <table id="selected_goods" border="1">
                <tbody>
                <tr>
                    <td>상품 번호</td>
                    <td>상품 명</td>
                    <td>크기</td>
                    <td>소재</td>
                    <td>구성</td>
                    <td>옵션</td>
                    <td>제조/수입</td>
                    <td>제조국</td>
                    <td>가격</td>
                    <td>재고</td>
                    <td>등록자</td>
                    <td>등록일</td>
                    <td>삭제</td>
                </tr>

                <c:choose>
                    <c:when test="${hasGoods == false}">
                        <tr>
                            <td id="empty_goods" colspan="13">
                                상품이 없습니다.
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>
                        <c:forEach var="list" items="${goodsVOList}">
                            <tr id="goods_id_${list.number}">
                                <td>${list.number}</td>
                                <td>
                                    <c:set var="query" value="goodsNumber=${list.number}"/>
                                    <a target="_blank " href="<c:url value="/goods/read.do?${query}"/> ">
                                            ${list.name}
                                    </a>
                                </td>
                                <td>${list.size}</td>
                                <td>${list.material}</td>
                                <td>${list.component}</td>
                                <td>${list.options}</td>
                                <td>${list.manufacturer}</td>
                                <td>${list.madein}</td>
                                <td id="price_${list.number}">${list.price}</td>
                                <td>${list.stock}</td>
                                <td>${list.userEmail}</td>
                                <td><fmt:formatDate value="${list.createdDate}" pattern="yyyy-MM-dd"/></td>
                                <td class="popup_button"><button onclick="delGoods(${list.number})" name="${list.number}">선택</button></td>
                                <input type="hidden" name="goods" value="${list.number}">
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

                </tbody>
            </table>

            <button id="popup">상품 추가</button><br/>

            전체 가격 : <input type="text" name="total_price" id="total_price" size="30" value="${boardVO.totalPrice}"/><br/>

            <div id='element_to_pop_up' style='display:none;'>
                <span class='button b-close'><span>X</span></span> <!-- 닫기 버튼 (스타일은 알아서 지정) -->
                <div class='content'></div> <!-- 컨텐츠가 들어갈 영역 지정 (이미지, html 등.. 여러가지를 동적으로 부를수 있다. -->
            </div>
        </c:if>

        <!-- call editor frame -->
        <div id="editor_frame"></div>
        <textarea name="daumeditor" id="daumeditor" rows="10" cols="100" style="width:766px; height:412px;display: none;">${boardVO.content}</textarea>
        <c:if test="${param.s == 'product'}">
    	Thumbnail
    	<input type="file" name="thumnail" id="thumnail"/><br>
    	</c:if>
   		<input type="hidden" name="hid" value="${param.s}" id="hid"/>
   		<input type="button" onclick="location.href='list.do?s=${param.s}'" value="취소"/>
        <input type="button" id="save_button" value="전송"/>
    </form>
</div>
<!-- end editor -->
<script type="text/javascript" >
    Editor.modify({
    "content":$tx('daumeditor')
    });
</script>
</div>
</body>
</html>
