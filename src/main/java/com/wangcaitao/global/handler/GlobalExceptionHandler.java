package com.wangcaitao.global.handler;

import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.exception.ResultException;
import com.wangcaitao.common.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

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
    public Result handlerException(Exception e) {
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
    public Result handlerException(RuntimeException e) {
        log.error("runtime error.", e);

        return ResultUtils.error();
    }

    /**
     * NoHandlerFoundException handler
     *
     * @param e Exception
     * @return Result
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Result handlerException(NoHandlerFoundException e) {
        log.error("resource not fund.", e);

        return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
    }

    /**
     * HttpMediaTypeNotSupportedException handler
     *
     * @param e HttpMediaTypeNotSupportedException
     * @return Result
     */
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public Result handlerException(HttpMediaTypeNotSupportedException e) {
        log.error("content-type error. content-type: {}", e.getContentType(), e);

        return ResultUtils.error(415, "Content-Type 错误");
    }

    /**
     * HttpMessageNotReadableException handler
     *
     * @param e HttpMessageNotReadableException
     * @return Result
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Result handlerException(HttpMessageNotReadableException e) {
        log.error("miss request body.", e);

        return ResultUtils.error(400, "缺少请求参数");
    }

    /**
     * HttpRequestMethodNotSupportedException handler
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return Result
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Result handlerException(HttpRequestMethodNotSupportedException e) {
        log.error("method error. method: {}", e.getMethod(), e);

        return ResultUtils.error(405, "请求方式错误");
    }

    /**
     * ResultException handler
     *
     * @param e ResultException
     * @return Result
     */
    @ExceptionHandler(value = ResultException.class)
    public Result handlerException(ResultException e) {
        log.error("result error. msg: {}", e.getMsg(), e);

        String params = e.getParams();
        if (StringUtils.isNotEmpty(params)) {
            log.error("request params: {}", params, e);
        }

        return ResultUtils.error(e.getCode(), e.getMsg());
    }
}
