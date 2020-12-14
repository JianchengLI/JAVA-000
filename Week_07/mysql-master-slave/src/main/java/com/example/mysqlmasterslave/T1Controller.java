package com.example.mysqlmasterslave;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class})
public class T1Controller {

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("slaveJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/index")
    public List<Map<String, Object>> index() throws SQLException {
        log.info(dataSource.toString());
        Connection conn = dataSource.getConnection();
        log.info(conn.toString());
        conn.close();
        return jdbcTemplate.queryForList("SELECT * FROM t1");
    }

//    @Bean
//    @ConfigurationProperties("master.datasource")
//    public DataSourceProperties masterDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    public DataSource masterDataSource() {
//        DataSourceProperties dataSourceProperties = masterDataSourceProperties();
//        log.info("master datasource: {}", dataSourceProperties.getUrl());
//        return dataSourceProperties.initializeDataSourceBuilder().build();
//    }
//
//    @Bean(name="masterJdbcTemplate")
//    public JdbcTemplate masterJdbcTemplate (
//            @Qualifier("masterDataSource")  DataSource dataSource ) {
//
//        return new JdbcTemplate(dataSource);
//    }

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
