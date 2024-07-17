package org.credo.labs.coindemo.repository;

import java.util.Optional;
import org.credo.labs.coindemo.entity.CoinPrice;
import org.credo.labs.coindemo.price.enums.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinPriceRepository extends JpaRepository<CoinPrice, Long> {
    Optional<CoinPrice> findByCode(CurrencyCode currencyCode);
}