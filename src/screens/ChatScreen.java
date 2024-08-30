package screens;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import network.ServerHandler;
import testchatclient.TestChatClient;

public class ChatScreen extends AnchorPane {

    protected static TextArea textArea;
    protected final TextField textField;
    protected final Button btn_send;
    protected final Button btn_back;

    ServerHandler serverHandler;

    public ChatScreen() {

        textArea = new TextArea();
        textField = new TextField();
        btn_send = new Button();
        btn_back = new Button();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        textArea.setLayoutX(35.0);
        textArea.setLayoutY(40.0);
        textArea.setPrefHeight(264.0);
        textArea.setPrefWidth(530.0);

        textField.setLayoutX(68.0);
        textField.setLayoutY(324.0);
        textField.setPrefHeight(31.0);
        textField.setPrefWidth(252.0);

        btn_send.setLayoutX(381.0);
        btn_send.setLayoutY(324.0);
        btn_send.setMnemonicParsing(false);
        btn_send.setOnAction(this::_send);
        btn_send.setText("Send");

        btn_back.setLayoutX(463.0);
        btn_back.setLayoutY(324.0);
        btn_back.setMnemonicParsing(false);
        btn_back.setOnAction(this::_back);
        btn_back.setText("Back");

        getChildren().add(textArea);
        getChildren().add(textField);
        getChildren().add(btn_send);
        getChildren().add(btn_back);

        textArea.setEditable(false);

        serverHandler = ServerHandler.getInstance();
    }

    protected void _send(javafx.event.ActionEvent actionEvent) {

        serverHandler.writeToServer("MESSAGE");
        serverHandler.writeToServer(textField.getText().toString());
        textField.clear();

    }

    public static void setMessage(String message) {
        Platform.runLater(() -> {
            textArea.appendText(message + "\n");
        });
    }

    protected void _back(javafx.event.ActionEvent actionEvent)
    {
        //TestChatClient.setScreens("ONLINE");
        serverHandler.writeToServer("LEAVE");
    }

}
