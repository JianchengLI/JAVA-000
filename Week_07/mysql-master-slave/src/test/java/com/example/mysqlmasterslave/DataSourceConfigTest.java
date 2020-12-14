package com.example.mysqlmasterslave;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = {MysqlMasterSlaveApplication.class})
@RunWith(SpringRunner.class)
@Slf4j
public class DataSourceConfigTest {
    @Autowired
    @Qualifier("masterJdbcTemplate")
    private JdbcTemplate masterJdbcTemplate;

    @Autowired
    @Qualifier("slaveJdbcTemplate")
    private JdbcTemplate slaveJdbcTemplate;

    @Before
    public void setUp() {
        log.info(masterJdbcTemplate.toString());
        log.info(slaveJdbcTemplate.toString());
    }

    @Test
    public void should_can_query_select_given_both_master_and_slave_jdbcTemplate() {
        List<Map<String, Object>> resultsMaster = masterJdbcTemplate.queryForList("SELECT * FROM t1");
        List<Map<String, Object>> resultsSlave = slaveJdbcTemplate.queryForList("SELECT * FROM t1");
        Assert.assertTrue(!resultsMaster.isEmpty());
        Assert.assertTrue(!resultsSlave.isEmpty());
        Assert.assertEquals(resultsMaster, resultsSlave);
    }

    @Test
    public void shou_throw_exception_when_insert_into_slave() {
        try {
            slaveJdbcTemplate.update("INSERT INTO t1(id) VALUES(99)");
        } catch ( Exception e) {
            Assert.assertTrue(e instanceof UncategorizedSQLException);
            Assert.assertTrue(e.getMessage().contains("The MySQL server is running with the --read-only option so it cannot execute this statement;"));
        }
    }
}
