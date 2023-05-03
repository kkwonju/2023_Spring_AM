<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER CheckPw" />
<%@ include file="../common/head.jspf"%>
<hr />

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<form action="../member/doCheckPw" method="POST">
				<table border="1">
					<colgroup>
						<col width="200" />
					</colgroup>
					<tbody>
						<input type="hidden" name="loginId" value=${rq.loginedMember.loginId }/>
						<tr>
							<th>비밀번호</th>
							<td><input type="password" name="loginPw" /></td>
						</tr>
						<tr>
							<th></th>
							<td>
								<button type="submit">확인</button>
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