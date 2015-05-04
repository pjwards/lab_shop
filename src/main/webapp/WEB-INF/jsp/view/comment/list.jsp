<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  : 
 * Date         : 5/1/15 | 4:02 PM
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
                        작된 글이 없습니다.
                    </td>
                </tr>
            </table>
        </div>
    </c:when>

    <c:otherwise>

        <c:forEach var="comment" items="${commentVOList}" varStatus="status">
            <div id="${cs}_${comment.number}" style="border: 1px solid; margin-left: ${comment.level * 30}px; width: 50%;">
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
                        <td><c:out value="${comment.content}"/></td>
                    </tr>

                    <tr>
                        <td colspan="2">
                            <c:if test="${isLogin}">
                                <c:if test="${comment.level < 1}">
                                    <button id="${cs}_${comment.number}_reply_btn" onclick="toggleReply('${cs}', '${comment.number}', '${param.boardNumber}')" >답글</button>
                                </c:if>
                                <c:if test="${memberId == comment.userEmail}">
                                    <button id="${cs}_${comment.number}_update_btn" onclick="updateComment('${cs}', '${comment.number}')" >수정</button>
                                    <button id="${cs}_${comment.number}_delete_btn" onclick="deleteComment('${cs}', '${comment.number}', '${param.boardNumber}')" >삭제</button>
                                </c:if>
                            </c:if>
                        </td>
                    </tr>
                </table>
                <div id="${cs}_${comment.number}_reply" style="display: none;"></div>
            </div>
            <br/>
        </c:forEach>
    </c:otherwise>
</c:choose>

<%-- Paging --%>
<div id="comment_paging">
        <c:if test="${pagingVO.beginPage > 10}">
            <button onclick="showComment(${pagingVO.beginPage-1}, '${cs}')">이전</button>
        </c:if>
        <c:forEach var="pno" begin="${pagingVO.beginPage}" end="${pagingVO.endPage}">
            <button onclick="showComment(${pno}, '${cs}')">${pno}</button>
        </c:forEach>
        <c:if test="${pagingVO.endPage < pagingVO.totalPageCount}">
            <button onclick="showComment(${pagingVO.endPage+1}, '${cs}')">다음</button>
        </c:if>
</div>

<c:if test="${isLogin}">
    <div id="${cs}_write" style="display: none;">
        <table style="border: 2px solid; margin: 30px 10px; width: 50%;">
            <tr>
                <td>
                    <form name="${cs}_ajaxform" id="${cs}_ajaxform" action="<c:url value="/comment/write.do" />" method="post">
                        <input type="hidden" name="boardNumber" value="${param.boardNumber}">
                        <input type="hidden" name="cs" value="${cs}"/>
                        내용: <br/>
                        <textarea name="content" id="${cs}_content" cols="40" rows="5"></textarea>
                        <br/>
                    </form>
                    <button id="${cs}_ajaxform_cancle" onclick="toggleWrite('${cs}')" >취소</button>
                    <button id="${cs}_ajaxform_submit" onclick="ajaxForm('${cs}')" >전송</button>
                </td>
            </tr>
        </table>
    </div>

    <div id="${cs}_write_btn">
        <button onclick="toggleWrite('${cs}')">글쓰기</button>
</div>
</c:if>