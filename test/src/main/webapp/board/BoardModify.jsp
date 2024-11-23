<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@page import="mvc.vo.*"%>
   <%@page import = "java.util.*" %>
   <%@ page import="mvc.vo.BoardVo" %>
   <% BoardVo bv = new BoardVo();
   %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
<link href="/css/Style.css" rel="stylesheet">
<script>
    function check() {
        let fm = document.frm;
        
        if(fm.subject.value == "") {
            alert("제목을 입력해주세요");
            fm.subject.focus();
            return;
        } else if(fm.contents.value == "") {
            alert("내용을 입력해주세요");
            fm.contents.focus();
            return;
        } else if(fm.writer.value == "") {
            alert("작성자를 입력해주세요");
            fm.writer.focus();
            return;
        } else if(fm.password.value == "") {
            alert("비밀번호를 입력해주세요");
            fm.password.focus();
            return;
        }

        let ans = confirm("저장하시겠습니까?");
        if(ans == true) {
            fm.action = "<%= request.getContextPath() %>/board/BoardModifyAction.aws";
            fm.method = "post";
            fm.submit();
        }
    }
</script>
</head>
<body>
    <form name="frm">
        <h3>글 수정</h3>
        <input type="hidden" name="bidx" value="<%= bv.getBidx() %>"> <!-- 수정할 글의 ID -->
        <div>제목: <input type="text" name="subject" value="<%= bv.getSubject() %>"></div>
        <div>내용: <textarea name="contents"><%= bv.getContents() %></textarea></div>
        <div>작성자: <input type="text" name="writer" value="<%= bv.getWriter() %>"></div>
        <div>비밀번호: <input type="password" name="password"></div>
        <div>첨부파일: <input type="text" name="filename"></div>
        <div>
            <button type="button" name="save" onclick="check();">저장</button>
            <button type="button" name="cancle" onclick="history.back();">취소</button>
        </div>
        <table>
            <tr>
                <td id="NoTop">번호</td>
                <td id="WriterTop">작성자</td>
                <td id="TitleTop">내용</td>
                <td id="Viewcnt">조회</td>
                <td id="WritedayTop">날짜</td>
            </tr>
        </table>
    </form>
</body>
</html>
