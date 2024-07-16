package org.credo.labs.coindemo.coin_desk.vo;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
public class UpdatedTimeVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4049392598734678113L;

    private String updated;
    private String updatedISO;
    private String updateduk;
}
