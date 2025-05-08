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

![image](https://www.figma.com/design/WIdgPXgYKVYQdWuVFuTV9D/%EB%8B%A4%EA%B5%AC%EB%8F%85-%EB%8B%A4%EA%B5%AC%EB%8F%85?node-id=0-1&t=NT0d2qEw7NmmGSsN-1)

</details>

### 🔐 정책 및 가이드
<details>
<summary><strong> Git 브랜치 전략</strong></summary>

![image](https://github.com/user-attachments/assets/4bb2cd78-151d-478c-9300-41ab6fed4688)
![다구독 다구독.png](../Downloads/%EB%8B%A4%EA%B5%AC%EB%8F%85%20%EB%8B%A4%EA%B5%AC%EB%8F%85.png)

</details>

---

## 📁 디렉토리 구조