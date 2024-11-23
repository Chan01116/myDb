<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@page import="mvc.vo.*"%>
 <%@page import="mvc.vo.BoardVo" %>
 <%@page import = "java.util.*" %> 
 <%@page import="java.util.ArrayList"%> 
 
 <%
    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
    ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");
    /* System.out.print(alist); */
%>
  
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글목록</title>
<link href ="/css/Style.css" rel="stylesheet">
<style>
body {
	background-color: #f4f4f4;
	margin: 0;
	padding: 0;
}

.container {
	width: 800px;
	margin: 0 auto;
	background-color: #fff;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

h3 {
	border-bottom: 2px solid #ff7a00;
	padding-bottom: 10px;
	margin-bottom: 20px;
}

table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	border: 1px solid #ddd;
	padding: 8px;
}

th {
	background-color: #f2f2f2;
	text-align: center;
}

.list {
	display: flex;
	justify-content: flex-end; /* 콘텐츠 항목 사이와 주위에 공간을 분배, 아이템들 끝점으로 분배 */
	margin-bottom: 20px;
}

select, input[type="text"], button {
	padding: 5px;
	margin-right: 5px;
	border: 1px solid #ddd;
	border-radius: 4px;
}

button {
	background-color: #333;
	color: #fff;
	cursor: pointer;
}

button:hover {
	background-color: #ff7a00;
}

a {
	text-decoration: none;
	color: #333;
}

a:hover {
	color: #ff7a00;
}
</style>


</head>
<body>

	 <h2>글목록</h2>
	 <div class = "list">
	 
	 	<form class ="search" name ="frm" action = "<%=request.getContextPath() %>/board/BaordList.aws" method = "get">
	 	<select name = "search">
	 	<option value = "title">제목</option>
	 	<option value = "writer">작성자</option>
	 	</select><input type = "text" name = "keyword">검색어를 입력하세요
	 	<button type = "submit">검색</button>
	 	</form>
	 </div>
	 <table>
	 	<thead>
	 	<tr>
	 		<td id = "NoTop">No</td>
			<td id = "TitleTop">제목</td>
			<td id = "WriterTop">작성자</td>
			<td id = "HitTop">조회</td>
			<td id = "ViewRecom">추천</td>
			<td id = "WritedayTop">날짜</td>
		 </tr>
		 </thead>
		<tbody>
		<tr> 
		
		<td class = "title">
		
		
		<%
			for(BoardVo bv : alist){%>
			<tr>	
				<td><%=bv.getBidx() %></td>
				<td><a href="<%= request.getContextPath() %>/board/BoardContent.aws?bidx=<%= bv.getBidx() %>">
        <%= bv.getSubject() %>
    </a>
</td>
				<td><%=bv.getWriter()%></td>
				<td><%=bv.getViewcnt()%></td>
				<td><%=bv.getRecom()%></td>
				<td><%=bv.getWirteday().substring(0, 10)%></td>
				
		
			</tr>	
		<%}%>
		
		
	 	
	 		
	 	</tbody>
	 </table>
<button type = "button" name = "btnwrite" id = "btnwrite"> <a href = "<%=request.getContextPath()%>/board/BoardWrite.aws">글쓰기</a></button>
</body>
</html>