package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.ShowOnlineUsers;
import screens.ChatScreen;
import screens.OnlinePageScreen;
import testchatclient.TestChatClient;

public class ServerHandler {

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    private static ServerHandler serverHandler;

    private ServerHandler() {
        try {
            socket = new Socket("127.0.0.1", 1999);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            readFromServer();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ServerHandler getInstance() {
        if (serverHandler == null) {
            serverHandler = new ServerHandler();
        }
        return serverHandler;
    }

    public void readFromServer() {
        new Thread() {
            @Override
            public void run() {
                while (!socket.isClosed()) {
                    try {
                        String message = dataInputStream.readUTF();
                        if (message.startsWith("THIS USERNAME IS ALLREDY EXIST")) {
                            showErorrAlert(message);
                        } else if (message.startsWith("THIS EMAIL IS ALLREDY EXIST")) {
                            showErorrAlert(message);
                        } else if (message.startsWith("SIGNUP SUCCESS")) {
                            TestChatClient.setScreens("LOGIN");
                        } else if (message.startsWith("LOGIN SUCCESS")) {
                            TestChatClient.setScreens("ONLINE");
                        } else if (message.startsWith("LOGIN FIELD")) {
                            showErorrAlert(message);
                        } else if (message.startsWith("This user is online")) {
                            showErorrAlert(message);
                        } else if (message.startsWith("ONLINE USERS")) {
                            String[] split = message.split(",");
                            ShowOnlineUsers showOnlineUsers = new ShowOnlineUsers(split[1], "Online", "3ezzat");
                            OnlinePageScreen.users.add(showOnlineUsers);
                            System.out.println(message);
                        } else if (message.startsWith("REQUEST")) {
                            showRequestAlert(message);
                        } else if (message.startsWith("ACCEPTED")) {
                            TestChatClient.setScreens("CHAT");
                        } else if (message.startsWith("REFUSED")) {
                            showErorrAlert(message);
                        } else if (message.startsWith("MESSAGE")) {
                            ChatScreen.setMessage(dataInputStream.readUTF());
                        } else if (message.startsWith("this user alredy playing with another user")) {
                            showErorrAlert(message);
                        } else if (message.startsWith("SERVER DAWN")) {
                            showServerDawnAlert(message);
                        } else if (message.startsWith("LEAVE")) {
                            showLeaveAlert("this user leave chat");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }

    public void writeToServer(String message) {
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showErorrAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.show();
        });
    }

    public void showLeaveAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.showAndWait().ifPresent(click -> {
                if (click == ButtonType.OK) {
                    TestChatClient.setScreens("ONLINE");
                }
            });
        });
    }

    public void showServerDawnAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.showAndWait().ifPresent(click -> {
                if (click == ButtonType.OK) {
                    Platform.exit();
                }
            });
        });
    }

    public void showRequestAlert(String message) {
        String[] split = message.split(",");
        String recever = split[1];
        String sender = split[2];
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Do You Want Play With Me :" + sender, ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(click -> {
                if (click == ButtonType.YES) {
                    writeToServer("ACCPTED," + sender + "," + recever);
                    TestChatClient.setScreens("CHAT");
                } else if (click == ButtonType.NO) {
                    writeToServer("REFUSED," + sender + "," + recever);
                }
            });
        });
    }

}
