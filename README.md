# :file_folder: cucumber-market
- 당근마켓, 번개장터와 같은 중고상품 거래 플랫폼 오이마켓을 개발하는 토이프로젝트     
- 웹 UI는 카카오 오븐으로 대체하여 프론트엔드 부분은 생략하고 백엔드에 초점을 맞춰 개발에 집중

## :pushpin: 개발중점   
- 단순 기능 구현 뿐 아니라 코드의 **재사용성** 및 **유지보수성**을 고려하여 구현하는 것을 목표로 개발   
- 현재까지 학습한 서버사이드 렌더링 기술이 아닌 실제 **프론트와의 협업**을 고려하여 개발   
- **확장성**을 위한 **객체지향**의 기본 원리 DIP, OCP 준수 Spring framework의 IOC/DI , AOP의 활용
- 라이브러리 및 기능 추가 시 이유있는 선택과 사용 목적 고려
- key-value 형태로 구성된 **JSON**으로 모든 데이터 통신 
- Runtime Exception **예외처리** 선정과 그 종류에 따른 **Http Status** 응답 고려

## :wrench: 사용 기술
```
Java11, IntelliJ, Spring Boot, Gradle, Lombok, Mybatis, MySQL, Redis, Springfox
```

## :tv: UI
:point_right: [**cucumber-market ProtoType UI 카카오 오븐 링크**](https://ovenapp.io/view/nlfjeRbawILO48ugsXrRsyt6UI2Bdusk/)  
   
**예시)**  
<img src = "https://user-images.githubusercontent.com/86584999/145705214-6410f8b0-ef25-4517-972d-68329e6f3b28.PNG" width="45%" height="45%">
<img src = "https://user-images.githubusercontent.com/86584999/145706031-3427ba29-a88e-4392-8dda-e1e1fa5ea1e5.PNG" width="45%" height="45%">
<img src = "https://user-images.githubusercontent.com/86584999/145706256-0811c927-a8e4-45b1-8071-cc0b61496879.PNG" width="90%" height="45%">

## 아키텍처
mysql 아키텍처 링크   
Redis 아키텍처 링크   
패키지 구조 아키텍처 링크(그림판)

## ERD
이미지
* 자세한 사항 링크 -> Used RDBMS MySQL, 왜 이렇게 구성했는가 남겨놓기

## 주요 기능
* 각 기능별 비지니스 로직 링크 -> 
* 각 기능별 Use Case 링크 ->

사용자
회원가입 및 로그인 -> 아이디 중복 체크, 비밀번호 암호화, 회원정보 수정, 회원탈퇴   
//물품 카테고리에 따른 검색 기능 -> 대분류, 소분류   
글제목, 작성자 아이디에 따른 검색 기능   
검색된 물품 정렬 기능 -> 최신순,    
판매물품 등록   
글 작성자와 댓글 작성자만 볼수있는 댓글 기능   

관리자   

## 프로젝트를 진행하며 겪은 Issue
링크

