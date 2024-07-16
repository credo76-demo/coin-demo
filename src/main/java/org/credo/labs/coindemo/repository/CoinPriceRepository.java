package org.credo.labs.coindemo.repository;

import java.util.Optional;
import org.credo.labs.coindemo.entity.CoinPrices;
import org.credo.labs.coindemo.price.enums.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinPriceRepository extends JpaRepository<CoinPrices, Long> {
    Optional<CoinPrices> findByCode(CurrencyCode currencyCode);
}