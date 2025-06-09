
## 🗂️ 프로젝트

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

<br />

![시스템아키텍쳐](https://github.com/user-attachments/assets/6c04df5b-19f7-4aaf-be81-bf24fa7b37e0)

</details>

<details>
<summary><strong>4️⃣ WBS(Work Breakdown Structure)</strong></summary>

![image](https://github.com/user-attachments/assets/ee52043e-3492-4602-9642-537ee3335abe)


다운로드 : [Beyond_Final_Project-WBS.pdf](https://github.com/user-attachments/files/20007126/Beyond_Final_Project-WBS.pdf)

</details>

<details>
<summary><strong>5️⃣ ERD(Entity-Relationship Diagram)</strong></summary>

![FINAL_PROJECT](https://github.com/user-attachments/assets/09430956-a6e2-4a07-93e1-2b030067aeb4)

```
-- DROP TABLES
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `qna_post`;
DROP TABLE IF EXISTS `alarm`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `info_db`;
DROP TABLE IF EXISTS `request_list`;
DROP TABLE IF EXISTS `payment_detail`;
DROP TABLE IF EXISTS `payment`;
DROP TABLE IF EXISTS `discount`;
DROP TABLE IF EXISTS `payment_category`;
DROP TABLE IF EXISTS `company`;
DROP TABLE IF EXISTS `membership`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `analysis`;
DROP TABLE IF EXISTS `info_column`;

-- CREATE TABLES
CREATE TABLE `role` (
                        `role_no` TINYINT PRIMARY KEY,
                        `name` ENUM('ADMIN', 'CLIENT_ADMIN', 'CLIENT_USER', 'USER') NOT NULL DEFAULT 'USER'
);

CREATE TABLE `membership` (
                              `membership_no` TINYINT AUTO_INCREMENT PRIMARY KEY,
                              `name` VARCHAR(50) NOT NULL,
                              `description` TEXT NOT NULL,
                              `status` BOOLEAN NOT NULL DEFAULT FALSE,
                              `price` INT UNSIGNED NOT NULL,
                              `duration` TINYINT NOT NULL,
                              `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                              `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `payment_category` (
                                    `payment_category_no` TINYINT AUTO_INCREMENT PRIMARY KEY,
                                    `name` VARCHAR(50) NOT NULL
);

CREATE TABLE `analysis` (
                            `analysis_no` TINYINT AUTO_INCREMENT PRIMARY KEY,
                            `name` VARCHAR(50),
                            `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                            `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `company` (
                           `company_no` BIGINT AUTO_INCREMENT PRIMARY KEY,
                           `membership_no` TINYINT NOT NULL,
                           `name` VARCHAR(100) NOT NULL,
                           `email` VARCHAR(50) NOT NULL,
                           `phone` VARCHAR(20) NOT NULL,
                           `registration_number` VARCHAR(20) NOT NULL,
                           `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE,
                           `is_subscribed` BOOLEAN NOT NULL DEFAULT FALSE,
                           `membership_started_at` DATETIME,
                           `membership_expired_at` DATETIME,
                           FOREIGN KEY (`membership_no`) REFERENCES `membership` (`membership_no`)
);

CREATE TABLE `user` (
                        `user_no` BIGINT AUTO_INCREMENT PRIMARY KEY,
                        `username` VARCHAR(50) NOT NULL,
                        `role_no` TINYINT NOT NULL,
                        `company_no` BIGINT NOT NULL,
                        `password` VARCHAR(60) NOT NULL,
                        `logined_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                        `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE,
                        FOREIGN KEY (`role_no`) REFERENCES `role` (`role_no`),
                        FOREIGN KEY (`company_no`) REFERENCES `company` (`company_no`)
);

CREATE TABLE `qna_post` (
                            `post_no` BIGINT AUTO_INCREMENT PRIMARY KEY,
                            `user_no` BIGINT NOT NULL,
                            `title` VARCHAR(100) NOT NULL,
                            `content` TEXT NOT NULL,
                            `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                            `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
);

CREATE TABLE `comment` (
                           `comment_no` BIGINT AUTO_INCREMENT PRIMARY KEY,
                           `post_no` BIGINT NOT NULL,
                           `user_no` BIGINT NOT NULL,
                           `content` TEXT NOT NULL,
                           `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           FOREIGN KEY (`post_no`) REFERENCES `qna_post` (`post_no`),
                           FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
);

CREATE TABLE `payment` (
                           `payment_no` BIGINT AUTO_INCREMENT PRIMARY KEY,
                           `company_no` BIGINT NOT NULL,
                           `payment_category_no` TINYINT NOT NULL,
                           `detail` TEXT NOT NULL,
                           `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           FOREIGN KEY (`company_no`) REFERENCES `company` (`company_no`),
                           FOREIGN KEY (`payment_category_no`) REFERENCES `payment_category` (`payment_category_no`)
);

CREATE TABLE `discount` (
                            `discount_no` BIGINT AUTO_INCREMENT PRIMARY KEY,
                            `company_no` BIGINT NOT NULL,
                            `state` BOOLEAN NOT NULL DEFAULT FALSE,
                            `started_at` DATETIME,
                            `expired_at` DATETIME,
                            `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                            `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (`company_no`) REFERENCES `company` (`company_no`)
);

CREATE TABLE `payment_detail` (
                                  `payment_detail_no` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  `membership_no` TINYINT NOT NULL,
                                  `payment_no` BIGINT NOT NULL,
                                  `company_no` BIGINT NOT NULL,
                                  `discount_no` BIGINT NOT NULL,
                                  `price` INT UNSIGNED NOT NULL,
                                  `status` ENUM('SUCCESS','FAIL','PENDING') NOT NULL DEFAULT 'PENDING',
                                  `paid_at` DATETIME,
                                  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  FOREIGN KEY (`membership_no`) REFERENCES `membership` (`membership_no`),
                                  FOREIGN KEY (`payment_no`) REFERENCES `payment` (`payment_no`),
                                  FOREIGN KEY (`company_no`) REFERENCES `company` (`company_no`),
                                  FOREIGN KEY (`discount_no`) REFERENCES `discount` (`discount_no`)
);

CREATE TABLE `request_list` (
                                `request_no` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                `analysis_no` TINYINT NOT NULL,
                                `company_no` BIGINT NOT NULL,
                                `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                FOREIGN KEY (`analysis_no`) REFERENCES `analysis` (`analysis_no`),
                                FOREIGN KEY (`company_no`) REFERENCES `company` (`company_no`)
);

CREATE TABLE `alarm` (
                         `alarm_no` BIGINT AUTO_INCREMENT PRIMARY KEY,
                         `user_no` BIGINT NOT NULL,
                         `content` TEXT NOT NULL,
                         `is_read` BOOLEAN NOT NULL DEFAULT FALSE,
                         `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (`user_no`) REFERENCES `user` (`user_no`)
);

CREATE TABLE `info_db` (
                           `info_db_no` BIGINT AUTO_INCREMENT PRIMARY KEY,
                           `company_no` BIGINT NOT NULL,
                           `name` VARCHAR(50) NOT NULL,
                           `host` VARCHAR(100),
                           `user` VARCHAR(50),
                           `password` VARCHAR(100),
                           `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           FOREIGN KEY (`company_no`) REFERENCES `company` (`company_no`)
);

CREATE TABLE `info_column` (
                               `info_column_no` BIGINT AUTO_INCREMENT PRIMARY KEY,
                               `info_db_no` BIGINT NOT NULL,
                               `analysis_column` VARCHAR(50) NOT NULL,
                               `origin_column` VARCHAR(50) NOT NULL,
                               `note` TEXT,
                               `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
                               `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               FOREIGN KEY (`info_db_no`) REFERENCES `info_db` (`info_db_no`)
);
```

</details>

<details>
<summary><strong>6️⃣ 화면설계서</strong></summary>

[다구독다구독 화면설계서](https://www.figma.com/design/WIdgPXgYKVYQdWuVFuTV9D/%EB%8B%A4%EA%B5%AC%EB%8F%85-%EB%8B%A4%EA%B5%AC%EB%8F%85?node-id=0-1&t=NT0d2qEw7NmmGSsN-1)

</details>


<details>
<summary><strong>7️⃣ 통합테스트</strong></summary>
<br />

<details>
<summary><strong>로그인/회원가입</strong></summary>

<br />

- 회원가입
  
   <br />
   
 ![회원가입](https://github.com/user-attachments/assets/03a31f1b-19be-48ba-bb6c-b6aa3bcc59c4)
 
<br />

- 회원가입 OTP 재발송
  
  <br />
  
 ![회원가입 OTP 재발송](https://github.com/user-attachments/assets/71ab77b8-130c-4da4-a2f2-316b3551f589)
 
<br />

- 비밀번호 변경
  
  <br />
  
 ![비밀번호 변경](https://github.com/user-attachments/assets/bd66f343-8da7-4820-b8df-fe4674ba02a0)
 
<br />

</details>

<details>
<summary><strong>대시보드</strong></summary><br />

/////여기

</details>


<details>
<summary><strong>세그먼트 분석</strong></summary><br />
  
![segment_1](https://github.com/user-attachments/assets/3eeaad42-81ad-4bab-a0f6-18ac2c5d30d6)

  <br />
  
![segment_2](https://github.com/user-attachments/assets/898a4ec9-7108-4347-b977-dfdd0e161e84)

  <br />
  
![segment_3](https://github.com/user-attachments/assets/ca4b04b0-8c78-4a40-8f4f-f726dfad8881)

<br />

![segment_4](https://github.com/user-attachments/assets/54f70aa0-f416-4433-acbc-92fbdf8d3beb)


</details>

<details>
<summary><strong>코호트 분석 (단일 분석 / 양측 분석)</strong></summary><br />

- 코호트 단일분석

  <br />


![cohort_single](https://github.com/user-attachments/assets/b242e784-4cf4-468e-a57f-6e3cea4d5c6a)

<br />

- 코호트 양측분석

 <br />


![cohort_double](https://github.com/user-attachments/assets/41a25e58-9cc3-4aaa-90fa-611459dbe075)

<br />

</details>

<details>
<summary><strong>마이페이지</strong></summary><br />

/////여기

</details>

<details>
<summary><strong>결제</strong></summary><br />

![payment](https://github.com/user-attachments/assets/6c1f0906-eeef-49e5-8b70-2cb973523c44)


</details>

<details>
<summary><strong>About us</strong></summary><br />

![about_us](https://github.com/user-attachments/assets/fae03767-3f51-45d6-9b22-ad73df185d8e)


</details>

<details>
<summary><strong>Membership</strong></summary><br />

![Membership](https://github.com/user-attachments/assets/ee38251e-29cd-4781-a916-2c304f9ce1de)


</details>

<details>
<summary><strong>How to</strong></summary><br />

/////여기

</details>

<details>
<summary><strong>BackEnd CI</strong></summary><br />

![be_test3merge](https://github.com/user-attachments/assets/79fc9c3a-1168-4289-a904-d56cf39f7c56)

<br />

![be_test4ci](https://github.com/user-attachments/assets/ff9c725d-e9ee-460a-9d2e-b8ae65bfa3e0)

<br />

</details>

<details>
<summary><strong>BackEnd CD</strong></summary><br />

![be_test5cd](https://github.com/user-attachments/assets/41360f7d-6e77-4a14-9c1c-995abef754bf)


</details>

<details>
<summary><strong>FrontEnd CI</strong></summary><br />

![fe_test1](https://github.com/user-attachments/assets/7437d6c7-6504-486b-9c56-85a58df4f3f0)

<br />

![fe_test2](https://github.com/user-attachments/assets/18a14a28-194a-46dc-9208-904597fc232f)

<br />

![fe_test3](https://github.com/user-attachments/assets/f4159e59-4ffe-4e62-9d9f-8228542e459e)


</details>

<details>
<summary><strong>FrontEnd CD</strong></summary><br />

![fe_test4](https://github.com/user-attachments/assets/c54b1c0d-aebf-4e84-b075-6750176aef94)


</details>

<details>
<summary><strong>Machine Learning CI</strong></summary><br />

/////여기

</details>

<details>
<summary><strong>Machine Learning CD</strong></summary><br />

/////여기

</details>

<br /><br />





</details>




<details>
<summary><strong>8️⃣ CI/CD 계획서</strong></summary><br />

<details>
<summary><strong>BackEnd</strong></summary><br />

![BE 배포 계획서1](https://github.com/user-attachments/assets/90296c6e-5b1b-420c-bd13-d34416e75121)
![BE 배포 계획서2](https://github.com/user-attachments/assets/ce189fd0-e925-499f-a453-027389f43327)
![BE 배포 계획서3](https://github.com/user-attachments/assets/04a68471-cf56-4ee0-9c6a-4db1ab3aab8d)
![BE 배포 계획서4](https://github.com/user-attachments/assets/3cc829f1-de9b-4748-be34-b021fddcc6a4)
![BE 배포 계획서5](https://github.com/user-attachments/assets/580bfb49-f11f-451d-9ebe-0578d88a483d)
![BE 배포 계획서6](https://github.com/user-attachments/assets/f938356b-d206-4f35-9a03-2c5c07edde22)
![BE 배포 계획서7](https://github.com/user-attachments/assets/d23b646e-fbe8-4ed1-9089-6986e4d07929)
![BE 배포 계획서8](https://github.com/user-attachments/assets/9a988986-6b99-4552-9774-12481dd342f4)


</details>

<details>
<summary><strong>FrontEnd</strong></summary><br />

![FE 배포 계획서1](https://github.com/user-attachments/assets/d5832583-d640-45cb-ac92-762235b47ddd)
![FE 배포 계획서2](https://github.com/user-attachments/assets/f1ad9435-065f-4f36-b720-d06067156b54)
![FE 배포 계획서3](https://github.com/user-attachments/assets/36f07251-3111-4b67-adf9-a2051ba7f409)
![FE 배포 계획서4](https://github.com/user-attachments/assets/e0e037a2-aa61-4118-b02d-50a7246c7a39)


</details>

<details>
<summary><strong>MachineLearning</strong></summary><br />

![ML 배포 계획서1](https://github.com/user-attachments/assets/92634d06-8fd3-487f-9766-9ceadca9e5cc)
![ML 배포 계획서2](https://github.com/user-attachments/assets/43a067a3-6652-437f-a663-7f34f5bcf37d)
![ML 배포 계획서3](https://github.com/user-attachments/assets/f08ae7af-8051-423d-b077-8c71ac21d4f2)
![ML 배포 계획서4](https://github.com/user-attachments/assets/57566b85-56fc-4017-bad1-3950e75588ce)


</details>


<details>
<summary><strong>AWS</strong></summary><br />

![AWS 이용 항목](https://github.com/user-attachments/assets/8a1c273d-690c-45be-9491-2f8e1d4399aa)


</details></details> <br /><br />

### 🔐 정책 및 가이드
<details>
<summary><strong> Git 브랜치 전략</strong></summary>

[다구독다구독 화면설계서 링크](https://www.figma.com/design/WIdgPXgYKVYQdWuVFuTV9D/%EB%8B%A4%EA%B5%AC%EB%8F%85-%EB%8B%A4%EA%B5%AC%EB%8F%85?node-id=0-1&t=NT0d2qEw7NmmGSsN-1)   

</details>
[API 테스트 명세서](https://playdatacademy.notion.site/API-1d6d943bcac280259831f92e231b983f)
---

## 📁 디렉토리 구조
