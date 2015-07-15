<%--
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-04-20
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>delete</title>
</head>

<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<div class="text-center">
<h2>Why do you want to disable your account?</h2>

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
    		I don't like this site
  	 </label>
	</div>
	<button type="submit" class="btn btn-primary" value="submit">Submit</button>
</form>
</div>
</div>
</body>
</html>