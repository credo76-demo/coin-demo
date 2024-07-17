package org.credo.labs.coindemo.repository;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.credo.labs.coindemo.entity.CoinPrice;
import org.credo.labs.coindemo.price.enums.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoinPriceRepository extends JpaRepository<CoinPrice, Long> {
    Optional<CoinPrice> findByCode(CurrencyCode currencyCode);

    List<CoinPrice> findAllByOrderByCodeAsc();

    @Modifying
    @Transactional
    @Query(value = "MERGE INTO coin_prices (code, symbol, rate, description, rate_float, updated, created) " +
            "KEY(code) VALUES (:code, :symbol, :rate, :description, :rateFloat, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)", nativeQuery = true)
    int upsertByCode(@Param("code") String code,
                     @Param("symbol") String symbol,
                     @Param("rate") String rate,
                     @Param("description") String description,
                     @Param("rateFloat") BigDecimal rateFloat);
}