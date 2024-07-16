package org.credo.labs.coindemo.price.service;

import java.util.List;
import javax.transaction.Transactional;
import org.credo.labs.coindemo.coin_desk.vo.BpiCurrencyVO;
import org.credo.labs.coindemo.entity.CoinPrices;
import org.credo.labs.coindemo.price.enums.CurrencyCode;

public interface CoinPriceService {

    @Transactional
    CoinPrices create(BpiCurrencyVO vo);

    @Transactional
    CoinPrices update(Long id, BpiCurrencyVO vo);

    CoinPrices getCoinPriceById(Long id);

    CoinPrices getCoinPriceByCode(CurrencyCode code);

    @Transactional
    void deleteCoinPriceById(Long id);

    List<CoinPrices> getAllCoinPrices();
}
