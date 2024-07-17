package org.credo.labs.coindemo.schedule;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.credo.labs.coindemo.config.SchedulingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "coin-demo.scheduling.enabled", havingValue = "true", matchIfMissing = true)
public class ScheduledTasks {

    private final SchedulingConfig schedulingConfig;
    private final ThreadPoolTaskScheduler taskScheduler;
    final AbstractPriceProvider coinPriceProvider;

    @Autowired
    public ScheduledTasks(SchedulingConfig schedulingConfig,
                          ThreadPoolTaskScheduler taskScheduler,
                          @Qualifier("coinDeskProvider") AbstractPriceProvider coinPriceProvider) {
        this.schedulingConfig = schedulingConfig;
        this.taskScheduler = taskScheduler;
        this.coinPriceProvider = coinPriceProvider;
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
            int size = coinPriceProvider.execute(uuid);
            log.info("Scheduled task {} - Coin prices saved: {}", uuid, size);
        } catch (Exception e) {
            log.error("Error occurred while processing scheduled task " + uuid, e);
            throw new RuntimeException(e);
        } finally {
            log.info("Scheduled task {} - accomplished at: {}", uuid, System.currentTimeMillis());
        }
    }
}
