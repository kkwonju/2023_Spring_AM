<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>Ajax Test</title>
<style>
.rs {
	border: 5px solid black;
	margin-top: 3px;
	padding: 20px;
	font-size: 2rem;
}
</style>
<!-- 제이쿼리 불러오기 -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script>
	function callByAjax(){
		var form = document.form1;
	
		var num1 = form.num1.value;
		var num2 = form.num2.value;
		var action = form.action;
	
		$.get(action,{ // 받는 누군가, 요청
			num1 : num1,
			num2 : num2
		}, function(data){ // function의 데이터로 보냄
	
			$('.rs').empty().append(data); // 대답
			// $('.rs').text(data);
	
		}, 'html');
	}
</script>
</head>
<body>
	<h1>Ajax Test</h1>
	<form name="form1" action="./doPlus" method="POST">
		<div>
			<input type="text" name="num1" placeholder="정수 입력" />
		</div>
		<div>
			<input type="text" name="num2" placeholder="정수 입력" />
		</div>
		<div>
			<input type="submit" value="더하기" />
			<input onclick="callByAjax();" type="button" value="더하기 v2" />
		</div>
	</form>

	<h2>더한 결과</h2>
	<div class="rs"></div>
</body>
</html>