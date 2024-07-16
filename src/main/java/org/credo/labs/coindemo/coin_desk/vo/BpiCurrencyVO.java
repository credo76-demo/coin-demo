package org.credo.labs.coindemo.coin_desk.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.credo.labs.coindemo.util.BigDecimalDeserializer;
import org.credo.labs.coindemo.util.BigDecimalSerializer;

@Data
public class BpiCurrencyVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2571371426707959853L;

    @NotBlank
    private String code;
    @NotBlank
    private String symbol;
    @NotBlank
    private String rate;
    @NotNull
    private String description;

    @NotNull
    @JsonProperty("rate_float")
    @JsonSerialize(using = BigDecimalSerializer.class)
    @JsonDeserialize(using = BigDecimalDeserializer.class)
    private BigDecimal rateFloat;

}
