package org.credo.labs.coindemo.coin_desk.service;

import java.util.Map;
import org.credo.labs.coindemo.coin_desk.vo.BpiCurrencyVO;
import org.credo.labs.coindemo.coin_desk.vo.CoinDeskResponseVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CoinDeskServiceTest {

    @Autowired
    CoinDeskService coinDeskService;

    /**
     * Test to fetch coin price
     */
    @Test
    @DisplayName("Fetch Coin Price")
    void fetchCoinPrice() {
        //Act
        CoinDeskResponseVO vo = coinDeskService.fetchCoinPrice();

        //Assert
        assertNotNull(vo);
        assertNotNull(vo.getTime());
        assertNotNull(vo.getDisclaimer());
        assertNotNull(vo.getChartName());

        Map<String, BpiCurrencyVO> bpiVO = vo.getBpi();
        assertNotNull(bpiVO);

        BpiCurrencyVO usdCurrency = bpiVO.get("USD");
        assertNotNull(usdCurrency);
        assertNotNull(usdCurrency.getCode());
        assertNotNull(usdCurrency.getSymbol());
        assertNotNull(usdCurrency.getRate());
        assertNotNull(usdCurrency.getDescription());
        assertNotNull(usdCurrency.getRateFloat());
    }

}