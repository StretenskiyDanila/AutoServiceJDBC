package ru.stretenskiy.autoservice.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ru.stretenskiy.autoservice.db.AutoServiceDB;
import ru.stretenskiy.autoservice.db.CarsDB;
import ru.stretenskiy.autoservice.db.MastersDB;
import ru.stretenskiy.autoservice.db.ServicingsDB;
import ru.stretenskiy.autoservice.tables.Car;
import ru.stretenskiy.autoservice.tables.Master;
import ru.stretenskiy.autoservice.tables.Servicing;

public class ServicingController {

    private final AutoServiceDB autoServiceDB = new AutoServiceDB();
    private ObservableList<Servicing> servicings;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Servicing, String> costForeignColumn;

    @FXML
    private TableColumn<Servicing, String> costOurColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField fieldCostForeign;

    @FXML
    private TextField fieldCostOur;

    @FXML
    private TextField fieldName;

    @FXML
    private Button goMainButton;

    @FXML
    private TableColumn<Servicing, String> nameColumn;

    @FXML
    private TableView<Servicing> tableServicings;

    @FXML
    private Button updateButton;

    @FXML
    void AddServices(ActionEvent event) {
        try {
            String name = fieldName.getText();
            String costOurS = fieldCostOur.getText();
            String costForeignS = fieldCostForeign.getText();
            BigDecimal costForeign = BigDecimal.valueOf(Double.parseDouble(costForeignS));
            BigDecimal costOur = BigDecimal.valueOf(Double.parseDouble(costOurS));
            Servicing servicing = new Servicing(name, costOur, costForeign);
            int countSuccessOper = autoServiceDB.insert(new ServicingsDB(), servicing);
            if (countSuccessOper >= 1) {
                servicings = FXCollections.observableList(autoServiceDB.getTable(new ServicingsDB()));
                tableServicings.setItems(servicings);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            clearField();
        }
    }

    @FXML
    void DeleteServices(ActionEvent event) {
        try {
            Servicing deleteServicing = tableServicings.getSelectionModel().getSelectedItem();
            if (deleteServicing != null) {
                int countSuccessOper = autoServiceDB.delete(new ServicingsDB(), deleteServicing);
                if (countSuccessOper >= 1) {
                    servicings = FXCollections.observableList(autoServiceDB.getTable(new ServicingsDB()));
                    tableServicings.setItems(servicings);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            clearField();
        }
    }

    @FXML
    void UpdateServices(ActionEvent event) {
        try {
            String name = fieldName.getText();
            String costOurS = fieldCostOur.getText();
            String costForeignS = fieldCostForeign.getText();
            BigDecimal costForeign = costForeignS.isEmpty() ? null : BigDecimal.valueOf(Double.parseDouble(costForeignS));
            BigDecimal costOur = costOurS.isEmpty() ? null : BigDecimal.valueOf(Double.parseDouble(costOurS));
            Servicing servicingUpd = new Servicing(name, costOur, costForeign);
            Servicing servicing = tableServicings.getSelectionModel().getSelectedItem();
            if (servicing != null) {
                servicing.setValue(servicingUpd);
                int countSuccessOper = autoServiceDB.update(new ServicingsDB(), servicing);
                if (countSuccessOper >= 1) {
                    tableServicings.refresh();
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            clearField();
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
        try {
            servicings = FXCollections.observableList(autoServiceDB.getTable(new ServicingsDB()));

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            costForeignColumn.setCellValueFactory(new PropertyValueFactory<>("costForeign"));
            costOurColumn.setCellValueFactory(new PropertyValueFactory<>("costOur"));

            tableServicings.setItems(servicings);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void clearField() {
        fieldName.setText("");
        fieldCostForeign.setText("");
        fieldCostOur.setText("");
    }

}
