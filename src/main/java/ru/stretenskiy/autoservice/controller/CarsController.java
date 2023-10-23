package ru.stretenskiy.autoservice.controller;

import java.io.IOException;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.stretenskiy.autoservice.db.AutoServiceDB;
import ru.stretenskiy.autoservice.db.CarsDB;
import ru.stretenskiy.autoservice.tables.Car;

public class CarsController {

    private final AutoServiceDB autoServiceDB = new AutoServiceDB();
    private ObservableList<Car> cars;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField fieldColor;

    @FXML
    private TextField fieldForeign;

    @FXML
    private TextField fieldMark;

    @FXML
    private TextField fieldNum;

    @FXML
    private TableColumn<Car, String> foreignColumn;

    @FXML
    private TableColumn<Car, String> markColumn;

    @FXML
    private TableColumn<Car, String> numColumn;

    @FXML
    private TableColumn<Car, String> colorColumn;

    @FXML
    private TableView<Car> tableCars;

    @FXML
    private Button goMainButton;

    @FXML
    private Button updateButton;

    @FXML
    private Text errorAdd;

    @FXML
    private Text errorDelete;


    @FXML
    void AddCars(ActionEvent event) {
        try {
            clearText();
            String num = fieldNum.getText();
            String color = fieldColor.getText();
            String mark = fieldMark.getText();
            int foreign = Integer.parseInt(fieldForeign.getText());
            Car car = new Car(num, color, mark, foreign);
            int countSuccessOper = autoServiceDB.insert(new CarsDB(), car);
            if (countSuccessOper >= 1) {
                cars = FXCollections.observableList(autoServiceDB.getTable(new CarsDB()));
                tableCars.setItems(cars);
            }
            else {
                errorAdd.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            clearField();
        }
    }

    @FXML
    void DeleteCars(ActionEvent event) {
        try {
            clearText();
            Car deleteCar = tableCars.getSelectionModel().getSelectedItem();
            if (deleteCar != null) {
                int countSuccessOper = autoServiceDB.delete(new CarsDB(), deleteCar);
                if (countSuccessOper >= 1) {
                    cars = FXCollections.observableList(autoServiceDB.getTable(new CarsDB()));
                    tableCars.setItems(cars);
                }
                else {
                    errorDelete.setVisible(true);
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
    void UpdateCars(ActionEvent event) {
        try {
            clearText();
            String num = fieldNum.getText();
            String color = fieldColor.getText();
            String mark = fieldMark.getText();
            String foreignS = fieldForeign.getText();
            int foreign = foreignS.isEmpty() ? -1 : Integer.parseInt(foreignS);
            Car carUpd = new Car(num, color, mark, foreign);
            Car car = tableCars.getSelectionModel().getSelectedItem();
            if (car != null) {
                car.setValue(carUpd);
                int countSuccessOper = autoServiceDB.update(new CarsDB(), car);
                if (countSuccessOper >= 1) {
                    tableCars.refresh();
                }
                else {
                    errorAdd.setVisible(true);
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
            clearText();

            cars = FXCollections.observableList(autoServiceDB.getTable(new CarsDB()));

            numColumn.setCellValueFactory(new PropertyValueFactory<>("carNumber"));
            markColumn.setCellValueFactory(new PropertyValueFactory<>("carMark"));
            colorColumn.setCellValueFactory(new PropertyValueFactory<>("carColor"));
            foreignColumn.setCellValueFactory(new PropertyValueFactory<>("isForeign"));

            tableCars.setItems(cars);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearField() {
        fieldForeign.setText("");
        fieldMark.setText("");
        fieldColor.setText("");
        fieldNum.setText("");
    }

    private void clearText() {
        errorAdd.setVisible(false);
        errorDelete.setVisible(false);
    }

}
