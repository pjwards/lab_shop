<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : jisung jeon
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

    <script type="text/javascript">

        contextPath = "${pageContext.request.contextPath}";

        $(document).ready(function(){
            var parentReferrer = parent.document.referrer;

            if(~parentReferrer.indexOf('board')){
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
        });

        function loadPage(url) {
            $('#element_to_pop_up .content').load(contextPath + url)
        };

    </script>
</head>
<body>
<table border="1">
    <c:if test="${pagingVO.totalPageCount > 0}">
        <tr>
            <td class="popup_button_col" colspan="12">
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
        <td>가격</td>
        <td>재고</td>
        <td>등록자</td>
        <td>등록일</td>
        <td class="popup_button" style="display:none;">추가</td>
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
                        <c:set var="query" value="p=${pagingVO.requestPage}&goodsNumber=${list.number}"/>
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
                    <td class="popup_button" style="display:none;"><button class="btn_add_goods" name="${list.number}">선택</button></td>
            	</tr>
            </c:forEach>

            <%-- Paging --%>
            <c:if test="${header.referer.contains('goods')}">
                <tr>
                    <td class="popup_button_col" colspan="12">
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
            <c:if test="${header.referer.contains('board')}">
                <tr>
                    <td class="popup_button_col" colspan="12">
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

    <tr>
        <td class="popup_button_col" colspan="12">
            <a target="_blank " href="<c:url value="/goods/write.do" />">상품 등록</a>
        </td>
    </tr>

</table>
<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</body>
</html>