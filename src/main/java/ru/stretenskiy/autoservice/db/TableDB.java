package ru.stretenskiy.autoservice.db;

import lombok.Getter;

import java.sql.SQLException;
import java.util.List;

@Getter
public abstract class TableDB<T> {

    private final DataAccessor da = DataAccessor.getDataAccessor();
    private final Converter converter = new Converter();

    public abstract int insert(T table) throws SQLException;

    public abstract int update(T table) throws SQLException;

    public abstract int delete(T table) throws SQLException;

    public abstract List<T> getTable() throws SQLException;

    public abstract T getValue(T table) throws SQLException;

}
