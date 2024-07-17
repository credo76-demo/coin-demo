package org.credo.labs.coindemo.schedule;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.credo.labs.coindemo.coin_desk.service.CoinDeskService;
import org.credo.labs.coindemo.coin_desk.vo.BpiCurrencyVO;
import org.credo.labs.coindemo.coin_desk.vo.CoinDeskResponseVO;
import org.credo.labs.coindemo.config.SchedulingConfig;
import org.credo.labs.coindemo.entity.CoinPrice;
import org.credo.labs.coindemo.price.enums.CurrencyCode;
import org.credo.labs.coindemo.price.service.CoinPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTasks {

    private final SchedulingConfig schedulingConfig;
    private final ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    CoinDeskService coinDeskService;

    @Autowired
    CoinPriceService coinPriceService;

    @Autowired
    public ScheduledTasks(SchedulingConfig schedulingConfig, ThreadPoolTaskScheduler taskScheduler) {
        this.schedulingConfig = schedulingConfig;
        this.taskScheduler = taskScheduler;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        startScheduledTask();
    }

    private void startScheduledTask() {
        long interval = schedulingConfig.getInterval();
        taskScheduler.scheduleAtFixedRate(this::performTask, interval);
    }

    /**
     * Fetch coin price from CoinDesk API and save to database.
     */
    private void performTask() {
        String uuid = UUID.randomUUID().toString();
        log.info("Scheduled task {} - executed at: {}", uuid, System.currentTimeMillis());

        try { //sync with CoinDesk API
            CoinDeskResponseVO targetVO = coinDeskService.fetchCoinPrice();
            if (null == targetVO) return;

            List<CoinPrice> coinPrices = targetVO.getBpi().values().stream().map((BpiCurrencyVO vo) -> {
                CoinPrice coinPrice = new CoinPrice();
                coinPrice.setCode(CurrencyCode.valueOf(vo.getCode()));
                coinPrice.setSymbol(vo.getSymbol());
                coinPrice.setRate(vo.getRate());
                coinPrice.setDescription(vo.getDescription());
                coinPrice.setRateFloat(vo.getRateFloat());
                return coinPrice;
            }).collect(Collectors.toList());

            coinPriceService.updateAll(coinPrices);

            log.info("Scheduled task {} - Coin prices saved: {}", uuid, coinPrices.size());
        } catch (Exception e) {
            log.error("Error occurred while processing scheduled task " + uuid, e);
            throw new RuntimeException(e);
        } finally {
            log.info("Scheduled task {} - accomplished at: {}", uuid, System.currentTimeMillis());
        }
    }
}
