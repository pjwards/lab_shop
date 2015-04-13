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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit</title>
</head>
<body>
	<form action="" method="post" enctype="multipart/form-data">
		<input type="text" class="form-control" name="firstName" maxlength="10" required="required" placeholder="Write your First name" autocomplete="off" value="${uservo.firstName}"><br>
		<input type="text" class="form-control" name="lastName" maxlength="10" required="required" placeholder="Write your Last name" autocomplete="off" value="${uservo.lastName}"><br>
		<input type="password" class="form-control" name="password" maxlength="10" required="required" placeholder="Write Password" autocomplete="off" ><br>
		<input type="file" name="thumnail"/><br>
		<button type="submit" class="btn btn-primary" value="submit">Submit</button>
	</form>
	<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</body>
</html>