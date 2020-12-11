/*
 * Copyright 2018 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.tuhu.sf.newcomer.task.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MssqlGenerator {
	
	/**
	 * 生成文件所在目录
	 */
	private static final String outputdir = "D://generator";
	/**
	 * 作者
	 */
	private static final String author = "someone";
	/**
	 * 数据库用户名
	 */
	private static final String dbUserName = "user_shop";
	/**
	 * 数据库密码
	 */
	private static final String dbPassword = "Itsme@999";
	/**
	 * 数据库连接url
	 */
	private static final String dbUrl = "jdbc:sqlserver://172.16.20.1:1433;DatabaseName=test";
	/**
	 * 要生成代码的数据表
	 */
	private static final String[] includeTables = new String[] {"test_user", "test_order"};
	
	
	
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputdir);
        gc.setFileOverride(true);
        gc.setActiveRecord(false);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setOpen(true);
        gc.setAuthor(author);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sWriteMapper");
        gc.setXmlName("%sWriteMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setEntitySuffix("%sDAO");
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.SQL_SERVER);
        dsc.setDriverName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dsc.setUsername(dbUserName);
        dsc.setPassword(dbPassword);
        dsc.setUrl(dbUrl);
        mpg.setDataSource(dsc);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //        strategy.setTablePrefix("app_");// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.nochange);// 表名生成策略
        strategy.setInclude(includeTables); // 需要生成的表
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        //        strategy.setSuperEntityClass("top.ibase4j.core.base.BaseModel");
        // 自定义实体，公共字段
        //        strategy.setSuperEntityColumns(
        //                new String[] { "id_", "enable_", "remark_", "create_by", "create_time", "update_by", "update_time" });
        // 自定义 mapper 父类
        strategy.setSuperMapperClass("com.tuhu.base.mapper.BaseWriteMapper");
        // 自定义 service 父类(生成后请把Service的父类删除)
        strategy.setSuperServiceClass("com.tuhu.base.service.IBaseService");
        // 自定义 service 实现类父类
        strategy.setSuperServiceImplClass("com.tuhu.base.service.impl.BaseServiceImpl");
        // 自定义 controller 父类
        strategy.setSuperControllerClass("com.tuhu.springcloud.common.controller.BaseController");
        //生成 <code>@RestController</code> 控制器
        strategy.setRestControllerStyle(true);
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);
        //        strategy.setLogicDeleteFieldName("enable");
        //【实体】是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(false);
        mpg.setStrategy(strategy);
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        //        InjectionConfig cfg = new InjectionConfig() {
        //            @Override
        //            public void initMap() {
        //                Map<String, Object> map = new HashMap<String, Object>();
        //                //                map.put("providerClass", "IBizProvider");
        //                //                map.put("providerClassPackage", "org.xshop.provider.IBizProvider");
        //                //map.put("rpcService", false);
        //                this.setMap(map);
        //            }
        //        };
        //        mpg.setCfg(cfg);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.tuhu.sf.newcomer.task");
        pc.setEntity("dataobject");
        pc.setMapper("mapper");
        pc.setXml("mapper.xml");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        mpg.setPackageInfo(pc);
        
        TemplateConfig tc  = new TemplateConfig();
        tc.setController(null);
        mpg.setTemplate(tc);
        
        mpg.execute();
    }
}
