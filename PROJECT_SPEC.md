# 프로젝트 명세서: VibeApp

## 1. 프로젝트 개요
- **설명:** 최소 기능 스프링부트 애플리케이션을 생성하는 프로젝트입니다.
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
- `spring-boot-starter-thymeleaf`: 서버 사이드 렌더링(SSR)을 위한 뷰 템플릿 엔진
- `spring-boot-starter-web`: REST API 및 웹 서블릿 지원
- `spring-boot-starter`: 기본 스프링부트 스타터
- `spring-boot-starter-test`: 테스트 자동화를 위한 라이브러리

## 5. 프론트엔드 도구
- **CSS 프레임워크:**
  - `Tailwind CSS` (CDN 방식): 메인 랜딩 페이지 UI 담당
  - `Bootstrap 5` (CDN 방식): 범용 UI 컴포넌트 및 기본 유틸리티 담당
- **폰트:** `Inter`, `Nanum Gothic` (Google Fonts)

## 6. API 및 컨트롤러 명세
### Home (Web)
- **컨트롤러:** `HomeController`
- **엔드포인트:** `/`
- **뷰 템플릿:** `home.html`
- **기능:** "Hello, Vibe!" 메시지 표시 (Bootstrap 5 적용)

### Landing (Web)
- **엔드포인트:** `/landing.html` (정적 리소스 또는 향후 컨트롤러 매핑)
- **뷰 템플릿:** `landing.html`
- **기능:** 바이브코딩 메인 브랜드 랜딩 페이지 (Tailwind CSS 적용)

### Hello (API)
- **컨트롤러:** `VibeApp`
- **엔드포인트:** `/api/hello`
- **HTTP 메서드:** GET
- **기능:** "Hello, Vibe!" 문자열 반환

### Posts (Web)
- **컨트롤러:** `PostController`
- **엔드포인트:** `/posts`
- **뷰 템플릿:** `posts.html`
- **기능:** 게시글 목록 조회 (번호, 제목, 생성일, 조회수 표시)
- **데이터 관리:** `PostService` (ArrayList 기반 메모리 저장소)

## 6. 개발 표준
- **설정 관리:** 모든 설정은 `src/main/resources/application.yml` 파일에서 관리합니다.
- **포트 설정:** 기본 포트는 `8080`을 사용합니다.
