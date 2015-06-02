<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<header>

<nav class="navbar navbar-default">
	<div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a href="<%=request.getContextPath()%>/main/main.do" class="navbar-brand" style="font-size:34px;font-color:0xFFFFF;">Shop </a>
    </div>
	<div class="container-fluid pull-right">
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      	<ul class="nav navbar-nav">
      	<sec:authorize access="isAnonymous()">
      		<li><a href="<%=request.getContextPath()%>/user/userAdd.do">Sign Up</a></li>
			<li><a href="<%=request.getContextPath()%>/main/login.do">Sign In</a></li>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<c:url value="/j_spring_security_logout" var="logoutUrl" />
			<li><a href="${logoutUrl}">Log Out</a></li>
		</sec:authorize>
        	<li><a href="<%=request.getContextPath()%>/goods/cart.do">Cart</a></li>
        	<li><a href="<%=request.getContextPath()%>/user/userEdit.do">My page</a></li>
      	</ul>
      </div>
	</div>
</nav>
<style>
.navbar{
	margin-bottom: 0px;
}
</style>
</header>