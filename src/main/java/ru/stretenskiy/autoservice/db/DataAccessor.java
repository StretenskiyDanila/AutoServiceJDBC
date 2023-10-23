package ru.stretenskiy.autoservice.db;

import lombok.Getter;
import ru.stretenskiy.autoservice.config.AppProperties;

import java.sql.*;

public class DataAccessor {

    private static final AppProperties appProperties = AppProperties.getInstance();

    private static final String URL = appProperties.getProperties("db_url");
    private static final String USER_NAME = appProperties.getProperties("db_user");
    private static final String PASSWORD = appProperties.getProperties("db_pwd");

    @Getter
    private static DataAccessor dataAccessor = new DataAccessor(URL, USER_NAME, PASSWORD);
    @Getter
    private Connection connection;

    private DataAccessor(String url, String userName, String password) {
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
