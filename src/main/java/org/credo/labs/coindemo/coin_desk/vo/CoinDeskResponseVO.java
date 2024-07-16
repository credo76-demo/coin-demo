package org.credo.labs.coindemo.coin_desk.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import lombok.Data;

/**
 * CoinDesk API response
 */
@Data
public class CoinDeskResponseVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4846197654904817324L;
    private UpdatedTimeVO time;
    private String disclaimer;
    private String chartName;
    private Map<String, BpiCurrencyVO> bpi;
}

