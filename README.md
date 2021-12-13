# :file_folder: cucumber-market
- 당근마켓, 번개장터와 같은 **중고상품 거래 플랫폼** 오이마켓을 개발하는 토이프로젝트     
- 웹 UI는 카카오 오븐으로 대체하여 프론트엔드 부분은 생략하고 **백엔드에 초점**을 맞춰 개발에 집중

## :pushpin: 개발중점   
- 단순 기능 구현 뿐 아니라 코드의 **재사용성** 및 **유지보수성**을 고려하여 구현하는 것을 목표로 개발   
- 현재까지 학습한 서버사이드 렌더링 기술이 아닌 실제 **프론트와의 협업**을 고려하여 개발   
- **확장성**을 위한 **객체지향**의 기본 원리 DIP, OCP 준수 Spring framework의 IOC/DI , AOP의 활용
- 라이브러리 및 기능 추가 시 이유있는 선택과 사용 목적 고려
- key-value 형태로 구성된 **JSON**으로 모든 데이터 통신 
- Runtime Exception **예외처리** 선정과 그 종류에 따른 **Http Status** 응답 고려
- **대용량 트래픽**을 고려한 로그인 기능 구현 추가
- 리소스를 고려한 의미있는 **도메인 설계**
- **부하 분산 및 장애대응**을 위한 MySQL 쿼리 요청 분기(Replication) 및 Redis **Master - Slave** 환경구축

## :wrench: 사용 기술
```
Java11, IntelliJ, Spring Boot, Gradle, Lombok, Mybatis, MySQL, Redis, Springfox
```

## :tv: Web Application UI
:point_right: [**cucumber-market ProtoType UI 카카오 오븐 링크**](https://ovenapp.io/view/nlfjeRbawILO48ugsXrRsyt6UI2Bdusk/)  
   
**:point_down: 미리보기**  
<img src = "https://user-images.githubusercontent.com/86584999/145708688-c8295288-df79-48e0-a125-9b855bc4f560.PNG" width="90%" height="45%">
<img src = "https://user-images.githubusercontent.com/86584999/145706256-0811c927-a8e4-45b1-8071-cc0b61496879.PNG" width="90%" height="45%">

## :clapper: Architecture
:point_right: [**MySQL 아키텍처 링크**](https://github.com/Hwangwonuk/cucumber-market/wiki/MySQL-Architecture)   
:point_right: [**Redis 아키텍처 링크**](https://github.com/Hwangwonuk/cucumber-market/wiki/Redis-Architecture)   
   
- **Architecture**   
<img src = "https://user-images.githubusercontent.com/86584999/145709767-3827528f-1c4a-4bb2-85bc-de9ac8922804.jpg">   
   
   
## :books: Layer  
<img src = "https://user-images.githubusercontent.com/86584999/145710572-9df24562-2fd7-45fe-98b7-6345a6aaac34.png">   

## :floppy_disk: ERD
:point_right: [**상세정보 보러가기**](https://github.com/Hwangwonuk/cucumber-market/wiki/ERD)   

<img src = "https://user-images.githubusercontent.com/86584999/145718047-cef57cfa-e28e-40e8-80c3-f92923320a27.png">  

## :computer: 주요 기능
:point_right: [**각 기능별 비지니스 로직**](https://github.com/Hwangwonuk/cucumber-market/wiki/Business-Rule)   
:point_right: [**각 기능별 Use Case**](https://github.com/Hwangwonuk/cucumber-market/wiki/Use-Case)   
   
:busts_in_silhouette: **사용자**   
- 회원가입, 탈퇴   
- 로그인, 로그아웃   
- 마이페이지, 정보수정   
- 판매물품 등록, 수정, 삭제   
- 물품 검색기능   
- 상품찜 기능   
- 비밀 댓글로 1:1채팅기능   

:guardsman: **관리자**   
- 전체회원 조회   
- 기존회원 관리자 등록   
- 카테고리 관리   

## :warning: 프로젝트를 진행하며 겪은 Issue 및 학습한 내용
링크

