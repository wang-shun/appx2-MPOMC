package com.dreawer.appxauth.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlSessionFactoryBean extends org.mybatis.spring.SqlSessionFactoryBean {

    private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器

    @Override
    public void setTypeAliasesPackage(String typeAliasesPackage) {

        // 获取匹配目录下的所有class文件
        ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        typeAliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(typeAliasesPackage) + "/**/*.class";

        // 加载所有Resource，然后进行遍历 
        try {
            List<String> result = new ArrayList<String>();
            Resource[] resources = resolver.getResources(typeAliasesPackage);
            if (resources != null && resources.length > 0) {
                MetadataReader metadataReader = null;
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        metadataReader = metadataReaderFactory.getMetadataReader(resource);
                        try {
                            String name = Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName();
                            if (!result.contains(name)) {
                                result.add(name);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (result.size() > 0) {
                super.setTypeAliasesPackage(StringUtils.join(result.toArray(), ","));
            } else {
                logger.warn("参数typeAliasesPackage:" + typeAliasesPackage + ",未找到任何包");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
