package sample.controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class DeleteController2 {

    @FXML
    private Label ErrorLabel;

    @FXML
    private Button NextButton;

    @FXML
    private ComboBox<String> SubjectComboBox;




    private Stage stage;
    private Scene scene;
    private Parent root;

    Subject subject = new Subject(StaticSubject.group_id, StaticSubject.day.trim(), StaticSubject.serial_num);
    DatabaseHandler Handler = new DatabaseHandler();

    @FXML
    void initialize() throws SQLException {


        setSubjectComboBox(subject);

        NextButton.setOnAction(event -> {

            subject.setName(getSubjectComboBoxValue(SubjectComboBox));

            Handler.DeleteSubject(subject);

            StaticSubject.setAllNull();

            showScene("/sample/fxml/Settings.fxml", event);
        });
    }

    public void setSubjectComboBox(Subject subject) throws SQLException {
        ResultSet result = Handler.getSubject(subject);
        while (result.next()) SubjectComboBox.getItems().addAll("      "+result.getString(Const.SUBJECT_NAME));
    }

    public String getSubjectComboBoxValue(ComboBox<String> ComboBox) {
        return ComboBox.getValue().trim();
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

