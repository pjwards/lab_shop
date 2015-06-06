<%--
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-03-23
 * Description  :
 * Copyright ⓒ 2013-2015 Jisung Jeon All rights reserved.
 * version      :
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
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
	
	var stack = "";
	
	$(function() {
		$("#dialog").dialog({
			autoOpen: false
		});
		$(".sendMail").on("click", function() {
			stack = $(this).attr("vals");
			$("#reciver").prop('value',stack);
			$("#dialog").dialog("open");
		});
	});
	
	// Validating Form Fields
	$("#submit").click(function(e) {
		var reciver = $("#reciver").val();
		var title = $("#title").val();
		var content = $("#content").val();
		var emailReg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		
		if (reciver === "" || title === "" || content === "") {
			alert("Please fill all fields!");
			$("#reciver").val('');
			$("#title").val('');
			$("#content").val('');
			e.preventDefault();
		} else if (!(reciver).match(emailReg)) {
			alert("Invalid Email!");
			$("#reciver").val('');
			$("#title").val('');
			$("#content").val('');
			e.preventDefault();
		} else {
			stack="";
			alert("Send it Successfully");
		}
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
				<th scope="col">thumbnail</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${userVOList}">
			 <tr>
				<th scope="row">${list.number }</th>
				<td>${list.lastName }</td>
				<td><a href="#" class="sendMail" id="sendMail" vals="${list.email }" >${list.email }</a></td>
				<td>${list.authority }</td>
				<td><a href="#" class="give_auth" vals="${list.email }" ><span style="font-size:16px;" class="hidden-xs showopacity glyphicon glyphicon-cog"></span></a></td>
			    <td><fmt:formatDate value="${list.createdDate}" pattern="yyyy-MM-dd"/></td>
			    <td><fmt:formatDate value="${list.lastDate}" pattern="yyyy-MM-dd"/></td>
			    <td><img alt="" src="<%=request.getContextPath()%>/resource/upload/${list.imagePath}" height="42" ></td>
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
</div>
<div id="dialog" title="Email">
	<form action="#" method="post">
	<label>Reciver:</label>
	<input type="text" class="form-control" id="reciver" name="reciver" maxlength="250" required="required" placeholder="Email" autocomplete="off" value="">
	<br><label>Title:</label><br>
	<input type="text" class="form-control" id="title" name="title" maxlength="50" required="required" placeholder="Title" autocomplete="off">
	<label>Content:</label>
	<textarea name="content" id="content" rows="10" cols="25"></textarea><br>
	<button type="submit" class="btn btn-primary" id="submit" value="submit">Submit</button>
	</form>
</div>

</body>
<style>
.center {
    width: 50%;
    margin-left: auto;
    margin-right: auto;
}
textarea {
    resize: none;
}
</style>
</html>