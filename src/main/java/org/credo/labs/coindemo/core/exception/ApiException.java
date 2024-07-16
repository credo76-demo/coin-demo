package org.credo.labs.coindemo.core.exception;


import java.io.Serial;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Pair;

/**
 * TODO key pair replace
 * maybe use java.text.MessageFormat;
 */
public class ApiException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4603059242251105014L;
    Throwable cause;
    @Getter
    private ErrorCode errorCode;
    @Getter
    private Pair<String, Object>[] arguments;

    @SafeVarargs
    public ApiException(ErrorCode errorCode, Pair<String, Object>... arguments) {
        super(errorCode.substitute(arguments));
        this.errorCode = errorCode;
        this.arguments = arguments;
    }

    @SafeVarargs
    public ApiException(Throwable cause, ErrorCode errorCode, Pair<String, Object>... arguments) {
        super(errorCode.substitute(arguments), cause);
        this.errorCode = errorCode;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        if(StringUtils.isNotBlank(super.getMessage()))
            return super.getMessage();

        return new StringBuilder(errorCode.getCode()).append(" : ").append(errorCode.substitute(arguments)).toString();
    }
}
