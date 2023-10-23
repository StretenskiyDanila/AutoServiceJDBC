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
import ru.stretenskiy.autoservice.db.MastersDB;
import ru.stretenskiy.autoservice.tables.Car;
import ru.stretenskiy.autoservice.tables.Master;

public class MastersController {

    private final AutoServiceDB autoServiceDB = new AutoServiceDB();
    private ObservableList<Master> masters;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField fieldName;

    @FXML
    private Button goMainButton;

    @FXML
    private TableColumn<Master, String> nameColumn;

    @FXML
    private TableView<Master> tableMasters;

    @FXML
    private Button updateButton;

    @FXML
    private Text errorAddMaster;

    @FXML
    void AddMaster(ActionEvent event) {
        try {
            clearText();
            String name = fieldName.getText();
            Master master = new Master(name);
            int countSuccessOper = autoServiceDB.insert(new MastersDB(), master);
            if (countSuccessOper >= 1) {
                masters = FXCollections.observableList(autoServiceDB.getTable(new MastersDB()));
                tableMasters.setItems(masters);
            }
            else {
                errorAddMaster.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            clearField();
        }
    }

    @FXML
    void DeleteMasters(ActionEvent event) {
        try {
            clearText();
            Master deleteMaster = tableMasters.getSelectionModel().getSelectedItem();
            if (deleteMaster != null) {
                int countSuccessOper = autoServiceDB.delete(new MastersDB(), deleteMaster);
                if (countSuccessOper >= 1) {
                    masters = FXCollections.observableList(autoServiceDB.getTable(new MastersDB()));
                    tableMasters.setItems(masters);
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
    void UpdateMasters(ActionEvent event) {
        try {
            clearText();
            String name = fieldName.getText();
            Master masterUpd = new Master(name);
            Master master = tableMasters.getSelectionModel().getSelectedItem();
            if (master != null) {
                master.setValue(masterUpd);
                int countSuccessOper = autoServiceDB.update(new MastersDB(), master);
                if (countSuccessOper >= 1) {
                    tableMasters.refresh();
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
            masters = FXCollections.observableList(autoServiceDB.getTable(new MastersDB()));

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            tableMasters.setItems(masters);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void clearField() {
        fieldName.setText("");
    }

    private void clearText() {
        errorAddMaster.setVisible(false);
    }

}
