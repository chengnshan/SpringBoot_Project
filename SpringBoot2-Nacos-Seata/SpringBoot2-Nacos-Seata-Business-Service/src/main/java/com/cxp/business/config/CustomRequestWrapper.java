package com.cxp.business.config;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author : cheng
 * @date : 2020-08-28 23:13
 */
public class CustomRequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    /**
     * 只能获取请求体中的数据，如post请求application/json;
     * @param request
     */
    public CustomRequestWrapper(HttpServletRequest request) {
        super(request);
        StringBuilder sb = new StringBuilder();
        BufferedReader bfr = null;
        try {
            ServletInputStream inputStream = request.getInputStream();
            if (inputStream != null){
                bfr = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int readCount = 0 ;
                while ((readCount = bfr.read(charBuffer)) != -1 ){
                    sb.append(charBuffer,0, readCount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bfr != null){
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        body = sb.toString();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
        return servletInputStream;
    }

    public String getBody() {
        return body;
    }
}
