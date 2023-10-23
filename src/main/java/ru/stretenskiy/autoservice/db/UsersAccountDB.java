package ru.stretenskiy.autoservice.db;

import ru.stretenskiy.autoservice.tables.Car;
import ru.stretenskiy.autoservice.tables.UsersAccount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsersAccountDB extends TableDB<UsersAccount>{
    @Override
    public int insert(UsersAccount table) throws SQLException {
        final String QUERY = "INSERT INTO users_account(user_login, user_psw) VALUES (?, ?);";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, table.getUserLogin());
        preparedStatement.setString(2, table.getUserPsw());
        return preparedStatement.executeUpdate();
    }

    @Override
    public int update(UsersAccount table) throws SQLException {
        final String QUERY = "UPDATE users_account SET user_login = ?, user_psw = ? WHERE id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, table.getUserLogin());
        preparedStatement.setString(2, table.getUserPsw());
        preparedStatement.setLong(3, table.getId());

        return preparedStatement.executeUpdate();
    }

    @Override
    public int delete(UsersAccount table) throws SQLException {
        final String QUERY = "DELETE FROM users_account WHERE id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setLong(1, table.getId());

        return preparedStatement.executeUpdate();
    }

    @Override
    public List<UsersAccount> getTable() throws SQLException {
        final String query = "SELECT * FROM users_account";
        Statement statement = getDa().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<UsersAccount> usersAccounts = new ArrayList<>();
        while (resultSet.next()) {
            usersAccounts.add(getConverter().getUsersAccount(resultSet));
        }

        return usersAccounts;
    }

    @Override
    public UsersAccount getValue(UsersAccount table) throws SQLException {
        final String QUERY = "SELECT * FROM users_account WHERE id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setLong(1, table.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return getConverter().getUsersAccount(resultSet);
    }

    public UsersAccount getValueByLogin(String userLogin, String psw) throws SQLException {
        final String QUERY = "SELECT * FROM users_account WHERE user_login = ? AND user_psw = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, userLogin);
        preparedStatement.setString(2, psw);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            throw new SQLException("Нет такого пользователя");
        }
        return getConverter().getUsersAccount(resultSet);
    }

    public boolean isValueByLogin(String userLogin, String psw) throws SQLException {
        final String QUERY = "SELECT * FROM users_account WHERE user_login = ? AND user_psw = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, userLogin);
        preparedStatement.setString(2, psw);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}
