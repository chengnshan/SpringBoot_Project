package com.cxp.springboot2rsaaes.RSAandAES.config;

import com.alibaba.fastjson.JSON;
import com.cxp.springboot2rsaaes.AES.utils.AesEncryptUtils;
import com.cxp.springboot2rsaaes.RSAandAES.utils.RSAUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @desc 请求数据解密
 * @author 程
 * @date 2019/7/14 上午10:29
 */
@ControllerAdvice(basePackages = "com.cxp.springboot2rsaaes.RSAandAES.controller")
@Slf4j
public class RSADecodeRequestBodyAdvice implements RequestBodyAdvice{

    private static final Logger logger = LoggerFactory.getLogger(RSADecodeRequestBodyAdvice.class);

    @Value("${server.private.key}")
    private String SERVER_PRIVATE_KEY;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
                                           Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try{
            boolean encode = true;
            if (parameter.getMethod().isAnnotationPresent(SecurityParameter.class)){
                //获取注解配置的包含和去除字段
                SecurityParameter serializedField = parameter.getMethodAnnotation(SecurityParameter.class);
                //入参是否需要解密
                encode = serializedField.inDecode();
            }
            if (encode){
                log.info("对方法method :【{}】返回数据进行解密",parameter.getMethod().getName());
                return new MyHttpInputMessage(inputMessage);
            }else {
                return inputMessage;
            }
        }catch (Exception e){
            logger.error("对方法method :【" + parameter.getMethod().getName() + "】返回数据进行解密出现异常："+e.getMessage());
            return inputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = parameter.getMethod();
        log.info("DecodeRequestBodyAdvice afterBodyRead {}.{}:{}",
                method.getDeclaringClass().getSimpleName(),method.getName(),JSON.toJSONString(body));
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Method method=parameter.getMethod();
        log.info("{}.{}",method.getDeclaringClass().getSimpleName(),method.getName());
        return body;
    }

    class MyHttpInputMessage implements HttpInputMessage{

        private HttpHeaders headers;

        private InputStream body;

        public MyHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            this.body = IOUtils.toInputStream(
                    easpString(IOUtils.toString(inputMessage.getBody(),"UTF-8")),
                            "UTF-8");
        }
        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        /**
         *
         * @param requestData
         * @return
         */
        public String easpString(String requestData) {
            if(requestData != null && !requestData.equals("")){
                Map<String,String> map = new Gson().fromJson(requestData,new TypeToken<Map<String,String>>() {
                }.getType());
                // 密文
                String data = map.get("requestData");
                // 加密的ase秘钥
                String encrypted = map.get("encrypted");
                if(StringUtils.isEmpty(data) || StringUtils.isEmpty(encrypted)){
                    throw new RuntimeException("参数【requestData】缺失异常！");
                }else{
                    String content = null ;
                    String aseKey = null;

                    try {
                        aseKey = RSAUtils.decryptDataOnJava(encrypted,SERVER_PRIVATE_KEY);
                    }catch (Exception e){
                        throw  new RuntimeException("参数【aseKey】解析异常！");
                    }

                    try {
                        content  = AesEncryptUtils.decrypt(data, aseKey);
                    }catch (Exception e){
                        throw  new RuntimeException("参数【content】解析异常！");
                    }
                    if (StringUtils.isEmpty(content) || StringUtils.isEmpty(aseKey)){
                        throw  new RuntimeException("参数【requestData】解析参数空指针异常!");
                    }
                    return content;
                }
            }
            throw new RuntimeException("参数【requestData】不合法异常！");
        }

    }
}
