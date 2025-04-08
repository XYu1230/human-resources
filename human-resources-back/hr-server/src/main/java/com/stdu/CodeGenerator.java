package com.stdu;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.model.ClassAnnotationAttributes;

import java.sql.Types;
import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        String database = "jdbc:mysql://localhost:3306/human_resources?useSSL=false";
        String table = "organization";
        String tablePrefix = "tb_";
        String path = "C:\\Users\\lenovo\\Desktop\\stdu\\XYu1230\\human-resources\\human-resources-back\\src\\main";

        //取到当前java文件的绝对路径
        FastAutoGenerator.create(database, "root", "123456")
                .globalConfig(builder -> {
                    builder.author("XYu1230") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(path + "\\java") // 指定输出目录
                            .disableOpenDir();
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.stdu") // 设置父包名
//                                .moduleName("system") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, path + "\\resources\\com\\stdu\\mapper")) // 设置mapperXml生成路径
                )
                .strategyConfig(builder ->
                        builder.addInclude(table) // 设置需要生成的表名
                                .addTablePrefix(tablePrefix) // 设置过滤表前缀
                                .entityBuilder()
                                .enableFileOverride()
                                .enableLombok(new ClassAnnotationAttributes("@Data","lombok.Data"))
                                .controllerBuilder()
                                .enableRestStyle()
                )
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}