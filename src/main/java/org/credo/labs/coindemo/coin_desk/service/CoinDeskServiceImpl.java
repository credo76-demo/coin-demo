package org.credo.labs.coindemo.coin_desk.service;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Request;
import org.credo.labs.coindemo.coin_desk.vo.CoinDeskResponseVO;
import org.credo.labs.coindemo.core.client.OkHttpSingleton;
import org.credo.labs.coindemo.core.exception.ApiException;
import org.credo.labs.coindemo.util.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import static org.credo.labs.coindemo.core.exception.CoreErrorCodes.HTTP_API_FAILED;

@Service
@Slf4j
public class CoinDeskServiceImpl implements CoinDeskService {

    @Value("${coin-demo.external.coin-desk.url}")
    private String apiURL;

    /**
     * fetch coin price from CoinDesk API
     */
    @Override
    public CoinDeskResponseVO fetchCoinPrice() {
        Call call = OkHttpSingleton.getInstance().newCall(new Request.Builder().url(apiURL).build());
        try {
            String jsonString = Objects.requireNonNull(call.execute().body()).string();
            return JsonUtils.asObject(jsonString, CoinDeskResponseVO.class);
        } catch (Exception e) {
            log.error("Error fetching coin price from CoinDesk API", e);
            throw new ApiException(e, HTTP_API_FAILED, Pair.of("reason", e.getMessage()));
        }
    }
}
