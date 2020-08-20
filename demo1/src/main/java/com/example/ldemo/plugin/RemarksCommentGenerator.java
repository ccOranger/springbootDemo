package com.example.ldemo.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @package:        com.example.ldemo.plugin
 * @className:      RemarksCommentGenerator
 * @description:    类作用描述
 * @author:         李臣臣
 * @createDate:     2019/4/16 14:15
 * @updateUser:     李臣臣
 * @updateDate:     2019/4/16 14:15
 * @updateRemark:   The modified content
 * @version:        1.0
 * <p>copyright: Copyright (c) 2019/4/16</p>
 *
 */
public class RemarksCommentGenerator extends DefaultCommentGenerator {
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("import io.swagger.annotations.ApiModel;");
        topLevelClass.addJavaDocLine("import io.swagger.annotations.ApiModelProperty;");

        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * 实体名称 "+introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(" * @projectName: iot-platform");
        topLevelClass.addJavaDocLine(" * @package: com.rayeye.iot.persist.vo");
        topLevelClass.addJavaDocLine(" * @className: ${TYPE_NAME}");
        topLevelClass.addJavaDocLine(" * @description: Describes the function of the class");
        topLevelClass.addJavaDocLine(" * @author: 李臣臣");
        topLevelClass.addJavaDocLine(" * @createDate: "+new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        topLevelClass.addJavaDocLine(" * @updateUser: 李臣臣");
        topLevelClass.addJavaDocLine(" * @updateRemark: The modified content");
        topLevelClass.addJavaDocLine(" * @version: 1.0");
        topLevelClass.addJavaDocLine(" * <p>Copyright: Copyright (c) 2018</p>");
        topLevelClass.addJavaDocLine(" */");


        topLevelClass.addJavaDocLine("@ApiModel(value = \""+introspectedTable.getFullyQualifiedTable().getDomainObjectName()+"\",description = \""+introspectedTable.getFullyQualifiedTable().getDomainObjectName()+"\")");

    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        String remark = introspectedColumn.getRemarks();
        String columnName = introspectedColumn.getActualColumnName();
        List<IntrospectedColumn> primaryKey = introspectedTable.getPrimaryKeyColumns();
        for (IntrospectedColumn pk : primaryKey) {
            if(columnName.equals(pk.getActualColumnName())){
                remark +=" (主健ID)";
                continue;   //主健属性上无需生明可选项跟必填项介绍
            }
            if (StringUtility.stringHasValue(remark)) {
                remark += introspectedColumn.isNullable() ? "(可选项)" : "(必填项)";
            }
        }
        String defaultValue = introspectedColumn.getDefaultValue();
        remark += null != defaultValue ? "  (默认值为: "+defaultValue+")" : " (无默认值)";
        field.addJavaDocLine("/** "+ remark+" */");

        field.addJavaDocLine("@ApiModelProperty(value =  \""+introspectedColumn.getRemarks()+"\", name = \""+columnName+"\", example =\""+defaultValue+"\")");

    }
}
