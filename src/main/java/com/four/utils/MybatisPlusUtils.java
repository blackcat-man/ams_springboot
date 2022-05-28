package com.four.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @Auther: Mr_xu
 * @Date: 2022/05/14
 * @Description:
 */
public class MybatisPlusUtils {
    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
        // 数据源
        DataSourceConfig dataSource = new DataSourceConfig();
        dataSource.setDriverName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ams?characterEncoding=utf-8&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        autoGenerator.setDataSource(dataSource);
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir") + "/src/main/java"); // 设置代码输出位置
        globalConfig.setOpen(true); // 设置生成完毕后是否打开代码所在的目录
        globalConfig.setAuthor("Mr_xu"); // 设置作者
        globalConfig.setFileOverride(false); // 设置是否覆盖原始生成的文件
        globalConfig.setMapperName("%sMapper"); // 设置mapper接口的名称
        globalConfig.setServiceName("I%sService"); // 设置业务层接口名称
        globalConfig.setServiceImplName("%sServiceImpl");// 设置业务层实现名称
        globalConfig.setControllerName("%sController");// 设置控制层名称
        globalConfig.setIdType(IdType.AUTO); // 设置id生成策略
        autoGenerator.setGlobalConfig(globalConfig);
        // 设置包相关配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.four"); // 设置生成的包名
        autoGenerator.setPackageInfo(packageConfig);
        // 策略设置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setTablePrefix("sys_"); // 设置数据库表的前缀，配置后，前缀不参与命名
        strategyConfig.setRestControllerStyle(false); // 设置Controller是否启用Restful风格
        strategyConfig.setEntityLombokModel(true); // 实体中是否其中Lombok模式
        strategyConfig.setNaming(NamingStrategy.underline_to_camel); // 设置驼峰命名
//        strategyConfig.setInclude("sys_user","sys_role"); // 设置参与生成的数据库表，参数为可变参数，默认为数据库中全部表
//        strategyConfig.setVersionFieldName("version"); // 设置乐观锁字段名
//        strategyConfig.setLogicDeleteFieldName("delete"); // 设置逻辑删除字段
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.execute();
    }
}