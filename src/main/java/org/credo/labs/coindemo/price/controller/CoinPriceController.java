package org.credo.labs.coindemo.price.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.credo.labs.coindemo.coin_desk.vo.BpiCurrencyVO;
import org.credo.labs.coindemo.core.web.advice.ApiResponse;
import org.credo.labs.coindemo.entity.CoinPrice;
import org.credo.labs.coindemo.price.enums.CurrencyCode;
import org.credo.labs.coindemo.price.service.CoinPriceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A endpoint to handle coin price related operations.
 */
@RestController
@RequestMapping("/api/v1/price")
@Validated
@Slf4j
public class CoinPriceController {
    private final CoinPriceService coinPriceService;

    public CoinPriceController(CoinPriceService coinPriceService) {
        this.coinPriceService = coinPriceService;
    }

    /**
     * It will create a new coin price if it does not exist.
     */
    @PostMapping
    @ApiResponse
    public CoinPrice createCoinPrice(@RequestBody @Valid BpiCurrencyVO vo) {
        return coinPriceService.create(vo);
    }

    /**
     * Get coin price by id.
     */
    @GetMapping("/{id}")
    @ApiResponse
    public CoinPrice getCoinPriceById(@PathVariable Long id) {
        return coinPriceService.getCoinPriceById(id);
    }

    /**
     * Get coin price by code.
     */
    @GetMapping("/code/{code}")
    @ApiResponse
    public CoinPrice getCoinPriceByCode(@PathVariable("code") CurrencyCode code) {
        return coinPriceService.getCoinPriceByCode(code);
    }

    /**
     * Update coin price by id.
     */
    @PutMapping("/{id}")
    @ApiResponse
    public CoinPrice updateCoinPrice(@PathVariable Long id, @RequestBody @Valid BpiCurrencyVO vo) {
        return coinPriceService.update(id, vo);
    }

    /**
     * Delete coin price by id.
     */
    @DeleteMapping("/{id}")
    @ApiResponse
    public void deleteCoinPriceById(@PathVariable Long id) {
        coinPriceService.deleteCoinPriceById(id);
    }

    /**
     * Get all coin prices.
     */
    @GetMapping("/list")
    @ApiResponse
    public List<CoinPrice> getAllCoinPrices() {
        return coinPriceService.getAllCoinPrices();
    }
}
