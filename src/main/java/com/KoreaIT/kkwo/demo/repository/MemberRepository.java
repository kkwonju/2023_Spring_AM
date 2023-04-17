package com.KoreaIT.kkwo.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.kkwo.demo.vo.Member;

@Mapper
public interface MemberRepository {

	void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);

	Member getMemberById(int id);
	
	int getLastInsertId();
	
	Member getMemberByLoginId(String loginId);
}
