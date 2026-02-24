# 프로젝트 명세서: VibeApp

## 1. 프로젝트 개요
- **설명:** 최소 기능 스프링부트 기반 REST API 애플리케이션입니다.
- **프로젝트 명:** vibeapp
- **메인 클래스 명:** VibeApp
- **기본 패키지:** `com.example.vibeapp`

## 2. 환경 및 도구
- **언어:** Java
- **JDK 버전:** 25 이상
- **Spring Boot 버전:** 4.0.1 이상
- **빌드 도구:** Gradle 9.3.0 이상
- **빌드 스크립트 언어:** Groovy DSL
- **설정 형식:** YAML (`application.yml`)

## 3. 프로젝트 메타데이터
- **그룹(Group):** `com.example`
- **아티팩트(Artifact):** `vibeapp`
- **버전:** `0.0.1-SNAPSHOT`

## 4. 의존성 및 플러그인
### 플러그인
- `org.springframework.boot`: Spring Boot 버전에 맞춰 추가
- `io.spring.dependency-management`: Spring Boot 버전에 맞춰 추가
- `java`: 기본 Java 플러그인

### 의존성
- `spring-boot-starter-web`: REST API 및 웹 서블릿 지원
- `spring-boot-starter-data-jpa`: Spring Data JPA를 이용한 데이터 영속성 관리
- `com.h2database:h2`: 로컬 개발용 인메모리/파일 기반 DB
- `spring-boot-starter-validation`: 데이터 검증 지원
- `spring-boot-starter`: 기본 스프링부트 스타터
- `spring-boot-starter-test`: 테스트 자동화를 위한 라이브러리
- `spring-boot-devtools`: 개발 중 자동 재시작 및 실시간 변경 반영 지원

## 5. 프로젝트 구조 (기능형 구조)
- **Post:** `com.example.vibeapp.post` / 게시글 관련 API 담당

## 6. API 및 컨트롤러 명세

### Hello (API)
- **엔드포인트:** `/api/hello`
- **HTTP 메서드:** GET
- **기능:** "Hello, Vibe!" 문자열 반환

### Posts (API)
- **베이스 경로:** `/api/posts`

| 기능 | 메서드 | 엔드포인트 | 설명 |
|---|---|---|---|
| 목록 조회 | GET | `/api/posts` | 게시글 목록 조회 (page 파라미터 가능, 기본 1) |
| 상세 조회 | GET | `/api/posts/{no}` | 게시글 상세 조회 (조회수 증가 포함) |
| 게시글 등록 | POST | `/api/posts` | 새 게시글 등록 |
| 게시글 수정 | PUT | `/api/posts/{no}` | 기존 게시글 수정 |
| 게시글 삭제 | DELETE | `/api/posts/{no}` | 게시글 삭제 |

## 7. 개발 표준
- **설정 관리:** 모든 설정은 `src/main/resources/application.yml` 파일에서 관리합니다.
- **포트 설정:** 기본 포트는 `8080`을 사용합니다.
- **영속성 계층:** **Spring Data JPA (JpaRepository)**를 사용하여 데이터 접근 계층을 구현합니다.
- **응답 형식:** 모든 API는 JSON 형식으로 응답합니다.
- **명명 규칙:** 실무 관례를 준수하며, Spring Data JPA의 쿼리 메서드 명명 규칙을 따릅니다.
