<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>upload ex</title>
</head>
<body>
<form method="post" action="" enctype="multipart/form-data">
	<input type="file" name="file1"/>
	</br>
	<input type="submit">Upload</input>
</form>

 <img alt="" src="${imageBase64}">
 <a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</body>
</html>