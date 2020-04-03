package com.cxp.h2database.config;

import com.cxp.h2database.pojo.ResultCode;
import lombok.Data;

/**
 * @author : cheng
 * @date : 2020-03-14 19:09
 */
@Data
public class CustRuntimeException extends RuntimeException {

    static final long serialVersionUID = 1L;

    private String code;
    private String msg;
    private ResultCode resultCode;

    public CustRuntimeException(ResultCode resultCode){
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.resultCode = resultCode;
    }
}
