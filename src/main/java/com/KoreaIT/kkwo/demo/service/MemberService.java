package com.KoreaIT.kkwo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.kkwo.demo.repository.MemberRepository;
import com.KoreaIT.kkwo.demo.vo.Member;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;
	
	public int doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		Member existsMember = getMemberByLoginId(loginId);
		
		if (existsMember != null) {
			return -1;
		}
		
		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		int id = memberRepository.getLastInsertId();
		return id; 
	}
	
	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id); 
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}
}
