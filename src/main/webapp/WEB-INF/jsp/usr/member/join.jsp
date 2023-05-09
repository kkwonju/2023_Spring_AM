<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER SIGN UP" />
<%@ include file="../common/head.jspf"%>
<style>
.loginIdCheckMsg {
	color: red;
	font-size: 0.8rem;
}
</style>
<script type="text/javascript">
	let submitJoinFormDone = false;
	let validLoginId = "";
	
	function submitJoinForm(form) {
		if (submitJoinFormDone) {
			alert('처리중입니다');
			return;
		}
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value == 0) {
			alert('아이디를 입력해주세요');
			return;
		}
		if (form.loginId.value != validLoginId) {
			alert('사용할 수 없는 아이디야');
			form.loginId.focus();
			return;
		}
		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value == 0) {
			alert('비밀번호를 입력해주세요');
			return;
		}
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value == 0) {
			alert('비밀번호 확인을 입력해주세요');
			return;
		}
		if (form.loginPwConfirm.value != form.loginPw.value) {
			alert('비밀번호가 일치하지 않습니다');
			form.loginPw.focus();
			return;
		}
		form.name.value = form.name.value.trim();
		if (form.name.value == 0) {
			alert('이름을 입력해주세요');
			return;
		}
		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value == 0) {
			alert('닉네임을 입력해주세요');
			return;
		}
		form.email.value = form.email.value.trim();
		if (form.email.value == 0) {
			alert('이메일을 입력해주세요');
			return;
		}
		form.cellphoneNum.value = form.cellphoneNum.value.trim();
		if (form.cellphoneNum.value == 0) {
			alert('전화번호를 입력해주세요');
			return;
		}
		
		submitJoinFormDone = true;
		form.submit();
	}
	
	$(document).ready(function() {
		$('.inputLoginId').on('blur', function checkLoginIdDup() { // checkLoginIdDup(el) el제거
// 			$('.loginIdCheckMsg').empty();

			const form = $(this).closest('form').get(0); // closest 현재 엘리먼트에서 가장 가까운 것 중 첫번째

// 			if (form.loginId.value.length == 0) {
// 				validLoginId = '';
// 				return;
// 			}

			$.get("../member/getLoginIdDup", {
				isAjax : 'Y',
				loginId : form.loginId.value

			}, function(data) {

				$('.loginIdCheckMsg').html(data.msg);
				if (data.success) {
					validLoginId = data.data1;
				} else {
					validLoginId = '';
				}
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
								<input class="inputLoginId" name="loginId" maxlength=20
									type="text" placeholder="아이디를 입력하세요" autocomplete="off" />
									<span style="font-size: 0.95rem; position: absolute; transform: translate(-100px, 8px); opacity: 0.6;">@clance.com</span>
								<br />
								<span class="loginIdCheckMsg"> </span>
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