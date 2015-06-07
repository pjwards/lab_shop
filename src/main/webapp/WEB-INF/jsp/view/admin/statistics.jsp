<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 6/7/15 | 1:58 PM
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<%@ include file="/WEB-INF/jsp/includes/src.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/header.jsp"%>
<head>
    <title>Statistics</title>

    <script src="<%=request.getContextPath()%>/resource/js/jquery-2.1.3.min.js"></script>

    <script type="text/javascript">

        contextPath = "${pageContext.request.contextPath}";

        $(document).ready(function(){

        });

        function showList(separator, page, search) {

            // 0.5초 의무적으로 로딩
            setTimeout(function() {

                var id = "#"+separator

                $(id).load(
                        contextPath + "/admin/"+separator+".do",
                        {
                            p:page,
                            q:search
                        }
                )
            }, 500);
        }

        showList("orderList", 1);
        showList("goodsList", 1);
    </script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/includes/nav.jsp"%>
<div class="main">
<h1>Statistics</h1>

    <div id="orderList"></div>
    <hr>
    <br>
    <div id="goodsList"></div>

</div>

</body>
</html>
