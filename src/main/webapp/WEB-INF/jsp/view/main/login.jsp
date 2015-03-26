<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
			
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">

</script>	                        	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sign In</title>
</head>

<body>
<form action="<c:url value='/j_spring_security_check' />" id="signin" method="post">
    Email:<br>
 	<input type="text" name="email" id="email" required="required" placeholder="email" autocomplete="off"><br>    
    Password:<br>
    <input type="password" name="password" id="password" required="required" placeholder="password" autocomplete="off"><br>
    <button type="submit" class="btn btn-primary btn-lg active">Sign In</button><br>
</form>
</body>
</html>