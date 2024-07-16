package org.credo.labs.coindemo.util;

import java.math.BigDecimal;
import org.credo.labs.coindemo.coin_desk.vo.BpiCurrencyVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BigDecimalDeserializerTest {

    @Test
    void testDeserialize() {
        //Act
        String json = "{\n" +
                "  \"code\": \"USD\",\n" +
                "  \"symbol\": \"&#36;\",\n" +
                "  \"rate\": \"62,583.52\",\n" +
                "  \"description\": \"United States Dollar\",\n" +
                "  \"rate_float\": 62583.5202\n" +
                "}";

        BpiCurrencyVO vo = JsonUtils.asObject(json, BpiCurrencyVO.class);

        //Assert
        assertNotNull(vo);

        BigDecimal rateFloat = vo.getRateFloat();
        assertNotNull(rateFloat);

    }
}