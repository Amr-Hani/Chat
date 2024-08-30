package screens;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.ShowOnlineUsers;
import network.ServerHandler;
import testchatclient.TestChatClient;

public class OnlinePageScreen extends AnchorPane {

    protected final TableView tableView;
    protected final TableColumn col_name;
    protected final TableColumn col_status;
    protected final TableColumn col_avilable;
    protected final Button btn_refresh;
    protected final Button btn_logOut;
    protected final Button btn_back;
    ServerHandler serverHandler;

    public static ObservableList<ShowOnlineUsers> users = FXCollections.observableArrayList();

    public OnlinePageScreen() {
        tableView = new TableView();
        col_name = new TableColumn();
        col_status = new TableColumn();
        col_avilable = new TableColumn();
        btn_refresh = new Button();
        btn_logOut = new Button();
        btn_back = new Button();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        tableView.setLayoutX(7.0);
        tableView.setLayoutY(6.0);
        tableView.setPrefHeight(283.0);
        tableView.setPrefWidth(586.0);

        col_name.setPrefWidth(266.0);
        col_name.setText("Player Name");

        col_status.setPrefWidth(165.0);
        col_status.setText("Status");

        col_avilable.setPrefWidth(154.0);
        col_avilable.setText("Avilability");

        btn_refresh.setLayoutX(88.0);
        btn_refresh.setLayoutY(320.0);
        btn_refresh.setMnemonicParsing(false);
        btn_refresh.setOnAction(this::_refresh);
        btn_refresh.setText("Refresh");

        btn_logOut.setLayoutX(235.0);
        btn_logOut.setLayoutY(320.0);
        btn_logOut.setMnemonicParsing(false);
        btn_logOut.setOnAction(this::_logOut);
        btn_logOut.setText("LogOut");

        btn_back.setLayoutX(397.0);
        btn_back.setLayoutY(320.0);
        btn_back.setMnemonicParsing(false);
        btn_back.setOnAction(this::_back);
        btn_back.setText("Back");

        tableView.getColumns().add(col_name);
        tableView.getColumns().add(col_status);
        tableView.getColumns().add(col_avilable);
        getChildren().add(tableView);
        getChildren().add(btn_refresh);
        getChildren().add(btn_logOut);
        getChildren().add(btn_back);

        col_name.setCellValueFactory(new PropertyValueFactory("username"));
        col_status.setCellValueFactory(new PropertyValueFactory("status"));
        col_avilable.setCellValueFactory(new PropertyValueFactory("avilable"));
        tableView.setItems(users);

        col_name.setCellFactory(new Callback<TableColumn<ShowOnlineUsers, String>, TableCell<ShowOnlineUsers, String>>() {
            @Override
            public TableCell<ShowOnlineUsers, String> call(TableColumn<ShowOnlineUsers, String> param) {
                return new TableCell<ShowOnlineUsers, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                        setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                boolean flag = false;
                                if (event.getClickCount() == 2) {
                                    serverHandler.writeToServer("REQUEST," + item);
                                }
                            }
                        });
                    }
                };
            }
        });
        serverHandler = ServerHandler.getInstance();
    }

    protected void _refresh(javafx.event.ActionEvent actionEvent) {
        serverHandler.writeToServer("UPDATE ONLINE USERS");
        tableView.getItems().clear();
    }

    protected void _logOut(javafx.event.ActionEvent actionEvent) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You Want LogOut", ButtonType.OK, ButtonType.NO);
            alert.showAndWait().ifPresent(click -> {
                if (click == ButtonType.OK) {
                    serverHandler.writeToServer("LOGOUT");
                    TestChatClient.setScreens("LOGIN");
                    tableView.getItems().clear();
                }
            });
        });
    }

    protected void _back(javafx.event.ActionEvent actionEvent) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You Want To Back ", ButtonType.OK, ButtonType.NO);
            alert.showAndWait().ifPresent(click -> {
                if (click == ButtonType.OK) {
                    serverHandler.writeToServer("LOGOUT");
                    TestChatClient.setScreens("LOGIN");
                    tableView.getItems().clear();
                }
            });
        });
    }

    public void sendServerDownToAllClient(String message) {

    }

}
