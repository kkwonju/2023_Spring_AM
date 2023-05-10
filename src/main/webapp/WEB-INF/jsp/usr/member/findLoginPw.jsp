<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="MEMBER FINDLOGINID" />
<%@ include file="../common/head.jspf"%>

<hr />

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<form name="findLoginIdForm" action="../member/doFindLoginPw" method="POST">
				<table border="1">
					<colgroup>
						<col width="200" />
					</colgroup>
					<tbody>
						<tr>
							<th>아이디</th>
							<td>
								<input autocomplete="off" autofocus type="text" name="loginId" />
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td>
								<input autocomplete="off" autofocus type="email" name="email" />
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<button type="submit">찾기</button>
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