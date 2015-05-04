<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 15. 4. 5. | 오후 6:07
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table style="border: 2px solid; margin: 30px 10px; width: 50%;">
    <tr>
        <td>
            <form name="${cs}_${parentCommentNumber}_ajaxform_reply" id="${cs}_${parentCommentNumber}_ajaxform_reply" action="<c:url value="/comment/reply.do" />" method="post">
                <input type="hidden" name="boardNumber" value="${boardNumber}">
                <input type="hidden" name="parentCommentNumber" value="${parentCommentNumber}"/>
                <input type="hidden" name="cs" value="${cs}"/>
                내용: <br/>
                <textarea name="content" id="${cs}_${parentCommentNumber}_content_reply" cols="40" rows="5"></textarea>
                <br/>
            </form>
            <button id="${cs}_${parentCommentNumber}_ajaxform_cancle_reply" onclick="toggleReply('${cs}', '${parentCommentNumber}', '${boardNumber}')" >취소</button>
            <button id="${cs}_${parentCommentNumber}_ajaxform_submit_reply" onclick="ajaxForm_reply('${cs}', '${parentCommentNumber}')" >전송</button>
        </td>
    </tr>
</table>

