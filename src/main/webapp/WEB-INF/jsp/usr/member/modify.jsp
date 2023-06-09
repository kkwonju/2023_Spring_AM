<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER MODIFY" />
<%@ include file="../common/head.jspf"%>

<script type="text/javascript">
	let MemberModify__submitFormDone = false;

	function MemberModify__submit(form) {
		if (MemberModify__submitFormDone) {
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

		MemberModify__submitFormDone = true;
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
						<tr>
							<th>아이디</th>
							<td>${rq.getLoginedMember().loginId }</td>
						</tr>
						<tr>
							<th>새 비밀번호</th>
							<td>
								<input name="loginPw" type="text"
									placeholder="새 비밀번호를 입력하세요" />
							</td>
						</tr>
						<tr>
							<th>새 비밀번호 확인</th>
							<td>
								<input name="loginPwConfirm" type="text"
									placeholder="새 비밀번호를 입력하세요" />
							</td>
						</tr>
						<tr>
							<th>이름</th>
							<td>
								<input required type="text" name="name"
									value="${rq.getLoginedMember().name }" />
							</td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td>
								<input required type="text" name="nickname"
									value="${rq.getLoginedMember().nickname }" />
							</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td>
								<input required type="text" name="cellphoneNum"
									value="${rq.getLoginedMember().cellphoneNum }" />
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td>
								<input required type="text" name="email"
									value="${rq.getLoginedMember().email }" />
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<input type="submit" value="수정" />
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