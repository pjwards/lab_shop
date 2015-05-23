/**
 * Created by seodonghyeon on 4/30/15.
 */


// 덧글 리스트 전체 바꿈.
function showComment(page, separator) {
    loading_comment();

    // 0.5초 의무적으로 로딩
    setTimeout(function() {

        var id = "#"+separator

        j(id).load(
            contextPath + "/comment/list.do",
            {
                boardNumber:boardNumber,
                cp:page,
                cs:separator
            }
        )
    }, 500);
}

function isLogin() {
    var ret;
    j.ajax({
        type: "GET",
        url: contextPath + "/isLogin.do",
        success: function() {
            ret = true;
        },
        statusCode: {
            500: function() {
                alert("로그인을 하지 않았습니다.");
                ret = false;
            }
        },
        async: false
    });
    return ret
}

function isEqualMember(number) {
    var ret;
    j.ajax({
        type: "GET",
        url: contextPath + "/comment/isEqualMember.do?commentNumber="+number,
        success: function() {
            ret = true;
        },
        statusCode: {
            500: function() {
                alert("로그인한 사용자와 글쓴이가 같지 않습니다.");
                ret = false;
            }
        },
        async: false
    });
    return ret
}

function toggleWrite(separator) {

    if(!isLogin()){
        return;
    }

    j('#'+separator+'_write').toggle()
    j('#'+separator+'_write_btn').toggle()
}

function toggleReply(separator, number, boardNumber) {

    if(!isLogin()){
        return;
    }

    j('#'+separator+'_'+number+'_reply').toggle()
    j('#'+separator+'_'+number+'_reply').load(
        contextPath + "/comment/reply.do?parentCommentNumber="+number+"&boardNumber="+boardNumber+"&cs="+separator
    )
}

function updateComment(separator, number) {

    if(!isLogin() || !isEqualMember(number)){
        return;
    }

    j('#'+separator+'_'+number).load(
        contextPath + "/comment/update.do?commentNumber="+number+"&cs="+separator
    )
}

function deleteComment(separator, number, boardNumber) {

    if(!isLogin() || !isEqualMember(number)){
        return;
    }

    j.ajax({
        type: "POST",
        url: contextPath + "/comment/delete.do",
        data: {
            boardNumber:boardNumber,
            commentNumber:number
        },
        success: function (data) {
            if (data === "404" || data === "400")
                alert("삭제 오류가 발생하였습니다.");
            else {
                alert("삭제가 성공적으로 완료되었습니다.");

                // 덧글 페이지 처음으로 리로딩
                showComment(1, separator);
            }
        }
    });
}

function ajaxForm(separator) {

    if(!isLogin()){
        // 덧글 페이지 처음으로 리로딩
        showComment(1, separator);

        // 덧글 내용 초기화
        $("#" + separator + "_content").val("");
        return;
    }

    j("#" + separator + "_ajaxform").submit(function (e) {
        if (isWriting == false) {
            isWriting = true;

            if ($("#" + separator + "_content").val() == "") {
                alert("덧글 내용을 작성하시기 바랍니다.");
                isWriting = false;
                e.preventDefault(); //STOP default action
                return;
            }

            var postData = j(this).serializeArray();
            var formURL = j(this).attr("action");

            j.ajax({
                type: "POST",
                url: formURL,
                data: postData,
                success: function (data) {
                    alert("덧글 쓰기가 성공적으로 완료되었습니다.");

                    // 덧글 페이지 처음으로 리로딩
                    showComment(1, separator);

                    // 덧글 내용 초기화
                    j("#" + separator + "_content").val("");

                    // 초기화
                    isWriting = false;
                },
                statusCode: {
                    400: function() {
                        alert("덧글 쓰기 오류가 발생하였습니다.");
                    },
                    404: function() {
                        alert("덧글 쓰기 오류가 발생하였습니다.");
                    },
                    500: function() {
                        alert("덧글 쓰기 오류가 발생하였습니다.");
                    }
                }
            });

            e.preventDefault(); //STOP default action
        }
        ;
    });
    j("#" + separator + "_ajaxform").submit();
}

function ajaxForm_reply(separator, number) {

    if(!isLogin()){
        // 덧글 페이지 처음으로 리로딩
        showComment(1, separator);

        // 덧글 내용 초기화
        j("#" + separator + "_" + number + "_content_reply").val("");
        return;
    }

    j("#" + separator + "_" + number + "_ajaxform_reply").submit(function (e) {
        if (isReplying == false) {
            isReplying = true;

            if ($("#" + separator + "_" + number + "_content_reply").val() == "") {
                alert("답글 내용을 작성하시기 바랍니다.");
                isReplying = false;
                e.preventDefault(); //STOP default action
                return;
            }

            var postData = j(this).serializeArray();
            var formURL = j(this).attr("action");

            j.ajax({
                type: "POST",
                url: formURL,
                data: postData,
                success: function (data) {
                    alert("답글 쓰기가 성공적으로 완료되었습니다.");

                    // 덧글 페이지 처음으로 리로딩
                    showComment(1, separator);

                    // 덧글 내용 초기화
                    j("#" + separator + "_" + number + "_content_reply").val("");

                    // 초기화
                    isReplying = false;
                },
                statusCode: {
                    400: function() {
                        alert("답글 쓰기 오류가 발생하였습니다.");
                    },
                    404: function() {
                        alert("답글 쓰기 오류가 발생하였습니다.");
                    },
                    500: function() {
                        alert("답글 쓰기 오류가 발생하였습니다.");
                    }
                }
            });

            e.preventDefault(); //STOP default action
        }
        ;
    });
    j("#" + separator + "_" + number + "_ajaxform_reply").submit();
}

function ajaxForm_update(separator, number) {

    if(!isLogin() || !isEqualMember(number)){
        // 덧글 페이지 처음으로 리로딩
        showComment(1, separator);

        // 덧글 내용 초기화
        j("#" + separator + "_" + number + "_content_update").val("");
        return;
    }

    j("#" + separator + "_" + number + "_ajaxform_update").submit(function (e) {
        if (isUpdating == false) {
            isUpdating = true;

            if ($("#" + separator + "_" + number + "_content_update").val() == "") {
                alert("수정 내용을 작성하시기 바랍니다.");
                isUpdating = false;
                e.preventDefault(); //STOP default action
                return;
            }

            var postData = j(this).serializeArray();
            var formURL = j(this).attr("action");

            j.ajax({
                type: "POST",
                url: formURL,
                data: postData,
                success: function (data) {
                    alert("수정이 성공적으로 완료되었습니다.");

                    // 덧글 내용 초기화
                    j("#" + separator + "_" + number + "_content_update").val("");

                    // 덧글 페이지 처음으로 리로딩
                    showComment(1, separator);

                    // 초기화
                    isUpdating = false;
                },
                statusCode: {
                    400: function () {
                        alert("수정에 오류가 발생하였습니다.");
                    },
                    404: function () {
                        alert("수정에 오류가 발생하였습니다.");
                    },
                    500: function () {
                        alert("수정에 오류가 발생하였습니다.");
                    }
                }
            });

            e.preventDefault(); //STOP default action
        }
        ;
    });
    j("#" + separator + "_" + number + "_ajaxform_update").submit();
}