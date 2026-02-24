-- Post 초기 데이터 (MERGE INTO: 중복 실행해도 데이터 중복 삽입 없음)
MERGE INTO post (no, title, content, created_at, updated_at, views) KEY(no) VALUES
    (1, 'Spring Boot와 H2 데이터베이스 시작하기', 'Spring Boot에서 H2 인메모리 데이터베이스를 사용하는 방법에 대해 알아봅니다. H2는 개발 및 테스트 환경에서 매우 유용하게 사용됩니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 15),
    (2, 'MyBatis로 SQL 매핑하기', 'MyBatis는 Java 객체와 SQL 구문을 자동으로 매핑해주는 프레임워크입니다. XML 기반의 매퍼 파일을 이용하면 복잡한 쿼리도 쉽게 관리할 수 있습니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 28),
    (3, 'Spring MVC 컨트롤러 설계 패턴', 'Spring MVC에서 RESTful API를 설계하는 다양한 패턴들을 살펴봅니다. 컨트롤러, 서비스, 레포지토리 레이어로 분리하는 방법을 예제와 함께 소개합니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 42),
    (4, 'Java Record를 DTO로 활용하기', 'Java 16에서 정식 도입된 Record 타입은 DTO(Data Transfer Object) 패턴에 매우 적합합니다. 불변성과 간결한 코드 작성 방법을 알아봅니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 31),
    (5, 'Thymeleaf 템플릿 엔진 심화 가이드', 'Spring Boot와 Thymeleaf를 함께 사용하는 방법을 자세히 설명합니다. 레이아웃, 조각(fragments), 조건부 렌더링 등 다양한 기능을 다룹니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 9);

-- IDENTITY 시퀀스를 max(no)+1로 리셋 (신규 INSERT 시 PK 충돌 방지)
ALTER TABLE post ALTER COLUMN no RESTART WITH 6;
