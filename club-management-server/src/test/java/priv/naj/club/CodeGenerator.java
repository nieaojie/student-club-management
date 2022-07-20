package priv.naj.club;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @author: nie
 * @description: 代码生成器
 **/
public class CodeGenerator {
    // 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //路径不要写死
//        String projectPath = System.getProperty("user.dir");

        gc.setOutputDir(scanner("请输入您的路径") + "/src/main/java");
        gc.setAuthor("nieaojie");
        //生成之后是否打开资源管理器
        gc.setOpen(false);
        //重新生成时是否覆盖文件
        gc.setFileOverride(false);
        //各层文件名称方式，例如： %sAction 生成 UserAction %s 为占位符
        gc.setServiceName("%sService");
        gc.setEntityName("%sEntity");
        //设置主键生成策略，主键自动增长
        gc.setIdType(IdType.AUTO);
        //设置日期类型为只使用 java.util.date 代替
        gc.setDateType(DateType.ONLY_DATE);
//        实体属性 Swagger2 注解
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(
                "jdbc:mysql://127.0.0.1:3306/club_manage_system?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        //设置数据库的类型为mysql
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        //设置模块名
        pc.setModuleName(scanner("请输入模块名："));
        //设置父包名
        pc.setParent("priv.naj.club");
        //设置controller包名
        pc.setController("controller");
        //设置service的包的名称
        pc.setService("service");
        //设置service实现类的包的名称
        pc.setServiceImpl("service.impl");
        //设置mapper的包的名称
        pc.setMapper("mapper");
        //设置entity的包的名称
        pc.setEntity("entity");
        //设置mapper xml文件的路径
        pc.setXml("mapper.xml");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        //实体类名下划线转驼峰命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //列名下划线转驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //设置实体类的父类
        strategy.setSuperEntityClass("priv.naj.club.common.CommonAbstractEntity");
        //设置controller的父类
        strategy.setSuperControllerClass("priv.naj.club.common.CommonAbstractController");
        //设置service的父类
        strategy.setSuperServiceClass("priv.naj.club.common.CommonAbstractService");
        //设置serviceImpl的父类
        strategy.setSuperServiceImplClass("priv.naj.club.common.CommonAbstractServiceImpl");
        //设置mapper.java的父类
        strategy.setSuperMapperClass("priv.naj.club.common.CommonAbstractMapper");
        //是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(true);
        //生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        //设置entity忽略生成哪些字段,即写于父类中的公共字段
        strategy.setSuperEntityColumns("pkid", "status", "is_deleted", "creator", "create_time", "updator",
                                       "update_time");
        // 公共父类
//        strategy.setSuperControllerClass(ParentController.class);
        //驼峰转连字符@RequestMapping("/managerUserActionHistory") -> @RequestMapping("/manager-user-action-history")
        strategy.setControllerMappingHyphenStyle(false);
        //忽略数据库表的前缀(否则生成的实体、service等等会有前缀)
        strategy.setTablePrefix("sys_");

        /*************************************************************/
        //设置自定义模板
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/templates/controller2.java");
        tc.setService("/templates/service2.java");
        tc.setServiceImpl("templates/serviceImpl2.java");
        tc.setMapper("templates/mapper2.java");
        mpg.setTemplate(tc);
        /*************************************************************/

//        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
