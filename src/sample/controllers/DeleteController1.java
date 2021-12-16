package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.database.Const;
import sample.database.DatabaseHandler;
import sample.database.StaticSubject;
import sample.database.Subject;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class DeleteController1 {

    @FXML
    private ComboBox<String> SerialNumberComboBox, DayComboBox, GroupComboBox;

    @FXML
    private Button NextButton;

    @FXML
    private Label ErrorLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    void initialize() throws SQLException {
        DatabaseHandler Handler = new DatabaseHandler();
        ResultSet result = Handler.getAllGroups();
        setComboBoxes(result);


        NextButton.setOnAction(event -> {
            switch (DayComboBox.getValue().trim()){
                case ("Понедельник"):
                    StaticSubject.day = Const.MONDAY;
                    break;
                case ("Вторник"):
                    StaticSubject.day = Const.TUESDAY;
                    break;
                case ("Среда"):
                    StaticSubject.day = Const.WEDNESDAY;
                    break;
                case ("Четверг"):
                    StaticSubject.day = Const.THURSDAY;
                    break;
                case ("Пятница"):
                    StaticSubject.day = Const.FRIDAY;
                    break;
                case ("Суббота"):
                    StaticSubject.day = Const.SATURDAY;
                    break;
                case ("Воскресенье"):
                    StaticSubject.day = Const.SUNDAY;
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
            try {
                ResultSet result2 = Handler.getGroupId(GroupComboBox.getValue().trim());
                result2.next();
                StaticSubject.group_id = result2.getInt(Const.ID);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            showScene("/sample/fxml/Delete2.fxml", event);
        });


    }



    private void setComboBoxes(ResultSet result) {
        try{
            result.last();
            int size = result.getRow();
            System.out.println(size);
            String[] groups = new String[size];
            for (result.first(); size != 0; size--) {
                groups[size-1] = "      "+result.getString(Const.GROUP_NAME);
                result.next();
            }
            GroupComboBox.getItems().addAll(groups);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DayComboBox.getItems().addAll("      Понедельник","      Вторник","      Среда",
                "      Четверг", "      Пятница","      Суббота","      Воскресенье");
        SerialNumberComboBox.getItems().addAll("      Первая","      Вторая","      Третья",
                "      Четвёртая","      Пятая");
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
