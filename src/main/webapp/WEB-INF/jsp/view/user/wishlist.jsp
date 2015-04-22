<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>

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
	
	$("a#del_wish").click(function(){
		var choice = prompt("Delete this? yes/no").trim().toLowerCase();
		
		if(choice === ""){
			return false;
		}
		if(choice !== "yes"){
			return false;
		}
		
		var data = $("a#del_wish").attr("vals");
		var arr = data.split('/');
		
		var link = 'delWishlist.do?&email='+arr[0]+'&no='+arr[1]+'&choice='+choice;
		$(location).attr('href', link);
	});
});

function search_enter(form){
	var keycode = window.event.keyCode;
	if(keycode == 13) $("#search_btn").click();
}
</script>
</head>
<body>
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
				<th scope="col">Name</th>
				<th scope="col">Manufacturer</th>
				<th scope="col">Price</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${wishlist}">
			 <tr>
				<th scope="row">${list.name }</th>
				<td>${list.manufacturer }</td>
				<td>${list.price }</td>
				<td><a href="#" id="del_wish" vals="${list.userEmail }/${list.no}">Delete</a></td>
			 </tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="16" align="center">
  				 <c:if test="${pagingVO.beginPage > 5}">
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
		<input type="text" name="search_word" id="q" onkeypress="search_enter(document.q);"/>
		<input type="button" value="search" id="search_btn"/>
	</form>
	
	<a href="<%=request.getContextPath()%>/user/wishlist.do">Back</a>
	<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</body>
</html>