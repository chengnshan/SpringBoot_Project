package com.cxp.springboot2rsaaes.RSAandAES.config;

import com.cxp.springboot2rsaaes.AES.utils.AesEncryptUtils;
import com.cxp.springboot2rsaaes.RSAandAES.utils.RSAUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @desc 返回数据加密
 * @author 程
 * @date 2019/7/14 上午10:47
 */
@ControllerAdvice(basePackages = "com.cxp.springboot2rsaaes.RSAandAES.controller")
public class RSAEncodeResponseBodyAdvice implements ResponseBodyAdvice {

    private final static Logger logger = LoggerFactory.getLogger(RSAEncodeResponseBodyAdvice.class);

    @Value("${client.public.key}")
    private String CLIENT_PUBLIC_KEY;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        boolean encode = false;
        if (methodParameter.getMethod().isAnnotationPresent(SecurityParameter.class)) {
            //获取注解配置的包含和去除字段
            SecurityParameter serializedField = methodParameter.getMethodAnnotation(SecurityParameter.class);
            //出参是否需要加密
            encode = serializedField.outEncode();
        }
        if (encode) {
            logger.info("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行加密");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
                // 生成aes秘钥
                String aseKey = getRandomString(16);
                // rsa加密
                String encrypted = RSAUtils.encryptedDataOnJava(aseKey, CLIENT_PUBLIC_KEY);
                // aes加密
                String requestData = AesEncryptUtils.encrypt(result, aseKey);
                Map<String, String> map = new HashMap<>();
                map.put("encrypted", encrypted);
                map.put("requestData", requestData);

                return map;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("对方法method :【" + methodParameter.getMethod().getName() + "】返回数据进行解密出现异常："+e.getMessage());
            }
        }
        return body;
    }

    /**
     * 创建指定位数的随机字符串
     * @param length 表示生成字符串的长度
     * @return 字符串
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
