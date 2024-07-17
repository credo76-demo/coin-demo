# Coin Demo

This project is a Spring Boot application for managing coin prices, including CRUD operations with a H2 database.

## Features
- CRUD operations for Coin Prices
- RESTful API endpoints with AOP exception handling, response body wrappers.
- Integration with H2 in-memory database for testing
- Validation and error handling

## Prerequisites
- Java 17 or higher
- Maven 3.6.3 or higher

## Getting Started
### 1. Table DDL
```sql
CREATE TABLE coin_prices (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    code VARCHAR(3) NOT NULL,
    symbol VARCHAR(255) NOT NULL,
    rate VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    rate_float NUMERIC(18, 4) NOT NULL,
    updated TIMESTAMP,
    created TIMESTAMP,
    UNIQUE (code),
    UNIQUE (symbol)
);

CREATE INDEX idx_code ON coin_prices (code);

CREATE INDEX idx_updated ON coin_prices (updated);
```
### 2. Content
- OpenAPI 3.0 Docs: [http://localhost:8080/index.html](http://localhost:8080/index.html)
- The main API Controller: `CoinPriceController.java`
- OpenAPI 3.0 Specification: `spec/coin_demo-openapi.yaml`
- Scheduled Task: `ScheduledTasks.java`
  
### 3. Testing
- API Testing, refer to the [CoinPriceControllerTest.java](https://github.com/credo76-demo/coin-demo/blob/main/src/test/java/org/credo/labs/coindemo/price/controller/CoinPriceControllerTest.java)
  - OkHttp3 過不了自簽憑證，請使用 `DEV` profile 測試 Spring Application。

### 4. 實作加分題
- AOP 應用 
  - a. 印出所有 API 被呼叫: `LoggingApiAspect.java`。
  - b. Error handling 處理 API response: `GlobalExceptionHandler.java`、`GlobalResponseAdvice.java`。
- 多語系設計: 簡易 `messageSource` 設定，顯示欄位於 `CoinPrice.codeName`。
- Design pattern 實作
  - Template Method Pattern in scheduling: `AbstractPriceProvider.java`、`CoinDeskProvider.java`。
  - Singleton Pattern in HTTP client: `OkHttpSingleton.java`。
  - 簡易 SSL 設定: 8443 port，使用自簽憑證 (請使用 Browser/HTTP Client 測試)。

### Clone the repository
```bash
git clone https://github.com/credo76-demo/coin-demo.git
cd coin-demo
