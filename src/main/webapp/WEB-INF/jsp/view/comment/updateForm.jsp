<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 2015. 4. 2. | 오후 11:37
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form name="${cs}_${commentNumber}_ajaxform_update" id="${cs}_${commentNumber}_ajaxform_update" action="<c:url value="/comment/update.do" />" method="post">
    <input type="hidden" name="commentNumber" value="${commentVO.number}"/>
    내용 : <br/>
    <textarea name="content" id="${cs}_${commentNumber}_content_update" cols="40" rows="5" >${commentVO.content}</textarea>
    <br/>
</form>

<button id="${cs}_${commentNumber}_ajaxform_cancle_update" onclick="showComment(1, '${cs}')" >취소</button>
<button id="${cs}_${commentNumber}_ajaxform_submit_update" onclick="ajaxForm_update('${cs}', '${commentNumber}')" >전송</button>

