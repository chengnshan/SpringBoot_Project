package com.cxp.springbootthymeleaf.utils;

import com.cxp.springbootthymeleaf.config.BASE64DecodedMultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 程
 * @date 2019/6/9 上午10:38
 */
public class Base64AndImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(Base64AndImageUtil.class);

    /**
     *
     * @param imgStr
     * @return
     */
    public static MultipartFile base64ToMutipartFile(String imgStr){
        try {
            String[] baseStr = imgStr.split(",");
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] b =  new byte[0];
            b = base64Decoder.decodeBuffer(baseStr[1]);
            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return  new BASE64DecodedMultipartFile(b,baseStr[0]) ;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  图片转换成Base64编码
     * @return
     */
    public static String imageToBase64(String imageFile){
        InputStream in = null;
        byte[] data = null;
        try{
            //读取图片数组
            in = new FileInputStream(imageFile);
            data = new byte[in.available()];
            in.read(data);

            //对字节数组Base64编码
            BASE64Encoder base64Encoder = new BASE64Encoder();
            return base64Encoder.encode(data);
        }catch (Exception e){
            logger.error("imageToBase64 Exception :"+e.getMessage(),e);
        }finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
