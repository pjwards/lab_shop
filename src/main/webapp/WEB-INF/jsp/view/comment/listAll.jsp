<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 2015-03-27 | 오후 3:48
 * Description  : 
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      : 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${hasComment == false}">
        <div style="border: 1px solid; width: 50%;">
            <table>
                <tr>
                    <td>
                        댓글이 없습니다.
                    </td>
                </tr>
            </table>
        </div>
    </c:when>

    <c:otherwise>

        <c:forEach var="comment" items="${commentVOList}" varStatus="status">
            <div style="border: 1px solid; margin-left: ${comment.level * 30}px; width: 50%;">
                <table>

                    <tr>
                        <td>작성자</td>
                        <td>${comment.userEmail}</td>
                    </tr>

                    <tr>
                        <td>작성일</td>
                        <td>${comment.postingDate}</td>
                    </tr>

                    <tr>
                        <td>내용</td>
                        <td>
            <pre>
              <pre><c:out value="${comment.content}"/></pre>

              </pre>
                        </td>

                    </tr>
                    <tr>
                        <td colspan="2">
                            <a href="<c:url value="/comment/reply.do?boardNumber=${param.boardNumber}&parentId=${comment.id}&p=${param.p}"/>">답변하기</a>
                            <a href="<c:url value="/comment/update.do?boardNumber=${param.boardNumber}&commentId=${comment.id}&p=${param.p}"/> ">수정하기</a>
                            <a href="<c:url value="/comment/delete.do?boardNumber=${param.boardNumber}&commentId=${comment.id}"/> ">삭제하기</a>
                        </td>
                    </tr>

                </table>
            </div>

            <br/>

        </c:forEach>

    </c:otherwise>
</c:choose>

<div style="border: 2px solid; margin: 30px 10px; width: 50%;">
    <table>
        <tr>
            <td>
                <form action="<c:url value="/comment/write.do" />" method="post">
                    <input type="hidden" name="boardNumber" value="${param.boardNumber}">
                    <input type="hidden" name="s" value="${param.s}">
                    작성자 : <input type="text" name="userEmail"/><br/>
                    내용: <br/>
                    <textarea name="content" cols="40" rows="5"></textarea>
                    <br/>
                    <input type="submit" value="전송">
                </form>
            </td>
        </tr>
    </table>
</div>