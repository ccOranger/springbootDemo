package com.example.ldemo.plugin;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * @package:        com.example.ldemo.plugin
 * @className:      MybatisJavaTypeResolver
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
public class MybatisJavaTypeResolver extends JavaTypeResolverDefaultImpl{

        /**
         * 将tinyint转换为Integer，这里是关键所在
         */
        public MybatisJavaTypeResolver() {
            super();
            super.typeMap.put(-6, new JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Integer.class.getName())));
        }
}
