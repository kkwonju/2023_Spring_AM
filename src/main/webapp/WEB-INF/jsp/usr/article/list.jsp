<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.code }" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<div>${articlesCount }</div>
			<table>
				<colgroup>
					<col width="140" />
					<col width="200" />
					<col/>
					<col width="140" />
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>날짜</th>
						<th>제목</th>
						<th>작성자</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="article" items="${articles}">
						<tr>
							<td>${article.id }</td>
							<td>${article.regDate.substring(2, 16) }</td>
							<td>
								<a class="hover:underline" href="detail?id=${article.id }">${article.title }</a>
							</td>
							<td>${article.extra__writer }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="page container">
		<c:if test="${page > 1}">
			<a href="list?boardId=${board.id}&page=1"> << </a>
		</c:if>
		<c:forEach begin="${page - 5 < 1 ? 1 : page - 5}" end="${page + 5 > totalPage ? totalPage : page + 5}" var="i">
			&nbsp;
			<a class="${page == i ? 'red' : ''}" href="list?boardId=${board.id}&page=${i}">${i}</a>
			&nbsp;
		</c:forEach>
		<c:if test="${page < totalPage}">
			<a href="list?boardId=${board.id}&page=${totalPage}"> >> </a>
		</c:if>
	</div>
</section>
<%@ include file="../common/foot.jspf"%>