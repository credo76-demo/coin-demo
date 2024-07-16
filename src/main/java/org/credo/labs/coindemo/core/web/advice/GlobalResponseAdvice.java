package org.credo.labs.coindemo.core.web.advice;

import lombok.extern.slf4j.Slf4j;
import org.credo.labs.coindemo.core.exception.ErrorCode;
import org.credo.labs.coindemo.core.web.util.ErrorResponse;
import org.credo.labs.coindemo.core.web.util.RestfulApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {
    public static ErrorCode OK = new ErrorCode(HttpStatus.OK, "0", "OK.");

    /**
     * 只處理 ErrorResponse 和 RestfulApiResponse 類型
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.hasMethodAnnotation(CoreResponseBody.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response) {
        if (body instanceof RestfulApiResponse) return body;

        if (body instanceof ErrorResponse) {
            response.setStatusCode(((ErrorResponse) body).getHttpStatus());
            return body;
        }

        return new RestfulApiResponse(OK.getCode(), OK.getMessage(), body);
    }
}
