<%--
 * First Editor : Donghyun Seo (egaoneko@naver.com)
 * Last Editor  :
 * Date         : 2015-03-22 | 오후 2:29
 * Description  :
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version      :
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/daumeditor/css/editor.css" type="text/css" charset="utf-8"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/bpopup.css" type="text/css" charset="utf-8"/>
    <script src="${pageContext.request.contextPath}/resource/daumeditor/js/editor_loader.js" type="text/javascript" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/resource/js/jquery-2.1.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/js/jquery.bpopup.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/js/board/editor.js"></script>
    <script src="${pageContext.request.contextPath}/resource/js/board/popup.js"></script>
    <title>글쓰기</title>
    
<script type="text/javascript">
$(function(){
    $.ajax({
        url : "<%=request.getContextPath()%>/resource/daumeditor/editor_frame.jsp",
        success : function(data){
            $("#editor_frame").html(data);
            // 에디터UI load
            var config = {
                /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) http://xxx.xxx.com */
                txHost: '', 
                /* 런타임 시 리소스들을 로딩할 때 필요한 부분으로, 경로가 변경되면 이 부분 수정이 필요. ex) /xxx/xxx/ */
                txPath: '', 
                /* 수정필요없음. */
                txService: 'sample', 
                /* 수정필요없음. 프로젝트가 여러개일 경우만 수정한다. */
                txProject: 'sample',
                /* 대부분의 경우에 빈문자열 */
                initializedId: "", 
                /* 에디터를 둘러싸고 있는 레이어 이름(에디터 컨테이너) */
                wrapper: "tx_trex_container",
                /* 등록하기 위한 Form 이름 */
                form: "frm", 
                /*에디터에 사용되는 이미지 디렉터리, 필요에 따라 수정한다. */
                txIconPath: "<%=request.getContextPath()%>/resource/daumeditor/images/icon/editor/", 
                /*본문에 사용되는 이미지 디렉터리, 서비스에서 사용할 때는 완성된 컨텐츠로 배포되기 위해 절대경로로 수정한다. */
                txDecoPath: "<%=request.getContextPath()%>/resource/daumeditor/images/deco/contents/", 
                canvas: {
                    styles: {
                        /* 기본 글자색 */
                        color: "#123456", 
                        /* 기본 글자체 */
                        fontFamily: "굴림", 
                        /* 기본 글자크기 */
                        fontSize: "10pt", 
                        /*기본 배경색 */
                        backgroundColor: "#fff", 
                        /*기본 줄간격 */
                        lineHeight: "1.5", 
                        /* 위지윅 영역의 여백 */
                        padding: "8px"
                    },
                    showGuideArea: false
                },
                events: {
                    preventUnload: false
                },
                sidebar: {
                    attachbox: {
                        show: true,
                        confirmForDeleteAll: true
                    }
                },
                size: {
                    /* 지정된 본문영역의 넓이가 있을 경우에 설정 */
                    contentWidth: 700 
                }
            };
             
            //에디터내에 환경설정 적용하기
            new Editor(config);
        }
    });
     
    //form submit 버튼 클릭
    $("#save_button").click(function(){
        //다음에디터가 포함된 form submit
        Editor.save();
    })
})
 
 
//Editor.save() 호출 한 다음에 validation 검증을 위한 함수 
//validation 체크해줄 입력폼들을 이 함수에 추가 지정해줍니다.
function validForm(editor) {
    var validator = new Trex.Validator();
    var content = editor.getContent();
    if (!validator.exists(content)) {
        alert('내용을 입력하세요');
        return false;
    }
    return true;
}
  
//validForm 함수까지 true값을 받으면 이어서 form submit을 시켜주는  setForm함수
function setForm(editor) {
    var content = editor.getContent();
    $("#daumeditor").val(content)
    return true;
}
</script>
<style type="text/css">
textarea {
    resize: none;
}
</style>

    <script type="text/javascript">
        contextPath = "${pageContext.request.contextPath}";
    </script>
</head>
<body>

<!-- start editor -->
<div>
<c:if test="${act == 'write'}">
    <form id="frm" name="frm" action="<c:url value="/board/write.do" />" method="post" accept-charset="utf-8">
</c:if>
<c:if test="${act == 'reply'}">
    <form id="frm" name="frm" action="<c:url value="/board/reply.do" />" method="post" accept-charset="utf-8">
    <input type="hidden" name="parentBoardNumber" value="${param.parentBoardNumber}" />
</c:if>

    <input type="hidden" name="s" value="${param.s}"/>
    
    제목 : <input type="text" name="title" size="100"/><br/>

    <c:if test="${param.s == 'product'}">
        <table id="selected_goods" border="1">
            <tbody>
                <tr>
                    <td>상품 번호</td>
                    <td>상품 명</td>
                    <td>크기</td>
                    <td>소재</td>
                    <td>구성</td>
                    <td>옵션</td>
                    <td>제조/수입</td>
                    <td>제조국</td>
                    <td>상품 설명</td>
                    <td>가격</td>
                    <td>재고</td>
                    <td>등록자</td>
                    <td>등록일</td>
                    <td>삭제</td>
                </tr>

                <tr>
                    <td id="empty_goods" colspan="14">
                        상품이 없습니다.
                    </td>
                </tr>
            </tbody>
        </table>

        <button id="popup">상품 추가</button>

        <div id='element_to_pop_up' style='display:none;'>
            <span class='button b-close'><span>X</span></span> <!-- 닫기 버튼 (스타일은 알아서 지정) -->
            <div class='content'></div> <!-- 컨텐츠가 들어갈 영역 지정 (이미지, html 등.. 여러가지를 동적으로 부를수 있다. -->
        </div>
    </c:if>

    <!-- call editor frame -->
    <div id="editor_frame"></div>
    <textarea name="daumeditor" id="daumeditor" rows="10" cols="100" style="width:766px; height:412px;display: none;"></textarea>
    <input type="button" onclick="location.href='list.do?s=${param.s}'" value="취소"/>
    <input type="button" id="save_button" value="전송"/>
</form>
</div>
<!-- end editor -->
</body>
</html>
