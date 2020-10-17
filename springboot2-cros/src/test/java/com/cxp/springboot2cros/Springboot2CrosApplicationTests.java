package com.cxp.springboot2cros;

import com.cxp.springboot2cros.read_config.PropertySourceConfig;
import com.cxp.springboot2cros.read_config.PropertySourceValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot2CrosApplicationTests {

    @Autowired
    private PropertySourceConfig propertySourceConfig;
    @Autowired
    private PropertySourceValue propertySourceValue;

    @Autowired
    private Environment env;

    @Test
    public void contextLoads() {
        System.out.println(propertySourceConfig);
        System.out.println(propertySourceValue);

        System.out.println(env.getProperty("config.username"));
    }

}
