package com.cxp.springboot2rabbitmq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 程
 * @date 2019/7/1 下午6:22
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto implements Serializable {

    private static final long serialVersionUID = -3428675361884556948L;

    private Integer resCode;

    private String message;

    public static ResponseDto isSuccess(Integer resCode,String message){
        return new ResponseDto(resCode,message);
    }

}
