package me.wangcai.myblog.utils;

import me.wangcai.myblog.config.DataBaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseUtil {

    private Connection conn;

    private String TABLE;

    public DataBaseUtil(String table) throws SQLException, ClassNotFoundException {
        this.TABLE = table;
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://" + DataBaseConfig.URL + ":" + DataBaseConfig.PORT + "?serverTimezone=UTC",DataBaseConfig.USERNAME,DataBaseConfig.PASSWORD);
    }

    public Connection getConnection() {
        return conn;
    }
}
