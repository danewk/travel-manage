# 여행지(도시) 관리 API 서버 구현

### 개요
- 구현한 서버는 총 9개의 REST API를 제공함, 별도로 작성된 화면은 존재하지 않음. 
#### REST API로 개발 된 기능은 다음과 같음.
    - `GET - /api/v1/user/{userId}/cities` : 사용자별 도시 목록 조회 API
    - `POST - /api/v1/city` : 도시 등록 API
    - `POST - /api/v1/cities/{cityId}` : 도시 단건 조회 API
    - `PUT - /api/v1/cities/{cityId}` : 도시 수정 API
    - `DELETE - /api/v1/cities/{cityId}` : 도시 삭제 API
    - `POST - /api/v1/travel` : 여행 등록 API
    - `POST - /api/v1/travel/{travelId}` : 여행 수정 API
    - `GET - /api/v1/travel/{travelId}` : 여행 단건 조회 API
    - `DELETE - /api/v1/travel/{travelId}` : 여행 삭제 API
- 서비스는 8083포트로 서버를 구동한다.
    - ` http://localhost:8083`
- 본 서비스의 Swaager Api spec 아래와 같다.
  - http://localhost:8083/swagger-ui/index.html


### 기술 스택
#### Java 17, Springboot 3.0.0, Gradle
#### Framework : Spring Boot를 사용하였으며 다음 모듈을 이용한다.
    - data-jpa : DB ORM
    - h2 : 인메모리 DB (테스트 용도)
    - mysql : docker를 통해 PRD 환경에서 사용
    - validation : 필수값 검증
    - lombok : 코드 가독성
#### Junit5 사용
    - 단위테스트 : 8083 포트 사용 (application-dev.yml)
    API 검증테스트 : 8084 포트 사용 (applicaiton-test.yml)
#### 형상관리 : git
#### 환경별로 데이터 베이스 구분
    - dev, test : H2
    - prd : MySql
#### 헥사고날 아키텍처 적용
    - PersistenceAdapter는 Command와 Query로 나눠 구현 
    - Command(Create, Update, Delete)기능 담당 Adapter : *PersistenceCommandAdapter 
    - Query(Read)기능 담담 Adapter : *PersistenceQueryAdapter
    - UseCase와 Port는 기본적으로 도메인별 CRUD 기준으로 4개를 구현 
      - 조회용 UseCase class -> Get+XXX 
      - 생성용 UseCase class -> Create+XXX 
      - 업데이트 UseCase class -> Update+XXX 
      - 삭제용 UseCase class -> Delete+XXX
    - 다른 도메인 정보 호출 전략
      - 정보를 제공 받는 도메인 : adapter.out 패키지 내에 해당 도메인 패키지를 생성하고 그 안에 adapter를 구성하여 정보제공 도메인의 UseCase 구현체 가져와 사용
    - 계층별 매핑전략은 상황별로 자유롭게 선택한다.

### 로컬(dev) 실행 방법
1. 로컬 h2 구동 
2. jdbc:h2:~/travel_manage (최소 한번)
3. ~/travel_manage.mv.db 파일 생성 확인 
4. 이후 부터는 jdbc:h2:tcp://localhost/~/travel_manage.mv.db 이렇게 접속 (dev.yml, test.yml 설정완료)

### 설계 및 구현
- 테이블간의 연관관계를 설정하지 않고 진행.
- 도메인 별로 custom exception 생성 후 공통 handler에서 처리할 수 있도록 함.
- delete 정책은 물리적인 삭제가 아닌 논리적인 삭제를 하도록 함.
- ErrorMessage Enum 객체 생성 후 오류메시지 관리하도록 함.

#### 사용자별 도시 목록 조회 API
    - 사용자가 등록한 여행에 속한 도시 전체 조회 - A
    - A에서 여행중인 도시, 여행 시작일 빠른 것부터 정렬 B
    - A에서 여행이 예정된 도시, 여행시작일 가까운 것 부터 정렬 C
    - A에서 하루 이내에 등록이 된 도시, 최근 등록 순으로 정렬 D 
    - A에서 최근 일주일 이내 한 번 이상 조회된 도시, 가장 최근 조회건으로 정렬 E
    - A에 있으나 B,C,D,E에 속하지 않는 도시, 무작위 F
    - C,D,E,F에서 중복 제거 후 상위 10개 추출
    - 추출된 리스트 앞에 B 데이터리스트 삽입

#### 도시 등록 API
- 도시명, 나라명으로 기존에 같은 데이터가 존재하다면 에러 발생

#### 도시 단건 조회 API
- 도시 정보가 존재하지 않는다면 에러 발생
- 조회시 도시ID 와 사용자ID로 조회내역 관리하는 CitySearchHistory 데이터 조회
- CitySearchHistory 데이터 존재 유무 및 생성시점(7일이내 유효)으로 create/update/delete->update 조회내역 관리
- CitySearchHistory 테이블의 제약조건으로 중복 데이터 생성 방지
- 조회내역(카운트) 업데이트 시 동시성 이슈가 발생할 수 있어 JPA 메서드 비관적락 적용

#### 도시수정 API
- 도시 정보가 존재하지 않는다면 에러 발생

#### 도시 삭제 API 
- 다른 여행지에 도시가 포함되어 있다면 에러 발생

#### 여행 등록 API
- 여행도시, 나라 정보를 입력 후 등록된 도시가 아니면 오류 발생
- 여행종료일이 시작일보다 앞이면 오류 발생

#### 여행 수정 API
- 여행도시, 나라 정보를 입력 후 등록된 도시가 아니면 오류 발생
- 여행종료일이 시작일보다 앞이면 오류 발생

#### 여행 단건 조회 API
- 등록된 여행정보가 존재하지 않으면 에러 발생

#### 여행 삭제 
- 등록된 여행정보가 존재하지 않으면 에러 발생

