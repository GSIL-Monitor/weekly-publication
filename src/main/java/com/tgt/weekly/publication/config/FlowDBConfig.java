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
@MapperScan(basePackages = "com.tgt.weekly.publication.flow.dao", nameGenerator = DefaultBeanNameGenerator.class, sqlSessionFactoryRef = "flowSqlSessionFactory")
public class FlowDBConfig {

    @Bean(name = "flowDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.flow")
    public DataSource flowDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "flowSqlSessionFactory")
    public SqlSessionFactory flowSqlSessionFactory(@Qualifier("flowDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/flow/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "flowTransactionManager")
    public DataSourceTransactionManager flowTransactionManager(@Qualifier("flowDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "flowSqlSessionTemplate")
    public SqlSessionTemplate flowSqlSessionTemplate(@Qualifier("flowSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
