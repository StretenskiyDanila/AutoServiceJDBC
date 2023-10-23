package ru.stretenskiy.autoservice.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.stretenskiy.autoservice.WriterExcel;
import ru.stretenskiy.autoservice.db.*;
import ru.stretenskiy.autoservice.tables.Car;
import ru.stretenskiy.autoservice.tables.Master;
import ru.stretenskiy.autoservice.tables.Servicing;
import ru.stretenskiy.autoservice.tables.Work;

public class HelloController {

    private final AutoServiceDB autoServiceDB = new AutoServiceDB();
    private final WriterExcel writerExcel = new WriterExcel();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addWorks;

    @FXML
    private Button buttonClients;

    @FXML
    private Button buttonMasters;

    @FXML
    private Button buttonServices;

    @FXML
    private Button buttonDopInf;

    @FXML
    private Button buttonNewAcc;

    @FXML
    private Button buttonReport;

    @FXML
    private Text clients;

    @FXML
    private DatePicker dateWork;

    @FXML
    private ComboBox<String> listClients;

    @FXML
    private ComboBox<String> listMasters;

    @FXML
    private ComboBox<String> listServices;

    @FXML
    private Text masters;

    @FXML
    private Text masters1;

    @FXML
    private Text masters11;

    @FXML
    private Text masters12;

    @FXML
    private Text masters13;

    @FXML
    private Text services;

    @FXML
    private Text works;

    @FXML
    private Text errorAddWork;

    @FXML
    void AddWork(ActionEvent event) {
        try {
            clearText();
            String carNum = listClients.getSelectionModel().getSelectedItem().split(", ")[1];
            String masterName = listMasters.getSelectionModel().getSelectedItem();
            String serviceName = listServices.getSelectionModel().getSelectedItem();
            LocalDate localDate = dateWork.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            java.sql.Date date1 = new java.sql.Date(date.getTime());

            Car car = new CarsDB().getValueByNum(carNum);
            Master master = new MastersDB().getValueByName(masterName);
            Servicing servicing = new ServicingsDB().getValueByName(serviceName);

            Work work = new Work(null, date1, master, car, servicing);
            int countSuccess = autoServiceDB.insert(new WorksDB(), work);
            if (countSuccess == 0) {
                errorAddWork.setVisible(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void GoClients(ActionEvent event) {
        try {
            buttonClients.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("cars-view.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void GoMasters(ActionEvent event) {
        try {
            buttonMasters.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("masters-view.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void GoServices(ActionEvent event) {
        try {
            buttonServices.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("servicing-view.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void GoDopInf(ActionEvent event) {
        try {
            buttonDopInf.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("dopinfo-view.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CreateReportOnWorks(ActionEvent event) throws SQLException, IOException {
        List<Work> workList = autoServiceDB.getTable(new WorksDB());
        writerExcel.writeWorks(workList, "report.xlsx");
    }

    @FXML
    void GoNewAcc(ActionEvent event) {
        try {
            buttonDopInf.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("logout-view.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        try {
            clearText();
            listClients.getItems().clear();
            listMasters.getItems().clear();
            listServices.getItems().clear();

            ObservableList<String> cars = FXCollections.observableList(autoServiceDB.getTable(new CarsDB())
                    .stream().map(x -> x.getCarMark() + ", " + x.getCarNumber()).toList());
            ObservableList<String> masters = FXCollections.observableList(autoServiceDB.getTable(new MastersDB())
                    .stream().map(Master::getName).toList());
            ObservableList<String> services = FXCollections.observableList(autoServiceDB.getTable(new ServicingsDB())
                    .stream().map(Servicing::getName).toList());

            listClients.setItems(cars);
            listMasters.setItems(masters);
            listServices.setItems(services);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearText() {
        errorAddWork.setVisible(false);
    }

}
