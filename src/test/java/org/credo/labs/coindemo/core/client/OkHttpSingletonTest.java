package org.credo.labs.coindemo.core.client;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import org.credo.labs.coindemo.coin_desk.vo.CoinDeskResponseVO;
import org.credo.labs.coindemo.util.JsonUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class OkHttpSingletonTest {

    @Test
    public void testGetInstance() throws IOException {
        // Arrange
        String target = "https://api.coindesk.com/v1/bpi/currentprice.json";

        // Act
        Call call = OkHttpSingleton.getInstance().newCall(new Request.Builder().url(target).build());
        Response response = call.execute();

        // Assert
        try (response) {
            // Assert that the response is successful
            assertTrue(response.isSuccessful());
            assertEquals(200, response.code());
            assertNotNull(response.body());

            String json = response.body().string();
            assertNotNull(json);

            CoinDeskResponseVO vo = JsonUtils.asObject(json, CoinDeskResponseVO.class);
            assertNotNull(vo);
            assertNotNull(vo.getBpi());
            assertFalse(vo.getBpi().isEmpty());
        }
    }

}
