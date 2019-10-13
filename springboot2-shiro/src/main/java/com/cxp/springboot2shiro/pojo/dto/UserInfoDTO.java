package com.cxp.springboot2shiro.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-10 22:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDTO extends BaseRowModel {

    @ExcelProperty(value = "主键",index = 0)
    private Integer id;

    @ExcelProperty(value = "姓名",index = 2)
    private String userName;

    @ExcelProperty(value = "盐",index = 3)
    private String salt;

    @ExcelProperty(value = "邮箱",index = 4)
    private String email;

}
