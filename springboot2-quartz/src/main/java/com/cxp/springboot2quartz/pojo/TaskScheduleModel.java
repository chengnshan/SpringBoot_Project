package com.cxp.springboot2quartz.pojo;

import lombok.Data;

/**
 * @author : cheng
 * @date : 2020-03-10 14:37
 */
@Data
public class TaskScheduleModel {

    private Integer jobType;
    private Integer second;
    private Integer minute;
    private Integer hour;
    private Integer[] dayOfWeeks;
    private Integer[] dayOfMonths;
}
