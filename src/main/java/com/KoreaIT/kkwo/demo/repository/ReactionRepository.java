package com.KoreaIT.kkwo.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.KoreaIT.kkwo.demo.vo.ReactionPoint;

@Mapper
public interface ReactionRepository {

	@Select("""
			<script>
				SELECT
				IFNULL(SUM(RP.point), 0)
				FROM reactionPoint AS RP
				WHERE RP.relTypeCode = 'article'
				AND RP.relId = #{id}
				AND RP.memberId = #{actorId}
			</script>
			""")
	public int getSumReactionPointByMemberId(int actorId, String relTypeCode, int id);
	
	@Insert("""
			<script>
			INSERT INTO reactionPoint
			SET regDate = NOW(),
			updateDate = NOW(),
			memberId = #{memberId},
			relTypeCode = 'article',
			relId = #{relId},
			`point` = 1
			</script>
			""")
	public int increaseReactionPoint(int memberId, int relId);

	@Delete("""
			DELETE FROM reactionPoint
			WHERE memberId = #{memberId} AND relId = #{relId}
			""")
	public int decreaseReactionPoint(int memberId, int relId);

	@Select("""
			SELECT
				IFNULL(SUM(RP.point), 0) AS extra__sumReactionPoint
				ABS(IFNULL(SUM(IF(RP.point &gt; 0, RP.point, 0)),0)) AS extra__goodReactionPoint
				ABS(IFNULL(SUM(IF(RP.point &lt; 0, RP.point, 0)),0)) AS extra__badReactionPoint
			FROM reactionPoint AS RP
			WHERE relId = #{relId}
			""")
	public ReactionPoint getReactionPoint(int relId);

}