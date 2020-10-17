package com.cxp.config;

import java.text.*;
import java.util.Date;

/**
 * @author : cheng
 * @date : 2020-06-22 22:10
 */
public class MyDateFormat extends DateFormat {

    private DateFormat dateFormat;

    private String pattern;

    public MyDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public MyDateFormat(DateFormat dateFormat, String pattern) {
        this.dateFormat = dateFormat;
        this.pattern = pattern;
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return dateFormat.format(date, toAppendTo, fieldPosition);
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        Date date = null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        try {
            date = format1.parse(source, pos);
        } catch (Exception e) {
            date = dateFormat.parse(source, pos);
        }
        return date;
    }

    /**
     * 主要还是装饰这个方法
     * @param source
     * @return
     * @throws ParseException
     */
    @Override
    public Date parse(String source) throws ParseException {
        Date date = null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        try {
            // 先按我的规则来
            date = format1.parse(source);
        } catch (Exception e) {
            // 不行，那就按原先的规则吧
            date = dateFormat.parse(source);
        }
        return date;
    }

    // 这里装饰clone方法的原因是因为clone方法在jackson中也有用到
    @Override
    public Object clone() {
        Object format = dateFormat.clone();
        return new MyDateFormat((DateFormat) format);
    }
}
