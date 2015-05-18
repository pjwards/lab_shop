<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/jqueryui/jquery-ui.css">
<script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/jqueryui/jquery-ui.js"></script>

<title>Test Email</title>

<script type="text/javascript">
$(document).ready(function() {
	$(function() {
		$("#dialog").dialog({
			autoOpen: false
		});
		$("#button").on("click", function() {
			$("#dialog").dialog("open");
		});
	});
	
	// Validating Form Fields
	$("#submit").click(function(e) {
		var reciver = $("#reciver").val();
		var title = $("#title").val();
		var content = $("#content").val();
		var emailReg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		
		if (reciver === "" || title === "" || content === "") {
			alert("Please fill all fields!");
			$("#reciver").val('');
			$("#title").val('');
			$("#content").val('');
			e.preventDefault();
		} else if (!(reciver).match(emailReg)) {
			alert("Invalid Email!");
			$("#reciver").val('');
			$("#title").val('');
			$("#content").val('');
			e.preventDefault();
		} else {
			alert("Send it Successfully");
		}
	});
});
</script>
<style type="text/css">
textarea {
    resize: none;
}
</style>
</head>
<body>
<div id="dialog" title="Email">
	<form action="#" method="post">
	<label>Reciver:</label>
	<input type="text" class="form-control" id="reciver" name="reciver" maxlength="250" required="required" placeholder="Email" autocomplete="off">
	<br><label>Title:</label><br>
	<input type="text" class="form-control" id="title" name="title" maxlength="50" required="required" placeholder="Title" autocomplete="off">
	<label>Content:</label>
	<textarea name="content" id="content" rows="10" cols="25"></textarea><br>
	<button type="submit" class="btn btn-primary" id="submit" value="submit">Submit</button>
	</form>
</div>
<h2>Send Email</h2>
<input id="button" type="button" value="Send Email">
</body>
</html>