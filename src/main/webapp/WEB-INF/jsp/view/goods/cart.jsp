<%--
 * First Editor : Jisung Jeon (cbajs20@gmail.com)
 * Last Editor  :
 * Date         : 2015-04-28
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Cart</title>

<script type="text/javascript">
$(document).ready(function(){
	function confirmation(question) {
	    var defer = $.Deferred();
	    $('<div></div>')
	        .html(question)
	        .dialog({
	            autoOpen: true,
	            modal: true,
	            title: 'Confirmation',
	            buttons: {
	                "Yes": function () {
	                    defer.resolve("true");
	                    $(this).dialog("close");
	                },
	                "No": function () {
	                    defer.resolve("false");
	                    $(this).dialog("close");
	                }
	            },
	            close: function () {
	                $(this).remove();
	            }
	        });
	    return defer.promise();
	}
	
	$(".del_cart").click(function(){
		var question = "Do you want to delete it?";
		var data = $(this).attr("vals");
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) === "true");
		    if(ansbool){
				var link = 'delCart.do?&no='+data+'&choice='+ansbool;
				$(location).attr('href', link);
		    }
		});		
	});
	
	$(".order").click(function(){
		var question = "Do you want to order it?";
		var data = [];
		var addr = "${user.address }";
		var post = "${user.postcode }";
		var name = "${user.lastName }";
		//alert(addr);
		data.push($(this).attr("vals"));
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) === "true");
		    if(ansbool){
				var link = '<%=request.getContextPath()%>/goods/addOrders.do?&no='+data+'&addr='+addr+'&post='+post+'&name='+name;
				$(location).attr('href', link);
		    }
		});		
	});
	
	$(".change_quan").click(function(){
		var question = "Do you want to change it?";
		var number = $(this).attr("vals");
		var quantity = $("#qty_"+number).val();
		var price = $("#price_"+number).attr("vals");
		if(quantity === 0){
			alert("No zero quantity");
			return;
		}
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) === "true");
		    if(ansbool){
		    	$.ajax({
					type:"POST",
					url:"<%=request.getContextPath()%>/goods/changeQuan.do",
					data:{quantity:quantity, number:number},
					success:function(result){
						if(result === "400"){
							alert("Error");
						}else{
							var total = 0;
							$("#qty_"+number).val(result);
							$("#sub_tot_"+number).text(price*result);
							$("table tbody").find("td.sub_tot").each(function(){
								var sub = $(this).text();
								total = total + parseInt(sub);
							});
							$("#total").text(total);
						}
					}
				});
		    }
		});		
	});
	
	$("#total_order").click(function(){
		var question = "Do you want to order it?";
		var data = [];
		var addr = "${user.address }";
		var post = "${user.postcode }";
		var name = "${user.lastName }";
		
		$('.order').each(function(){
			data.push($(this).attr("vals"));
		});
		//alert(data);
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) === "true");
		    if(ansbool){
				var link = '<%=request.getContextPath()%>/goods/addOrders.do?&no='+data+'&addr='+addr+'&post='+post+'&name='+name;
				$(location).attr('href', link);
		    }
		});		
	});
	
	/* $("#change_info").on("click",function(){
		var addr = $("#address").val();
		var post = $("#postcode").val();
		var name = $("#receiver").val();
		
		if(addr === "" || post === "" || name === "" ){
			$("#address").focus();
			alert("Fill out all");
			return false;
		}
		
		var trimAddr = addr.trim();
		var trimName = name.trim();
		
		$("#addr").text(trimAddr);
		$("#post").text(post);
		$("#name").text(trimName);
		
		$("#address").val('');
		$("#postcode").val('');
		$("#receiver").val('');
		$('.collapse').collapse('hide');
	}); */
	
	$(".number").keypress(function (e) {
	     //if the letter is not digit then display error and don't type anything
	     var number = $(this).attr("vals");
	     
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        //display error message
	        $("#errmsg"+number).html("Digits Only").show().fadeOut("slow");
	               return false;
	    }
	});

});
</script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<h1>Cart</h1>

<c:choose>
	<c:when test="${hasUser == false}">
            <tr>
                <td colspan="5">
                    No result
                </td>
            </tr>
    </c:when>
   	<c:otherwise>
   		<table id="box-table-a" class="table table-hover" id="table">
		<thead>
			<tr>
				<th scope="col"></th>
				<th scope="col">Product</th>
				<th scope="col">Price</th>
				<th scope="col">Quantity</th>
				<th scope="col">Sub Total</th>
				<th scope="col">Buy</th>
				<th scope="col">Cancel</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="s" value="0"></c:set>
			<c:forEach var="list" items="${cartlist}">
				<c:set var="s" value="${s + list.quantity * list.price}"></c:set>
			 <tr>
			 	<th scope="row"><img alt="" src="<%=request.getContextPath()%>/resource/upload/${list.imagePath}" height="42" ></th>
				<td><a href="<%=request.getContextPath()%>/board/read.do?s=product&boardNumber=${list.boardNumber}">${list.title}</a></td>
				<td id="price_${list.number}" class="prc" vals="${list.price }">${list.price }</td>
				<td><input type="text" value="${list.quantity }" id="qty_${list.number}" class="number" vals="${list.number}"/>
					<input type="button" class="change_quan" vals="${list.number}" value="Change" >&nbsp;<span id="errmsg${list.number}" class="error"></span></td>
				<td id="sub_tot_${list.number}" class="sub_tot">${list.quantity * list.price}</td>
				<td><a href="#" class="order" id="order" vals="${list.boardNumber}">Buy</a></td>
				<td><a href="#" class="del_cart" vals="${list.number}">Cancel</a></td>
			 </tr>
			</c:forEach>
		</tbody>
		<tr>
			<td colspan="3" align="right">Total Price : </td>
			<td id="total">${s }</td>
			<td><a href="#" id="total_order" vals="${list.boardNumber}">Buy All</a></td>
		</tr>
	 </table>
   	</c:otherwise>
</c:choose>
<br>
<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</div>
</body>
</html>