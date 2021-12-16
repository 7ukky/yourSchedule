package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.*;
import com.itextpdf.text.Anchor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminController {


    @FXML
    private AnchorPane MainPane;

    @FXML
    private Button DeleteGroupButton, DeleteSubjectButton, AddGroupButton, AddSubjectButton, BackButton;

    @FXML
    private Label ErrorLabel;

    @FXML
    private VBox Vbox;

    @FXML
    void initialize() {

        new FadeInUpBig(Vbox).play();

        AddSubjectButton.setOnAction(event -> {
            FadeOutDownBig animation = new FadeOutDownBig(Vbox);
            animation.setOnFinished(event1 -> showScene("/sample/fxml/Add1.fxml"));
        });

        BackButton.setOnAction(event -> {
            SlideOutUp animation = new SlideOutUp(Vbox);
            animation.setOnFinished(event1 -> showScene("/sample/fxml/Settings.fxml"));
        });

    }

    public void showScene(String window) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(window));
            Scene scene = new Scene(root);
            Stage stage = (Stage) MainPane.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
