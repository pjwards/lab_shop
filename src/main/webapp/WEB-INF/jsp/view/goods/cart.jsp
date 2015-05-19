<%--
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-04-28
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>

<title>Cart</title>

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
	
	$(".del_cart").click(function(){
		var question = "Do you want to delete it?";
		var data = $(this).attr("vals");
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) == "true");
		    if(ansbool){
				var link = 'delCart.do?&no='+data+'&choice='+ansbool;
				$(location).attr('href', link);
		    }
		});		
	});
});
</script>
</head>
<body>

<h1>Cart</h1>

<c:choose>
	<c:when test="${sessionScope.cart == NULL}">
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
				<th scope="col">Number</th>
				<th scope="col">Product</th>
				<th scope="col">Brand</th>
				<th scope="col">Size</th>
				<th scope="col">Price</th>
				<th scope="col">Options</th>
				<th scope="col">Quantity</th>
				<th scope="col">Sub Total</th>
				<th scope="col">Cancel</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="s" value="0"></c:set>
			<c:forEach var="list" items="${sessionScope.cart}">
				<c:set var="s" value="${s + list.quantity * list.goodsVO.price}"></c:set>
			 <tr>
				<th scope="row">${list.goodsVO.number }</th>
				<td><a href="<%=request.getContextPath()%>/goods/read.do?goodsNumber=${list.goodsVO.number}">${list.goodsVO.name}</a></td>
				<td>${list.goodsVO.manufacturer }</td>
				<td>${list.goodsVO.size }</td>
				<td>${list.goodsVO.price }</td>
				<td>${list.goodsVO.options}</td>
				<td>${list.quantity }</td>
				<td>${list.quantity * list.goodsVO.price}</td>
				<td><a href="#" class="del_cart" vals="${list.goodsVO.number}">Cancel</a></td>
			 </tr>
			</c:forEach>
			<tr>
				<td colspan="5" align="right">Total Price</td>
				<td>${s }</td>
				<td><a href="<%=request.getContextPath()%>/goods/addOrders.do">Buy</a></td>
			</tr>
		</tbody>
	 </table>
   	</c:otherwise>
</c:choose>
<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</body>
</html>