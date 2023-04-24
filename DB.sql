#DB 생성
DROP DATABASE IF EXISTS Spring_AM;
CREATE DATABASE Spring_AM;
USE Spring_AM;

# 게시물 테이블 생성
CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

# 회원 테이블 생성
CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(60) NOT NULL,
    `authLevel` SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한 레벨 (3=일반,7=관리자)',
    `name` CHAR(20) NOT NULL,
    nickname CHAR(20) NOT NULL,
    cellphoneNum CHAR(20) NOT NULL,
    email CHAR(50) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴 여부 (0=탈퇴 전, 1=탈퇴 후)',
    delDate DATETIME COMMENT '탈퇴 날짜'
);

# 게시물 테스트데이터 생성
INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
title = '공지사항 1',
`body` = '공지 1';

INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 1',
`body` = '내용 1';


INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 2',
`body` = '내용 2';

INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 3',
`body` = '내용 3';

# 회원 테스트 데이터 생성
INSERT INTO `member`
SET 
regDate = NOW(), 
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
`authLevel` = 7,
`name` = '유재석',
nickname = '재석',
cellphoneNum = '010-1111-1111',
email = '1111@naver.com';

INSERT INTO `member`
SET 
regDate = NOW(), 
updateDate = NOW(),
loginId = 'test2',
loginPw = 'pw2',
`name` = '박명수',
nickname = '명수',
cellphoneNum = '010-2222-2222',
email = '2222@naver.com';

INSERT INTO `member`
SET 
regDate = NOW(), 
updateDate = NOW(),
loginId = 'test3',
loginPw = 'pw3',
`name` = '정준하',
nickname = '준하',
cellphoneNum = '010-3333-3333',
email = '3333@naver.com',
delStatus = 1,
delDate = NOW();

# 작성자 데이터 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER `updateDate`;

# 작성자 데이터 업데이트
UPDATE article
SET memberId = 1
WHERE id = 1;

UPDATE article
SET memberId = 2
WHERE id IN(2,3);

UPDATE article
SET memberId = 3
WHERE id = 4;

# 게시물 테이블 생성
CREATE TABLE board(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항), free(자유), qna(질의응답), ..',
    `name` CHAR(20) NOT NULL UNIQUE COMMENT '게시판 이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제 여부 (0=삭제 전, 1=삭제 후)',
    delDate DATETIME COMMENT '삭제 날짜'
);

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'NOTICE',
`name` = '공지사항';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'FREE',
`name` = '자유';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'QNA',
`name` = '질의응답';

ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER `memberId`;

UPDATE article
SET boardId = 1
WHERE id = 1;

UPDATE article
SET boardId = 2
WHERE id IN (2, 4);

UPDATE article
SET boardId = 3
WHERE id = 3;

###################################
DESC article;

SELECT * FROM article;
SELECT * FROM `member`;
SELECT * FROM board;

SELECT LAST_INSERT_ID();