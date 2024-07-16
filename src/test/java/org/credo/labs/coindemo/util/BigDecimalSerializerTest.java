package org.credo.labs.coindemo.util;

import java.math.BigDecimal;
import org.credo.labs.coindemo.coin_desk.vo.BpiCurrencyVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BigDecimalSerializerTest {

    @Test
    void testSerialize() {
        //Arrange
        BpiCurrencyVO vo = new BpiCurrencyVO();
        vo.setRateFloat(new BigDecimal("62583.5202"));
        assertEquals("62583.5202", vo.getRateFloat().toString());

        //Act
        String json = JsonUtils.writeString(vo);

        //Assert
        assertTrue( json.contains("rate_float\":62583.5202") );
    }
}