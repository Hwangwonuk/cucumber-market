# :file_folder: cucumber-market
- 당근마켓, 번개장터와 같은 **중고상품 거래 플랫폼** 오이마켓을 개발하는 토이프로젝트     
- 웹 UI는 카카오 오븐으로 대체하여 프론트엔드 부분은 생략하고 **백엔드에 초점**을 맞춰 개발에 집중

## :pushpin: 개발중점   
- 단순 기능 구현 뿐 아니라 코드의 **재사용성** 및 **유지보수성**을 고려하여 구현하는 것을 목표로 개발   
- 현재까지 학습한 서버사이드 렌더링 기술이 아닌 실제 **프론트와의 협업**을 고려하여 개발   
- **확장성**을 위한 **객체지향**의 기본 원리 **DIP, OCP** 준수  
- Spring framework의 **IOC/DI , AOP**의 활용
- 라이브러리 및 기능 추가 시 이유있는 선택과 **사용 목적** 고려
- key-value 형태로 구성된 **JSON**으로 모든 데이터 통신 
- Runtime Exception **예외처리** 선정과 그 종류에 따른 **Http Status** 응답 고려
- **대용량 트래픽**을 고려한 로그인 기능 구현
- 리소스를 고려한 의미있는 도메인 설계 - **Restful API**
- **부하 분산 및 장애대응**을 위한 MySQL 쿼리 요청 분기(Replication) 및 Redis **Master - Slave** 환경구축
- **다중서버 환경** 구축 및 그에 따른 **CI/CD** 구축

## :wrench: 사용 기술

| Category | Content |
| --- | --- |
| **Language** | Java 11 |
| **Framework** | Spring Boot 2.5.7 |
| **RDBMS** | MySQL 8.0.x |
| **SQL Mapper** | Mybatis |
| **Session Server** | Redis |
| **CI/CD** | Jenkins |
| **Container** | Docker |
| **Build Tool** | Gradle 7.3  |
| **API Documentation** | Springfox OpenAPI 3.0  |
| **Boilerplate Code Library** | Lombok |
| **IDE** | IntelliJ IDEA  |

## :tv: Web Application UI
:point_right: [**cucumber-market ProtoType UI 카카오 오븐 링크**](https://ovenapp.io/view/nlfjeRbawILO48ugsXrRsyt6UI2Bdusk/)  
   
**:point_down: 미리보기**  
<img src = "https://user-images.githubusercontent.com/86584999/145708688-c8295288-df79-48e0-a125-9b855bc4f560.PNG" width="90%" height="45%">
<img src = "https://user-images.githubusercontent.com/86584999/145706256-0811c927-a8e4-45b1-8071-cc0b61496879.PNG" width="90%" height="45%">

## :clapper: Architecture
:point_right: [**전체 아키텍처 링크**](https://github.com/Hwangwonuk/cucumber-market/wiki/Architecture)   
:point_right: [**MySQL 아키텍처 링크**](https://github.com/Hwangwonuk/cucumber-market/wiki/MySQL-Architecture)   
:point_right: [**Redis 아키텍처 링크**](https://github.com/Hwangwonuk/cucumber-market/wiki/Redis-Architecture)   
   
- **Architecture**   
![1](https://user-images.githubusercontent.com/86584999/147556575-6bf1eb37-2c69-450e-81ec-abdd34b871a2.jpg)

## :books: Layer  
<img src = "https://user-images.githubusercontent.com/86584999/145710572-9df24562-2fd7-45fe-98b7-6345a6aaac34.png">   

## :floppy_disk: ERD
:point_right: [**상세정보 보러가기**](https://github.com/Hwangwonuk/cucumber-market/wiki/ERD)   

<img src = "https://user-images.githubusercontent.com/86584999/145718047-cef57cfa-e28e-40e8-80c3-f92923320a27.png">  

## :page_with_curl: Springfox OpenAPI 3.0
**:point_down: Springfox(Swagger) 미리보기**

<img src = "https://user-images.githubusercontent.com/86584999/145809566-f0551828-60de-45cc-9fde-f41e79e3d7f2.JPG">
<img src = "https://user-images.githubusercontent.com/86584999/145809586-bcd7cdc8-4e8b-434c-9768-9260238c058d.JPG">
<img src = "https://user-images.githubusercontent.com/86584999/145809600-3383f9c6-7d95-41df-be8b-c69ca4678472.JPG">

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
* [**프로젝트 관련 학습내용** 개인블로그](https://wonuk.tistory.com/category/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%20%EA%B4%80%EB%A0%A8)
* [**MultiPartFile 바인딩 이슈** Pull Request](https://github.com/Hwangwonuk/cucumber-market/pull/19)
* [**ArgumentResolver와 Springfox 충돌이슈** Pull Request](https://github.com/Hwangwonuk/cucumber-market/pull/10)
* [**Lombok을 사용한 DTO객체 JSON 데이터 파싱  이슈** Pull Request](https://github.com/Hwangwonuk/cucumber-market/pull/6)
* [**SpringBoot와 Springfox 버전 호환 이슈** wiki](https://github.com/Hwangwonuk/cucumber-market/wiki/Spring-Boot%EC%99%80-Springfox-%EB%B2%84%EC%A0%84-%ED%98%B8%ED%99%98-%EC%9D%B4%EC%8A%88)   
* [**테스트 코드 Json Parsing 에러** Issue](https://github.com/Hwangwonuk/cucumber-market/issues/30)   
* [**테스트 코드 실행시 body가 null로 인식되는 문제** Issue](https://github.com/Hwangwonuk/cucumber-market/issues/28)   
* [**logback 라이브러리의 취약점 대응** Issue](https://github.com/Hwangwonuk/cucumber-market/issues/26)   
