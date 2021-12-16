package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {




    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Button AddButton, DeleteButton, ChangeButton;

    @FXML
    void initialize() {

        AddButton.setOnAction(event -> {
            showScene("/sample/fxml/Add1.fxml", event);
        });

        DeleteButton.setOnAction(event -> {
            showScene("/sample/fxml/Delete1.fxml", event);
        });

    }




    public void showScene(String window, ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getResource(window));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}


