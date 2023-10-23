package ru.stretenskiy.autoservice.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.apache.commons.codec.digest.DigestUtils;
import ru.stretenskiy.autoservice.db.AutoServiceDB;
import ru.stretenskiy.autoservice.db.UsersAccountDB;
import ru.stretenskiy.autoservice.tables.UsersAccount;

public class LogoutController {

    private final AutoServiceDB autoServiceDB = new AutoServiceDB();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonLogout;

    @FXML
    private TextField fieldUserName;

    @FXML
    private PasswordField fieldUserPsw;

    @FXML
    void OnActLogin(MouseEvent event) {
        fieldUserName.addEventFilter(MouseEvent.MOUSE_CLICKED, event1 -> fieldUserName.clear());
    }

    @FXML
    void OnActPsw(MouseEvent event) {
        fieldUserPsw.addEventFilter(MouseEvent.MOUSE_CLICKED, event1 -> fieldUserPsw.clear());
    }

    @FXML
    void GoLogin(ActionEvent event) {
        try {
            buttonLogin.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("login-view.fxml"));
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
    void OnLogout(ActionEvent event) {
        try {
            String userLogin = fieldUserName.getText();
            String userPsw = fieldUserPsw.getText();

            String hashPsw = DigestUtils.md5Hex(userPsw);

            UsersAccount ua = new UsersAccountDB().getValueByLogin(userLogin, hashPsw);

            buttonLogout.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("hello-view.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            fieldUserName.setText(e.getMessage());
        }
    }

    @FXML
    void initialize() {

    }

}
