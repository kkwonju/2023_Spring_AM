package com.KoreaIT.kkwo.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.KoreaIT.kkwo.demo.vo.Reply;

@Mapper
public interface ReplyRepository {

	@Select("""
			<script>
				SELECT R.*, M.nickname AS extra__writer
				FROM reply AS R
				INNER JOIN `member` AS M
				ON R.memberId = M.id
				WHERE R.relId = #{relId}
				AND R.relTypeCode = #{relTypeCode}
			</script>
			""")
	public List<Reply> getForPrintReplies(int actorId, String relTypeCode, int relId);

	@Insert("""
			<script>
				INSERT INTO reply
				SET regDate = NOW(),
				updateDate = NOw(),
				memberId = #{actorId},
				relId = #{relId},
				relTypeCode = #{relTypeCode},
				`body` = #{body}
			</script>
					""")
	public void writeReply(String relTypeCode, int relId, String body, int actorId);

	@Select("SELECT LAST_INSERT_ID()")
	public int getLastInsertId();
}
