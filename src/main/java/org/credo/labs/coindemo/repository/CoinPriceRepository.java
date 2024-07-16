package org.credo.labs.coindemo.repository;

import org.credo.labs.coindemo.entity.CoinPrices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinPriceRepository extends JpaRepository<CoinPrices, Long> {
}