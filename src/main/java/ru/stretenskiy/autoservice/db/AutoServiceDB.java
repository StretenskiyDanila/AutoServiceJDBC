package ru.stretenskiy.autoservice.db;

import java.sql.SQLException;
import java.util.List;

public class AutoServiceDB {

    public <T> List<T> getTable (TableDB<T> tableDB) throws SQLException {
        return tableDB.getTable();
    }

    public <T> T getValue(TableDB<T> tableDB, T table) throws SQLException {
        return tableDB.getValue(table);
    }

    public <T> int insert(TableDB<T> tableDB, T table) throws SQLException {
        return tableDB.insert(table);
    }

    public <T> int update(TableDB<T> tableDB, T table) throws  SQLException {
        return tableDB.update(table);
    }

    public <T> int delete(TableDB<T> tableDB, T table) throws SQLException {
        return tableDB.delete(table);
    }

}
