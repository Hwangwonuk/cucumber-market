# cucumber-market
목적
웹 UI는 카카오 오븐으로 대체하여 프론트엔드 부분은 생략하고 벡엔드에 초점을 맞춰 백엔드 개발에 주력
단순 기능 구현 뿐 아니라 코드의 재사용성 및 유지보수성 등을 고려하여 구현하는 것을 목표로 개발
ex) 당근마켓 같은 중고거래 사이트

사용 기술
Java8, InteliJ, Spring Boot, Lombok, Gradle, Mybatis, MysSQL, Redis, Springfox

UI
https://ovenapp.io/view/nlfjeRbawILO48ugsXrRsyt6UI2Bdusk/
예시) 

아키텍처
mysql 아키텍처 링크
Redis 아키텍처 링크
패키지 구조 아키텍처 링크(그림판)

전체 그림(만들어낼거)

ERD
자세한 사항 링크 -> Used RDBMS MySQL, 왜 이렇게 구성했는가 남겨놓기
이미지

주요 기능
각 기능별 비지니스 로직 링크 -> 
각 기능별 Use Case 링크 ->

사용자
회원가입 및 로그인 -> 아이디 중복 체크, 비밀번호 암호화, 회원정보 수정, 회원탈퇴
//물품 카테고리에 따른 검색 기능 -> 대분류, 소분류
글제목, 작성자 아이디에 따른 검색 기능
검색된 물품 정렬 기능 -> 최신순, 
판매물품 등록
글 작성자와 댓글 작성자만 볼수있는 댓글 기능

관리자

프로젝트를 진행하며 겪은 Issue

API 문서화 및 테스트를 위한 프레임워크인 Springfox에서
문서화 타입으로 Open API 3.0 사용시
Spring Boot 2.6.0 버전에서 아래와 같은 이슈가 발생하며 서버 구동이 안됨 -> NullPointException 발생
참고링크 https://github.com/springfox/springfox/issues/3791
따라서 2.5.7으로 다운그레이드 해서 임시 해결

기술적인 집중 요소
객체지향의 기본 원리와 spring의 IOC/DI , AOP 활용과 의미 있는 코드 작성
라이브러리 및 기능 추가 시 이유있는 선택과 사용 목적 고려
프로퍼티 파일을 이용한 외부 설정 주입과 운영 환경에 따른 프로퍼티 파일 분리
