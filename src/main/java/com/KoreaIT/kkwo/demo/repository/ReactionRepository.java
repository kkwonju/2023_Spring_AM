package com.KoreaIT.kkwo.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionRepository {

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
			ABS(IFNULL(SUM(IF(RP.point > 0, RP.point, 0)),0)) AS extra__goodReactionPoint
			FROM reactionPoint AS RP
			WHERE relId = #{relId}
			""")
	public Object getReactionPoint(int relId);

}