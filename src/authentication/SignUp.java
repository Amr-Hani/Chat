package authentication;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import network.ServerHandler;
import testchatclient.TestChatClient;

public class SignUp extends AnchorPane {

    protected final TextField tf_username;
    protected final TextField tf_password;
    protected final TextField tf_email;
    protected final Button btn_signup;
    protected final Button btn_goToLogIn;

    ServerHandler serverHandler;

    public SignUp() {

        tf_username = new TextField();
        tf_password = new TextField();
        tf_email = new TextField();
        btn_signup = new Button();
        btn_goToLogIn = new Button();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        tf_username.setLayoutX(218.0);
        tf_username.setLayoutY(61.0);
        tf_username.setPromptText("UserName");

        tf_password.setLayoutX(218.0);
        tf_password.setLayoutY(123.0);
        tf_password.setPromptText("Password");

        tf_email.setLayoutX(218.0);
        tf_email.setLayoutY(185.0);
        tf_email.setPromptText("Email");

        btn_signup.setLayoutX(218.0);
        btn_signup.setLayoutY(266.0);
        btn_signup.setMnemonicParsing(false);
        btn_signup.setOnAction(this::_signUp);
        btn_signup.setText("SignUp");

        btn_goToLogIn.setLayoutX(312.0);
        btn_goToLogIn.setLayoutY(266.0);
        btn_goToLogIn.setMnemonicParsing(false);
        btn_goToLogIn.setOnAction(this::_goToLogIn);
        btn_goToLogIn.setText("GoToLogin");

        getChildren().add(tf_username);
        getChildren().add(tf_password);
        getChildren().add(tf_email);
        getChildren().add(btn_signup);
        getChildren().add(btn_goToLogIn);

        serverHandler = ServerHandler.getInstance();

    }

    protected void _signUp(javafx.event.ActionEvent actionEvent) {
        if (tf_username.getText().isEmpty()||tf_username.getText().startsWith(" ")) {
            serverHandler.showErorrAlert("UserName is empty");
        } else if (tf_password.getText().isEmpty()||tf_password.getText().startsWith(" ")) {
            serverHandler.showErorrAlert("Password is empty");
        } else if (tf_email.getText().isEmpty()||tf_email.getText().startsWith(" ")) {
            serverHandler.showErorrAlert("Email is empty");
        }else {
            String user = tf_username.getText().toString();
            String pass = tf_password.getText().toString();
            String email = tf_email.getText().toString();
            serverHandler.writeToServer("SIGNUP," + user + "," + pass + "," + email);

        }
    }

    protected void _goToLogIn(javafx.event.ActionEvent actionEvent) {
        TestChatClient.setScreens("LOGIN");
        System.out.println("ay haga");
    }

}
