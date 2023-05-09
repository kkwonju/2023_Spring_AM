<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER SIGN UP" />
<%@ include file="../common/head.jspf"%>
<style>
#loginIdCheckMsg {
	color: red;
	font-size: 0.8rem;
}
</style>
<script type="text/javascript">
	let MemberJoin_submitFormDone = false;
	let validLoginId = ''; // 사용 가능한 로그인 아이디 지정

	function MemberJoin_submit(form) {
		if (MemberJoin_submitFormDone) {
			return;
		}
		form.loginPw.value = form.loginPw.value.trim();

		if (form.loginPw.value.length > 0) {
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

			if (form.loginPwConfirm.value.length == 0) {
				alert('비번 확인 써라');
				form.loginPwConfirm.focus();
				return;
			}

			if (form.loginPw.value != form.loginPwConfirm.value) {
				alert('비번 불일치');
				form.loginPw.value = "";
				form.loginPwConfirm.value = "";
				form.loginPw.focus();
				return;
			}
		}

		form.name.value = form.name.value.trim();
		form.nickname.value = form.nickname.value.trim();
		form.cellphoneNum.value = form.cellphoneNum.value.trim();
		form.email.value = form.email.value.trim();

		if (form.name.value.length == 0) {
			alert('이름 써라');
			form.name.focus();
		}

		if (form.nickname.value.length == 0) {
			alert('nickname 써라');
			form.nickname.focus();
		}

		if (form.cellphoneNum.value.length == 0) {
			alert('cellphoneNum 써라');
			form.cellphoneNum.focus();
		}

		if (form.email.value.length == 0) {
			alert('email 써라');
			form.email.focus();
		}

		MemberJoin_submitFormDone = true;
		form.submit();
	}

	$(document).ready(function() {
		$('.inputLoginId').on('blur', function checkLoginIdDup() { // checkLoginIdDup(el) el제거
			$('.loginIdCheckMsg').empty();
			
			const form = $(this).closest('form').get(0); // closest 현재 엘리먼트에서 가장 가까운 것 중 첫번째
	
			if (form.loginId.value.length == 0) {
				validLoginId = '';
				return;
			}
	
			$.get("../member/getLoginIdDup", {
				isAjax : 'Y',
				loginId : form.loginId.value
	
			}, function(data) {
	
				$('.loginIdCheckMsg').html(data.msg);
	
			}, 'json');
		});
	});
</script>
<hr />
<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<form action="../member/doJoin" method="POST"
				onsubmit="MemberJoin_submit(this); return false;">
				<table border="1">
					<colgroup>
						<col width="200" />
					</colgroup>
					<tbody>
						<tr>
							<th>로그인 아이디</th>
							<td>
<!-- 								<input onkeyup="checkLoginIdDup(this);" name="loginId"  --> 
								<input class="inputLoginId" name="loginId"
									type="text" placeholder="아이디를 입력하세요" autocomplete="off" />
								<br />
								<span class="loginIdCheckMsg"></span>
							</td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td>
								<input required name="loginPw" type="password"
									placeholder="비밀번호를 입력하세요" />
							</td>
						</tr>
						<tr>
							<th>비밀번호 확인</th>
							<td>
								<input required name="loginPwConfirm" type="password"
									placeholder="비밀번호를 입력하세요" />
							</td>
						</tr>
						<tr>
							<th>이름</th>
							<td>
								<input required type="text" name="name" placeholder="이름을 입력하세요" />
							</td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td>
								<input required type="text" name="nickname"
									placeholder="닉네임을 입력하세요" />
							</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td>
								<input required type="text" name="cellphoneNum"
									placeholder="전화번호를 입력하세요" />
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td>
								<input required type="email" name="email"
									placeholder="이메일을 입력하세요" />
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<button style="cursor: pointer" type="submit">가입</button>
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