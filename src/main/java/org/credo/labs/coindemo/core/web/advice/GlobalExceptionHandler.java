package org.credo.labs.coindemo.core.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.credo.labs.coindemo.core.exception.ApiException;
import org.credo.labs.coindemo.core.web.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.credo.labs.coindemo.core.exception.CoreErrorCodes.INVALID_PARAMS;
import static org.credo.labs.coindemo.core.exception.CoreErrorCodes.UNEXPECTED_EXCEPTION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 處理自定義異常
     */
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ErrorResponse handleCustomException(ApiException ex) {
        return new ErrorResponse(BAD_REQUEST, ex.getErrorCode().getCode(), ex.getMessage());
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            HttpMessageNotReadableException.class,
            HttpMediaTypeNotAcceptableException.class,
            IllegalArgumentException.class,
    })
    @ResponseBody
    public ErrorResponse handleInvalidParameterException(Exception exception) {
        log.trace("Catch an invalid input parameter exception.", exception);
        return new ErrorResponse(BAD_REQUEST, INVALID_PARAMS.getCode(), exception.getMessage());
    }

    // 處理所有未捕獲的異常
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorResponse handleException(Exception ex) {
        log.trace(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, UNEXPECTED_EXCEPTION.getCode()
                , "Catch an unexpected exception.");
        errorResponse.setStackTrace(ex.toString());
        return errorResponse;
    }

}
