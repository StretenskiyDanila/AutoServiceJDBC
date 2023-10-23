package ru.stretenskiy.autoservice.db;

import ru.stretenskiy.autoservice.tables.CostCars;
import ru.stretenskiy.autoservice.tables.Servicing;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ServicingsDB extends TableDB<Servicing> {

    @Override
    public int insert(Servicing servicing) throws SQLException {
        final String QUERY = "INSERT INTO services(name, cost_our, cost_foreign) VALUES (?, ?, ?);";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, servicing.getName());
        preparedStatement.setBigDecimal(2, servicing.getCostOur());
        preparedStatement.setBigDecimal(3, servicing.getCostForeign());
        return preparedStatement.executeUpdate();
    }

    @Override
    public int update(Servicing servicing) throws SQLException {
        final String QUERY = "UPDATE services SET name = ?, cost_our = ?, cost_foreign = ? WHERE id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, servicing.getName());
        preparedStatement.setBigDecimal(2, servicing.getCostOur());
        preparedStatement.setBigDecimal(3, servicing.getCostForeign());
        preparedStatement.setLong(4, servicing.getId());

        return preparedStatement.executeUpdate();
    }

    @Override
    public int delete(Servicing servicing) throws SQLException {
        final String QUERY = "DELETE FROM services WHERE id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setLong(1, servicing.getId());

        return preparedStatement.executeUpdate();
    }

    @Override
    public List<Servicing> getTable() throws SQLException {
        final String QUERY = "SELECT * FROM services";
        Statement statement = getDa().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(QUERY);

        List<Servicing> servicings = new ArrayList<>();
        while (resultSet.next()) {
            servicings.add(getConverter().getServicing(resultSet));
        }
        return servicings;
    }

    @Override
    public Servicing getValue(Servicing table) throws SQLException {
        final String QUERY = "SELECT * FROM services WHERE id = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setLong(1, table.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return getConverter().getServicing(resultSet);
    }

    public Servicing getValueByName(String name) throws SQLException {
        final String QUERY = "SELECT * FROM services WHERE name = ?";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return getConverter().getServicing(resultSet);
    }

    public CostCars getCostInPeriod(Date date1, Date date2) throws SQLException {
        final String QUERY = "SELECT " +
                "SUM(CASE WHEN cars.is_foreign = 0 THEN services.cost_our ELSE 0 END) AS cost_our, " +
                "SUM(CASE WHEN cars.is_foreign = 1 THEN services.cost_foreign ELSE 0 END) AS cost_foreign " +
                "FROM works " +
                "JOIN services ON works.service_id = services.id " +
                "JOIN cars ON works.car_id = cars.id " +
                "WHERE date_work >= ? AND date_work <= ?;";
        PreparedStatement preparedStatement = getDa().getConnection().prepareStatement(QUERY);
        preparedStatement.setDate(1, date1);
        preparedStatement.setDate(2, date2);
        ResultSet resultSet = preparedStatement.executeQuery();
        BigDecimal costOur = new BigDecimal(0);
        BigDecimal costForeign = new BigDecimal(0);
        if (resultSet.next()) {
            costOur = resultSet.getBigDecimal("cost_our");
            costForeign = resultSet.getBigDecimal("cost_foreign");
        }
        return new CostCars(costOur, costForeign);
    }

}
