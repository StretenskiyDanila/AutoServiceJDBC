package ru.stretenskiy.autoservice.db;

import ru.stretenskiy.autoservice.tables.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Converter {

    private final DataAccessor da = DataAccessor.getDataAccessor();

    private Master getMasterById(Long masterId) throws SQLException {
        String query = "SELECT * FROM masters WHERE id = ?";
        PreparedStatement preparedStatement = da.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, masterId);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();

        return getMaster(rs);
    }

    private Servicing getServicingById(Long servicingId) throws SQLException {
        String query = "SELECT * FROM services WHERE id = ?";
        PreparedStatement preparedStatement = da.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, servicingId);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();

        return getServicing(rs);
    }

    private Car getCarById(Long carId) throws SQLException {
        String query = "SELECT * FROM cars WHERE id = ?";
        PreparedStatement preparedStatement = da.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, carId);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();

        return getCar(rs);
    }

    public Work getWork(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Date dateWork = resultSet.getDate("date_work");

        Long masterId = resultSet.getLong("master_id");
        Long servicingId = resultSet.getLong("service_id");
        Long carId = resultSet.getLong("car_id");

        Master master = getMasterById(masterId);
        Servicing servicing = getServicingById(servicingId);
        Car car = getCarById(carId);

        return new Work(id, dateWork, master, car, servicing);
    }

    public Master getMaster(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Master(id, name);
    }

    public Servicing getServicing(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        BigDecimal costOur = resultSet.getBigDecimal("cost_our");
        BigDecimal costForeign = resultSet.getBigDecimal("cost_foreign");

        return new Servicing(id, name, costOur, costForeign);
    }

    public Car getCar(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String num = resultSet.getString("num");
        String mark = resultSet.getString("mark");
        String color = resultSet.getString("color");
        Integer isForeign = resultSet.getInt("is_foreign");

        return new Car(id, num, color, mark, isForeign);
    }

    public UsersAccount getUsersAccount(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String userLogin = resultSet.getString("user_login");
        String userPsw = resultSet.getString("user_psw");

        return new UsersAccount(id, userLogin, userPsw);
    }

}
