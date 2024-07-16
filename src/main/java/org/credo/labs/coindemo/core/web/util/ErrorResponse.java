package org.credo.labs.coindemo.core.web.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ErrorResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -7115539018577782397L;

    @JsonIgnore
    private HttpStatus httpStatus;

    private String code;
    private String message;
    private Map<String, Object> arguments;
    private String stackTrace;

    public ErrorResponse(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(HttpStatus httpStatus, String code, String message, Map<String, Object> arguments, String stackTrace) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.arguments = arguments;
        this.stackTrace = stackTrace;
    }

}
