package com.KoreaIT.kkwo.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.KoreaIT.kkwo.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	public void writeArticle(String title, String body, int memberId, int boardId);

	@Select("""
			SELECT *
			FROM article
			WHERE id = #{id}
			""")
	public Article getArticle(int id);

	@Select("""
			<script>
			SELECT *, M.nickname AS extra__writer
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			WHERE A.id = #{id}
			</script>
			""")
	public Article getForPrintArticle(int id);

	@Select("""
			SELECT *
			FROM article
			ORDER BY id DESC
			""")
	public List<Article> getArticles();

	public void modifyArticle(int id, String title, String body);

	public void deleteArticle(int id);

	public int getLastInsertId();

	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM article AS A
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'">
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'body'">
						AND A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</otherwise>
				</choose>
			</if>
			</script>
			""")
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword);

	@Select("""
			<script>
			SELECT
				A.*,
				M.nickname AS extra__writer
			FROM article AS A
			INNER JOIN `member` AS M
			ON A.memberId = M.id
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'">
						AND A.title LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'body'">
						AND A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						OR A.title LIKE CONCAT('%',#{searchKeyword},'%')
						OR A.body LIKE CONCAT('%',#{searchKeyword},'%')
					</otherwise>
				</choose>
			</if>
			ORDER BY A.id DESC
			<if test="limitFrom >= 0">
				LIMIT #{limitFrom}, #{itemsInAPage}
			</if>
			</script>
			""")
	public List<Article> getForPrintArticles(int limitFrom, int itemsInAPage, int boardId, String searchKeywordTypeCode,
			String searchKeyword);

	@Update("""
			<script>
			UPDATE article
			SET hitCount = hitCount + 1
			WHERE id = #{id}
			</script>
			""")
	public int increaseHitCount(int id);

	@Select("""
			<script>
			SELECT hitCount
			FROM article
			WHERE id = #{id}
			</script>
			""")
	public int getArticleHitCount(int id);

	@Update("""
			<script>
			UPDATE article
			SET goodReactionpoint = goodReactionpoint + 1
			WHERE id = #{relId}
			</script>
			""")
	public int increaseGoodReactionPoint(int relId);
	
	@Update("""
			<script>
			UPDATE article
			SET badReactionpoint = badReactionpoint + 1
			WHERE id = #{relId}
			</script>
			""")
	public int increaseBadReactionPoint(int relId);

	@Update("""
			<script>
			UPDATE article
			SET goodReactionpoint = goodReactionpoint - 1
			WHERE id = #{relId}
			</script>
			""")
	public int decreaseGoodReactionPoint(int relId);
	
	@Update("""
			<script>
			UPDATE article
			SET badReactionpoint = badReactionpoint - 1
			WHERE id = #{relId}
			</script>
			""")
	public int decreaseBadReactionPoint(int relId);
}