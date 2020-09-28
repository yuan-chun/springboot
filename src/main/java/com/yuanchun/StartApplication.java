package com.yuanchun;

import com.yuanchun.common.ApplicationProperties;
import com.yuanchun.util.Const;
import com.yuanchun.util.PropertiesUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 注解默认也是会扫描注解所在包的当前包及子包
 * scanBasePackages可配置扫描路径
**/
@SpringBootApplication
/**
 * 开启事务使用
 */
@EnableTransactionManagement
/**
 * 配置SpringBoot扫描Mybatis仓储，有两种配置方式，这两种方式都可以，根据项目结构进行选择即可，也可以结合私用
 * 方式一：在启动类上加入@MapperScan，填写Mapper接口所在的包名，SpringBoot就会自动将该包中的Mapper接口进行加载，sql在xnl中
 * 方式二：在Mapper接口上加上@Mapper注解，SpringBoot就会自动夹在该Mapper接口，sql在接口上方
 */
@MapperScan("com.yuanchun.dao")
@EnableConfigurationProperties({ApplicationProperties.class})
public class StartApplication {

    public static void main(String[] args) {
        //SpringApplication.run(BasicAbilityApplication.class, args);
        String port = PropertiesUtil.getProperty(Const.PROP_NAME_APPLICATION, Const.SERVER_PORT);
        new SpringApplicationBuilder(StartApplication.class)
                .properties("server.port=" + port)
                .run(args);
    }
    // 第一次提交
    // 第二次提交
}
