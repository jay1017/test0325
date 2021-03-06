-- 각 테이블 삭제
DROP TABLE BOOK
DROP TABLE ROOM
DROP TABLE MANAGER
DROP TABLE CUSTOMER

-- CUSTOMER : 고객
CREATE TABLE CUSTOMER(
	CID		NVARCHAR2(10) PRIMARY KEY,
	CPW		NVARCHAR2(10) NOT NULL,
	CNAME	NVARCHAR2(5) NOT NULL,
	CPHONE	NVARCHAR2(13) NOT NULL
)
-- MANAGER : 점주
CREATE TABLE MANAGER(
	BRANCH	NVARCHAR2(30) PRIMARY KEY,
	MID		NVARCHAR2(10) UNIQUE,
	MPW		NVARCHAR2(10) NOT NULL,
	MNAME	NVARCHAR2(5) NOT NULL,
	MPHONE	NVARCHAR2(13) NOT NULL
)
-- ROOM : 방 테마
CREATE TABLE ROOM(
	RNAME	NVARCHAR2(30) PRIMARY KEY,
	BRANCH	NVARCHAR2(30),
	CONSTRAINT RO_BR_FK FOREIGN KEY(BRANCH) REFERENCES MANAGER(BRANCH)
)
-- BOOK : 예약
CREATE TABLE BOOK(
	BDATE	DATE,
	CID		NVARCHAR2(10),
	RNAME	NVARCHAR2(30),
	BRANCH	NVARCHAR2(30),
	CONSTRAINT BO_CI_FK FOREIGN KEY(CID) REFERENCES CUSTOMER(CID),
	CONSTRAINT BO_RN_FK FOREIGN KEY(RNAME) REFERENCES ROOM(RNAME),
	CONSTRAINT BO_BR_FK FOREIGN KEY(BRANCH) REFERENCES MANAGER(BRANCH)
)
-- 각 테이블 조회
SELECT * FROM CUSTOMER
SELECT * FROM MANAGER
SELECT * FROM ROOM
SELECT * FROM BOOK
