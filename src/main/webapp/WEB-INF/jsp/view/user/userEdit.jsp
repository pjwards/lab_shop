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

<title>Edit</title>

<script type="text/javascript">
$(function(){
	$("#input_pwd").on("click",function(){
		var pass1 = $("#password").val();
		var pass2 = $("#password_chk").val();
		
		if(pass1 == "" || pass2 == ""){
			$("#password").focus();
			alert("Input password");
			return false;
		}
		
		if(pass1 !== pass2){
			$("#password").focus();
			alert("Two password are different");
			return false;
		}
		
		var pass = $("#password").serialize();
		
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath()%>/user/changePwd.do",
			data:pass,
			success:function(result){
				if(result === "404"){
					alert("Failed! Try again");
					$("#password").focus();
				}else{
					$("#txt").text(result);
				}
			}
		});
	
	});
});
</script>
</head>
<body>
<h1>Edit Profile</h1>
	<form action="" method="post" id="edit" name="edit" enctype="multipart/form-data">
		<input type="text" name="firstName" maxlength="10" required="required"  placeholder="Write your First name" autocomplete="off" value="${uservo.firstName}">
		<br><br>
		<input type="text" name="lastName" maxlength="10" required="required"  placeholder="Write your Last name" autocomplete="off" value="${uservo.lastName}">	
		<br><br>
		<button type="button" class="btn btn-primary btn-xs" id="password_btn" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">Change Password</button><br>
		<div class="collapse" id="collapseExample">
  			<div class="well">
    				<fieldset>
    					<legend>Chage password</legend>
    						<input type="password" class="form-control" id="password" name="password" maxlength="10" required="required" placeholder="Write Password" autocomplete="off" >
    						<input type="password" class="form-control" id="password_chk" name="password2" maxlength="10" required="required" placeholder="Write it one more time" autocomplete="off" >
    						<input type="button" class="btn btn-primary btn-sm" id="input_pwd" name="input_pwd" value="Submit" >
    						<p id="txt"></p>
    				</fieldset>
  			</div>
		</div>
		<br>
		<input type="file" name="thumnail"/>
		<br>
		<button type="submit" class="btn btn-primary" value="submit">Submit</button>	 
	</form>
	<br>
	<a href="<%=request.getContextPath()%>/user/userDelete.do">Delete Account</a><br>
	<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</body>
</html>