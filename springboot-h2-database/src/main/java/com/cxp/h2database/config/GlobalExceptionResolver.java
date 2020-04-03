package com.cxp.h2database.config;

import com.cxp.h2database.pojo.ApiResult;
import com.cxp.h2database.pojo.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局Controller层异常处理类
 * @author : cheng
 * @date : 2020-03-14 19:10
 */
@ControllerAdvice
public class GlobalExceptionResolver {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    /**
     * 处理所有业务异常
     *
     * @param e 业务异常
     * @return json结果
     */
    @ExceptionHandler(CustRuntimeException.class)
    @ResponseBody
    public ApiResult handleOpdRuntimeException(CustRuntimeException e) {
        // 不打印异常堆栈信息
        LOG.error(e.getMsg());
        return ApiResult.of(e.getResultCode());
    }

    /**
     * 处理所有不可知异常
     *
     * @param e 异常
     * @return json结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResult handleException(Exception e) {
        // 打印异常堆栈信息
        LOG.error(e.getMessage(), e);
        return ApiResult.of(ResultCode.UNKNOWN_ERROR);
    }

}
