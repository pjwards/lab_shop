<%--
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-03-23
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/jqueryui/jquery-ui.css">
<script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/jqueryui/jquery-ui.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	$("#search_btn").click(function(){
		if($("#q").val() == ''){
			alert("Enter Keyword");
			$("#q").focus();
			return false;
		}else{
			var act = 'userList.do?q='+$("#q").val();
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
	                "User": function () {
	                    defer.resolve("true");
	                    $(this).dialog("close");
	                },
	                "Admin": function () {
	                    defer.resolve("false");
	                    $(this).dialog("close");
	                },
	                "Exit": function () {
	                	 defer.resolve("");
	                    $(this).dialog("close");
	                }
	            },
	            close: function () {
	                $(this).remove();
	            }
	        });
	    return defer.promise();
	}
	
	$(".give_auth").click(function(){
		var question = "Select authority";
		var email = $(this).attr("vals");
		confirmation(question).then(function (answer) {
			if(answer == ""){
				return false;
			}
		    var ansbool = (String(answer) == "true");
		    if(ansbool){
				var link = 'giveAuth.do?email='+email+'&auth='+ansbool;//true(user)
				$(location).attr('href', link);
		    }else{
				var link = 'giveAuth.do?email='+email+'&auth='+ansbool;//false(admin)
				$(location).attr('href', link);
		    }
		});	
		
	});
});

function search_enter(form){
	var keycode = window.event.keyCode;
	if(keycode == 13) $("#search_btn").click();
}
</script>

<title>UserList</title>
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
                    사용자가 없습니다.
                </td>
            </tr>
        </c:when>
        <c:otherwise>
	<table id="box-table-a" class="table table-hover">
		<thead>
			<tr>
				<th scope="col">
				<c:choose>
					<c:when test="${order == 'no_asc' }">
						<a href="userList.do?q=${keyword }&order=no_desc">No up</a> 
					</c:when>
					<c:when test="${order == 'no_desc' }">
						<a href="userList.do?q=${keyword }&order=no_asc">No down</a> 
					</c:when>
					<c:otherwise>
						<a href="userList.do?order=no_asc">No</a>
					</c:otherwise>
				</c:choose>
				</th>
				<th scope="col">Lastname</th>
				<th scope="col">Email</th>
				<th scope="col">Authority</th>
				<th scope="col">Change Auth</th>
				<th scope="col">
				<c:choose>
					<c:when test="${order == 'creDate_asc'}">
						<a href="userList.do?q=${keyword }&order=creDate_desc">Created_Date up</a> 
					</c:when>
					<c:when test="${order == 'creDate_desc'}">
						<a href="userList.do?q=${keyword }&order=creDate_asc">Created_Date down</a> 
					</c:when>
					<c:otherwise>
						<a href="userList.do?order=creDate_asc">Created_Date</a>
					</c:otherwise>
				</c:choose>
				</th>
				<th scope="col">
				<c:choose>
					<c:when test="${order == 'lastDate_asc'}">
						<a href="userList.do?q=${keyword }&order=lastDate_desc">Last_Date up</a> 
					</c:when>
					<c:when test="${order == 'lastDate_desc'}">
						<a href="userList.do?q=${keyword }&order=lastDate_asc">Last_Date down</a> 
					</c:when>
					<c:otherwise>
						<a href="userList.do?order=lastDate_asc">Last_Date</a>
					</c:otherwise>
				</c:choose>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${userVOList}">
			 <tr>
				<th scope="row">${list.number }</th>
				<td>${list.lastName }</td>
				<td>${list.email }</td>
				<td>${list.authority }</td>
				<td><a href="#" class="give_auth" vals="${list.email }">Img</a></td>
			    <td><fmt:formatDate value="${list.createdDate}" pattern="yyyy-MM-dd"/></td>
			    <td><fmt:formatDate value="${list.lastDate}" pattern="yyyy-MM-dd"/></td>
			    <td><img alt="" src="<%=request.getContextPath()%>/resource/upload/${list.imagePath}"></td>
			 </tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="16" align="center">
  				 <c:if test="${pagingVO.beginPage > 10}">
                    	 <a href="<c:url value="userList.do?q=${keyword }&order=${order}&p=${pagingVO.beginPage-1}"/> ">이전</a>
                 </c:if>
                 <c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
                     	 <a href="<c:url value="userList.do?q=${keyword }&order=${order}&p=${pno}"/> ">[${pno}]</a>
             	 </c:forEach>
                 <c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
                  		 <a href="<c:url value="userList.do?q=${keyword }&order=${order}&p=${pagingVO.endPage + 1}"/> ">다음</a>
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
	
	<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</body>
</html>