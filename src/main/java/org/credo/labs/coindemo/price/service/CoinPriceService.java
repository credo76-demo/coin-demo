package org.credo.labs.coindemo.price.service;

import java.util.List;
import javax.transaction.Transactional;
import org.credo.labs.coindemo.coin_desk.vo.BpiCurrencyVO;
import org.credo.labs.coindemo.entity.CoinPrice;
import org.credo.labs.coindemo.price.enums.CurrencyCode;

public interface CoinPriceService {

    @Transactional
    CoinPrice create(BpiCurrencyVO vo);

    @Transactional
    CoinPrice update(Long id, BpiCurrencyVO vo);

    CoinPrice getCoinPriceById(Long id);

    CoinPrice getCoinPriceByCode(CurrencyCode code);

    @Transactional
    void deleteCoinPriceById(Long id);

    List<CoinPrice> getAllCoinPrices();
}
