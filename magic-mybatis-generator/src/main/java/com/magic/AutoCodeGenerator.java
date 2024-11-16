package com.magic;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;

/**
 * 代码生成器
 * mybatis-plus-generator == 3.5.2
 *
 * @author Magic Tang
 * @date 2024/09/06
 *
 * 使用方法： 需要手动修改的3处，已标注
 */
public class AutoCodeGenerator {
    /**
     * 项目根目录
     */
    private static final String project_root_path = System.getProperty("user.dir");

    /**
     * 注释@author 名称
     */
    private static final String author = "1950735817@qq.com";

    /**
     * 忽略的表前缀
     */
    private static final String[] ignore_table_prefix = new String[]{"mt_", "sys_"};

    /**
     * 父包全限定类名
     * ===========1.手动修改设置===========
     */
    private static final String parent_class_name = "com.magic.module";

    /**
     * 将要生成的模块名称
     * ===========2.手动修改设置===========
     */
    private static final String target_module_name = "sys2";

    /**
     * 要生成的表名
     * - 设置数据库表名称. 如果不设置，则会将数据库中所有表都生成。（注意：需要与数据库中表名称一致，也就是前缀也需添加）
     * ===========3.手动修改设置。===========
     */
    private static final String source_table_names = "sys_user";




    public static void main(String[] args) {
        // 1、数据源配置
        DataSourceConfig.Builder datasourceBuilder = new DataSourceConfig.Builder(
                "jdbc:mysql://localhost:3306/testdb?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC&remarks=true&useInformationSchema=true",
                "root",
                "magictang"
        );

        // 2、创建代码生成器对象
        FastAutoGenerator generator = FastAutoGenerator.create(datasourceBuilder);

        // 3、全局配置
        generator.globalConfig(builder -> {
            // 设置作者
            builder.author(author)
                    // 定义生成的实体类中日期类型
                    .dateType(DateType.ONLY_DATE)
                    // 注释时间策略。
                    .commentDate("yyyy-MM-dd")
                    // 生成后是否打开资源管理器:否
                    .disableOpenDir()
                    // 开启 swagger 模式。如果开启，需要导入 swagger 的 pom 依赖
                    .enableSwagger()
                    // 指定输出目录
                    .outputDir(project_root_path  + "/" + "/src/main/java");
        });

        // 4、包配置
        generator.packageConfig(builder -> {
            //   ===========1.手动修改设置===========
            builder.parent(parent_class_name)
                    //   ===========2.手动修改设置===========
                    .moduleName(target_module_name)
                    // 设置生成的 控制层 文件夹名称
                    .controller("controller")
                    // 设置生成的 实体类 文件夹名称
                    .entity("entity")
                    // 设置生成的 服务层 文件夹名称
                    .service("service")
                    // 设置生成的 映射层 文件夹名称
                    .mapper("mapper")
                    // mapper.xml 文件生成路径。
                    // 单模块项目下，文件路径默认即可。 多模块项目中，需要在中间多添加一个子模块路径，比如这里是子模块: magic-server
                    .pathInfo(Collections.singletonMap(OutputFile.xml, project_root_path + "/magic-server" + "/src/main/resources/mapper"));
        });

        // 5、策略配置
        generator.strategyConfig(builder -> {
            // 设置数据库表名称. 如果不设置，则会将数据库中所有表都生成。（注意：需要与数据库中表名称一致，前缀也需添加）
            // ===========3.手动修改设置。===========
            builder.addInclude(source_table_names)
                    // 过滤表前缀，生成的类名会去掉这个前缀
                    .addTablePrefix(ignore_table_prefix)

                    // 第一阶段
                    // 是否生成 entity：是
                    .entityBuilder()
                    // 开启lombok
                    .enableLombok()
                    // 设置生成的实体类名称. 默认配置不带 Entity 后缀，我习惯添加上
                    .convertFileName(entityName -> entityName + "Entity")
                    // 开启实体时字段注解。 会在生成的实体类的字段上，添加注解： @TableField("nickname")
                    .enableTableFieldAnnotation()
                    // 设置主键Id生成策略，设置为默认的雪花算法(ASSIGN_ID)
                    .idType(IdType.ASSIGN_ID)
                    // 逻辑删除字段名。（与数据库中字段对应）
                    .logicDeleteColumnName("deleted")
                    // 逻辑删除属性名。（定义在实体中的属性）。 会在生成的实体类的字段上，添加注解：@TableLogic
                    .logicDeletePropertyName("deleted")
                    // 自动填充字段。 会在实体类的该字段上追加注解[@TableField(value = "create_time", fill = FieldFill.INSERT)]
                    .addTableFills(new Column("create_time", FieldFill.INSERT))
                    // 自动填充字段。 会在实体类的该字段上追加注解[@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)]
                    .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE));

                    // 第二阶段
//                    .mapperBuilder()
//                    // 开启 @Mapper 注解。 会在生成的 mapper 类上，添加注解：@Mapper
//                    .enableMapperAnnotation()
//                    // 启用 BaseResultMap 生成。 会在 mapper.xml文件生成[通用查询映射结果]配置。
//                    .enableBaseResultMap()
//                    // 启用 BaseColumnList。 会在mapper.xml文件生成[通用查询结果列 ]配置
//                    .enableBaseColumnList()

                    // 第三阶段
//                    .serviceBuilder()
//                    // 设置生成的实体类名称。 默认配置名称前有个I，我习惯添去掉
//                    .convertServiceFileName(serviceName -> serviceName + "Service")
//                    // 第四阶段
//                    .controllerBuilder()
//                    // 开启 @RestController 注解。 会在生成的 Controller 类上，添加注解：@RestController
//                    .enableRestStyle()

                    // 开启驼峰转连字符
//                    .enableHyphenStyle();
        });

        // 6. 模板引擎配置，默认为 Velocity , 可选模板引擎 Freemarker 或 Beetl
        // generator.templateEngine(new FreemarkerTemplateEngine());

        generator.execute();

    }

}
