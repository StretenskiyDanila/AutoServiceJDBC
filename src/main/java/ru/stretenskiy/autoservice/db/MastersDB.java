package ru.stretenskiy.autoservice.db;

import ru.stretenskiy.autoservice.tables.Master;
import ru.stretenskiy.autoservice.tables.MasterCountCars;

import java.math.BigInteger;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MastersDB extends TableDB<Master> {

    @Override
    public int insert(Master master) throws SQLException {
        final String QUERY = "INSERT INTO masters(name) VALUES (?);";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, master.getName());

        return preparedStatement.executeUpdate();
    }

    @Override
    public int update(Master master) throws SQLException {
        final String QUERY = "UPDATE masters SET name = ? WHERE id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, master.getName());
        preparedStatement.setLong(2, master.getId());

        return preparedStatement.executeUpdate();
    }

    @Override
    public int delete(Master master) throws SQLException {
        final String QUERY = "DELETE FROM masters WHERE id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setLong(1, master.getId());

        return preparedStatement.executeUpdate();
    }

    @Override
    public List<Master> getTable() throws SQLException {
        final String QUERY = "SELECT * FROM masters";
        Statement statement = getDa().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY);

        List<Master> masters = new ArrayList<>();
        while (resultSet.next()) {
            masters.add(getConverter().getMaster(resultSet));
        }
        return masters;
    }

    @Override
    public Master getValue(Master table) throws SQLException {
        final String QUERY = "SELECT * FROM masters WHERE id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setLong(1, table.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return getConverter().getMaster(resultSet);
    }

    public Master getValueByName(String name) throws SQLException {
        final String QUERY = "SELECT * FROM masters WHERE name = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return getConverter().getMaster(resultSet);
    }

    public List<MasterCountCars> getMasterMaxServiceCars(Date withDate, Date beforeDate) throws SQLException {
        final String QUERY = "SELECT masters.name, " +
                "COALESCE((SELECT count_work_in_period(masters.id::integer, ?, ?)), 0) AS countCars " +
                "FROM masters ORDER BY countCars DESC LIMIT 5;";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setDate(1, withDate);
        preparedStatement.setDate(2, beforeDate);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<MasterCountCars> list = new LinkedList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            Long countCars = resultSet.getLong("countCars");
            list.add(new MasterCountCars(name, countCars));
        }
        return list;
    }

}
