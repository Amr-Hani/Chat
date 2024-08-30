package authentication;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import network.ServerHandler;
import testchatclient.TestChatClient;

public class LogIn extends AnchorPane {

    protected final TextField tf_username;
    protected final TextField tf_password;
    protected final Button btn_login;
    protected final Button btn_goToSignUp;

    ServerHandler serverHandler;

    public LogIn() {

        tf_username = new TextField();
        tf_password = new TextField();
        btn_login = new Button();
        btn_goToSignUp = new Button();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        tf_username.setLayoutX(207.0);
        tf_username.setLayoutY(83.0);
        tf_username.setPromptText("Username");

        tf_password.setLayoutX(207.0);
        tf_password.setLayoutY(157.0);
        tf_password.setPromptText("Password");

        btn_login.setLayoutX(207.0);
        btn_login.setLayoutY(247.0);
        btn_login.setMnemonicParsing(false);
        btn_login.setOnAction(this::_login);
        btn_login.setText("LogIn");

        btn_goToSignUp.setLayoutX(300.0);
        btn_goToSignUp.setLayoutY(247.0);
        btn_goToSignUp.setMnemonicParsing(false);
        btn_goToSignUp.setOnAction(this::_goToSignUp);
        btn_goToSignUp.setText("GoToSinUp");

        getChildren().add(tf_username);
        getChildren().add(tf_password);
        getChildren().add(btn_login);
        getChildren().add(btn_goToSignUp);

        serverHandler = ServerHandler.getInstance();

    }

    protected void _login(javafx.event.ActionEvent actionEvent) {
        if (tf_username.getText().isEmpty() || tf_username.getText().startsWith(" ")) {
            serverHandler.showErorrAlert("UserName is empty");
        } else if (tf_password.getText().isEmpty() || tf_password.getText().startsWith(" ")) {
            serverHandler.showErorrAlert("Password is empty");
        } else {
            String user = tf_username.getText().toString();
            String pass = tf_password.getText().toString();
            serverHandler.writeToServer("LOGIN," + user + "," + pass);

        }
    }

    protected void _goToSignUp(javafx.event.ActionEvent actionEvent) {
        TestChatClient.setScreens("SIGNUP");
        System.out.println("ay haga");
    }

}
