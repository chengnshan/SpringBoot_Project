package com.cxp.springboot2webmagic.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @author : cheng
 * @date : 2019-11-21 13:29
 */
@Data
@ToString
@TableName(value = "job_info", resultMap = "baseResultMap")
public class JobInfo {

    @TableId(value = "id",type= IdType.AUTO)
    @TableField
    private Integer id;
    private String companyName;
    private String companyAddr;
    private String companyInfo;
    private String jobName;
    private String jobAddr;
    private String jobInfo;
    private Integer salaryMin;
    private Integer salaryMax;
    private String salary;
    private String url;
    private String time;
}
