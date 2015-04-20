<%--
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-04-20
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- CSS Files -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap-theme.css">
<!-- Javascript -->
<script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/bootstrap.min.js"></script>

<title>delete</title>
</head>
<body>
<h1>Why do you want to disable your account?</h1>

<form action="" method="post">
	<div class="radio">
 	 <label>
   		 <input type="radio" name="check" id="optionsRadios1" value="noUse" checked>
    		I don't use this account
  	 </label>
	</div>
	<div class="radio">
	 <label>
    	<input type="radio" name="check" id="optionsRadios2" value="shit">
    		This site is shit
  	 </label>
	</div>
	<button type="submit" class="btn btn-primary" value="submit">Submit</button>
</form>
</body>
</html>