package org.credo.labs.coindemo.core.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.credo.labs.coindemo.core.exception.ApiException;
import org.credo.labs.coindemo.core.web.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.credo.labs.coindemo.core.exception.CoreErrorCodes.UNEXPECTED_EXCEPTION;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 處理自定義異常
     */
    @ExceptionHandler(ApiException.class)
    @ResponseBody
//    public ResponseEntity<ErrorResponse> handleCustomException(ApiException ex) {
    public ErrorResponse handleCustomException(ApiException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getErrorCode().getCode(), ex.getMessage());
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getErrorCode().getCode(), ex.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 處理所有未捕獲的異常
    @ExceptionHandler(Throwable.class)
    @ResponseBody
//    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    public ErrorResponse handleException(Exception ex) {
        log.trace(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, UNEXPECTED_EXCEPTION.getCode()
                , "Catch an unexpected exception.");
        errorResponse.setStackTrace(ex.toString());
        return errorResponse;
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
