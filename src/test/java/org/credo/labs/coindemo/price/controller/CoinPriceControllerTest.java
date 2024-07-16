package org.credo.labs.coindemo.price.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.credo.labs.coindemo.core.client.OkHttpSingleton;
import org.credo.labs.coindemo.core.version.model.Version;
import org.credo.labs.coindemo.core.web.util.RestfulApiResponse;
import org.credo.labs.coindemo.entity.CoinPrices;
import org.credo.labs.coindemo.util.JsonUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class CoinPriceControllerTest {
    private final OkHttpClient client = OkHttpSingleton.getInstance();
    private final String baseUrl = "http://localhost:8080/coin-api/v1/price";

    /**
     * Create a sample entry before each test
     */
    @BeforeEach
    void setUp() throws IOException {
        // Create a sample entry before each test
        String json = "{\n" +
                "  \"code\": \"JPY\",\n" +
                "  \"symbol\": \"&haha\",\n" +
                "  \"rate\": \"12345.1234\",\n" +
                "  \"description\": \"lorem ipsum\",\n" +
                "  \"rate_float\": 22222.2222\n" +
                "}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder().url(baseUrl).post(body).build();
        client.newCall(request).execute().close();

    }

    @AfterEach
    void tearDown() throws IOException {
        CoinPrices coinPrices = testFindByCode();
        if (null != coinPrices) {
            testDeleteById(coinPrices);
        }
    }

    @Test
    @Order(1)
    void testCreateCoinPrice() throws IOException {
        CoinPrices coinPrices = testFindByCode();
        assertNotNull(coinPrices);
        assertEquals("JPY", coinPrices.getCode().name());
        assertEquals("&haha", coinPrices.getSymbol());
        assertEquals("12345.1234", coinPrices.getRate());
        assertEquals("lorem ipsum", coinPrices.getDescription());
        assertEquals("22222.2222", coinPrices.getRateFloat().toString());
        assertNotNull(coinPrices.getCreated());
        assertNotNull(coinPrices.getUpdated());
    }

    @Test
    void testHealthCheck() throws IOException {
        Request request = new Request.Builder().url("http://localhost:8080/coin-demo/version").get().build();
        try (Response response = client.newCall(request).execute()) {
            assertEquals(HttpStatus.OK.value(), response.code());
            assertNotNull(response.body());

            Version version = JsonUtils.asObject(response.body().string(), Version.class);
            assertNotNull(version);
            assertNotNull(version.getReleaseTime());
            assertNotNull(version.getReleaseVersion());
        }
    }

    @Test
    void testGetAllCoinPrices() throws IOException {
        Request request = new Request.Builder().url(baseUrl + "/list").get().build();
        try (Response response = client.newCall(request).execute()) {
            assertEquals(HttpStatus.OK.value(), response.code());
            assertNotNull(response.body());

            RestfulApiResponse<List<CoinPrices>> restfulApiResponse = JsonUtils.asObject(response.body().string(), new TypeReference<RestfulApiResponse<List<CoinPrices>>>() {
            });
            assertEquals("0", restfulApiResponse.getCode());
            assertFalse(restfulApiResponse.getData().isEmpty());
        }
    }

    @Test
    void testFindById() throws IOException {
        Request request = new Request.Builder().url(baseUrl + "/code/JPY").get().build();
        try (Response response = client.newCall(request).execute()) {
            assertEquals(HttpStatus.OK.value(), response.code());
            assertNotNull(response.body());

            RestfulApiResponse<CoinPrices> restfulApiResponse = JsonUtils.asObject(response.body().string(), new TypeReference<RestfulApiResponse<CoinPrices>>() {
            });
            assertEquals("0", restfulApiResponse.getCode());
            assertNotNull(restfulApiResponse.getData());
        }
    }

    private CoinPrices testFindByCode() throws IOException {
        Request request = new Request.Builder().url(baseUrl + "/code/JPY").get().build();
        try (Response response = client.newCall(request).execute()) {
            assertEquals(HttpStatus.OK.value(), response.code());
            assertNotNull(response.body());

            RestfulApiResponse<CoinPrices> restfulApiResponse = JsonUtils.asObject(response.body().string(), new TypeReference<RestfulApiResponse<CoinPrices>>() {
            });
            assertEquals("0", restfulApiResponse.getCode());

            return restfulApiResponse.getData();
        }
    }

    /**
     * Delete coin price by ID
     */
    private void testDeleteById(CoinPrices coinPrices) throws IOException {
        Request request = new Request.Builder().url(baseUrl + "/" + coinPrices.getId()).delete().build();
        try (Response response = client.newCall(request).execute()) {
            assertEquals(HttpStatus.OK.value(), response.code());
            assertNotNull(response.body());

            RestfulApiResponse<CoinPrices> restfulApiResponse = JsonUtils.asObject(response.body().string(), new TypeReference<RestfulApiResponse<CoinPrices>>() {
            });
            assertEquals("0", restfulApiResponse.getCode());
        }
    }
}