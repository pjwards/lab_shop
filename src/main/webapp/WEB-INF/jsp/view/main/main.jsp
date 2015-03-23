<%--
  Created by IntelliJ IDEA.
  User: Donghyun Seo (egaoneko@naver.com)
  Date: 2015-03-22
  Time: 오후 2:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index Page</title>
</head>

<body>
<h1>테스트 페이지</h1>
<ul>
	<li><a href="<%=request.getContextPath()%>/user/userList.do">User List</a></li>
	<li><a href="<%=request.getContextPath()%>/board/list.do">Board List</a></li>
    <li><a href="<%=request.getContextPath()%>/board/list.do?s=notice">Notice Board List</a></li>
    <li><a href="<%=request.getContextPath()%>/board/list.do?s=qna">QnA Board List</a></li>
</ul>


</body>

</html>