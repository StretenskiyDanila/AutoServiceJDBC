package ru.stretenskiy.autoservice.db;

import ru.stretenskiy.autoservice.tables.Work;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WorksDB extends TableDB<Work> {

    @Override
    public int insert(Work work) throws SQLException {
        final String QUERY = "INSERT INTO works(date_work, master_id, car_id, service_id) VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setDate(1, work.getDateWork());
        preparedStatement.setLong(2, work.getMaster().getId());
        preparedStatement.setLong(3, work.getCar().getId());
        preparedStatement.setLong(4, work.getServicing().getId());
        return preparedStatement.executeUpdate();
    }

    @Override
    public int update(Work table) throws SQLException {
        final String QUERY = "UPDATE works SET date_work = ?, master_id = ?, car_id = ?, service_id = ? WHERE id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setDate(1, table.getDateWork());
        preparedStatement.setLong(2, table.getMaster().getId());
        preparedStatement.setLong(3, table.getCar().getId());
        preparedStatement.setLong(4, table.getServicing().getId());

        return preparedStatement.executeUpdate();
    }

    @Override
    public int delete(Work table) throws SQLException {
        final String QUERY = "DELETE FROM works WHERE id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setLong(1, table.getId());

        return preparedStatement.executeUpdate();
    }

    @Override
    public List<Work> getTable() throws SQLException {
        final String query = "SELECT * FROM works";
        Statement statement = getDa().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<Work> works = new ArrayList<>();
        while (resultSet.next()) {
            works.add(getConverter().getWork(resultSet));
        }

        return works;
    }

    @Override
    public Work getValue(Work table) throws SQLException {
        final String QUERY = "SELECT * FROM works WHERE id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setLong(1, table.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return getConverter().getWork(resultSet);
    }

}
