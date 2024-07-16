package org.credo.labs.coindemo.core.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public final class CoreErrorCodes {

    public static ErrorCode OK = new ErrorCode(HttpStatus.OK, "0", "OK.");


    public static ErrorCode UNEXPECTED_EXCEPTION = new ErrorCode(INTERNAL_SERVER_ERROR, "core-0001", "Read json failed: %{reason}.");
    public static ErrorCode PATH_NOT_FOUND = new ErrorCode(NOT_FOUND, "core-0002", "The request path %{method} %{uri} is not found.");
    public static ErrorCode INVALID_PARAMS = new ErrorCode("core-0003", "Invalid parameters: %{reason}");
    public static ErrorCode READ_JSON_FAILED = new ErrorCode("core-0004", "Read json failed: %{reason}.");
    public static ErrorCode WRITE_JSON_FAILED = new ErrorCode("core-0005", "Write json failed: %{reason}.");
    public static ErrorCode UNSUPPORTED_MEDIA_TYPE = new ErrorCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "core-0006", "The media type %{mediaType} is unsupported");
    public static ErrorCode UNSUPPORTED_HTTP_METHOD = new ErrorCode(METHOD_NOT_ALLOWED, "core-0007", "The request path %{method} %{uri} is unsupported.");
    public static ErrorCode ACCESS_DENIED = new ErrorCode(FORBIDDEN, "core-0008", "Insufficient permission to access the request path %{method} %{uri}.");
    public static ErrorCode DUPLICATED_REQUEST_BLOCK = new ErrorCode("core-0009", "There exists other same request in the short period.");

    public static ErrorCode HTTP_API_FAILED = new ErrorCode("core-0010", "Call http API failed: %{reason}");
}
