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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UserList</title>
</head>
<body>
	<table id="box-table-a" class="table table-hover">
		<thead>
			<tr>
				<th scope="col">No</th>
				<th scope="col">Lastname</th>
				<th scope="col">Email</th>
				<th scope="col">Created_Date</th>
				<th scope="col">Last_Date</th>
			</tr>
		</thead>
		<tbody>
	<c:forEach var="list" items="${lists}">
		<tr>
			<th scope="row">${list.number }</th>
			<td>${list.lastName }</td>
			<td>${list.email }</td>
		    <td><fmt:formatDate value="${list.createdDate}" pattern="yyyy-MM-dd"/></td>
		    <td><fmt:formatDate value="${list.lastDate}" pattern="yyyy-MM-dd"/></td>
		</tr>
	</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="16" align="center">
  						<c:if test="${page != 1}">
							<td><a href="<%=request.getContextPath()%>/user/userList.do?page=${page - 1}">Previous</a></td>
						</c:if>
						
  						<c:forEach begin="1" end="${pages}" var="i">
							<c:choose>
								<c:when test="${page eq i}">
									<td>${i}</td>
								</c:when>
								<c:otherwise>
									<td><a href="<%=request.getContextPath()%>/user/userList.do?page=${i}">${i}</a></td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
						<c:if test="${page lt pages}">
							<td><a href="<%=request.getContextPath()%>/user/userList.do?page=${page + 1}">Next</a></td>
						</c:if>
 					</td>
			</tr>
		</tfoot>
	</table>
	<div><p><a href="<%=request.getContextPath()%>/user/userEdit.do" class="btn btn-success">Edit User Info</a></p></div>
	<div><p><a href="<%=request.getContextPath()%>/user/userDelete.do" class="btn btn-danger">Delete Account</a></p></div>
	
</body>
</html>