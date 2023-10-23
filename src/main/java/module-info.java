module ru.stretenskiy.autoservice {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires org.apache.commons.codec;
    requires fastexcel;


    opens ru.stretenskiy.autoservice to javafx.fxml;
    exports ru.stretenskiy.autoservice;
    exports ru.stretenskiy.autoservice.controller;
    opens ru.stretenskiy.autoservice.controller to javafx.fxml;
    exports ru.stretenskiy.autoservice.db;
    opens ru.stretenskiy.autoservice.db to javafx.fxml;
    exports ru.stretenskiy.autoservice.tables to javafx.fxml;
    opens ru.stretenskiy.autoservice.tables;
}