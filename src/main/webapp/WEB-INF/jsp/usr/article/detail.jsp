<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detail</title>
</head>
<body>
	<h1>NO.${article.id } Article</h1>

	<hr />

	<div>작성일 : ${article.regDate.substring(0,10) }</div>
	<div>수정일 : ${article.updateDate.substring(0,10) }</div>
	<div>작성자 : ${article.memberId }</div>
	<div>제목 : ${article.title }</div>
	<hr />
	<div>${article.body }</div>
	
</body>
</html>