package com.KoreaIT.kkwo.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	private int id;
	private String loginId;
	private String loginPw;
	private String name;
	private String nickname;
	private String cellphoneNum;
	private String email;
}
