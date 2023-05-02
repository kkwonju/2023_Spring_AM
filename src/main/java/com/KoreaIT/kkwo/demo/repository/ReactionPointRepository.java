package com.KoreaIT.kkwo.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {

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
	
	@Select("""
			<script>
				SELECT
				IFNULL(SUM(IF(RP.point &gt; 0, RP.point, 0)), 0)
				FROM reactionPoint AS RP
				WHERE RP.relTypeCode = 'article'
				AND RP.relId = #{id}
				AND RP.memberId = #{actorId}
			</script>
			""")
	public int getSumGoodReactionPointByMemberId(int actorId, String relTypeCode, int id);
	
	@Select("""
			<script>
				SELECT
				IFNULL(SUM(IF(RP.point &lt; 0, RP.point * -1, 0)), 0)
				FROM reactionPoint AS RP
				WHERE RP.relTypeCode = 'article'
				AND RP.relId = #{id}
				AND RP.memberId = #{actorId}
			</script>
			""")
	public int getSumBadReactionPointByMemberId(int actorId, String relTypeCode, int id);
	
	@Insert("""
			<script>
			INSERT INTO reactionPoint
			SET regDate = NOW(),
			updateDate = NOW(),
			memberId = #{actorId},
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			`point` = 1
			</script>
			""")
	public int addGoodReactionPoint(int actorId, String relTypeCode, int relId);
	
	@Insert("""
			<script>
			INSERT INTO reactionPoint
			SET regDate = NOW(),
			updateDate = NOW(),
			memberId = #{actorId},
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			`point` = -1
			</script>
			""")
	public int addBadReactionPoint(int actorId, String relTypeCode, int relId);

	@Delete("""
			DELETE FROM reactionPoint
			WHERE
			relTypeCode = #{relTypeCode}
			AND relId = #{relId}
			AND memberId = #{actorId}
			""")
	public void removeReactionPoint(int actorId, String relTypeCode, int relId);
}