package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Handler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.database.Const;
import sample.database.DatabaseHandler;
import sample.database.StaticSubject;
import sample.database.Subject;

public class AddController2 {

    @FXML
    private Label ErrorLabel;

    @FXML
    private Button NextButton;

    @FXML
    private ComboBox<String> SerialNumberComboBox;

    @FXML
    private ComboBox<String> TypeComboBox;



    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void initialize() throws SQLException {

        DatabaseHandler Handler = new DatabaseHandler();
        Subject subject = new Subject(StaticSubject.day.trim(), StaticSubject.week, StaticSubject.week2, StaticSubject.group_id);
        ResultSet result = Handler.getPossibleSerialNumber(subject);
        setComboBoxes(result);

        NextButton.setOnAction(event -> {
            switch (TypeComboBox.getValue().trim()){
                case ("Лекция"):
                    StaticSubject.type = Const.LECTURE;
                    break;
                case ("Семинар"):
                    StaticSubject.type = Const.SEMINAR;
                    break;
                case ("Практика"):
                    StaticSubject.type = Const.PRACTICE;
                    break;
                case ("Лабораторная"):
                    StaticSubject.type = Const.LABORATORY;
                    break;
            }

            switch (SerialNumberComboBox.getValue().trim()){
                case ("Первая"):
                    StaticSubject.serial_num = 1;
                    break;
                case ("Вторая"):
                    StaticSubject.serial_num = 2;
                    break;
                case ("Третья"):
                    StaticSubject.serial_num = 3;
                    break;
                case ("Четвёртая"):
                    StaticSubject.serial_num = 4;
                    break;
                case ("Пятая"):
                    StaticSubject.serial_num = 5;
                    break;
            }
            showScene("/sample/fxml/Add3.fxml", event);
        });


    }

    public void setComboBoxes(ResultSet result) throws SQLException {
        SerialNumberComboBox.getItems().addAll("      Первая", "      Вторая","      Третья","      Четвёртая","      Пятая");
        while (result.next()) {
            switch (result.getInt(Const.SUBJECT_SERIAL_NUMBER)) {
                case (1):
                    SerialNumberComboBox.getItems().removeAll("      Первая");
                    break;
                case (2):
                    SerialNumberComboBox.getItems().removeAll("      Вторая");
                    break;
                case (3):
                    SerialNumberComboBox.getItems().removeAll("      Третья");
                    break;
                case (4):
                    SerialNumberComboBox.getItems().removeAll("      Четвёртая");
                    break;
                case (5):
                    SerialNumberComboBox.getItems().removeAll("      Пятая");
                    break;

            }
        }
        TypeComboBox.getItems().addAll("      Лекция","      Семинар", "      Практика", "      Лабораторная");
    }


    private void showScene(String window, ActionEvent event) {
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

