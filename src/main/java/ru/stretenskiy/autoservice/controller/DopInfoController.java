package ru.stretenskiy.autoservice.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import ru.stretenskiy.autoservice.WriterExcel;
import ru.stretenskiy.autoservice.db.AutoServiceDB;
import ru.stretenskiy.autoservice.db.CarsDB;
import ru.stretenskiy.autoservice.db.MastersDB;
import ru.stretenskiy.autoservice.db.ServicingsDB;
import ru.stretenskiy.autoservice.tables.CostCars;
import ru.stretenskiy.autoservice.tables.Master;
import ru.stretenskiy.autoservice.tables.MasterCountCars;

public class DopInfoController {

    private final AutoServiceDB autoServiceDB = new AutoServiceDB();
    private ObservableList<MasterCountCars> masters;
    private ObservableList<CostCars> costCars;
    private final WriterExcel writerExcel = new WriterExcel();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button accButton;

    @FXML
    private DatePicker beforeData;


    @FXML
    private DatePicker withData;

    @FXML
    private TableColumn<CostCars, String> costForeignColumn;

    @FXML
    private TableColumn<CostCars, String> costOurColumn;

    @FXML
    private TableColumn<MasterCountCars, String> countCarsColumn;

    @FXML
    private Button findButton;

    @FXML
    private Button goMainButton;

    @FXML
    private TableColumn<MasterCountCars, String> nameColumn;

    @FXML
    private TableView<CostCars> tableCost;

    @FXML
    private TableView<MasterCountCars> tableName;

    @FXML
    void AccCost(ActionEvent event) {
        try {
            LocalDate localDate = withData.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            java.sql.Date withDate = new java.sql.Date(date.getTime());

            localDate = beforeData.getValue();
            instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            date = Date.from(instant);
            java.sql.Date beforeDate = new java.sql.Date(date.getTime());

            CostCars costCar = new ServicingsDB().getCostInPeriod(withDate, beforeDate);
            costCars = FXCollections.observableList(Collections.singletonList(costCar));
            tableCost.setItems(costCars);
            writerExcel.writeCostService(costCar, withDate, beforeDate, "reportCostService.xlsx");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void FindMasters(ActionEvent event) {
        try {
            LocalDate localDate = withData.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            java.sql.Date withDate = new java.sql.Date(date.getTime());

            localDate = beforeData.getValue();
            instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            date = Date.from(instant);
            java.sql.Date beforeDate = new java.sql.Date(date.getTime());

            List<MasterCountCars> list = new MastersDB().getMasterMaxServiceCars(withDate, beforeDate);
            masters = FXCollections.observableList(list);
            tableName.setItems(masters);tableCost.setItems(costCars);
            writerExcel.writeCountMaster(list, withDate, beforeDate, "reportCountMaster.xlsx");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goMain(ActionEvent event) {
        try {
            goMainButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("hello-view.fxml"));
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
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countCarsColumn.setCellValueFactory(new PropertyValueFactory<>("countCars"));

        costForeignColumn.setCellValueFactory(new PropertyValueFactory<>("allCostForeign"));
        costOurColumn.setCellValueFactory(new PropertyValueFactory<>("allCostOur"));

    }

}
