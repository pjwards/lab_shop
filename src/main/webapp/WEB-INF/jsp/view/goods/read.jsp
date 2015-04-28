<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 4/16/15 | 1:12 PM
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<!-- CSS Files -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/bootstrap-theme.css">
<!-- Javascript -->
<script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/bootstrap.min.js"></script>

    <title>상품 읽기</title>

<script type="text/javascript">
$(document).ready(function(){
	$("a#addWish").click(function(){
		var check = prompt("Do you want to add this in wishlist? yes/no").trim().toLowerCase();
		
		if(check === ""){
			alert("Wrong Input");
			return false;
		}
		if(check !== "yes"){
			return false;
		}
		
		var data = $("a#addWish").attr("vals");
		var arr = data.split('/');
		
		$.ajax({
			type:"POST",
			url:"<%=request.getContextPath()%>/user/addWishlist.do",
			data:{ email : arr[0], check : check, no : arr[1] },
			success:function(result){
				if(result === "400"){
					alert("Already existed");
				}else if(result === "200"){
					alert("Added in wishlist");
				}
			}
		});
	});
	
	$("#submit_form").on("submit",function(){
		var quantity = $("#quantity").val().trim();
		
		if(quantity === ""){
			alert("Check your quantity again");
			$("#quantity").focus();
			return false;
		}
	
	});
});
</script>
</head>
<body>
<table>
    <tr>
        <td>상품 번호</td>
        <td>${goodsVO.number}</td>
    </tr>
    <tr>
        <td>상품 명</td>
        <td>${goodsVO.name}</td>
    </tr>
    <tr>
        <td>크기</td>
        <td>${goodsVO.size}</td>
    </tr>
    <tr>
        <td>소재</td>
        <td>${goodsVO.material}</td>
    </tr>
    <tr>
        <td>구성</td>
        <td>${goodsVO.component}</td>
    </tr>
    <tr>
        <td>옵션</td>
        <td>${goodsVO.options}</td>
    </tr>
    <tr>
        <td>제조/수입</td>
        <td>${goodsVO.manufacturer}</td>
    </tr>
    <tr>
        <td>제조국</td>
        <td>${goodsVO.madein}</td>
    </tr>

        <td>가격</td>
        <td>${goodsVO.price}</td>
    </tr>
    <tr>
        <td>재고</td>
        <td>${goodsVO.stock}</td>
    </tr>
    <tr>
        <td>등록자</td>
        <td>${goodsVO.userEmail}</td>
    </tr>
    <tr>
        <td>등록일</td>
        <td><fmt:formatDate value="${goodsVO.createdDate}" pattern="yyyy-MM-dd"/></td>
    </tr>

    <tr>
        <td>상품 설명</td>
        <td>
            <pre><c:out value="${goodsVO.description}"/></pre>
        </td>
    </tr>
    <tr>
        <a href="#" id="addWish" vals="${goodsVO.userEmail }/${goodsVO.number}">WishList</a>
    
    	<form action="<%=request.getContextPath()%>/goods/addCart.do" method="get" class="form-inline" id="submit_form" name="submit_form">
    		<div class="form-group">
    			<label class="sr-only" for="quantity">Quantity</label>
    			<div class="input-group">
      				<div class="input-group-addon">*</div>
      					<input type="text" class="form-control" id="quantity" name="quantity" placeholder="Quantity" autocomplete="off">
    			</div>
  			</div>
  			<input type="hidden" name="number" value="${goodsVO.number}"/>
      		<input type="hidden" name="p" value="${param.p}"/>
  			<button type="submit" class="btn btn-primary">Add to Cart</button>
    	</form>
    	
        <td colspan="2">
            <a href="list.do?p=${param.p}">목록보기</a>
            <a href="update.do?p=${param.p}&goodsNumber=${goodsVO.number}">수정하기</a>
            <form action="<c:url value="delete.do" />" method="post">
                <input type="hidden" name="p" value="${param.p}"/>
                <input type="hidden" name="goodsNumber" value="${goodsVO.number}"/>
                <input type="submit" value="삭제하기" >
            </form>
        </td>
    </tr>
</table>

</body>
</html>