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
	<c:if test="${pagingVO.totalPageCount > 0}">
        <tr>
            <td colspan="5">
                    ${pagingVO.firstRow}-${pagingVO.endRow}
                [${pagingVO.requestPage}/${pagingVO.totalPageCount}]
            </td>
        </tr>
    </c:if>
    
    <c:choose>
        <c:when test="${hasBoard == false}">
            <tr>
                <td colspan="5">
                    게시글이 없습니다.
                </td>
            </tr>
        </c:when>
        <c:otherwise>
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
			<c:forEach var="list" items="${userVOList}">
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
  						 <c:if test="${pagingVO.beginPage > 5}">
                       		 <a href="<c:url value="userList.do?p=${pagingVO.beginPage-1}"/> ">이전</a>
                    	 </c:if>
                 		 <c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
                       		 <a href="<c:url value="userList.do?p=${pno}"/> ">[${pno}]</a>
             		     </c:forEach>
               		     <c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
                  		     <a href="<c:url value="userList.do?p=${pagingVO.endPage + 1}"/> ">다음</a>
                  		 </c:if>
 					  </td>
					</tr>
				</tfoot>
			</table>
		</c:otherwise>
	</c:choose>
	<li><a href="<%=request.getContextPath()%>/main/main.do">Back Home</a></li>
</body>
</html>