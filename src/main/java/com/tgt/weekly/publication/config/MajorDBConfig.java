package com.tgt.weekly.publication.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/14
 * @Time: 17:34
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Configuration
@MapperScan(basePackages = "com.tgt.weekly.publication.major.dao", nameGenerator = DefaultBeanNameGenerator.class, sqlSessionFactoryRef = "majorSqlSessionFactory")
public class MajorDBConfig {

    @Bean(name = "majorDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.major")
    @Primary
    public DataSource majorDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "majorSqlSessionFactory")
    @Primary
    public SqlSessionFactory majorSqlSessionFactory(@Qualifier("majorDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/major/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "majorTransactionManager")
    @Primary
    public DataSourceTransactionManager majorTransactionManager(@Qualifier("majorDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "majorSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate majorSqlSessionTemplate(@Qualifier("majorSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
