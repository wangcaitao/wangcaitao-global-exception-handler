package com.wangcaitao.global.handler;

import com.wangcaitao.common.constant.HttpStatusConstant;
import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.exception.ResultException;
import com.wangcaitao.common.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.Serializable;

/**
 * @author wangcaitao
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Exception handler
     *
     * @param e Exception
     * @return Result
     */
    @ExceptionHandler(value = Exception.class)
    public Result<Serializable> handlerException(Exception e) {
        log.error("un handler exception.", e);

        return ResultUtils.error();
    }

    /**
     * RuntimeException handler
     *
     * @param e Exception
     * @return Result
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result<Serializable> handlerException(RuntimeException e) {
        return ResultUtils.error();
    }

    /**
     * ResultException handler
     *
     * @param e ResultException
     * @return Result
     */
    @ExceptionHandler(value = ResultException.class)
    public Result<Serializable> handlerException(ResultException e) {
        return ResultUtils.error(e.getCode(), e.getMsg());
    }

    /**
     * NoHandlerFoundException handler
     *
     * @param e Exception
     * @return Result
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Result<Serializable> handlerException(NoHandlerFoundException e) {
        log.error("resource not fund. {} {}", e.getHttpMethod(), e.getRequestURL());

        return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
    }

    /**
     * HttpMediaTypeNotSupportedException handler
     *
     * @param e HttpMediaTypeNotSupportedException
     * @return Result
     */
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public Result<Serializable> handlerException(HttpMediaTypeNotSupportedException e) {
        log.error("content-type error. request content-type: {}, support content-type: {}", e.getContentType(), e.getSupportedMediaTypes());

        return ResultUtils.error(HttpStatusEnum.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * HttpMessageNotReadableException handler
     *
     * @param e HttpMessageNotReadableException
     * @return Result
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Result<Serializable> handlerException(HttpMessageNotReadableException e) {
        log.error("miss request body.");

        return ResultUtils.error(HttpStatusConstant.BAD_REQUEST_CODE, "缺少请求参数");
    }

    /**
     * HttpRequestMethodNotSupportedException handler
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return Result
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Result<Serializable> handlerException(HttpRequestMethodNotSupportedException e) {
        log.error("method error. request method: {}, support method: {}", e.getMethod(), e.getSupportedMethods());

        return ResultUtils.error(HttpStatusEnum.METHOD_NOT_ALLOWED);
    }

    /**
     * BindException handler
     *
     * @param e BindException
     * @return Result
     */
    @ExceptionHandler(value = BindException.class)
    public Result<Serializable> handlerException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        log.error("param validate error. objectName: {}, field: {}, message: {}", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());

        return ResultUtils.error(HttpStatusConstant.BAD_REQUEST_CODE, fieldError.getDefaultMessage());
    }

    /**
     * MethodArgumentNotValidException handler
     *
     * @param e MethodArgumentNotValidException
     * @return Result
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Serializable> handlerException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        log.error("param validate error. objectName: {}, field: {}, message: {}", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());

        return ResultUtils.error(HttpStatusConstant.BAD_REQUEST_CODE, fieldError.getDefaultMessage());
    }
}
