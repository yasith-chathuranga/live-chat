package lk.ijse.liveChat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"))));
        primaryStage.setTitle("Live Chat");
        primaryStage.getIcons().add(new Image("/img/PlayTech.png"));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}