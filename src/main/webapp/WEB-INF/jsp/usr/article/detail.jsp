<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="Article Detail" />
<%@ include file="../common/head.jspf"%>

<script>
	const params = {}
	params.id = parseInt('${param.id}');
</script>

<script>
	function ArticleDetail__increaseHitCount() {
		const localStorageKey = 'article__' + params.id + '__alreadyView';

		if (localStorage.getItem(localStorageKey)) {
			return;
		}

		localStorage.setItem(localStorageKey, true);

		$.get('../article/increaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__hit-count').empty().html(data.data1);
		}, 'json');
	}

	$(function() {
		ArticleDetail__increaseHitCount();
	})
</script>

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
						<th>게시판</th>
						<td>${article.id }</td>
					</tr>
					<tr>
						<th>번호</th>
						<td>${article.boardId}</td>
					</tr>
					<tr>
						<th>작성 날짜</th>
						<td>${article.regDate }</td>
					</tr>
					<tr>
						<th>수정 날짜</th>
						<td>${article.updateDate }</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>${article.extra__writer }</td>
					</tr>
					<tr>
						<th>조회 수</th>
						<td>
							<span class="article-detail__hit-count">${article.hitCount }</span>
						</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>${article.title }</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>${article.body }</td>
					</tr>
					<tr>
						<th>추천</th>
						<td>
							<span>좋아요 : ${article.goodReactionPoint }</span>
							<span>싫어요 : ${article.badReactionPoint }</span>
							<br />
							<c:if test="true">
								<c:choose>
									<c:when test="${actorCanCancelGoodReaction }">
										<div>
											<span>
												<span>&nbsp;</span>
												<a
													href="/usr/reactionPoint/doCancelGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
													class="btn btn-xs">좋아요 👍</a>
											</span>
											<span>
												<span>&nbsp;</span>
												<a onclick="alert(this.title); return false;"
													title="좋아요를 먼저 취소해" class="btn btn-xs">싫어요 👎</a>
											</span>
										</div>
									</c:when>
									<c:when test="${actorCanCancelBadReaction }">
										<div>
											<span>
												<span>&nbsp;</span>
												<a onclick="alert(this.title); return false;"
													title="싫어요를 먼저 취소해" class="btn btn-xs">좋아요 👍</a>
											</span>
											<span>
												<span>&nbsp;</span>
												<a
													href="/usr/reactionPoint/doCancelBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
													class="btn btn-xs">싫어요 👎</a>
											</span>
										</div>
									</c:when>
									<c:otherwise>
										<div>
											<span>
												<span>&nbsp;</span>
												<a
													href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
													class="btn btn-xs">좋아요 👍</a>
											</span>
											<span>
												<span>&nbsp;</span>
												<a
													href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
													class="btn btn-xs">싫어요 👎</a>
											</span>
										</div>

									</c:otherwise>
								</c:choose>
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div>
			<button class="btn-text-link" type="button"
				onclick="location.replace('../article/list')">뒤로가기</button>
			<c:if test="${article.actorCanModify}">
				<a class="btn-text-link" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.actorCanDelete}">
				<a class="btn-text-link" href="../article/delete?id=${article.id }"
					onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;">삭제</a>
			</c:if>
		</div>
	</div>
</section>
<%@ include file="../common/foot.jspf"%>