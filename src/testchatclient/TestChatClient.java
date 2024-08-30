package testchatclient;

import authentication.LogIn;
import authentication.SignUp;
import java.util.HashMap;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import network.ServerHandler;
import screens.ChatScreen;
import screens.OnlinePageScreen;

public class TestChatClient extends Application {

    static HashMap<String, Parent> screens = new HashMap<>();
    static Scene scene;
    Stage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        screens.put("LOGIN", new LogIn());
        screens.put("SIGNUP", new SignUp());
        screens.put("ONLINE", new OnlinePageScreen());
        screens.put("CHAT", new ChatScreen());
        
       

        scene = new Scene( screens.get("LOGIN"));

        stage.setScene(scene);
        stage.show();
        
        
        
        
        stage.setOnCloseRequest(event -> {
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to exit?");
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    ServerHandler.getInstance().writeToServer("LOGOUT");
                    primaryStage.close();  
                }
                else {
                    event.consume();
                }
            });
        });
    }

   

    public static void main(String[] args) {
        launch(args);
    }
 public static void setScreens(String screenName) {
        Platform.runLater(() -> {
            scene.setRoot(screens.get(screenName));
        });
    }
}
