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

<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Edit</title>

<script type="text/javascript">

$(function(){
	$("#input_pwd").on("click",function(){		
		
		var pass1 = $("#password").val();
		var pass2 = $("#password_chk").val();
		
		if(pass1 === "" || pass2 === ""){
			$("#password").focus();
			alert("Input password");
			return false;
		}
		
		if(pass1 !== pass2){
			$("#password").focus();
			$("#password").val('');
			$("#password_chk").val('');
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
					$("#password").val('');
					$("#password_chk").val('');
				}else{
					$("#txt").text(result);
					$("#password").val('');
					$("#password_chk").val('');
					$('.collapse').collapse('hide'); 
				}
			}
		});
	
	});
	
	$("#edit_form").on("submit",function(){
		var postcode = $("#postcode").val();
		var trimcode = postcode.trim();
		
		if(trimcode.length !== 6){
			alert("Check your postcode again");
			$("#postcode").focus();
			return false;
		}
		
	});
	
	$("#postcode").keypress(function (e) {
	     //if the letter is not digit then display error and don't type anything
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        //display error message
	        $("#errmsg,#errmsg2").html("Digits Only").show().fadeOut("slow");
	               return false;
	    }
	});
	
	$("#password_btn").on("click",function(){
		$("#txt").text("");
	});
});
</script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<h1>Edit Profile</h1>
	<form action="" method="post" id="edit_form" name="edit_form" enctype="multipart/form-data">
		<input type="text" name="firstName" maxlength="50" required="required"  autocomplete="off" value="${uservo.firstName}">
		<br><br>
		<input type="text" name="lastName" maxlength="50" required="required"  autocomplete="off" value="${uservo.lastName}">	
		<br><br>
		<button type="button" class="btn btn-primary btn-xs" id="password_btn" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">Change Password</button><p id="txt"></p>
		<div class="collapse" id="collapseExample">
  			<div class="well">
    				<fieldset>
    					<legend>Chage password</legend>
    						<input type="password" class="form-control" id="password" name="password" maxlength="20" required="required" placeholder="Password" autocomplete="off" >
    						<input type="password" class="form-control" id="password_chk" name="password2" maxlength="20" required="required" placeholder="Password again" autocomplete="off" >
    						<input type="button" class="btn btn-primary btn-sm" id="input_pwd" name="input_pwd" value="Submit" >
    				</fieldset>
  			</div>
		</div>
		<br>
		<input type="text" class="form-control" name="address" maxlength="250" required="required" autocomplete="off" value="${uservo.address}"><br>
		<input type="text" class="form-control" id="postcode" name="postcode" maxlength="6" required="required" autocomplete="off" value="${uservo.postcode}">&nbsp;<span id="errmsg"></span><br>
		<input type="file" name="thumnail"/>
		<br>
		<button type="submit" class="btn btn-primary" value="submit">Submit</button>	 
	</form>
	<br>
	<a href="<%=request.getContextPath()%>/user/userDelete.do">Delete Account</a><br>
	<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</div>
</body>
</html>