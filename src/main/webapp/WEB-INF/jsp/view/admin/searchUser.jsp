<%--
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-04-21
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>

<title>Search USER</title>

<script type="text/javascript">
$(document).ready(function(){
	$("#search_btn").click(function(){
		if($("#q").val() == ''){
			alert("Enter Keyword");
			$("#q").focus();
			return false;
		}else{
			var act = 'search.do?q='+$("#q").val();
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
<h1>Search</h1>
<form id="search" method="post">
		<input type="text" name="search_word" id="q" onkeypress="search_enter(document.q);" autocomplete="off"/>
		<input type="button" value="search" id="search_btn"/>
</form>

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
				<th scope="col">Order Status</th>
				<th scope="col">User email</th>
				<th scope="col">User name</th>
				<th scope="col">Product ID</th>
				<th scope="col">Product</th>
				<th scope="col">Options</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${lists}">
			 <tr>
				<th scope="row">${list.number }</th>
				<td><fmt:formatDate value="${list.orderDate}" pattern="yyyy-MM-dd"/></td>
				<td>${list.orderNow }</td>
				<td>${list.userEmail }</td>
				<td>${list.userName }</td>
				<td>${list.goodsNumber }</td>
				<td>${list.goodsName }</td>
				<td>${list.goodsOptions }</td>
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
</div>
</body>
</html>