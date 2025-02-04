package org.credo.labs.coindemo.repository;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.credo.labs.coindemo.coin_desk.vo.BpiCurrencyVO;
import org.credo.labs.coindemo.entity.CoinPrice;
import org.credo.labs.coindemo.price.enums.CurrencyCode;
import org.credo.labs.coindemo.util.JsonUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class CoinPriceRepositoryTest {

    @Autowired
    private CoinPriceRepository coinPriceRepository;

    private CoinPrice entity;

    @Test
    @Order(1)
    @Transactional
    @Rollback(value = false)
    void testCreate() {
        String json = "{\n" +
                "  \"code\": \"USD\",\n" +
                "  \"symbol\": \"&#36;\",\n" +
                "  \"rate\": \"63,489.472\",\n" +
                "  \"description\": \"United States Dollar\",\n" +
                "  \"rate_float\": 63489.4719\n" +
                "}";

        BpiCurrencyVO vo = JsonUtils.asObject(json, BpiCurrencyVO.class);
        //convert to CoinPrices
        entity = new CoinPrice();
        entity.setCode(CurrencyCode.valueOf(vo.getCode()));
        entity.setSymbol(vo.getSymbol());
        entity.setRate(vo.getRate());
        entity.setDescription(vo.getDescription());
        entity.setRateFloat(vo.getRateFloat());

        this.entity = coinPriceRepository.save(entity);
        coinPriceRepository.flush();
        assertNotNull(this.entity.getId());

        List<CoinPrice> list = coinPriceRepository.findAll();
        assertFalse(list.isEmpty());
    }

    @Test
    @Transactional
    @Order(2)
    void testRead() {
        Optional<CoinPrice> foundCoinPrices = coinPriceRepository.findById(1L);
        assertTrue(foundCoinPrices.isPresent());
        assertEquals(1L, foundCoinPrices.get().getId());
    }

    @Test
    @Order(3)
    @Transactional
    void testUpdate() {
        this.entity = coinPriceRepository.getReferenceById(1L);
        assertNotNull(this.entity);

        this.entity.setSymbol("New Value");
        CoinPrice updatedCoinPrice = coinPriceRepository.saveAndFlush(entity);
        assertEquals("New Value", updatedCoinPrice.getSymbol());
    }

    @Test
    @Order(4)
    @Transactional
    void testDelete() {
        coinPriceRepository.deleteById(1L);
        Optional<CoinPrice> deletedCoinPrices = coinPriceRepository.findById(1L);
        assertFalse(deletedCoinPrices.isPresent());
    }
}