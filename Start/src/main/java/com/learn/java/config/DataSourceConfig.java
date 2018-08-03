package com.learn.java.config;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
//三、设置扫描器
@MapperScan(basePackages = "com.zlx.user.dal.mapper", sqlSessionFactoryRef = "sqlSessionFactory1")
public class DataSourceConfig {
	// 一、创建数据源
	@Bean(name = "mysqldataSource")
	public DataSource dataSource1(@Value("${mysql.db.url}") String url,
			@Value("${mysql.db.passwd}") String passwd, @Value("${mysql.db.user}") String userName) {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(passwd);
		dataSource.setMaxWait(10000);
		dataSource.setMaxActive(100);
		dataSource.setInitialSize(2);
		dataSource.setMinIdle(0);
		dataSource.setValidationQuery("select 1");
		dataSource.setTimeBetweenEvictionRunsMillis(300000);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestWhileIdle(true);

		try {
			dataSource.setFilters("stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			dataSource.init();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return dataSource;
	}

	// 二、创建SqlSessionFactory
	@Bean(name = "sqlSessionFactory1")
	public SqlSessionFactory sqlSessionFactoryBean1(@Qualifier("mysqldataSource")DataSource mysqldataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

		sqlSessionFactoryBean.setDataSource(mysqldataSource);
		sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations(new String[]{"classpath:mapper/*.xml"}));

		return sqlSessionFactoryBean.getObject();
	}

	 public Resource[] resolveMapperLocations(String[] mapperLocations) {
	        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
	        List<Resource> resources = new ArrayList();
	        if (mapperLocations != null) {
	            for (String mapperLocation : mapperLocations) {
	                try {
	                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
	                    resources.addAll(Arrays.asList(mappers));
	                } catch (IOException localIOException) {
	                }
	            }
	        }
	        return (Resource[]) resources.toArray(new Resource[resources.size()]);
	    }
}
