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
<script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>

<title>Cart</title>

<script type="text/javascript">
$(document).ready(function(){
	$("a#del_cart").click(function(){
		var choice = prompt("Delete this? yes/no").trim().toLowerCase();
		
		if(choice === ""){
			return false;
		}
		if(choice !== "yes"){
			return false;
		}
		
		var data = $("a#del_cart").attr("vals");
		
		var link = 'delCart.do?&no='+data+'&choice='+choice;
		$(location).attr('href', link);
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
				<td>${list.goodsVO.name}</td>
				<td>${list.goodsVO.manufacturer }</td>
				<td>${list.goodsVO.size }</td>
				<td>${list.goodsVO.price }</td>
				<td>${list.goodsVO.options}</td>
				<td>${list.quantity }</td>
				<td>${list.quantity * list.goodsVO.price}</td>
				<td><a href="#" id="del_cart" vals="${list.goodsVO.number}">Cancel</a></td>
			 </tr>
			</c:forEach>
			<tr>
				<td colspan="5" align="right">SUM</td>
				<td>${s }</td>
			</tr>
		</tbody>
	 </table>
   	</c:otherwise>
</c:choose>
<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</body>
</html>