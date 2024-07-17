package org.credo.labs.coindemo.price.service;

import java.util.List;
import java.util.Locale;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.credo.labs.coindemo.coin_desk.vo.BpiCurrencyVO;
import org.credo.labs.coindemo.core.exception.ApiException;
import org.credo.labs.coindemo.core.exception.CoreErrorCodes;
import org.credo.labs.coindemo.entity.CoinPrices;
import org.credo.labs.coindemo.price.enums.CurrencyCode;
import org.credo.labs.coindemo.repository.CoinPriceRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * Process CRUD actions for Coin Price.
 */
@Service
@Slf4j
public class CoinPriceServiceImpl implements CoinPriceService {
    final CoinPriceRepository repository;
    final MessageSource messageSource;

    public CoinPriceServiceImpl(CoinPriceRepository repository, MessageSource messageSource) {
        this.repository = repository;
        this.messageSource = messageSource;
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

        CoinPrices result = repository.saveAndFlush(coinPrices);
        return convertCodeToI18n(result);
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

        CoinPrices result = repository.saveAndFlush(coinPrices);
        return convertCodeToI18n(result);
    }

    /**
     * Get coin price by id.
     *
     * @param id Long
     * @return CoinPrices
     */
    @Override
    public CoinPrices getCoinPriceById(Long id) {
        CoinPrices coinPrices = repository.findById(id).orElse(null);
        return convertCodeToI18n(coinPrices);

    }

    /**
     * Get coin price by code.
     *
     * @param code String
     * @return CoinPrices
     */
    @Override
    public CoinPrices getCoinPriceByCode(CurrencyCode code) {
        CoinPrices coinPrices = repository.findByCode(code).orElse(null);
        return convertCodeToI18n(coinPrices);
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
        List<CoinPrices> list = repository.findAll();
        list.forEach(coinPrices -> convertCodeToI18n(coinPrices));
        return list;
    }

    /**
     * Convert code to i18n.
     *
     * @return
     */
    private CoinPrices convertCodeToI18n(CoinPrices coinPrices) {
        if (null == coinPrices) return null;

        Locale locale = LocaleContextHolder.getLocale();

        String codeName = messageSource.getMessage(coinPrices.getCode().name(), null, locale);
        coinPrices.setCodeName(codeName);

        return coinPrices;
    }

}
