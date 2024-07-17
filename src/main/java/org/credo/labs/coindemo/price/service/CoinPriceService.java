package org.credo.labs.coindemo.price.service;

import java.util.List;
import javax.transaction.Transactional;
import org.credo.labs.coindemo.coin_desk.vo.BpiCurrencyVO;
import org.credo.labs.coindemo.entity.CoinPrice;
import org.credo.labs.coindemo.price.enums.CurrencyCode;

public interface CoinPriceService {

    CoinPrice create(BpiCurrencyVO vo);

    @Transactional
    void updateAll(List<CoinPrice> coinPrices);

    CoinPrice update(Long id, BpiCurrencyVO vo);

    CoinPrice getCoinPriceById(Long id);

    CoinPrice getCoinPriceByCode(CurrencyCode code);

    void deleteCoinPriceById(Long id);

    List<CoinPrice> getAllCoinPrices();
}
