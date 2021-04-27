[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/DakOoon/spring-rest-t1)

# 아키텍처
    Client - WebServer:APACHE (Load balancing) - WebApplicationServer:TOMCAT - DB:MYSQL 
                                               - WebApplicationServer:TOMCAT -
                                               - ... -
# API 명세
- 전체 투자 상품 조회
    URI: GET IP:PORT/api/investment/products
    요청
        현재시간
    응답
        상품ID, 상품제목, 총모집금액, 현재모집금액, 투자자수, 투자모집상태 (모집중, 모집 완료), 상품모집기간
- 투자하기
    URI: POST IP:PORT/api/investment/my/investments
    요청
        사용자식별값, 상품ID, 투자금액
    응답
        성공
        총투자모집금액 (total_investing_amount) 을 넘어서면 sold-out
- 나의 투자 상품 조회
    URI: GET IP:PORT/api/investment/my/investments
    요청
        사용자식별값
    응답
        상품ID, 상품제목, 총모집금액, 나의투자금액, 투자일시

# 테이블 명세
- 사용자 (USER)
    사용자식별값 (user_id): PK

- 상품 (PRODUCT)
    상품ID (product_id): PK
    상품제목 (title)
    총모집금액 (total_investing_amount)
    현재모집금액 (current_investing_amount)
    투자자수 (investor_count)
    투자모집상태 (product_state)
    투자시작일시 (started_at)
    투자종료일시 (finished_at)

- 투자 (INVESTMENT)
    투자ID (investment_id) PK
    사용자식별값 (user_id): FK
    상품ID (product_id): FK
    투자금액 (investing_amount)
    투자일시 (invested_at)
