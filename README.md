# 여행 체험 프로젝트

이 프로젝트는 Spring Boot, Spring Security, OAuth2-client, Thymeleaf, JPA를 사용하여 구현한 여행 체험 플랫폼입니다.

## 주요 기능

### 사용자 관리
- 회원가입
- 아이디 및 비밀번호 찾기
- OAuth 로그인 (Google, Naver, Kakao)

### 여행 관리
- 여행 등록
- 여행 예약
- 호스트 예약 관리
- 여행 리스트 페이징
- 파트너 등록 (여행 등록을 위한)

### 상호작용 기능
- 좋아요 기능
- 조회수 기능
- 이미지 업로드
- 댓글 기능

## 기술 스택

- Backend: Spring Boot, Spring Security, JPA
- Frontend: Thymeleaf
- 인증: OAuth2-client (Google, Naver, Kakao)
- 데이터베이스: MY-SQL

## 프로젝트 구조



이 프로젝트는 Spring Boot를 기반으로 한 멀티 모듈 아키텍처를 사용하여 구현되었습니다. 주요 패키지 구조와 각 컴포넌트의 역할

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── company/
│   │           └── rocally/
│   │               ├── config/          # 애플리케이션 설정
│   │               ├── controller/      # REST API 엔드포인트
│   │               ├── domain/          # JPA 엔티티
│   │               ├── repository/      # 데이터 접근 계층
│   │               ├── service/         # 비즈니스 로직
│   │               └── common/            # 공통 클래스
│   └── resources/
│       ├── static/                      # 정적 리소스 (CSS, JS 등)
│       └── templates/                   # Thymeleaf 템플릿
└── test/                                # 테스트 코드
```

### 주요 컴포넌트 설명

1. Controller Layer:
   - REST API 엔드포인트를 정의합니다.
   - 요청을 받아 적절한 서비스 메소드를 호출합니다.
   - 예: `UserController`, `TravelController`

2. Service Layer:
   - 비즈니스 로직을 구현합니다.
   - 트랜잭션 관리를 담당합니다.
   - 예: `UserService`, `TravelService`

3. Repository Layer:
   - JPA를 사용하여 데이터베이스와의 상호작용을 관리합니다.
   - 예: `UserRepository`, `TravelRepository`

4. Entity:
   - 데이터베이스 테이블과 매핑되는 JPA 엔티티 클래스입니다.
   - 예: `Partner`, `User`, `Rating`

5. DTO (Data Transfer Object):
   - API 요청 및 응답에 사용되는 객체입니다.
   - 엔티티와 별도로 관리하여 API 스펙 변경의 유연성을 확보합니다.

6. Security:
   - Spring Security를 사용한 인증 및 인가 로직을 구현합니다.
   - OAuth2 클라이언트 설정을 포함합니다.

### 아키텍처 특징

- **계층 구조**: 프리젠테이션 계층 (Controller) → 비즈니스 계층 (Service) → 데이터 접근 계층 (Repository)의 구조를 따릅니다.
- **의존성 주입**: Spring의 IoC 컨테이너를 활용하여 컴포넌트 간 느슨한 결합을 유지합니다.
- **RESTful API**: HTTP 메소드를 적절히 활용하여 리소스 중심의 API를 설계했습니다.
- **JPA & Hibernate**: 객체-관계 매핑(ORM)을 통해 데이터베이스 작업을 추상화했습니다.
- **보안**: Spring Security와 OAuth2를 결합하여 강력한 인증 및 인가 메커니즘을 구현했습니다.

이 구조를 통해 관심사의 분리, 코드의 재사용성, 그리고 유지보수의 용이성을 확보했습니다.


## 설치 및 실행 방법

### 필요 환경
- Java 11 이상
- MySQL 5.7 이상
- Gradle 6.0 이상 또는 Maven 3.6 이상

### 프로젝트 클론
```
git clone https://github.com/nus0503/rocally.git
cd rocally
```

### 데이터베이스 설정
1. MySQL에서 새 데이터베이스 생성:
   ```sql
   CREATE DATABASE travel_experience;
   ```
2. `src/main/resources/application.properties` 파일에서 데이터베이스 연결 정보 수정:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/rocally
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
* JPA를 사용한다면 properties설정에 따라 자동생성

### OAuth 설정
1. Google, Naver, Kakao 개발자 콘솔에서 OAuth 클라이언트 ID와 시크릿 발급
2. `application.properties` 파일에 OAuth 정보 추가:
   ```
   #naver registration
   spring.security.oauth2.client.registration.naver.client-id=xxxx
   spring.security.oauth2.client.registration.naver.client-secret=xxxxx
   spring.security.oauth2.client.registration.naver.redirect-uri= //설정한 리다이렉트 url
   spring.security.oauth2.client.registration.naver.authorization_grant_type=authorization_code
   spring.security.oauth2.client.registration.naver.scope=name,email,profile_image
   spring.security.oauth2.client.registration.naver.client-name=Naver
   

   #naver provider
   spring.security.oauth2.client.provider.naver.authorization_uri=https://nid.naver.com/oauth2.0/authorize
   spring.security.oauth2.client.provider.naver.token_uri=https://nid.naver.com/oauth2.0/token
   spring.security.oauth2.client.provider.naver.user-info-uri=https://openapi.naver.com/v1/nid/me
   spring.security.oauth2.client.provider.naver.user_name_attribute=response(OAuth제공자마다 다름)
   # Naver와 Kakao도 유사하게 설정
   ```

### 빌드 및 실행
Gradle 사용 시:
```
./gradlew build
java -jar build/libs/rocally-0.0.1-SNAPSHOT.jar
```

Maven 사용 시:
```
./mvnw package
java -jar target/rocally-0.0.1-SNAPSHOT.jar
```

### 애플리케이션 접속
브라우저에서 http://localhost:8080 접속

### 트러블슈팅
- 데이터베이스 연결 오류 시: MySQL 서비스가 실행 중인지, 그리고 `application.properties`의 연결 정보가 정확한지 확인하세요.
- OAuth 로그인 실패 시: 리다이렉트 URI가 각 OAuth 제공자의 개발자 콘솔에 올바르게 설정되었는지 확인하세요.


## API 문서

### 주요 API 엔드포인트

1. 사용자 관리

   - POST /api/users/signup: 새 사용자 등록

   - POST /api/users/login: 사용자 로그인

2. 여행 관리

   - GET /api/travels: 여행 목록 조회

   - POST /api/travels: 새 여행 등록

   - GET /api/travels/{id}: 특정 여행 상세 정보 조회

3. 예약 관리

   - POST /api/bookings: 새 예약 생성

   - GET /api/bookings/{id}: 예약 상세 정보 조회

   - PUT /api/bookings/{id}: 예약 정보 수정

   - DELETE /api/bookings/{id}: 예약 취소

4. 리뷰 및 평가

   - POST /api/reviews: 새 리뷰 작성

   - GET /api/travels/{id}/reviews: 특정 여행의 리뷰 목록 조회

### 인증

- 대부분의 API는 권한이 필요합니다.

- 권한은 PARTNER와 USER가 있으며, PARTNER 관련 API 외의 대부분의 API는 회원가입 시 USER권한이 생겨 로그인 시 API접근이 가능합니다.

### 에러 처리

- 모든 API는 일관된 에러 응답 형식을 따릅니다:

  ```json

  {

    "code": "에러 코드",

    "message": "에러 메시지"

  }

  ```

## 스크린샷


## 향후 계획



## 연락처

nus0503@gmail.com
