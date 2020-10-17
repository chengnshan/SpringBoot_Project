package com.cxp.springboot2mybatisplus.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-04 14:22
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分布插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
         paginationInterceptor.setOverflow(false);
         /**以下mybatis-plus-3.2.0版本使用方法
            // 设置最大单页限制数量，默认 500 条，-1 不受限制
            paginationInterceptor.setLimit(5);
            paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
          **/

        paginationInterceptor.setDialectType("oracle");

        return paginationInterceptor;
    }

}
