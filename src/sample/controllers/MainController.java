package sample.controllers;

import animatefx.animation.*;
import com.itextpdf.text.DocumentException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.PDF.PDF;

import sample.animations.MyZoom;
import sample.database.Const;
import sample.database.DatabaseHandler;
import sample.database.Subject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MainController {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Label DateLabel, DayLabel, TimeLabel;

    @FXML
    private VBox LeftVbox, RightVbox, Vbox1, Vbox2, Vbox3, Vbox4, Vbox5, MenuVbox;

    @FXML
    private HBox Hbox;

    @FXML
    public ScrollPane scrollPane;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button SettingsButton, DownloadButton;



    private Stage stage;
    private Scene scene;
    private Parent root;



    int cntr = 0;
    int max = 10000;

    @FXML
    void initialize() {
        Clock();
        try {
            setAvailableSchedule();
            Hbox.setOpacity(0);
            MenuVbox.setOpacity(0);
            new BounceInDown(Hbox).play();
            new ZoomIn(MenuVbox).play();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        datePicker.setOnAction(event -> {
            SlideOutUp animation = new SlideOutUp(Hbox);

            animation.setOnFinished(event1 -> {
                try {
                    Clock();
                    setAvailableSchedule(datePicker.getValue());
                    new BounceInDown(Hbox).play();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            animation.play();


        });

        SettingsButton.setOnAction(event -> {
            ZoomOut animation1 = new ZoomOut(MenuVbox);
            SlideOutUp animation2 = new SlideOutUp(Hbox);
            animation2.setOnFinished(event1 -> showScene("/sample/fxml/Settings.fxml"));
            animation1.play();
            animation2.play();
        });

        DownloadButton.setOnAction(event -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));
            fc.setTitle("Save to PDF");
            fc.setInitialFileName("schedule.pdf");
            File file = fc.showSaveDialog(new Stage());
            if (file != null){
                String str = file.getAbsolutePath();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(str);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                PDF pdf = new PDF();
                try {
                    pdf.getPDFofTable(fos);
                } catch (DocumentException | IOException | URISyntaxException | SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        });

        Vbox1.setOnMouseEntered(mouseDragEvent -> new MyZoom(Vbox1, "in").play());
        Vbox1.setOnMouseExited(mouseDragEvent -> new MyZoom(Vbox1, "out").play());

        Vbox2.setOnMouseEntered(mouseDragEvent -> new MyZoom(Vbox2, "in").play());
        Vbox2.setOnMouseExited(mouseDragEvent -> new MyZoom(Vbox2, "out").play());

        Vbox3.setOnMouseEntered(mouseDragEvent -> new MyZoom(Vbox3, "in").play());
        Vbox3.setOnMouseExited(mouseDragEvent -> new MyZoom(Vbox3, "out").play());

        Vbox4.setOnMouseEntered(mouseDragEvent -> new MyZoom(Vbox4, "in").play());
        Vbox4.setOnMouseExited(mouseDragEvent -> new MyZoom(Vbox4, "out").play());

        Vbox5.setOnMouseEntered(mouseDragEvent -> new MyZoom(Vbox5, "in").play());
        Vbox5.setOnMouseExited(mouseDragEvent -> new MyZoom(Vbox5, "out").play());
    }

    private void setAvailableSchedule() throws SQLException {

        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("c");
        String day = LocalDateTime.now().format(dayFormatter);

        ResultSet result;
        DatabaseHandler Handler = new DatabaseHandler();
        if(SignInController.email != null) result = Handler.getUserGroup(SignInController.email);
        else result = Handler.getUserGroup(SignUpController2.email);
        result.next();

        Subject subject = new Subject(day, whatWeekIs(), result.getInt(Const.USER_GROUP));
        


        result = Handler.getSubjectWithoutDate(subject);

        int i = 0, j = 0, n = 1, m = 2;
        Label label;
        Parent node;
        String type;
        while(result.absolute(n)) {
            type = null;
            n += 2;
            node = (Parent) LeftVbox.getChildren().get(i);
            node.setVisible(true);
            label = (Label) node.getChildrenUnmodifiable().get(0);
            label.getStyleClass().clear();
            switch (result.getString(Const.SUBJECT_TYPE)) {
                case Const.LECTURE -> {
                    label.setText("Лекция");
                    type = "Lecture";
                    label.getStyleClass().add(type + "Heading");
                }
                case Const.SEMINAR -> {
                    label.setText("Семинар");
                    type = "Seminar";
                    label.getStyleClass().add(type + "Heading");
                }
                case Const.PRACTICE -> {
                    label.setText("Практика");
                    type = "Practice";
                    label.getStyleClass().add(type + "Heading");
                }
                case Const.LABORATORY -> {
                    label.setText("Лабораторная");
                    type = "Laboratory";
                    label.getStyleClass().add(type + "Heading");
                }
            }

            label = (Label) node.getChildrenUnmodifiable().get(1);
            label.setText(result.getString(Const.SUBJECT_TIME_START).substring(0,5) + " - " + result.getString(Const.SUBJECT_TIME_END).substring(0,5));
            label.getStyleClass().clear();
            label.getStyleClass().add(type + "Time");



            label = (Label) node.getChildrenUnmodifiable().get(2);
            if(result.getString(Const.SUBJECT_NAME).length() > 8) label.setStyle("-fx-font-size: 23");
            label.setText(result.getString(Const.SUBJECT_NAME));
            label.getStyleClass().clear();
            label.getStyleClass().add(type + "Name");

            label = (Label) node.getChildrenUnmodifiable().get(3);
            label.setText(result.getString(Const.SUBJECT_TEACHER));
            label.getStyleClass().clear();
            label.getStyleClass().add(type + "Teacher");

            label = (Label) node.getChildrenUnmodifiable().get(4);
            label.setText(result.getString(Const.SUBJECT_ROOM));
            label.getStyleClass().clear();
            label.getStyleClass().add(type + "Room");

            label = (Label) node.getChildrenUnmodifiable().get(5);
            label.getStyleClass().clear();
            label.getStyleClass().add(type + "Ending");

            i++;
            while (result.absolute(m)) {
                type = null;
                m += 2;
                node = (Parent) RightVbox.getChildren().get(j+1);
                node.setVisible(true);
                label = (Label) node.getChildrenUnmodifiable().get(0);
                label.getStyleClass().clear();
                switch (result.getString(Const.SUBJECT_TYPE)) {
                    case Const.LECTURE -> {
                        label.setText("Лекция");
                        type = "Lecture";
                        label.getStyleClass().add(type + "Heading");
                    }
                    case Const.SEMINAR -> {
                        label.setText("Семинар");
                        type = "Seminar";
                        label.getStyleClass().add(type + "Heading");
                    }
                    case Const.PRACTICE -> {
                        label.setText("Практика");
                        type = "Practice";
                        label.getStyleClass().add(type + "Heading");
                    }
                    case Const.LABORATORY -> {
                        label.setText("Лабораторная");
                        type = "Laboratory";
                        label.getStyleClass().add(type + "Heading");
                    }
                }

                label = (Label) node.getChildrenUnmodifiable().get(1);
                label.setText(result.getString(Const.SUBJECT_TIME_START).substring(0,5) + " - " + result.getString(Const.SUBJECT_TIME_END).substring(0,5));
                label.getStyleClass().clear();
                label.getStyleClass().add(type + "Time");

                label = (Label) node.getChildrenUnmodifiable().get(2);
                if(result.getString(Const.SUBJECT_NAME).length() > 8) label.setStyle("-fx-font-size: 23");
                label.setText(result.getString(Const.SUBJECT_NAME));
                label.getStyleClass().clear();
                label.getStyleClass().add(type + "Name");


                label = (Label) node.getChildrenUnmodifiable().get(3);
                label.setText(result.getString(Const.SUBJECT_TEACHER));
                label.getStyleClass().clear();
                label.getStyleClass().add(type + "Teacher");

                label = (Label) node.getChildrenUnmodifiable().get(4);
                label.setText(result.getString(Const.SUBJECT_ROOM));
                label.getStyleClass().clear();
                label.getStyleClass().add(type + "Room");

                label = (Label) node.getChildrenUnmodifiable().get(5);
                label.getStyleClass().clear();
                label.getStyleClass().add(type + "Ending");
                j++;
            }
        }
        if (j < 2) {
            scrollPane.setVmax(0);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }
        else{
            scrollPane.setVmax(1);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        }
        for(; i < 3; i++) {

            LeftVbox.getChildren().get(i).setVisible(false);
            for (; j < 2; j++) RightVbox.getChildren().get(j+1).setVisible(false);
        }
    }

    private void setAvailableSchedule(LocalDate date) throws SQLException {

        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("c");
        String day = date.format(dayFormatter);

        ResultSet result;
        DatabaseHandler Handler = new DatabaseHandler();
        if(SignInController.email != null) result = Handler.getUserGroup(SignInController.email);
        else result = Handler.getUserGroup(SignUpController2.email);
        result.next();

        Subject subject = new Subject(day, whatWeekIs(date), result.getInt(Const.USER_GROUP));


        result = Handler.getSubjectWithDate(subject, date);

        int i = 0, j = 0, n = 1, m = 2;
        Label label;
        Parent node;
        String type;
        while(result.absolute(n)) {
            type = null;
            n += 2;
            node = (Parent) LeftVbox.getChildren().get(i);
            node.setVisible(true);
            label = (Label) node.getChildrenUnmodifiable().get(0);
            label.getStyleClass().clear();
            if (result.getString(Const.SUBJECT_TYPE).equals(Const.LECTURE)) {
                label.setText("Лекция");
                type = "Lecture";
                label.getStyleClass().add("LectureHeading");
            } else if (result.getString(Const.SUBJECT_TYPE).equals(Const.SEMINAR)) {
                label.setText("Семинар");
                type = "Seminar";
                label.getStyleClass().add(type + "Heading");
            } else if (result.getString(Const.SUBJECT_TYPE).equals(Const.PRACTICE)) {
                label.setText("Практика");
                type = "Practice";
                label.getStyleClass().add(type + "Heading");
            } else if (result.getString(Const.SUBJECT_TYPE).equals(Const.LABORATORY)) {
                label.setText("Лабораторная");
                type = "Laboratory";
                label.getStyleClass().add(type + "Heading");
            }

            label = (Label) node.getChildrenUnmodifiable().get(1);
            label.setText(result.getString(Const.SUBJECT_TIME_START).substring(0,5) + " - " + result.getString(Const.SUBJECT_TIME_END).substring(0,5));
            label.getStyleClass().clear();
            label.getStyleClass().add(type + "Time");




            label = (Label) node.getChildrenUnmodifiable().get(2);
            if(result.getString(Const.SUBJECT_NAME).length() > 8) label.setStyle("-fx-font-size: 23");
            label.setText(result.getString(Const.SUBJECT_NAME));
            label.getStyleClass().clear();
            label.getStyleClass().add(type + "Name");


            label = (Label) node.getChildrenUnmodifiable().get(3);
            label.setText(result.getString(Const.SUBJECT_TEACHER));
            label.getStyleClass().clear();
            label.getStyleClass().add(type + "Teacher");

            label = (Label) node.getChildrenUnmodifiable().get(4);
            label.setText(result.getString(Const.SUBJECT_ROOM));
            label.getStyleClass().clear();
            label.getStyleClass().add(type + "Room");

            label = (Label) node.getChildrenUnmodifiable().get(5);
            label.getStyleClass().clear();
            label.getStyleClass().add(type + "Ending");

            i++;
            while (result.absolute(m)) {
                m += 2;
                type = null;
                node = (Parent) RightVbox.getChildren().get(j+1);
                node.setVisible(true);
                label = (Label) node.getChildrenUnmodifiable().get(0);
                label.getStyleClass().clear();
                if (result.getString(Const.SUBJECT_TYPE).equals(Const.LECTURE)) {
                    label.setText("Лекция");
                    type = "Lecture";
                    label.getStyleClass().add(type + "Heading");
                } else if (result.getString(Const.SUBJECT_TYPE).equals(Const.SEMINAR)) {
                    label.setText("Семинар");
                    type = "Seminar";
                    label.getStyleClass().add(type + "Heading");
                } else if (result.getString(Const.SUBJECT_TYPE).equals(Const.PRACTICE)) {
                    label.setText("Практика");
                    type = "Practice";
                    label.getStyleClass().add(type + "Heading");
                } else if (result.getString(Const.SUBJECT_TYPE).equals(Const.LABORATORY)) {
                    label.setText("Лабораторная");
                    type = "Laboratory";
                    label.getStyleClass().add(type + "Heading");
                }

                label = (Label) node.getChildrenUnmodifiable().get(1);
                label.setText(result.getString(Const.SUBJECT_TIME_START).substring(0,5) + " - " + result.getString(Const.SUBJECT_TIME_END).substring(0,5));
                label.getStyleClass().clear();
                label.getStyleClass().add(type + "Time");

                label = (Label) node.getChildrenUnmodifiable().get(2);
                if(result.getString(Const.SUBJECT_NAME).length() > 8) label.setStyle("-fx-font-size: 23");
                label.setText(result.getString(Const.SUBJECT_NAME));
                label.getStyleClass().clear();
                label.getStyleClass().add(type + "Name");


                label = (Label) node.getChildrenUnmodifiable().get(3);
                label.setText(result.getString(Const.SUBJECT_TEACHER));
                label.getStyleClass().clear();
                label.getStyleClass().add(type + "Teacher");

                label = (Label) node.getChildrenUnmodifiable().get(4);
                label.setText(result.getString(Const.SUBJECT_ROOM));
                label.getStyleClass().clear();
                label.getStyleClass().add(type + "Room");

                label = (Label) node.getChildrenUnmodifiable().get(5);
                label.getStyleClass().clear();
                label.getStyleClass().add(type + "Ending");
                j++;
            }
        }
        if (j < 2) {
            scrollPane.setVmax(0);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }
        else {
            scrollPane.setVmax(1);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        }
        for(; i < 3; i++) {

            LeftVbox.getChildren().get(i).setVisible(false);
            for (; j < 2; j++) RightVbox.getChildren().get(j+1).setVisible(false);
        }
    }

    private void Clock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu");
            DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("cccc");
            TimeLabel.setText(LocalDateTime.now().format(timeFormatter));
            DateLabel.setText(LocalDateTime.now().format(dateFormatter));
            DayLabel.setText(LocalDateTime.now().format(dayFormatter));
        }));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public String whatWeekIs() {
        DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("w");
        String week = Const.TOP;
        if ((Integer.parseInt(LocalDateTime.now().format(weekFormatter))) % 2 == 0) week = Const.BOTTOM;
        return week;
    }

    public String whatWeekIs(LocalDate date) {
        DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("w");
        String week = Const.TOP;
        if ((Integer.parseInt(date.format(weekFormatter))) % 2 == 0) week = Const.BOTTOM;
        return week;
    }

    public void showScene(String window) {
        try {
            root = FXMLLoader.load(getClass().getResource(window));
            scene = new Scene(root);
            stage = (Stage) MainPane.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

