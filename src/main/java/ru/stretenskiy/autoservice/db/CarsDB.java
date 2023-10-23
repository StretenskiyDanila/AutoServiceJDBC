package ru.stretenskiy.autoservice.db;

import ru.stretenskiy.autoservice.tables.Car;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarsDB extends TableDB<Car> {

    @Override
    public int insert(Car table) throws SQLException {
        final String QUERY = "INSERT INTO cars(num, mark, color, is_foreign) VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, table.getCarNumber());
        preparedStatement.setString(2, table.getCarMark());
        preparedStatement.setString(3, table.getCarColor());
        preparedStatement.setInt(4, table.getIsForeign());
        return preparedStatement.executeUpdate();
    }

    @Override
    public int update(Car table) throws SQLException {
        final String QUERY = "UPDATE cars SET num = ?, color = ?, mark = ?, is_foreign = ? WHERE cars.id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, table.getCarNumber());
        preparedStatement.setString(2, table.getCarColor());
        preparedStatement.setString(3, table.getCarMark());
        preparedStatement.setInt(4, table.getIsForeign());
        preparedStatement.setLong(5, table.getId());

        return preparedStatement.executeUpdate();
    }

    @Override
    public int delete(Car table) throws SQLException {
        final String QUERY = "DELETE FROM cars WHERE cars.id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setLong(1, table.getId());

        return preparedStatement.executeUpdate();
    }

    @Override
    public List<Car> getTable() throws SQLException {
        final String QUERY = "SELECT * FROM cars";
        Statement statement = getDa().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY);
        List<Car> cars = new ArrayList<>();
        while (resultSet.next()) {
            cars.add(getConverter().getCar(resultSet));
        }
        statement.close();
        return cars;

    }

    @Override
    public Car getValue(Car table) throws SQLException {
        final String QUERY = "SELECT * FROM cars WHERE id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setLong(1, table.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return getConverter().getCar(resultSet);
    }

    public Car getValueByNum(String num) throws SQLException {
        final String QUERY = "SELECT * FROM cars WHERE num = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, num);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return getConverter().getCar(resultSet);
    }

}
