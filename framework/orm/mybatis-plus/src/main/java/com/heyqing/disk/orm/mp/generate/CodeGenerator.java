package com.heyqing.disk.orm.mp.generate;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ClassName:CodeGenerator
 * Package:com.heyqing.disk.orm.mp.generate
 * Description:
 *
 * @Date:2024/1/9
 * @Author:Heyqing
 */
public class CodeGenerator {

    /**
     * 读取控制台内容
     * @param tip
     * @return
     */
    public static String scanner(String tip){
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + ": ");
        System.out.println(help);
        if (scanner.hasNext()){
            String ipt = scanner.next();
            if(StringUtils.isNotBlank(ipt)){
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip +"！");
    }

    public static void generate(){
        AutoGenerator mpg = new AutoGenerator();
        String username = scanner("用户名"),
                password = scanner("密码"),
                moduleName = scanner("模块名"),
                outputDir = scanner("输出目录");
        String[] tableNameArray = scanner("表名，多个英文逗号分隔").split(",");

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir);
        gc.setAuthor("Heyqing");
        gc.setOpen(false);
        gc.setSwagger2(false);
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setIdType(IdType.INPUT);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);

        //数据配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/hey_disk?characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true");
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(username);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent("");
        mpg.setPackageInfo(pc);

        //自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                //to do nothing
            }
        };

        //如果模板引擎是 framework
        //String templatePath = "/templates/mapper.xml.ftl";
        //如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        //自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig() {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return outputDir + File.separator + pc.getParent().replace(".",File.separator) + File.separator +"mapper" + File.separator + tableInfo;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        //配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(false);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
        strategyConfig.setInclude(tableNameArray);
        strategyConfig.setControllerMappingHyphenStyle(true);
        strategyConfig.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategyConfig);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }
}
