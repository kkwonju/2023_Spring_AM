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
					<col width="100" />
					<col width="200" />
					<col width="500" />
					<col width="100" />
					<col width="100" />
					<col width="100" />
					<col width="100" />
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>날짜</th>
						<th>제목</th>
						<th>작성자</th>
						<th>조회수</th>
						<th>좋아요</th>
						<th>싫어요</th>
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
							<td>${article.hitCount }</td>
							<td>${article.goodReactionPoint }</td>
							<td>${article.badReactionPoint }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="page container my-3">

		<c:set var="paginationLen" value="4" />
		<c:set var="startPage"
			value="${page - paginationLen >= 1 ? page - paginationLen : 1}" />
		<c:set var="endPage"
			value="${page + paginationLen <= totalPage ? page + paginationLen : totalPage}" />

		<c:set var="baseUri" value="?boardId=${board.id}" />
		<c:set var="baseUri"
			value="${baseUri}&searchKeywordTypeCode=${searchKeywordTypeCode}" />
		<c:set var="baseUri" value="${baseUri}&searchKeyword=${searchKeyword}" />

		<c:if test="${page > 1}">
			<a href="${baseUri}&page=1"> << </a>
		</c:if>
		<c:forEach begin="${startPage}" end="${endPage}" var="i">
			&nbsp;
			<a class="${page == i ? 'red' : ''}" href="${baseUri}&page=${i}">${i}</a>
			&nbsp;
		</c:forEach>
		<c:if test="${page < totalPage}">
			<a href="${baseUri}&page=${totalPage}"> >> </a>

		</c:if>
	</div>
</section>
<%@ include file="../common/foot.jspf"%>