# 커뮤니티 제작 개인 프로젝트

## 프로젝트 소개

- 누구나 쉽게 하고 싶은 말을 작성하고 코멘트를 달 수 있는 커뮤니티 입니다.

## 사용 기술

- Spring framework 3.4.3
- JAVA 21
- MySQL 9.2.0

## 구동 영상

[![Video Label](https://github.com/user-attachments/assets/ee42074b-845f-408c-b13e-4d91c351b974
)](https://youtu.be/6OvaKdDx61Y)

## 주요 기능

### User

- 사용자 CRUD
- 사용자 비밀번호 bCrypt로 암호화
- 로그인 시 JWT access token을 발급하고, 사용자 관련 로직에 대해서 토큰을 주고받아 사용자 검증
- 회원 탈퇴, 로그아웃 시 token 삭제
- 서버에 프로필 이미지를 저장하고 url을 매핑하여 전달

### Post

- 게시글 CRUD
- 게시글 좋아요 엔티티를 따로 만들어서 관리
- 댓글 CRUD

## 구동 방법

```
git clone
cd 복제한 리포지토리
./gradlew build -x test
cd build/libs
java -jar arnold-kim-community-be-0.0.1-SNAPSHOT
```

## 기타

- [API 명세서](https://docs.google.com/spreadsheets/d/1bFqgA7WXP9xd0u1C2MehO26rh3C_Vp4E5Ecxs35aWYQ/edit?gid=1878554884#gid=1878554884)
- [ER Diagram](https://www.erdcloud.com/d/aYzPpjNDSjGp6B2Tn)
- [회고](https://changeable-kitchen-bd9.notion.site/1c4b4c7812e3809ba97cefedca29b342?pvs=4)  