package org.credo.labs.coindemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.credo.labs.coindemo.price.enums.CurrencyCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "coin_prices", indexes = {
        @Index(name = "idx_code", columnList = "code"),
        @Index(name = "idx_updated", columnList = "updated")
})
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@ToString
public class CoinPrices implements Serializable {
    @Serial
    private static final long serialVersionUID = -712263097189709942L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false)
    CurrencyCode code;

    @Column(name = "symbol", nullable = false)
    private String symbol;

    @Column(name = "rate", nullable = false)
    private String rate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "rate_float", nullable = false)
    private BigDecimal rateFloat;

    @Column(name = "updated", columnDefinition = "timestamp")
    private LocalDateTime updated;

    @Column(name = "created", columnDefinition = "timestamp")
    private LocalDateTime created;
}
