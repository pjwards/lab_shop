<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : jisung jeon
 * Date         : 4/16/15 | 11:54 AM
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Lacking Goods List</h2>

<c:if test="${pagingVO.totalPageCount > 0}">
    <tr>
        <td colspan="12">
            ${pagingVO.firstRow}-${pagingVO.endRow}
            [${pagingVO.requestPage}/${pagingVO.totalPageCount}]
        </td>
    </tr>
</c:if>

<table id="box-table-a" class="table table-hover">

    <tr>
        <th scope="col">상품 번호</th>
        <th scope="col">상품 명</th>
        <th scope="col">크기</th>
        <th scope="col">소재</th>
        <th scope="col">구성</th>
        <th scope="col">옵션</th>
        <th scope="col">제조/수입</th>
        <th scope="col">제조국</th>
        <th scope="col">가격</th>
        <th scope="col">재고</th>
        <th scope="col">등록자</th>
        <th scope="col">등록일</th>
    </tr>

    <c:choose>
        <c:when test="${hasGoods == false}">
            <tr>
                <td colspan="12">
                    상품이 없습니다.
                </td>
            </tr>
        </c:when>

        <c:otherwise>
            <c:forEach var="list" items="${goodsVOList}">
                <tr id="goods_${list.number}">
                    <td>${list.number}</td>
                    <td>
                        <c:set var="query" value="p=${pagingVO.requestPage}&goodsNumber=${list.number}"/>
                        <a href="<c:url value="/goods/read.do?${query}"/> ">
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
                </tr>
            </c:forEach>

            <%-- Paging --%>
            <tr>
                <td colspan="12" align="center">
                    <c:if test="${pagingVO.beginPage > 10}">
                        <a href="#" onclick="showList('goodsList','${pagingVO.beginPage-1}','${param.q}'); return false;">이전</a>
                    </c:if>
                    <c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
                        <a href="#" onclick="showList('goodsList','${pno}','${param.q}'); return false;">[${pno}]</a>
                    </c:forEach>
                    <c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
                        <a href="#" onclick="showList('goodsList','${pagingVO.endPage + 1}','${param.q}'); return false;">다음</a>
                    </c:if>
                </td>
            </tr>

        </c:otherwise>
    </c:choose>

</table>

<div id="search_div_goods">
    <form id="search_goods" method="post">
        <input type="text" name="search_word" id="q_goods" onkeypress="search_enter(document.q_goods);" autocomplete="off"/>
        <input type="button" value="search" id="search_goods_btn"/>
    </form>
</div>

<script type="text/javascript">

    contextPath = "${pageContext.request.contextPath}";

    $(document).ready(function(){

        $("#search_goods_btn").click(function(){
            if($("#q_goods").val() == ''){
                alert("Enter Keyword");
                $("#q_goods").focus();
                return false;
            }else{
                showList("goodsList", 1, $("#q_goods").val())
            }
        });
    });

    function search_enter(form){
        var keycode = window.event.keyCode;
        if(keycode == 13) $("#search_goods_btn").click();
    }

</script>
