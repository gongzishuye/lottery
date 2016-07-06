package monitor.dao;

import monitor.model.Monitor;
import monitor.utils.DateUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by chenlu11 on 2016/1/21.
 */
@Component
public class MonitorDao {


    private JdbcTemplate jdbcTemplate;

    public MonitorDao() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        this.jdbcTemplate = (JdbcTemplate)ac.getBean("jdbcTemplate");
    }

    public List<Monitor> queryDates() {
        Date currData = new Date();
        String beginDate = DateUtil.toYmdFromDate(DateUtil.getLastDays(currData, 1));

        String sql = "select * from monitor where timer < ?;";
        Object[] params = {beginDate};
        return query(sql, params);
    }

    private List<Monitor> query(String sql, Object[] params) {
        return jdbcTemplate.query(sql, params, new RowMapper<Monitor>() {
            @Override
            public Monitor mapRow(ResultSet resultSet, int i) throws SQLException {
                Monitor monitor = new Monitor();
                monitor.setNumber(resultSet.getInt("number"));
                monitor.setTimer(resultSet.getString("timer"));
                return monitor;
            }
        });
    }

    public List<Monitor> readFromMysql() {
        return queryDates();
    }

    public static void main(String[] args) {
        MonitorDao monitorDao = new MonitorDao();
        List<Monitor> list = monitorDao.readFromMysql();
        if (list.size() != 0) {
            System.out.println(list.get(0).getTimer());
        }
    }
}
