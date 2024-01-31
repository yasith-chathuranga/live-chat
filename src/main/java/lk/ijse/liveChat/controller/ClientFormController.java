package lk.ijse.liveChat.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClientFormController extends Thread{
    @FXML
    public Label lblName;

    @FXML
    public VBox vBox;

    @FXML
    public TextField txtEnter;

    @FXML
    private Pane emojiPane;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    private FileChooser fileChooser;
    private File filePath;

    public void initialize() throws IOException{
        String userName = LoginFormController.userName;
        lblName.setText(userName);

        try {
            socket = new Socket("localhost",3792);
            System.out.println("Connected.");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(),true);
            this.start();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try {
            while (true){
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];

                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i]+" ");
                }

                String[] msgToAr = msg.split(" ");
                String st = "";
                for (int i = 0; i < msgToAr.length - 1; i++) {
                    st += msgToAr[i + 1] + " ";
                }

                Text text = new Text(st);
                String firstChars = "";
                if (st.length() > 3) {
                    firstChars = st.substring(0, 3);
                }

                if (firstChars.equalsIgnoreCase("img")) {
                    st = st.substring(3, st.length() - 1);

                    File file = new File(st);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitHeight(150);
                    imageView.setFitWidth(200);

                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);

                    if (!cmd.equalsIgnoreCase(lblName.getText())) {

                        vBox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);

                        Text text1 = new Text("  " + cmd + " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);

                    }
                    else {
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(imageView);
                        Text text1 = new Text(": Me ");
                        hBox.getChildren().add(text1);

                    }

                    Platform.runLater(() -> vBox.getChildren().addAll(hBox));

                } else {
                    String[] name = cmd.split(":");
                    String username = name[0];
                    String message = name[1];

                    TextFlow tempFlow = new TextFlow();
                    System.out.println(lblName.getText()+" "+cmd);

                    if (!username.equalsIgnoreCase(lblName.getText())) {
                        Text txtName = new Text(cmd + " ");
                        txtName.setFont(Font.font(25));
                        txtName.getStyleClass().add("txtName");
                        tempFlow.getChildren().add(txtName);
                    }

                    tempFlow.getChildren().add(text);
                    //tempFlow.setMaxWidth(200); //200

                    TextFlow flow = new TextFlow(tempFlow);

                    HBox hBox = new HBox(12);//12
                    hBox.setPadding(new Insets(5));

                    HBox hBox1 = new HBox();
                    hBox1.setPadding(new Insets(10));

                    if (!username.equalsIgnoreCase(lblName.getText())) {
                        hBox1.setStyle("-fx-background-color: lightGreen;"+
                                "-fx-background-radius: 15px");

                        vBox.setAlignment(Pos.TOP_LEFT);
                        hBox1.setAlignment(Pos.CENTER_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox1.getChildren().add(flow);
                        hBox.getChildren().add(hBox1);

                    }
                    else {
                        hBox1.setStyle("-fx-background-color: lightBlue;"+
                                "-fx-background-radius: 15px");

                        Text text2 = new Text(message + ": Me");
                        text2.setFont(Font.font(25));
                        TextFlow flow2 = new TextFlow(text2);
                        hBox1.setAlignment(Pos.CENTER_RIGHT);
                        hBox1.getChildren().add(flow2);
                        hBox.setAlignment(Pos.CENTER_RIGHT);
                        hBox.getChildren().add(hBox1);
                    }

                    Platform.runLater(() -> vBox.getChildren().addAll(hBox));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnEmjOnAction(ActionEvent actionEvent) {
        emojiPane.setVisible(!emojiPane.isVisible());

    }

    @FXML
    public void btnImgOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        writer.println(lblName.getText() + " " + "img" + filePath.getPath());
    }

    @FXML
    public void btnSendOnAction(ActionEvent actionEvent) {
        String msg = txtEnter.getText();
        writer.println(lblName.getText() + ":" + msg);
        txtEnter.clear();

        if (msg.equalsIgnoreCase("Logout")) {
            System.exit(0);
        }
    }

    @FXML
    void imgCareOnMouseClicked(MouseEvent event) {
        txtEnter.appendText("\uD83D\uDE0D");
        txtEnter.requestFocus();

    }

    @FXML
    void imgHappyOnMouseClick(MouseEvent event) {
        txtEnter.appendText("\uD83D\uDE0A");
        txtEnter.requestFocus();

    }

    @FXML
    void imgWowOnMouseClicked(MouseEvent event) {
        txtEnter.appendText("\uD83D\uDE2F");
        txtEnter.requestFocus();

    }
}
