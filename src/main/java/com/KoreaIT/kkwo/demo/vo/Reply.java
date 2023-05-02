package com.KoreaIT.kkwo.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	private int id;
	private String regDate;
	private String updateDate;
	private String body;
	private int memberId;
	private String relTypeCode;
	private int relId;
	private int goodReactionPoint;
	private int badReactionPoint;
	
	private String extra__writer;

	private boolean actorCanDelete;
	private boolean actorCanModify;
	
}