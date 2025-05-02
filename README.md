## 🗂️ 프로젝트 test15

<details>
<summary><strong>1️⃣ 프로젝트 기획서</strong></summary>
  
![프로젝트 기획서_page-0001](https://github.com/user-attachments/assets/0e8e7336-db43-4fd0-81da-6b0a7bc2e542)
![프로젝트 기획서_page-0002](https://github.com/user-attachments/assets/501908c0-3749-4a07-b1b6-e669c595f75a)
![프로젝트 기획서_page-0003](https://github.com/user-attachments/assets/db1638a3-f3d0-492e-8489-1b930d99bb65)
![프로젝트 기획서_page-0004](https://github.com/user-attachments/assets/5a3efebd-79ae-4b75-9cca-a52dc8979bfd)
![프로젝트 기획서_page-0005](https://github.com/user-attachments/assets/610e3285-89b3-47e7-9907-504cbf0706f9)

![프로젝트 기획서_page-0006](https://github.com/user-attachments/assets/82615f70-3754-41a7-8052-ce870e807dac)


다운로드 : [프로젝트 기획서.pdf](https://github.com/user-attachments/files/19887048/default.pdf)

</details>

<details>
<summary><strong>2️⃣ 요구사항 정의서</strong></summary>

![요구사항 정의서_page-0001](https://github.com/user-attachments/assets/5c977fa3-9327-42c6-af1d-7b281f813399)
![요구사항 정의서_page-0002](https://github.com/user-attachments/assets/71b5cd07-267a-4540-8123-0901136ad458)
![요구사항 정의서_page-0003](https://github.com/user-attachments/assets/3b62c603-0758-490f-96e8-7730adfaa214)


다운로드 : [요구사항 정의서.pdf](https://github.com/user-attachments/files/19887735/default.pdf)

</details>

<details>
<summary><strong>3️⃣ 시스템 아키텍쳐</strong></summary>

</details>

<details>
<summary><strong>4️⃣ WBS(Work Breakdown Structure)</strong></summary>

![image](https://github.com/user-attachments/assets/ee52043e-3492-4602-9642-537ee3335abe)


다운로드 : [Beyond_Final_Project-WBS.pdf](https://github.com/user-attachments/files/20007126/Beyond_Final_Project-WBS.pdf)

</details>

<details>
<summary><strong>5️⃣ ERD(Entity-Relationship Diagram)</strong></summary>

![FINAL_PROJECT](https://github.com/user-attachments/assets/94958563-a13e-40e4-8d5a-ec72adc0291f)
```
CREATE TABLE `analysis` (
	`analysis_no`	TINYINT	NOT NULL,
	`name`	VARCHAR(20)	NULL
);

CREATE TABLE `qna_post` (
	`post_no`	BIGINT	NOT NULL,
	`user_no`	BIGINT	NOT NULL,
	`title`	VARCHAR(20)	NOT NULL,
	`content`	TEXT	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL
);

CREATE TABLE `comment` (
	`comment_no`	BIGINT	NOT NULL,
	`post_no`	BIGINT	NOT NULL,
	`user_no`	BIGINT	NOT NULL,
	`content`	TEXT	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL
);

CREATE TABLE `membership` (
	`membership_no`	TINYINT	NOT NULL,
	`name`	VARCHAR(20)	NOT NULL,
	`description`	TEXT	NOT NULL,
	`status`	BOOLEAN	NOT NULL	DEFAULT FALSE,
	`price`	INT UNSIGNED	NOT NULL,
	`duration`	TINYINT	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL
);

CREATE TABLE `role` (
	`role_no`	TINYINT	NOT NULL,
	`name`	ENUM('ADMIN', 'CLIENT_ADMIN', 'CLIENT_USER', 'USER')	NOT NULL	DEFAULT 'USER'
);

CREATE TABLE `payment_detail` (
	`payment_detail_no`	BIGINT	NOT NULL,
	`membership_no`	TINYINT	NOT NULL,
	`payment_no`	BIGINT	NOT NULL,
	`company_no`	BIGINT	NOT NULL,
	`discount_no`	BIGINT	NOT NULL,
	`price`	INT UNSIGNED	NOT NULL,
	`status`	ENUM('SUCCESS','FAIL','PENDING')	NOT NULL	DEFAULT 'PENDING',
	`paid_at`	DATETIME	NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL
);

CREATE TABLE `request_list` (
	`request_no`	BIGINT	NOT NULL,
	`analysis_no`	TINYINT	NOT NULL,
	`company_no`	BIGINT	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL
);

CREATE TABLE `alarm` (
	`alarm_no`	BIGINT	NOT NULL,
	`user_no`	BIGINT	NOT NULL,
	`content`	TEXT	NOT NULL,
	`is_read`	BOOLEAN	NOT NULL	DEFAULT FALSE,
	`created_at`	DATETIME	NOT NULL
);

CREATE TABLE `payment_category` (
	`payment_category_no`	TINYINT	NOT NULL,
	`name`	VARCHAR(20)	NOT NULL
);

CREATE TABLE `payment` (
	`payment_no`	BIGINT	NOT NULL,
	`company_no`	BIGINT	NOT NULL,
	`payment_category_no`	TINYINT	NOT NULL,
	`detail`	TEXT	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL
);

CREATE TABLE `discount` (
	`discount_no`	BIGINT	NOT NULL,
	`company_no`	BIGINT	NOT NULL,
	`state`	BOOLEAN	NOT NULL	DEFAULT FALSE,
	`started_at`	DATETIME	NULL,
	`expired_at`	DATETIME	NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL
);

CREATE TABLE `info_db` (
	`info_db_no`	BIGINT	NOT NULL,
	`company_no`	BIGINT	NOT NULL,
	`name`	VARCHAR(20)	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL
);

CREATE TABLE `user` (
	`user_no`	BIGINT	NOT NULL,
	`username`	VARCHAR(20)	NOT NULL,
	`role_no`	TINYINT	NOT NULL,
	`company_no`	BIGINT	NOT NULL,
	`password`	VARCHAR(20)	NOT NULL,
	`logined_at`	DATETIME	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL,
	`is_deleted`	BOOLEAN	NOT NULL	DEFAULT FALSE
);

CREATE TABLE `company` (
	`company_no`	BIGINT	NOT NULL,
	`membership_no`	TINYINT	NOT NULL,
	`name`	VARCHAR(50)	NOT NULL,
	`email`	VARCHAR(20)	NOT NULL,
	`phone`	VARCHAR(20)	NOT NULL,
	`registration_number`	VARCHAR(20)	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NOT NULL,
	`is_deleted`	BOOLEAN	NOT NULL	DEFAULT FALSE,
	`is_subscribed`	BOOLEAN	NOT NULL	DEFAULT FALSE,
	`membership_started_at`	DATETIME	NULL,
	`membership_expired_at`	DATETIME	NULL
);

ALTER TABLE `analysis` ADD CONSTRAINT `PK_ANALYSIS` PRIMARY KEY (
	`analysis_no`
);

ALTER TABLE `qna_post` ADD CONSTRAINT `PK_QNA_POST` PRIMARY KEY (
	`post_no`
);

ALTER TABLE `comment` ADD CONSTRAINT `PK_COMMENT` PRIMARY KEY (
	`comment_no`
);

ALTER TABLE `membership` ADD CONSTRAINT `PK_MEMBERSHIP` PRIMARY KEY (
	`membership_no`
);

ALTER TABLE `role` ADD CONSTRAINT `PK_ROLE` PRIMARY KEY (
	`role_no`
);

ALTER TABLE `payment_detail` ADD CONSTRAINT `PK_PAYMENT_DETAIL` PRIMARY KEY (
	`payment_detail_no`
);

ALTER TABLE `request_list` ADD CONSTRAINT `PK_REQUEST_LIST` PRIMARY KEY (
	`request_no`
);

ALTER TABLE `alarm` ADD CONSTRAINT `PK_ALARM` PRIMARY KEY (
	`alarm_no`
);

ALTER TABLE `payment_category` ADD CONSTRAINT `PK_PAYMENT_CATEGORY` PRIMARY KEY (
	`payment_category_no`
);

ALTER TABLE `payment` ADD CONSTRAINT `PK_PAYMENT` PRIMARY KEY (
	`payment_no`,
	`company_no`
);

ALTER TABLE `discount` ADD CONSTRAINT `PK_DISCOUNT` PRIMARY KEY (
	`discount_no`
);

ALTER TABLE `info_db` ADD CONSTRAINT `PK_INFO_DB` PRIMARY KEY (
	`info_db_no`
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`user_no`
);

ALTER TABLE `company` ADD CONSTRAINT `PK_COMPANY` PRIMARY KEY (
	`company_no`
);

ALTER TABLE `qna_post` ADD CONSTRAINT `FK_user_TO_qna_post_1` FOREIGN KEY (
	`user_no`
)
REFERENCES `user` (
	`user_no`
);

ALTER TABLE `comment` ADD CONSTRAINT `FK_qna_post_TO_comment_1` FOREIGN KEY (
	`post_no`
)
REFERENCES `qna_post` (
	`post_no`
);

ALTER TABLE `comment` ADD CONSTRAINT `FK_user_TO_comment_1` FOREIGN KEY (
	`user_no`
)
REFERENCES `user` (
	`user_no`
);

ALTER TABLE `payment_detail` ADD CONSTRAINT `FK_membership_TO_payment_detail_1` FOREIGN KEY (
	`membership_no`
)
REFERENCES `membership` (
	`membership_no`
);

ALTER TABLE `payment_detail` ADD CONSTRAINT `FK_payment_TO_payment_detail_1` FOREIGN KEY (
	`payment_no`
)
REFERENCES `payment` (
	`payment_no`
);

ALTER TABLE `payment_detail` ADD CONSTRAINT `FK_payment_TO_payment_detail_2` FOREIGN KEY (
	`company_no`
)
REFERENCES `payment` (
	`company_no`
);

ALTER TABLE `payment_detail` ADD CONSTRAINT `FK_discount_TO_payment_detail_1` FOREIGN KEY (
	`discount_no`
)
REFERENCES `discount` (
	`discount_no`
);

ALTER TABLE `request_list` ADD CONSTRAINT `FK_analysis_TO_request_list_1` FOREIGN KEY (
	`analysis_no`
)
REFERENCES `analysis` (
	`analysis_no`
);

ALTER TABLE `request_list` ADD CONSTRAINT `FK_company_TO_request_list_1` FOREIGN KEY (
	`company_no`
)
REFERENCES `company` (
	`company_no`
);

ALTER TABLE `alarm` ADD CONSTRAINT `FK_user_TO_alarm_1` FOREIGN KEY (
	`user_no`
)
REFERENCES `user` (
	`user_no`
);

ALTER TABLE `payment` ADD CONSTRAINT `FK_company_TO_payment_1` FOREIGN KEY (
	`company_no`
)
REFERENCES `company` (
	`company_no`
);

ALTER TABLE `payment` ADD CONSTRAINT `FK_payment_category_TO_payment_1` FOREIGN KEY (
	`payment_category_no`
)
REFERENCES `payment_category` (
	`payment_category_no`
);

ALTER TABLE `discount` ADD CONSTRAINT `FK_company_TO_discount_1` FOREIGN KEY (
	`company_no`
)
REFERENCES `company` (
	`company_no`
);

ALTER TABLE `info_db` ADD CONSTRAINT `FK_company_TO_info_db_1` FOREIGN KEY (
	`company_no`
)
REFERENCES `company` (
	`company_no`
);

ALTER TABLE `user` ADD CONSTRAINT `FK_role_TO_user_1` FOREIGN KEY (
	`role_no`
)
REFERENCES `role` (
	`role_no`
);

ALTER TABLE `user` ADD CONSTRAINT `FK_company_TO_user_1` FOREIGN KEY (
	`company_no`
)
REFERENCES `company` (
	`company_no`
);

ALTER TABLE `company` ADD CONSTRAINT `FK_membership_TO_company_1` FOREIGN KEY (
	`membership_no`
)
REFERENCES `membership` (
	`membership_no`
);


```

</details>

<details>
<summary><strong>6️⃣ 화면설계서</strong></summary>

</details>

### 🔐 정책 및 가이드
<details>
<summary><strong> Git 브랜치 전략</strong></summary>

![image](https://github.com/user-attachments/assets/4bb2cd78-151d-478c-9300-41ab6fed4688)

</details>



---

## 📁 디렉토리 구조

