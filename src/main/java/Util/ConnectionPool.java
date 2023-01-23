package Util;

import com.zaxxer.hikari.HikariDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionPool {



    private static HikariDataSource dataSource;

    static {
        try {

            dataSource = new HikariDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/hospitaldb");
            dataSource.setUsername("root");
            dataSource.setPassword("12345");

            dataSource.setMinimumIdle(100);
            dataSource.setMaximumPoolSize(2000);//The maximum number of connections, idle or busy, that can be present in the pool.
            dataSource.setAutoCommit(true);
            dataSource.setLoginTimeout(3);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}


