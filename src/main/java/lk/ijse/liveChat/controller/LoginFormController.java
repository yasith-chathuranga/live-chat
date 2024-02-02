package lk.ijse.liveChat.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController extends Thread{
    @FXML
    public JFXTextField txtUsername;
    static String userName;

    @FXML
    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        userName = txtUsername.getText();
        txtUsername.clear();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(LoginFormController.class.getResource("/view/ClientForm.fxml"))));
        stage.setTitle("Live Chat");
        stage.getIcons().add(new Image("/img/PlayTech.png"));
        stage.close();
        stage.centerOnScreen();
        stage.show();
    }
}
