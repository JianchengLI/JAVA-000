package com.example.mysqlmasterslave;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties("master.datasource")
    public DataSourceProperties masterDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource masterDataSource() {
        DataSourceProperties dataSourceProperties = masterDataSourceProperties();
        log.info("master datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name="masterJdbcTemplate")
    public JdbcTemplate masterJdbcTemplate (
            @Qualifier("masterDataSource")  DataSource dataSource ) {

        return new JdbcTemplate(dataSource);
    }

    @Bean
    @ConfigurationProperties("slave.datasource")
    public DataSourceProperties slaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource slaveDataSource() {
        DataSourceProperties dataSourceProperties = slaveDataSourceProperties();
        log.info("slave datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name="slaveJdbcTemplate")
    public JdbcTemplate slaveJdbcTemplate (
            @Qualifier("slaveDataSource")  DataSource dataSource ) {

        return new JdbcTemplate(dataSource);
    }
}
