package cn.wangcaitao.global.handler;

import cn.wangcaitao.common.constant.ResultConstant;
import cn.wangcaitao.common.entity.Result;
import cn.wangcaitao.common.exception.ResultException;
import cn.wangcaitao.common.util.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 统一异常处理
 *
 * @author wangcaitao
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result handlerException(Exception e) {
        if (e instanceof NoHandlerFoundException) {
            return ResultUtils.error(ResultConstant.NOT_FOUND_CODE, ResultConstant.NOT_FOUND_MSG);
        } else if (e instanceof BindException) {
            return ResultUtils.error(ResultConstant.BAD_REQUEST_CODE, ((BindException) e).getFieldErrors().get(0).getDefaultMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            return ResultUtils.error(ResultConstant.BAD_REQUEST_CODE, ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        } else if (e instanceof AccessDeniedException) {
            return ResultUtils.error(ResultConstant.FORBIDDEN_CODE, ResultConstant.FORBIDDEN_MSG);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return ResultUtils.error(ResultConstant.METHOD_NOT_ALLOWED_CODE, ResultConstant.METHOD_NOT_ALLOWED_MSG);
        } else if (e instanceof ResultException) {
            String params = ((ResultException) e).getParams();
            if (StringUtils.isNotEmpty(params)) {
                LOGGER.error("request params: {}", params, e);
            }

            return ResultUtils.error(((ResultException) e).getCode(), ((ResultException) e).getMsg());
        } else if (e instanceof HttpMessageNotReadableException) {
            return ResultUtils.error(ResultConstant.BAD_REQUEST_CODE, ResultConstant.BAD_REQUEST_MSG);
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            return ResultUtils.error(ResultConstant.UNSUPPORTED_MEDIA_TYPE_CODE, ResultConstant.UNSUPPORTED_MEDIA_TYPE_MSG);
        } else {
            LOGGER.error("un handle exception: {}", e.getClass().getName());

            return ResultUtils.error(ResultConstant.INTERNAL_SERVER_ERROR_CODE, ResultConstant.INTERNAL_SERVER_ERROR_MSG);
        }
    }
}
