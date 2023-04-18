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

	public ResultData doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		// 로그인 아이디 중복 체크
		Member existsMember = getMemberByLoginId(loginId);

		if (existsMember != null) {
			return ResultData.from("F-8", Ut.f("이미 존재하는 아이디(%s)입니다", loginId), -1);
		}
		// 이름, 이메일 중복 체크
		existsMember = getMemberByNameAndEmail(name, email);

		if (existsMember != null) {
			return ResultData.from("F-8", Ut.f("이미 존재하는 이름(%s)과 이메일(%s)입니다", name, email), -2);
		}

		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		int id = memberRepository.getLastInsertId();
		return ResultData.from("S-1", Ut.f("%d번 회원 가입", id), id);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}
	
	private Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}
}
