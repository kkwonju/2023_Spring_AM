<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="Member My Page" />
<%@ include file="../common/head.jspf"%>
<%@ page import="com.KoreaIT.kkwo.demo.util.Ut"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<table border="1">
				<colgroup>
					<col width="200" />
					<col width="200" />
				</colgroup>
				<tbody>
					<tr>
						<th>회원 번호</th>
						<td>${member.id }</td>
					</tr>
					<tr>
						<th>가입 날짜</th>
						<td>${member.regDate}</td>
					</tr>
					<tr>
						<th>수정 날짜</th>
						<td>${member.updateDate }</td>
					</tr>
					<tr>
						<th>로그인 아이디</th>
						<td>${member.loginId }</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>${member.name }</td>
					</tr>
					<tr>
						<th>닉네임</th>
						<td>${member.nickname }</td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td>${member.cellphoneNum }</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td>${member.email }</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<a class="my_btn"
								href="../member/checkPw?replaceUri=${Ut.getEncodedUri('../member/modify') }">수정</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</section>
<%@ include file="../common/foot.jspf"%>