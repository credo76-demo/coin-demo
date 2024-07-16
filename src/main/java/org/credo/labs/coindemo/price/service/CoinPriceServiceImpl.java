package org.credo.labs.coindemo.price.service;

import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.credo.labs.coindemo.coin_desk.vo.BpiCurrencyVO;
import org.credo.labs.coindemo.core.exception.ApiException;
import org.credo.labs.coindemo.core.exception.CoreErrorCodes;
import org.credo.labs.coindemo.entity.CoinPrices;
import org.credo.labs.coindemo.price.enums.CurrencyCode;
import org.credo.labs.coindemo.repository.CoinPriceRepository;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * Process CRUD actions for Coin Price.
 */
@Service
@Slf4j
public class CoinPriceServiceImpl implements CoinPriceService {
    final CoinPriceRepository repository;

    public CoinPriceServiceImpl(CoinPriceRepository repository) {
        this.repository = repository;
    }

    /**
     * Create coin price.
     *
     * @param vo BpiCurrencyVO
     * @return CoinPrices
     */
    @Override
    @Transactional
    public CoinPrices create(BpiCurrencyVO vo) {
        CoinPrices coinPrices = new CoinPrices();
        coinPrices.setCode(CurrencyCode.valueOf(vo.getCode()));
        coinPrices.setSymbol(vo.getSymbol());
        coinPrices.setRate(vo.getRate());
        coinPrices.setDescription(vo.getDescription());
        coinPrices.setRateFloat(vo.getRateFloat());
        return repository.saveAndFlush(coinPrices);
    }


    /**
     * Save and update coin price.
     *
     * @param vo BpiCurrencyVO
     * @return
     */
    @Override
    @Transactional
    public CoinPrices update(Long id, BpiCurrencyVO vo) {
        CoinPrices coinPrices = repository.findById(id).orElseThrow(() ->
                new ApiException(CoreErrorCodes.DATA_NOT_FOUND, Pair.of("reason", "Coin price not found")));

        coinPrices.setCode(CurrencyCode.valueOf(vo.getCode()));
        coinPrices.setSymbol(vo.getSymbol());
        coinPrices.setRate(vo.getRate());
        coinPrices.setDescription(vo.getDescription());
        coinPrices.setRateFloat(vo.getRateFloat());

        return repository.saveAndFlush(coinPrices);
    }


    /**
     * Get coin price by id.
     *
     * @param id Long
     * @return CoinPrices
     */
    @Override
    public CoinPrices getCoinPriceById(Long id) {
        return repository.findById(id).orElse(null);

    }

    /**
     * Get coin price by code.
     *
     * @param code String
     * @return CoinPrices
     */
    @Override
    public CoinPrices getCoinPriceByCode(CurrencyCode code) {
        return repository.findByCode(code).orElse(null);
    }

    /**
     * Delete coin price by id.
     *
     * @param id Long
     */
    @Override
    @Transactional
    public void deleteCoinPriceById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Get all coin prices.
     *
     * @return List<CoinPrices>
     */
    @Override
    public List<CoinPrices> getAllCoinPrices() {
        return repository.findAll();
    }

}
