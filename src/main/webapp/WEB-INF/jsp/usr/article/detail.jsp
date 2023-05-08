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

<script>
	$('.showModifyForm').click(() => {
		
	}))

	function Reply_showModifyForm(){
		
	}
</script>

<script type="text/javascript">
	let ReplyWrite__submitFormDone = false;

	function ReplyWrite__submitForm(form) {
		if (ReplyWrite__submitFormDone) {
			return;
		}
		form.body.value = form.body.value.trim();

		if (form.body.value.length < 3) {
			alert('2글자 이상 입력');
			form.body.focus();
			return;
		}

		ReplyWrite__submitFormDone = true;
		form.submit();
	}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="flex jc-f-e">
			<button class="my_btn" type="button"
				onclick="location.replace('../article/list')">뒤로가기</button>
			<c:if test="${article.actorCanModify}">
				<a class="my_btn" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.actorCanDelete}">
				<a class="my_btn" href="../article/delete?id=${article.id }&boardId=${article.boardId}"
					onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;">삭제</a>
			</c:if>
		</div>
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
													class="my_btn">좋아요 👍</a>
											</span>
											<span>
												<span>&nbsp;</span>
												<a onclick="alert(this.title); return false;"
													title="좋아요를 먼저 취소해" class="my_btn">싫어요 👎</a>
											</span>
										</div>
									</c:when>
									<c:when test="${actorCanCancelBadReaction }">
										<div>
											<span>
												<span>&nbsp;</span>
												<a onclick="alert(this.title); return false;"
													title="싫어요를 먼저 취소해" class="my_btn">좋아요 👍</a>
											</span>
											<span>
												<span>&nbsp;</span>
												<a
													href="/usr/reactionPoint/doCancelBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
													class="my_btn">싫어요 👎</a>
											</span>
										</div>
									</c:when>
									<c:otherwise>
										<div>
											<span>
												<span>&nbsp;</span>
												<a
													href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
													class="my_btn">좋아요 👍</a>
											</span>
											<span>
												<span>&nbsp;</span>
												<a
													href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
													class="my_btn">싫어요 👎</a>
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
	</div>
</section>

<!-- 댓글 -->
<section class="text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<c:if test="${rq.logined }">
				<form action="../reply/doWrite" method="POST"
					onsubmit="ReplyWrite__submitForm(this); return false;">
					<table border="1">
						<input type="hidden" name="relTypeCode" value="article" />
						<input type="hidden" name="relId" value="${article.id}" />
						<input type="hidden" name="replaceUri" value="${rq.currentUri }" />
						<colgroup>
							<col width="500" />
							<col width="400" />
							<col width="100" />
						</colgroup>
						<tbody>
							<tr>
								<th>댓글</th>
								<td>
									<textarea name="body" style="resize: none"></textarea>
								</td>
								<td>
									<button type="submit">등록</button>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</c:if>
			<c:if test="${rq.notLogined}">
				<a href="${rq.loginUri}" class="my_btn">로그인</a> 후 이용
			</c:if>
		</div>
	</div>
</section>
<section class="mt-5">
	<div class="container mx-auto px-3">
		<h1 class="text-3xl">댓글 리스트(${repliesCount })</h1>
		<table class="reply_table">
			<colgroup>
				<col width="70" />
				<col width="140" />
				<col width="100" />
				<col width="50" />
				<col width="140" />
				<col width="50" />
				<col width="50" />
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>날짜</th>
					<th>작성자</th>
					<th>추천</th>
					<th>내용</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="reply" items="${replies }">
					<tr class="hover">
						<td>
							<div class="badge">${reply.id}</div>
						</td>
						<td>${reply.getForPrintRegDateType1()}</td>
						<td>${reply.extra__writer}</td>
						<td>${reply.goodReactionPoint}</td>
						<td align="left">${reply.body}</td>
						<td>
							<c:if test="${reply.actorCanModify}">
								<a class="btn-text-link btn btn-active btn-ghost"
									href="../reply/modify?id=${reply.id }&replaceUri=${rq.encodedCurrentUri}">수정</a>
							</c:if>
							
							
						</td>
						<td>
							<c:if test="${reply.actorCanDelete}">
								<a class="my_btn" href="../reply/delete?id=${reply.id }"
									onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;">삭제</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>

		</table>
	</div>
</section>
<%@ include file="../common/foot.jspf"%>