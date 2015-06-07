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
<html>

<c:if test="${isProduct == false}">
    <%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
    <%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
</c:if>

<head>

    <title>상품 목록</title>

    <script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>

    <script type="text/javascript">

        contextPath = "${pageContext.request.contextPath}";

        $(document).ready(function(){
            var isProduct = ${isProduct};

            if(isProduct){
                $(".popup_button").show();
                $(".popup_button_col").attr("colspan", 13);

                $('.btn_add_goods').bind('click', function(e) {
                    var trId = '#goods_' + $(this).attr('name');
                    var addTrId = 'goods_id_' + $(this).attr('name');
                    var price = parseInt($('#price_' + $(this).attr('name')).text());
                    var totalPrice = parseInt($('#total_price').prop('value'));

                    if ( $("#"+addTrId).length > 0 ) { alert("이미 등록한 상품은 추가로 등록할 수 없습니다."); return; }

                    var trClone = $(trId).clone().html();
                    //var input = '<input type=\"hidden\" name=\"goods_'+$(this).attr('name')+'\" value=\"'+$(this).attr('name')+'\"/>'
                    var input = '<input type=\"hidden\" name=\"goods\" value=\"'+$(this).attr('name')+'\"/>'

                    // Prevents the default action to be triggered.
                    e.preventDefault();

                    var html = String('<tr id=\"'+addTrId+'\">'+trClone+input+'</tr>').replace('class="btn_add_goods"',
                            'onclick=\"delGoods('+$(this).attr('name')+')\"');

                    $("#empty_goods").hide();
                    $("#selected_goods  > tbody:last").append(html);
                    $('#total_price').prop('value',totalPrice+price);
                });
            }

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

        function loadPage(url) {
            $('#element_to_pop_up .content').load(contextPath + url)
        };

        function search_enter(form){
            var keycode = window.event.keyCode;
            if(keycode == 13) $("#search_btn").click();
        }

    </script>
</head>
<body>

<c:if test="${isProduct == false}">
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
</c:if>

    <c:if test="${pagingVO.totalPageCount > 0}">
        <tr>
            <td class="popup_button_col" colspan="12">
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
            <th scope="col" class="popup_button" style="display:none;">추가</th>
        </tr>

        <c:choose>
            <c:when test="${hasGoods == false}">
                <tr>
                    <td class="popup_button_col" colspan="12">
                        상품이 없습니다.
                    </td>
                </tr>
            </c:when>

            <c:otherwise>
                <c:forEach var="list" items="${goodsVOList}">
                    <tr id="goods_${list.number}">
                        <td>${list.number}</td>
                        <td>

                            <c:if test="${isProduct == false}">
                                <c:set var="query" value="p=${pagingVO.requestPage}&goodsNumber=${list.number}"/>
                                <a href="<c:url value="/goods/read.do?${query}"/> ">
                                        ${list.name}
                                </a>
                            </c:if>

                            <c:if test="${isProduct == true}">
                                <c:set var="query" value="p=${pagingVO.requestPage}&goodsNumber=${list.number}"/>
                                <a target="_blank " href="<c:url value="/goods/read.do?${query}"/> ">
                                        ${list.name}
                                </a>
                            </c:if>
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
                        <td class="popup_button" style="display:none;"><button class="btn_add_goods" name="${list.number}">선택</button></td>
                    </tr>
                </c:forEach>

                <%-- Paging --%>
                <c:if test="${isProduct == false}">
                    <tr>
                        <td class="popup_button_col" colspan="12" align="center">
                            <c:if test="${pagingVO.beginPage > 10}">
                                <a href="<c:url value="/goods/list.do?p=${pagingVO.beginPage-1}"/> ">이전</a>
                            </c:if>
                            <c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
                                <a href="<c:url value="/goods/list.do?p=${pno}"/> ">[${pno}]</a>
                            </c:forEach>
                            <c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
                                <a href="<c:url value="/goods/list.do?p=${pagingVO.endPage + 1}"/> ">다음</a>
                            </c:if>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${isProduct == true}">
                    <tr>
                        <td class="popup_button_col" colspan="12" align="center">
                            <c:if test="${pagingVO.beginPage > 10}">
                                <a href="#" onclick="loadPage('/goods/list.do?p=${pagingVO.beginPage-1}'); return false;">이전</a>
                            </c:if>
                            <c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
                                <a href="#" onclick="loadPage('/goods/list.do?p=${pno}'); return false;">[${pno}]</a>
                            </c:forEach>
                            <c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
                                <a href="#" onclick="loadPage('/goods/list.do?p=${pagingVO.endPage + 1}'); return false;">다음</a>
                            </c:if>
                        </td>
                    </tr>
                </c:if>
            </c:otherwise>
        </c:choose>


        <c:if test="${isProduct == false}">
            <tr>
                <td class="popup_button_col" colspan="12">
                    <a href="<c:url value="/goods/write.do" />">상품 등록</a>
                </td>
            </tr>
        </c:if>

        <c:if test="${isProduct == true}">
            <tr>
                <td class="popup_button_col" colspan="12">
                    <a target="_blank " href="<c:url value="/goods/write.do" />">상품 등록</a>
                </td>
            </tr>
        </c:if>

    </table>

    <div id="search_div">
        <form id="search" method="post">
            <input type="text" name="search_word" id="q" onkeypress="search_enter(document.q);" autocomplete="off"/>
            <input type="button" value="search" id="search_btn"/>
        </form>
    </div>

<c:if test="${isProduct == false}">
</div>
</c:if>

</body>
</html>