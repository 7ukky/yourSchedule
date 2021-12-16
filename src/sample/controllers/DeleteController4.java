package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.database.StaticSubject;
import sample.database.Subject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Handler;

public class DeleteController4 {

    @FXML
    private ComboBox<String> StartHourComboBox, StartMinuteComboBox, EndHourComboBox, EndMinuteComboBox;

    @FXML
    private Label ErrorLabel;

    @FXML
    private Button NextButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void initialize() {
        setComboBoxes();

        NextButton.setOnAction(event -> {

            StaticSubject.time_start =  StartHourComboBox.getValue().trim() + ":" + StartMinuteComboBox.getValue().trim()+":00";
            StaticSubject.time_end = EndHourComboBox.getValue().trim() + ":" + EndMinuteComboBox.getValue().trim()+":00";;
            Subject subject = new Subject(StaticSubject.serial_num, StaticSubject.type, StaticSubject.name,
                    StaticSubject.teacher, StaticSubject.room, StaticSubject.day,
                    StaticSubject.week, StaticSubject.time_start, StaticSubject.time_end,
                    StaticSubject.group_id);
            DatabaseHandler Handler = new DatabaseHandler();

            Handler.DeleteSubject(subject);
            StaticSubject.setAllNull();
            showScene("/sample/fxml/Settings.fxml", event);

        });



    }



    public void setComboBoxes() {
        StartHourComboBox.getItems().addAll("      00", "      01", "      02", "      03",
                "      04", "      05", "      06", "      07",
                "      08", "      09", "      10", "      11",
                "      12", "      13", "      14", "      15",
                "      16", "      17", "      18", "      19",
                "      20", "      21", "      22", "      23");
        StartMinuteComboBox.getItems().addAll("      00", "      05", "      10", "      15",
                "      20", "      25", "      30", "      35",
                "      40", "      45", "      50", "      55");
        EndHourComboBox.getItems().addAll("      00", "      01", "      02", "      03",
                "      04", "      05", "      06", "      07",
                "      08", "      09", "      10", "      11",
                "      12", "      13", "      14", "      15",
                "      16", "      17", "      18", "      19",
                "      20", "      21", "      22", "      23");
        EndMinuteComboBox.getItems().addAll("      00", "      05", "      10", "      15",
                "      20", "      25", "      30", "      35",
                "      40", "      45", "      50", "      55");
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
