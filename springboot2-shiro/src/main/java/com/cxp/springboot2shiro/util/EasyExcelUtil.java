package com.cxp.springboot2shiro.util;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-13 09:30
 */
public class EasyExcelUtil {

    /**
     * 下载EXCEL文件2007版本
     *
     * @throws IOException IO异常
     */
    public static void exportExcel2007Format(EasyExcelParams excelParams) throws IOException {
        exportExcel(excelParams, ExcelTypeEnum.XLSX);
    }

    /**
     * 下载EXCEL文件2003版本
     *
     * @throws IOException IO异常
     */
    public static void exportExcel2003Format(EasyExcelParams excelParams) throws IOException {
        exportExcel(excelParams, ExcelTypeEnum.XLS);
    }

    /**
     * 根据参数和版本枚举导出excel文件
     *
     * @param excelParams 参数实体
     * @param typeEnum    excel类型枚举
     * @throws IOException
     */
    private static void exportExcel(EasyExcelParams excelParams, ExcelTypeEnum typeEnum) throws IOException {
        Validate.isTrue(excelParams.isValid(), "easyExcel params is not valid");

        HttpServletResponse response = excelParams.getResponse();
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter writer = new ExcelWriter(out, typeEnum, excelParams.isNeedHead());
        prepareResponds(response, excelParams.getExcelNameWithoutExt(), typeEnum);
        Sheet sheet1 = new Sheet(1, 0, excelParams.getDataModelClazz());
        if (StringUtils.isNotBlank(excelParams.getSheetName())) {
            sheet1.setSheetName(excelParams.getSheetName());
        }
        writer.write(excelParams.getData(), sheet1);
        writer.finish();
        out.flush();
    }


    /**
     * 将文件输出到浏览器（导出文件）
     * @param response 响应
     * @param fileName 文件名（不含拓展名）
     * @param typeEnum excel类型
     */
    private static void prepareResponds(HttpServletResponse response, String fileName, ExcelTypeEnum typeEnum) {
        String fileName2Export = new String((fileName).getBytes(Charsets.UTF_8), Charsets.ISO_8859_1);
        response.setContentType("multipart/form-data");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName2Export + typeEnum.getValue());
    }

    public static class EasyExcelParams {

        /**
         * excel文件名（不带拓展名)
         */
        private String excelNameWithoutExt;
        /**
         * sheet名称
         */
        private String sheetName;
        /**
         * 是否需要表头
         */
        private boolean needHead = true;
        /**
         * 数据
         */
        private List<? extends BaseRowModel> data;

        /**
         * 数据模型类型
         */
        private Class<? extends BaseRowModel> dataModelClazz;

        /**
         * 响应
         */
        private HttpServletResponse response;


        public EasyExcelParams() {
        }


        /**
         * 检查不允许为空的属性
         */
        public boolean isValid() {
            return ObjectUtils.allNotNull(excelNameWithoutExt, data, dataModelClazz, response);
        }

        public String getExcelNameWithoutExt() {
            return excelNameWithoutExt;
        }

        public void setExcelNameWithoutExt(String excelNameWithoutExt) {
            this.excelNameWithoutExt = excelNameWithoutExt;
        }

        public String getSheetName() {
            return sheetName;
        }

        public void setSheetName(String sheetName) {
            this.sheetName = sheetName;
        }

        public boolean isNeedHead() {
            return needHead;
        }

        public void setNeedHead(boolean needHead) {
            this.needHead = needHead;
        }

        public List<? extends BaseRowModel> getData() {
            return data;
        }

        public void setData(List<? extends BaseRowModel> data) {
            this.data = data;
        }

        public Class<? extends BaseRowModel> getDataModelClazz() {
            return dataModelClazz;
        }

        public void setDataModelClazz(Class<? extends BaseRowModel> dataModelClazz) {
            this.dataModelClazz = dataModelClazz;
        }

        public HttpServletResponse getResponse() {
            return response;
        }

        public void setResponse(HttpServletResponse response) {
            this.response = response;
        }
    }
}
