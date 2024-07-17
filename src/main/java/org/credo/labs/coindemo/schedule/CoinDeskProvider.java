package org.credo.labs.coindemo.schedule;

import java.util.ArrayList;
import java.util.List;
import org.credo.labs.coindemo.coin_desk.service.CoinDeskService;
import org.credo.labs.coindemo.coin_desk.vo.BpiCurrencyVO;
import org.credo.labs.coindemo.coin_desk.vo.CoinDeskResponseVO;
import org.credo.labs.coindemo.entity.CoinPrice;
import org.credo.labs.coindemo.price.enums.CurrencyCode;
import org.springframework.stereotype.Component;

/**
 * [Template Method Pattern] CoinDesk provider for fetching coin price.
 */
@Component
public class CoinDeskProvider extends AbstractPriceProvider {

    final CoinDeskService coinDeskService;

    public CoinDeskProvider(CoinDeskService coinDeskService) {
        this.coinDeskService = coinDeskService;
    }

    /**
     * Fetch coin price from CoinDesk API.
     */
    @Override
    protected List<CoinPrice> fetchCoinPrice() {
        CoinDeskResponseVO targetVO = coinDeskService.fetchCoinPrice();
        if (targetVO == null) return List.of();

        List<CoinPrice> list = new ArrayList<>();
        for (BpiCurrencyVO bpiCurrencyVO : targetVO.getBpi().values()) {
            CoinPrice coinPrice = new CoinPrice();
            coinPrice.setCode(CurrencyCode.valueOf(bpiCurrencyVO.getCode()));
            coinPrice.setSymbol(bpiCurrencyVO.getSymbol());
            coinPrice.setRate(bpiCurrencyVO.getRate());
            coinPrice.setDescription(bpiCurrencyVO.getDescription());
            coinPrice.setRateFloat(bpiCurrencyVO.getRateFloat());
            list.add(coinPrice);
        }
        return list;
    }
}
