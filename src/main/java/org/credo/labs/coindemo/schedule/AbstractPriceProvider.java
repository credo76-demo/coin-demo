package org.credo.labs.coindemo.schedule;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.credo.labs.coindemo.entity.CoinPrice;
import org.credo.labs.coindemo.price.service.CoinPriceService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * [Template Method Pattern] Abstract class for fetching coin price from different providers.
 */
@Slf4j
public abstract class AbstractPriceProvider {

    @Autowired
    CoinPriceService coinPriceService;

    /**
     * Fetch coin price from provider and save to database.
     */
    public int execute(String taskId) {
        List<CoinPrice> coinPrices = fetchCoinPrice();
        log.info("Task {} - fetched {} coin prices", taskId, coinPrices.size());

        if (coinPrices.isEmpty()) return 0;

        coinPriceService.updateAll(coinPrices);
        return coinPrices.size();
    }


    /**
     * Fetch coin price from provider.
     */
    protected abstract  List<CoinPrice> fetchCoinPrice();
}
