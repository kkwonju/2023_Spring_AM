package com.KoreaIT.kkwo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.kkwo.demo.repository.MemberRepository;
import com.KoreaIT.kkwo.demo.util.Ut;
import com.KoreaIT.kkwo.demo.vo.Member;
import com.KoreaIT.kkwo.demo.vo.ResultData;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;

	public ResultData<Integer> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		Member existsMember = getMemberByLoginId(loginId);
		if (existsMember != null) {
			return ResultData.from("F-7", Ut.f("이미 가입된 아이디입니다 [%s]", loginId));
		}

		existsMember = getMemberByNameAndEmail(name, email);

		if (existsMember != null) {
			return ResultData.from("F-8", Ut.f("이미 가입된 이름과 이메일입니다 [%s, %s])", name, email));
		}

		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		int id = memberRepository.getLastInsertId();
		
		return ResultData.from("S-1", "가입 성공", "id", id);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	public Member getMemberByEamil(String email) {
		return memberRepository.getMemberByEamil(email);
	}
	
	// 손 댈 수 있는지 여부
	private void controlForPrintData(int actorId, Member member) {
		if (member == null) {
			return;
		}

		ResultData actorCanModifyRd = actorCanModify(actorId, member);
		member.setActorCanModify(actorCanModifyRd.isSuccess());

		ResultData actorCanDeleteRd = actorCanDelete(actorId, member);
		member.setActorCanDelete(actorCanDeleteRd.isSuccess());
	}
	
	/* 삭제 권한 체크 */
	private ResultData actorCanDelete(int actorId, Member member) {
		if (member == null) {
			return ResultData.from("F-1", "회원이 존재하지 않습니다");
		}
		if (member.getId() != actorId) {
			return ResultData.from("F-2", "해당 회원에 대한 권한이 없습니다");
		}

		return ResultData.from("S-1", "삭제 가능");
	}

	/* 수정 권한 체크 */
	public ResultData actorCanModify(int actorId, Member member) {
		if (member.getId() != actorId) {
			return ResultData.from("F-3", "해당 회원에 대한 권한이 없습니다");
		}
		return ResultData.from("S-1", "수정 가능");
	}

	public ResultData modifyMember(int id, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		memberRepository.modifyMember(id, loginPw, name, nickname, cellphoneNum, email);
		return ResultData.from("S-1", "회원 정보 수정이 완료되었습니다");
	}


}
