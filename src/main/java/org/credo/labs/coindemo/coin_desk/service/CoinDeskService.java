package org.credo.labs.coindemo.coin_desk.service;

import org.credo.labs.coindemo.coin_desk.vo.CoinDeskResponseVO;

/**
 * CoinDeskService
 */
public interface CoinDeskService {
    /**
     * Fetch coin price
     */
    CoinDeskResponseVO fetchCoinPrice();
}
