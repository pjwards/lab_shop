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
<!-- CSS Files -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap-theme.css">
<!-- Javascript -->
<script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/bootstrap.min.js"></script>

<title>add</title>

<script type="text/javascript">

function validateEmail(email) { 
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
} 

$(function(){
	var check = 0;

	$("#check_email").on("click",function(){
		var email = $("#email").val();

		if(email === ""){
			$("#email").focus();
			alert("Input Email");
			return false;
		}
		
		if(!validateEmail(email)){
			$("#email").focus();
			$("#email").empty();
			alert("Please write down available form of email");
			return false;
		}
		
		var email2 = $("#email").serialize();
		
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath()%>/user/checkEmail.do",
			data:email2,
			success:function(result){
				if(result === "404"){
					alert("Failed! Try again");
					$("#email").focus();
				}else if(result === "400"){
					alert("This email is used");
					$("#email").focus();
				}else{
					check = 1;
					$("#txt").text(result);
				}
			}
		});
	});
	
	$("#submit_form").on("submit",function(){
		if(check !== 1){
			alert("You should check your email before signing up");
			$("#email").focus();
			return false;
		}
	});
});
</script>
</head>
<body>
	<form action="" method="post" enctype="multipart/form-data" id="submit_form" name="submit_form">
		<input type="text" class="form-control" name="firstName" maxlength="10" required="required" placeholder="Write your First name" autocomplete="off"><br>
		<input type="text" class="form-control" name="lastName" maxlength="10" required="required" placeholder="Write your Last name" autocomplete="off"><br>
		<input type="text" class="form-control" id ="email" name="email" maxlength="50" required="required" placeholder="Write Email " autocomplete="off">
		<input type="button" class="btn btn-primary btn-sm" id="check_email" name="check_email" value="Check" ><p id="txt"></p><br>
		<input type="password" class="form-control" name="password" maxlength="10" required="required" placeholder="Write Password" autocomplete="off"><br>
		<input type="file" name="thumnail"/><br>
		<button type="submit" class="btn btn-primary" value="submit">Submit</button>
	</form>
	<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</body>
</html>