<%--
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-04-23
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>

<title>Your Orders</title>

<script type="text/javascript">
$(document).ready(function(){
	$("a#del_order").click(function(){
		var choice = prompt("Delete this? yes/no").trim().toLowerCase();
		
		if(choice === ""){
			return false;
		}
		if(choice !== "yes"){
			return false;
		}
		
		var data = $("a#del_order").attr("vals");
		var arr = data.split('/');
		
		var link = 'delOrderlist.do?&email='+arr[0]+'&no='+arr[1]+'&choice='+choice;
		$(location).attr('href', link);
	});
});
</script>
</head>
<body>

<h1>Orders</h1>

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
				<th scope="col">Order Status</th>
				<th scope="col">Product</th>
				<th scope="col">Brand</th>
				<th scope="col">Price</th>
				<th scope="col">Options</th>
				<th scope="col">Cancel</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${lists}">
			 <tr>
				<th scope="row">${list.number }</th>
				<td>${list.orderNow }</td>
				<td>${list.goodsName }</td>
				<td>${list.manufacturer }</td>
				<td>${list.goodsPrice }</td>
				<td>${list.goodsOptions }</td>
				<td><a href="#" id="del_order" vals="${list.userEmail }/${list.number}">Cancel</a></td>
			 </tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="16" align="center">
  					<c:if test="${pagingVO.beginPage > 5}">
                       	<a href="<c:url value="search.do?q=${keyword }&p=${pagingVO.beginPage-1}"/> ">이전</a>
                   	</c:if>
                 	<c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
                       	<a href="<c:url value="search.do?q=${keyword }&p=${pno}"/> ">[${pno}]</a>
             		</c:forEach>
               		<c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
                  		<a href="<c:url value="search.do?q=${keyword }&p=${pagingVO.endPage + 1}"/> ">다음</a>
                  	</c:if>
 				 </td>
			</tr>
		</tfoot>
	 </table>
   	</c:otherwise>
</c:choose>
<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</body>
</html>