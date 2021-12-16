package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.StaticSubject;

import java.io.IOException;

public class DeleteController3 {

    @FXML
    private Label ErrorLabel;

    @FXML
    private TextField NameField, TeacherField, RoomField;

    @FXML
    private Button NextButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void initialize() {

        NextButton.setOnAction(event -> {

            StaticSubject.name = NameField.getText();
            StaticSubject.teacher = TeacherField.getText();
            StaticSubject.room = RoomField.getText();

            showScene("/sample/fxml/Delete4.fxml", event);
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
