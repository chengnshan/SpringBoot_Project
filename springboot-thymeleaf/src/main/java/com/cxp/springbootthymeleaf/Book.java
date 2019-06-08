package com.cxp.springbootthymeleaf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author 程
 * @date 2019/6/8 下午2:26
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private Integer id;
    private String bookName;
    private BigDecimal bookPrice;
}
