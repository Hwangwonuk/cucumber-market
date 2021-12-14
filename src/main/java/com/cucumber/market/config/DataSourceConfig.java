package com.cucumber.market.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/*
 @PropertySource
 : 지정한 위치의 프로퍼티 파일을 읽어 스프링 Environment 오브젝트에 저장
   @Configuration 클래스와 함께 사용
 */
@Configuration
@EnableTransactionManagement // 애너테이션 기반 트랜잭션 기능 활성화
@PropertySource(value = {"classpath:/application-dev.properties", "classpath:/application-prod.properties"})
public class DataSourceConfig {

    @Bean(name = "masterDataSource") // 지정된 prefix로 시작된 속성값만 사용
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "routingDataSource")
    public DataSource routingDataSource(@Qualifier(value = "masterDataSource") DataSource masterDataSource,
                                        @Qualifier(value = "slaveDataSource") DataSource slaveDataSource) {

        AbstractRoutingDataSource routingDataSource = new RoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();

        // targetDataSources Map 객체에 분기할 서버들의 DataSource 빈을 저장
        targetDataSources.put("master",masterDataSource);
        targetDataSources.put("slave", slaveDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    @Bean(name = "proxyDataSource")
    public DataSource proxyDataSource(@Qualifier(value = "routingDataSource") DataSource routingDataSource) { // 빈 이름 중복을 막기위해 이름지정

        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier(value = "proxyDataSource") DataSource dataSource) {

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);

        return transactionManager;
    }
}
