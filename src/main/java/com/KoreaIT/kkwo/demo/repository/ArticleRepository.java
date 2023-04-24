package com.KoreaIT.kkwo.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.KoreaIT.kkwo.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	public void writeArticle(int memberId, String title, String body);

	@Select("""
			SELECT *
			FROM article
			WHERE id = #{id}
			""")
	public Article getArticle(int id);

	@Select("""
			SELECT *, M.nickname AS extra__writer
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			WHERE A.id = #{id}
			""")
	public Article getForPrintArticle(int id);

	@Select("""
			SELECT *
			FROM article
			ORDER BY id DESC
			""")
	public List<Article> getArticles();

	@Select("""
			<script>
			SELECT *, M.nickname AS extra__writer
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			ORDER BY A.id DESC
			</script>
			""")
	public List<Article> getForPrintArticles(int boardId);

	public void modifyArticle(int id, String title, String body);

	public void deleteArticle(int id);

	public int getLastInsertId();
}