<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE MODIFY" />
<%@ include file="../common/head.jspf"%>

<script>
	let MemberModify__submitFormDone = false;

	function MemberModify__submit(form){
		if(MemberModify__submitFormDone){
			return;
		}
		
		form.loginPw.value = form.loginPW.value.trim();
		
// 		필수 입력이 아닌 입력란에 입력이 되었나?
		if(form.loginPw.value.length > 0){
			form.checkLoginPw.value = form.checkLoginPw.value.trim();
			
			if(form.loginPw.value != form.checkLoginPw.value){
				alert('새 비밀번호가 일치하지 않습니다');
				fomr.loginPw.val("");
				form.loginPw.focus();
				return;
			}
		}
		
		form.submit();
	}
	
</script>

<hr />
<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<form action="../member/doModify" method="POST" onsubmit="MemberModify__submit(this); return false;">
				<table border="1">
					<colgroup>
						<col width="200" />
					</colgroup>
					<tbody>

						<input type="hidden" name="id" value="${member.id }" />
						<tr>
							<th>아이디</th>
							<td>${member.loginId }</td>
						</tr>
						<tr>
							<th>새 비밀번호</th>
							<td>
								<input type="password" name="loginPw" placeholder="새 비밀번호를 입력하세요"/>
							</td>
						</tr>
						<tr>
							<th>새 비밀번호 확인</th>
							<td>
								<input type="password" name="checkLoginPw" placeholder="새 비밀번호를 입력하세요"/>
							</td>
						</tr>
						<tr>
							<th>이름</th>
							<td>
								<input required type="text" name="name"  value="${member.name }" />
							</td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td>
								<input required type="text" name="nickname"  value="${member.nickname }" />
							</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td>
								<input required type="text" name="cellphoneNum"  value="${member.cellphoneNum }" />
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td>
								<input required type="text" name="email"  value="${member.email }" />
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<button type="submit">수정</button>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
			<button class="btn-text-link" type="button" onclick="history.back()">뒤로가기</button>
		</div>
	</div>
</section>
<%@ include file="../common/foot.jspf"%>