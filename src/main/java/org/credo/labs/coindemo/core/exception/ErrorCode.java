package org.credo.labs.coindemo.core.exception;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.credo.labs.coindemo.util.TextUtils;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ErrorCode implements Serializable {
    @Serial
    private static final long serialVersionUID = 2271874525723333857L;

    private HttpStatus httpStatus;
    private String code;
    private String message;

    public ErrorCode(String code, String message) {
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.code = code;
        this.message = message;
    }

    public ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @SafeVarargs
    public final String substitute(Pair<String, Object>... arguments){
        return TextUtils.substitute(message, arguments);
    }

}
