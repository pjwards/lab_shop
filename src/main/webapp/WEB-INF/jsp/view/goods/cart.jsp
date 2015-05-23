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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>

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
		    var ansbool = (String(answer) == "true");
		    if(ansbool){
				var link = 'delCart.do?&no='+data+'&choice='+ansbool;
				$(location).attr('href', link);
		    }
		});		
	});
	
	$(".order").click(function(){
		var question = "Do you want to order it?";
		var data = [];
		var addr = $("#address").val();
		var post = $("#postcode").val();
		var name = $("#receiver").val();
		
		data.push($(this).attr("vals"));
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) == "true");
		    if(ansbool){
				var link = '<%=request.getContextPath()%>/goods/addOrders.do?&no='+data+'&addr='+addr+'&post'+post+'&name='+name;
				$(location).attr('href', link);
		    }
		});		
	});
	
	var data = [];
	
	$('#box-table-a').find('tr').each(function(){
		var tot = 0;
		var row = $(this);
		
		if(row.find('input[type="checkbox"]').is(':checked')){
			data.push(row.find('input[type="checkbox"]').is(':checked').value());
			tot += $("sub_tot").val();
		}
		$("#total").text(tot);
	});
	
	$("#total_order").click(function(){
		var question = "Do you want to order it?";
		var addr = $("#address").val();
		var post = $("#postcode").val();
		var name = $("#receiver").val();
		
		confirmation(question).then(function (answer) {
		    var ansbool = (String(answer) == "true");
		    if(ansbool){
				var link = '<%=request.getContextPath()%>/goods/addOrders.do?&no='+data+'&addr='+addr+'&post'+post+'&name='+name;
				$(location).attr('href', link);
		    }
		});		
	});
	
	$("#change_info").on("click",function(){
		var addr = $("#address").val();
		var post = $("#postcode").val();
		var name = $("#receiver").val();
		
		if(addr === "" || post === "" || name === "" ){
			$("#address").focus();
			alert("Input it");
			return false;
		}
		
		var trimcode = post.trim();
		if(trimcode.length !== 6){
			alert("Check your postcode again");
			$("#postcode").focus();
			return false;
		}
		
		var intcode = parseInt(trimcode);
		if (intcode>999999 || intcode<100000) {
			alert("Check your postcode again");
			$("#postcode").focus();
			return false;
		}
		
		$("#addr").text(addr);
		$("#post").text(intcode);
		$("#name").text(name);
	});
	
	$("#number").keypress(function (e) {
	     //if the letter is not digit then display error and don't type anything
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        //display error message
	        $("#errmsg").html("Digits Only").show().fadeOut("slow");
	               return false;
	    }
	});
});
</script>
</head>
<body>

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
   		<table id="box-table-a" class="table table-hover">
		<thead>
			<tr>
				<th scope="col">Product</th>
				<th scope="col">Price</th>
				<th scope="col">Quantity</th>
				<th scope="col">Sub Total</th>
				<th scope="col">Order Now</th>
				<th scope="col">Cancel</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="s" value="0"></c:set>
			<c:forEach var="list" items="${cartlist}">
				<c:set var="s" value="${s + list.quantity * list.price}"></c:set>
			 <tr>
			 	<td><input type="checkbox" class="check" value="${list.boardNumber}"></td>
				<td><a href="<%=request.getContextPath()%>/goods/read.do?goodsNumber=${list.number}">${list.title}</a></td>
				<td>${list.price }</td>
				<td>${list.quantity }</td>
				<td id="sub_tot">${list.quantity * list.goodsVO.price}</td>
				<td><a href="#" class="order" vals="${list.boardNumber}">Buy</a></td>
				<td><a href="#" class="del_cart" vals="${list.number}">Cancel</a></td>
			 </tr>
			</c:forEach>
		</tbody>
		<tr>
			<td colspan="5" align="right">Total Price</td>
			<td id="total"></td>
			<td><a href="#" id="total_order" vals="${list.boardNumber}">Buy</a></td>
		</tr>
	 </table>
	 <tr>
	 	<td colspan="5" align="right">Your Address</td>
	 	<td id="addr">${user.address }</td>
	 	<td>Your Postcode</td>
	 	<td id="post">${user.postcode }</td>
	 	<td>Receiver</td>
	 	<td id="name">${user.lastName }</td>
	 	<button type="button" class="btn btn-primary btn-xs" id="change" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">Change Info</button>
		<div class="collapse" id="collapseExample">
  			<div class="well">
    				<fieldset>
    					<legend>Chage Info</legend>
    						<input type="text" class="form-control" id="address" name="address" maxlength="250" required="required" autocomplete="off" >
    						<input type="text" class="form-control" id="number" name="postcode" maxlength="6" required="required" autocomplete="off" >&nbsp;<span id="errmsg"></span>
    						<input type="text" class="form-control" id="receiver" name="receiver" maxlength="50" required="required" autocomplete="off"><br>
    						<input type="button" class="btn btn-primary btn-sm" id="change_info" name="change_info" value="Submit" >
    				</fieldset>
  			</div>
		</div>
	 </tr>
   	</c:otherwise>
</c:choose>
<a href="<%=request.getContextPath()%>/main/main.do">Back Home</a>
</body>
</html>