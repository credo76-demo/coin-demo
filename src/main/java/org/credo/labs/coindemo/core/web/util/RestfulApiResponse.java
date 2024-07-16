package org.credo.labs.coindemo.core.web.util;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(of = {"code", "message"})
@NoArgsConstructor
public class RestfulApiResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 2356385155112020577L;

    private String code;
    private String message;

    public RestfulApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private T data;
}
