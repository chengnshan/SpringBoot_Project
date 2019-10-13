package com.cxp.springboot2shiro;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.cxp.springboot2shiro.pojo.UserInfo;
import com.cxp.springboot2shiro.pojo.dto.UserInfoDTO;
import com.cxp.springboot2shiro.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot2ShiroApplicationTests {

    private Logger logger = LoggerFactory.getLogger(Springboot2ShiroApplicationTests.class);

    @Autowired
    private UserInfoService userInfoService;

    //静态列名称生成excel
    @Test
    public void contextLoads() throws IOException {
        List<UserInfo> userInfos = userInfoService.list();
        List<UserInfoDTO> dtos = new ArrayList<>(16);
         UserInfoDTO dto = null;
        for (UserInfo info : userInfos) {
            dto = UserInfoDTO.builder().userName(info.getUserName())
                    .salt(info.getSalt()).email(info.getEmail()).id(info.getId()).build();
            dtos.add(dto);
        }

        // 文件输出位置
        OutputStream out = new FileOutputStream("/Volumes/Passport_2/study_practice_file/test.xlsx");

        ExcelWriter writer = EasyExcelFactory.getWriter(out);

        // 写仅有一个 Sheet 的 Excel 文件, 此场景较为通用
        Sheet sheet1 = new Sheet(1, 0, UserInfoDTO.class);

        // 第一个 sheet 名称
        sheet1.setSheetName("第一个sheet");

        // 写数据到 Writer 上下文中
        // 入参1: 创建要写入的模型数据
        // 入参2: 要写入的目标 sheet
        writer.write(dtos, sheet1);

        // 将上下文中的最终 outputStream 写入到指定文件中
        writer.finish();

        // 关闭流
        out.close();
    }

    @Test
    public void readExcelTest() throws Exception {
        InputStream inputStream = new FileInputStream(new File("/Volumes/Passport_2/study_practice_file/test.xlsx"));

        System.out.println("开始读取");

        List<UserInfoDTO> dataList = new ArrayList<UserInfoDTO>();

        EasyExcelFactory.readBySax(inputStream, new Sheet(1,1), new AnalysisEventListener() {

            @Override
            public void invoke(Object object, AnalysisContext analysisContext) {

                logger.info("当前行：{} 对应的对象信息为：{}", analysisContext.getCurrentRowNum(), object);

                ArrayList userObj = (ArrayList) object;
                UserInfoDTO user = new UserInfoDTO();
                user.setId( userObj.get(0) == null ? null : Integer.valueOf(userObj.get(0).toString()));
                user.setUserName(userObj.get(1) == null ? null : userObj.get(1).toString());
                user.setSalt(userObj.get(2) == null ? null : (userObj.get(2).toString()));
                user.setEmail(userObj.get(2) == null ? null : (userObj.get(3).toString()));
                dataList.add(user);

                // 每批插入的数量
                int perReadCount = 5;
                Integer currentRowNum = analysisContext.getCurrentRowNum();
                if (currentRowNum % perReadCount == 0) {
                    logger.info("存储dataList的大小为：{}",dataList.size());
                    dataList.clear();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                logger.info("最后一批存储dataList的大小为：{}",dataList.size());
                dataList.clear();//解析结束销毁不用的资源
            }
        });
    }
}
