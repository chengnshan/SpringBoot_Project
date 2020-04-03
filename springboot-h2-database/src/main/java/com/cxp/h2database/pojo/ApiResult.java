package com.cxp.h2database.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author : cheng
 * @date : 2020-03-14 19:13
 */
@Data
@ToString
public class ApiResult {

    private String code;

    private String msg;

    public ApiResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public static ApiResult of(ResultCode resultCode) {
        return new ApiResult(resultCode);
    }
}
