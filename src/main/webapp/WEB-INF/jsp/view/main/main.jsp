<%--
  Created by IntelliJ IDEA.
  User: Donghyun Seo (egaoneko@naver.com)
  Last Editor: Jisung jeon
  Date: 2015-03-22
  Time: 오후 2:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
    <title>Shop</title>
</head>

<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">

<div class="jumbotron text-center">
  <h1>Hello, Everyone!</h1>
  <p>This is small shopping mall made by donghyun and jisung</p>
  <p><a class="btn btn-primary btn-lg" href="<%=request.getContextPath()%>/board/list.do?s=product" role="button">Look around</a></p>
</div>
<div class="container">
    <div class="row">
		<div class="col-md-12">
                <div id="Carousel" class="carousel slide">
                 
                <ol class="carousel-indicators">
                    <li data-target="#Carousel" data-slide-to="0" class="active"></li>
                    <li data-target="#Carousel" data-slide-to="1"></li>
                    <li data-target="#Carousel" data-slide-to="2"></li>
                </ol>
                 
                <!-- Carousel items -->
                <div class="carousel-inner">
                    
                <div class="item active">
                	<div class="row">
                	  <div class="col-md-3"><a href="#" class="thumbnail"><img src="/lab_shop/resource/upload/image2.jpg" alt="Image" style="width:250;max-width:100%;"></a></div>
                	  <div class="col-md-3"><a href="#" class="thumbnail"><img src="/lab_shop/resource/upload/image2.jpg" alt="Image" style="width:250;max-width:100%;"></a></div>
                	  <div class="col-md-3"><a href="#" class="thumbnail"><img src="/lab_shop/resource/upload/image2.jpg" alt="Image" style="width:250;max-width:100%;"></a></div>
                	  <div class="col-md-3"><a href="#" class="thumbnail"><img src="/lab_shop/resource/upload/image2.jpg" alt="Image" style="width:250;max-width:100%;"></a></div>
                	</div><!--.row-->
                </div><!--.item-->
                 
                <div class="item">
                	<div class="row">
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="/lab_shop/resource/upload/default.jpg" alt="Image" style="width:250;max-width:100%;"></a></div>
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="/lab_shop/resource/upload/default.jpg" alt="Image" style="width:250;max-width:100%;"></a></div>
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="/lab_shop/resource/upload/default.jpg" alt="Image" style="width:250;max-width:100%;"></a></div>
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="/lab_shop/resource/upload/default.jpg" alt="Image" style="width:250;max-width:100%;"></a></div>
                	</div><!--.row-->
                </div><!--.item-->
                 
                <div class="item">
                	<div class="row">
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="/lab_shop/resource/upload/download.jpg" alt="Image" style="width:250;max-width:100%;"></a></div>
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="/lab_shop/resource/upload/download.jpg" alt="Image" style="width:250;max-width:100%;"></a></div>
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="/lab_shop/resource/upload/download.jpg" alt="Image" style="width:250;max-width:100%;"></a></div>
                		<div class="col-md-3"><a href="#" class="thumbnail"><img src="/lab_shop/resource/upload/download.jpg" alt="Image" style="width:250;max-width:100%;"></a></div>
                	</div><!--.row-->
                </div><!--.item-->
                 
                </div><!--.carousel-inner-->
                  <a data-slide="prev" href="#Carousel" class="left carousel-control">‹</a>
                  <a data-slide="next" href="#Carousel" class="right carousel-control">›</a>
                </div><!--.Carousel-->
                 
		</div>
	</div>
</div><!--.container-->
</div>
</body>
<script>
$(document).ready(function() {
    $('#Carousel').carousel({
        interval: 5000
    })
});
</script>
<style>
body{padding-top:20px;}
.carousel {
    margin-bottom: 0;
    padding: 0 40px 30px 40px;
}
/* The controlsy */
.carousel-control {
	left: -12px;
    height: 40px;
	width: 40px;
    background: none repeat scroll 0 0 #222222;
    border: 4px solid #FFFFFF;
    border-radius: 23px 23px 23px 23px;
    margin-top: 90px;
}
.carousel-control.right {
	right: -12px;
}
/* The indicators */
.carousel-indicators {
	right: 50%;
	top: auto;
	bottom: -10px;
	margin-right: -19px;
}
/* The colour of the indicators */
.carousel-indicators li {
	background: #cecece;
}
.carousel-indicators .active {
background: #428bca;
}
</style>

</html>