package org.credo.labs.coindemo.price.service;

import java.util.List;
import java.util.Locale;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.credo.labs.coindemo.coin_desk.vo.BpiCurrencyVO;
import org.credo.labs.coindemo.core.exception.ApiException;
import org.credo.labs.coindemo.core.exception.CoreErrorCodes;
import org.credo.labs.coindemo.entity.CoinPrice;
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
    public CoinPrice create(BpiCurrencyVO vo) {
        CoinPrice coinPrice = new CoinPrice();
        coinPrice.setCode(CurrencyCode.valueOf(vo.getCode()));
        coinPrice.setSymbol(vo.getSymbol());
        coinPrice.setRate(vo.getRate());
        coinPrice.setDescription(vo.getDescription());
        coinPrice.setRateFloat(vo.getRateFloat());

        CoinPrice result = repository.saveAndFlush(coinPrice);
        return convertCodeToI18n(result);
    }

    @Override
    @Transactional
    public void updateAll(List<CoinPrice> coinPrices) {
        for (CoinPrice coinPrice : coinPrices) {
            repository.upsertByCode(
                    coinPrice.getCode().name(),
                    coinPrice.getSymbol(),
                    coinPrice.getRate(),
                    coinPrice.getDescription(),
                    coinPrice.getRateFloat()
            );
        }
    }

    /**
     * Save and update coin price.
     *
     * @param vo BpiCurrencyVO
     * @return
     */
    @Override
    @Transactional
    public CoinPrice update(Long id, BpiCurrencyVO vo) {
        CoinPrice coinPrice = repository.findById(id).orElseThrow(() ->
                new ApiException(CoreErrorCodes.DATA_NOT_FOUND, Pair.of("reason", "Coin price not found")));

        coinPrice.setCode(CurrencyCode.valueOf(vo.getCode()));
        coinPrice.setSymbol(vo.getSymbol());
        coinPrice.setRate(vo.getRate());
        coinPrice.setDescription(vo.getDescription());
        coinPrice.setRateFloat(vo.getRateFloat());

        CoinPrice result = repository.saveAndFlush(coinPrice);
        return convertCodeToI18n(result);
    }

    /**
     * Get coin price by id.
     *
     * @param id Long
     * @return CoinPrices
     */
    @Override
    public CoinPrice getCoinPriceById(Long id) {
        CoinPrice coinPrice = repository.findById(id).orElse(null);
        return convertCodeToI18n(coinPrice);

    }

    /**
     * Get coin price by code.
     *
     * @param code String
     * @return CoinPrices
     */
    @Override
    public CoinPrice getCoinPriceByCode(CurrencyCode code) {
        CoinPrice coinPrice = repository.findByCode(code).orElse(null);
        return convertCodeToI18n(coinPrice);
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
    public List<CoinPrice> getAllCoinPrices() {
        List<CoinPrice> list = repository.findAllByOrderByCodeAsc();
        list.forEach(coinPrices -> convertCodeToI18n(coinPrices));
        return list;
    }

    /**
     * Convert code to i18n.
     *
     * @return
     */
    private CoinPrice convertCodeToI18n(CoinPrice coinPrice) {
        if (null == coinPrice) return null;

        Locale locale = LocaleContextHolder.getLocale();

        String codeName = messageSource.getMessage(coinPrice.getCode().name(), null, locale);
        coinPrice.setDescription(codeName);

        return coinPrice;
    }

}
