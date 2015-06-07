<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 2015-06-06
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<h2>Orders</h2>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<table id="box-table-a" class="table table-hover">
		<thead>
			<tr>
				<th scope="col">Total Price</th>
				<th scope="col">Total Count</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${totalPrice}</td>
				<td>${totalCount}</td>
			</tr>
		</tbody>
	</table>
</sec:authorize>

<c:choose>
	<c:when test="${lists == NULL}">
            <tr>
                <td colspan="5">
                    No result.
                </td>
            </tr>
   	</c:when>
   	<c:otherwise>
   		<c:if test="${pagingVO.totalPageCount > 0}">
     	   <tr>
    	        <td colspan="5">
    	                ${pagingVO.firstRow}-${pagingVO.endRow}
    	            [${pagingVO.requestPage}/${pagingVO.totalPageCount}]
    	        </td>
  	      </tr>
   		</c:if>
   		<table id="box-table-a" class="table table-hover">
		<thead>
			<tr>
				<th scope="col">Order Number</th>
				<th scope="col">Order Date</th>
				<th scope="col">Product</th>
				<th scope="col">Price</th>
				<th scope="col">Quantity</th>
				<th scope="col">State of Order</th>
				<th scope="col">Orderer</th>
				<th scope="col">Receiver</th>
				<th scope="col">Delete it on List</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="s" value="0"></c:set>
			<c:forEach var="list" items="${lists}">
				<c:set var="s" value="${s + list.quantity * list.totalPrice}"></c:set>
			 <tr>
				<th scope="row">${list.number }</th>
				<td><fmt:formatDate value="${list.orderDate}" pattern="yyyy-MM-dd"/></td>
				<td><a href="<%=request.getContextPath()%>/board/read.do?s=product&boardNumber=${list.boardNumber}">${list.title }</a></td>
				<td>${list.totalPrice }</td>
				<td>${list.quantity }</td>
				<td>${list.orderNow }</td>
				<td>${list.userName}</td>
				<td>${list.receiver}</td>
				<td><a href="#" class="del_order" vals="${list.number}">Delete</a></td>
			 </tr>
			</c:forEach>
		</tbody>

		<tfoot>
			<tr>
				<td colspan="16" align="center">
					<c:if test="${pagingVO.beginPage > 10}">
						<a href="#" onclick="showList('orderList','${pagingVO.beginPage-1}','${param.q}'); return false;">이전</a>
					</c:if>
					<c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
						<a href="#" onclick="showList('orderList','${pno}','${param.q}'); return false;">[${pno}]</a>
					</c:forEach>
					<c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
						<a href="#" onclick="showList('orderList','${pagingVO.endPage + 1}','${param.q}'); return false;">다음</a>
					</c:if>
 				 </td>
			</tr>
		</tfoot>
	 </table>
   	</c:otherwise>
</c:choose>

<div id="search_div_order">
	<form id="search_order" method="post">
		<input type="text" name="search_word" id="q_order" onkeypress="search_enter(document.q_order);" autocomplete="off"/>
		<input type="button" value="search" id="search_order_btn"/>
	</form>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		function confirmation(question) {
			var defer = $.Deferred();
			$('<div></div>')
					.html(question)
					.dialog({
						autoOpen: true,
						modal: true,
						title: 'Confirmation',
						buttons: {
							"Yes": function () {
								defer.resolve("true");
								$(this).dialog("close");
							},
							"No": function () {
								defer.resolve("false");
								$(this).dialog("close");
							}
						},
						close: function () {
							$(this).remove();
						}
					});
			return defer.promise();
		}

		$(".del_order").click(function(){
			var question = "Do you want to delete it?";
			var data = $(this).attr("vals");

			confirmation(question).then(function (answer) {
				var ansbool = (String(answer) == "true");
				if(ansbool){
					var arr = data.split('/');
					var link = 'delOrderlist.do?&no='+data+'&choice='+ansbool;
					$(location).attr('href', link);
				}
			});
		});

		$("#search_order_btn").click(function(){
			if($("#q_order").val() == ''){
				alert("Enter Keyword");
				$("#q_order").focus();
				return false;
			}else{
				showList("orderList", 1, $("#q_order").val())
			}
		});
	});

	function search_enter(form){
		var keycode = window.event.keyCode;
		if(keycode == 13) $("#search_order_btn").click();
	}

</script>