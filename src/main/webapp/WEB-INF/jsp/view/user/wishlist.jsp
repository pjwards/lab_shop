<%--
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Last Update  : 2015-05-12
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>Wishlist</title>

<script type="text/javascript">
$(document).ready(function(){
	$("#search_btn").click(function(){
		if($("#q").val() == ''){
			alert("Enter Keyword");
			$("#q").focus();
			return false;
		}else{
			var act = 'wishlist.do?q='+$("#q").val();
			$("#search").attr('action',act).submit();
		}
	});
	
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
	
	$(".del_wish").click(function(){
		var question = "Do you want to delete it?";
		var data = $(this).attr("vals");
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) == "true");
		    if(ansbool){
				var arr = data.split('/');
				var link = 'delWishlist.do?&email='+arr[0]+'&no='+arr[1]+'&choice='+ansbool;
				$(location).attr('href', link);
		    }
		});		
	});
	
	$(".cart").click(function(){
		var question = "Do you want to do it?";
		var data = $(this).attr("vals");
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) == "true");
		    if(ansbool){
				var arr = data.split('/');
				$.ajax({
					type:"POST",
					url:"<%=request.getContextPath()%>/goods/addCartAjax.do",
					data:{ number : arr[1], choice : arr[0] },
					success:function(result){
						if(result === "400"){
							alert("Error");
						}else if(result === "200"){
							alert("Copied in Cart");
						}else if(result === "202"){
							alert("Moved in Cart");
						}else{
							alert("Already listed");
						}
					}
				});
		    }
		});		
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
	<c:if test="${pagingVO.totalPageCount > 0}">
        <tr>
            <td colspan="5">
                    ${pagingVO.firstRow}-${pagingVO.endRow}
                [${pagingVO.requestPage}/${pagingVO.totalPageCount}]
            </td>
        </tr>
    </c:if>
    
    <c:choose>
        <c:when test="${hasUser == false}">
            <tr>
                <td colspan="5">
                    No result
                </td>
            </tr>
        </c:when>
        <c:otherwise>
	<table id="box-table-a" class="table table-hover">
		<thead>
			<tr>
				<th scope="col"></th>
				<th scope="col">Product</th>
				<th scope="col">Price</th>
				<th scope="col">Go to Cart</th>
				<th scope="col">Copy to Cart</th>
				<th scope="col">Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${wishlist}">
			 <tr>
                <th scope="row"><img alt="" src="<%=request.getContextPath()%>/resource/upload/${list.imagePath}" height="42" ></th>
				<td><a href="<%=request.getContextPath()%>/board/read.do?boardNumber=${list.boardNumber}">${list.title }</a></td>
				<td>${list.price }</td>
				<td><a href="#" class="cart" vals="go/${list.boardNumber}">Go</a></td>
				<td><a href="#" class="cart" vals="copy/${list.boardNumber}">Copy</a></td>			
				<td><a href="#" class="del_wish" vals="${list.userEmail }/${list.boardNumber}">Delete</a></td>
			 </tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="16" align="center">
  				 <c:if test="${pagingVO.beginPage > 10}">
                    	 <a href="<c:url value="wishlist.do?q=${keyword }&p=${pagingVO.beginPage-1}"/> ">이전</a>
                 </c:if>
                 <c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
                     	 <a href="<c:url value="wishlist.do?q=${keyword }&p=${pno}"/> ">[${pno}]</a>
             	 </c:forEach>
                 <c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
                  		 <a href="<c:url value="wishlist.do?q=${keyword }&p=${pagingVO.endPage + 1}"/> ">다음</a>
                 </c:if>
 				</td>
			</tr>
		</tfoot>
	</table>
		</c:otherwise>
	</c:choose>
	
	<form id="search" method="post">
		<input type="text" name="search_word" id="q" onkeypress="search_enter(document.q);" autocomplete="off"/>
		<input type="button" value="search" id="search_btn"/>
	</form>
	
	<a href="<%=request.getContextPath()%>/user/wishlist.do">Back</a>
	<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</div>
</body>
</html>