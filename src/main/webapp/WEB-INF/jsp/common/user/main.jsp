<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Example</title>
</head>
<body>
	<table id="box-table-a" class="table table-hover">
		<thead>
			<tr>
				<th scope="col">Number</th>
				<th scope="col">Writer</th>
			</tr>
		</thead>
		<tbody>
	
		<tr>
			<th scope="row">${lists.id }</th>
			<td>${lists.name }</td>
		</tr>
	
		</tbody>
	</table>
</body>
</html>