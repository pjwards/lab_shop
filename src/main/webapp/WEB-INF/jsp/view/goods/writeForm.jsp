<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 4/16/15 | 12:07 PM
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>

<head>

    <script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>

    <title>상품 등록</title>
</head>
<body>

<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">

    <form action="<c:url value="/goods/write.do" />" method="post">
        상품 명 : <input type="text" name="name" size="100"/><br/>
        크기 : <input type="text" name="size" size="20"/><br/>
        소재 : <input type="text" name="material" size="50"/><br/>
        구성 : <input type="text" name="component" size="20"/><br/>
        옵션 : <input type="text" name="options"/><br/>
        제조/수입 : <input type="text" name="manufacturer" size="50"/><br/>
        제조국 : <input type="text" name="madein" size="50"/><br/>
        가격 : <input type="text" name="price" size="20"/><br/>
        재고 : <input type="text" name="stock" size="20"/><br/>

        상품 설명 : <br/>
        <textarea name="description" cols="40" rows="5"></textarea>
        <br/>

        <input type="button" onclick="location.href='list.do?s=${param.s}'" value="취소"/>
        <input type="submit" value="전송">
    </form>

</div>

</body>
</html>