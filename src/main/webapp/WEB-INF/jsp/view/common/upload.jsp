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
 업로드 한 파일 이름 : ${targetFileInfo} <br />
 업로드된 임시 파일 위치 : ${uploadFilePath} <br />
 업로드된 이미지파일은 썸네일이 표시됩니다. 썸네일 사이즈는  100x100 입니다. <br />
 <img alt="" src="${imageBase64}">
 <a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</body>
</html>