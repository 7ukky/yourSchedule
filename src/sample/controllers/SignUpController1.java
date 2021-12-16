package sample.controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import animatefx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import sample.animations.MyBackFlip;
import sample.animations.MyForwardFlip;
import sample.database.Const;
import sample.database.DatabaseHandler;
import sample.database.StaticSubject;

public class SignUpController1 {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Hyperlink SignInLink;

    @FXML
    private Button NextButton, BackButton;

    @FXML
    private TextField NameField, SurnameField;

    @FXML
    private ComboBox<String> ComboBox;

    @FXML
    private VBox Vbox;

    @FXML
    private Label SignInLabel, HeaderLabel;

    @FXML
    private AnchorPane MainPane;

//    String css = this.getClass().getResource("application.css").toExternalForm();

    static String name;
    static String surname;
    static int group;
    static String group_mem;

    int i = 0;

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

    @FXML
    void initialize() {

        DatabaseHandler Handler = new DatabaseHandler();
        ResultSet result = Handler.getAllGroups();
        setComboBox(result);

        if (StaticSubject.conditionSignOut == 0) {
            Vbox.setOpacity(0);
            new FadeInDownBig(Vbox).play();
        }
        else if (StaticSubject.conditionSignOut == 1) {
            StaticSubject.conditionSignOut = 0;
        }

        NameField.setText(name);
        SurnameField.setText(surname);
        ComboBox.setValue(group_mem);






        NameField.setOnKeyReleased(event -> {
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Введите имя");
            SignInLink.setText("");
            SignInLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignInLabel.setAlignment(Pos.CENTER);
            SignInLink.setVisible(true);
            new FadeIn(SignInLabel).play();
            i = 0;
        });

        NameField.setOnMouseClicked(event -> {
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Введите имя");
            SignInLink.setText("");
            SignInLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignInLabel.setAlignment(Pos.CENTER);
            SignInLink.setVisible(true);
            new FadeIn(SignInLabel).play();
            i = 0;
        });

       SurnameField.setOnKeyReleased(zoomEvent -> {
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Введите фамилию");
            SignInLink.setText("");
            SignInLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignInLink.setVisible(true);
            SignInLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignInLabel).play();
            i = 0;
        });

        SurnameField.setOnMouseClicked(zoomEvent -> {
            i = 0;
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Введите фамилию");
            SignInLink.setText("");
            SignInLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignInLink.setVisible(true);
            SignInLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignInLabel).play();
        });

        ComboBox.setOnKeyReleased(zoomEvent -> {
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Выберите группу");
            SignInLink.setText("");
            SignInLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignInLink.setVisible(true);
            SignInLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignInLabel).play();
            i = 0;
        });

        ComboBox.setOnMouseClicked(zoomEvent -> {
            i = 0;
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Выберите группу");
            SignInLink.setText("");
            SignInLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignInLink.setVisible(true);
            SignInLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignInLabel).play();
        });
        MainPane.setOnMouseReleased(mouseEvent -> {
            if (i == 0){
                SignInLabel.setPrefWidth(220);
                SignInLink.setPrefWidth(180);
                SignInLabel.setText("Есть аккаунт? ");
                SignInLink.setText("Войти");
                SignInLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
                SignInLink.setVisible(true);
                SignInLabel.setAlignment(Pos.CENTER_RIGHT);
                new FadeIn(SignInLabel).play();
                new FadeIn(SignInLink).play();
                i = 1;
            }
        });
        NextButton.setOnAction(event -> {
            i = 0;
            name = NameField.getText();
            surname = SurnameField.getText();
            if (isDataValid()){
                group_mem = ComboBox.getValue();
                ResultSet result2 = Handler.getGroupId(ComboBox.getValue().trim());
                setGroupId(result2);
                MyForwardFlip flip = new MyForwardFlip(Vbox);

                NameField.setText(SignUpController2.email);
                SurnameField.setText("");
                ComboBox.setPromptText("");
                NextButton.setText("Зарегистрироваться");
                flip.setOnFinished(event1 -> {
                    showScene("/sample/fxml/SignUp2.fxml");
                });
                flip.setSpeed(2);
                flip.play();

            }
        });
        SignInLink.setOnAction(event -> {
            StaticSubject.conditionSignIn = 1;
            FadeOutUpBig animation1 = new FadeOutUpBig(Vbox);
            animation1.setOnFinished(event1 -> {
                showScene("/sample/fxml/SignIn.fxml");
            });
            animation1.play();
            i = 0;
        });
        BackButton.setOnAction(event -> {
            StaticSubject.conditionSignIn = 1;
            FadeOutUpBig animation1 = new FadeOutUpBig(Vbox);
            animation1.setOnFinished(event1 -> {
                showScene("/sample/fxml/SignIn.fxml");
            });
            animation1.play();
            i = 0;
        });
    }

    public boolean isDataValid() {
        if (name == null || name.trim().equals("")){
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Вы не ввели имя");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new Shake(NameField).play();
            new FadeIn(SignInLabel).play();
            return false;
        }
        else if(name.trim().length() < 2){
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Слишком короткое имя");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new Shake(NameField).play();
            new FadeIn(SignInLabel).play();
            return false;
        }
        else if(name.trim().length() > 20){
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Слишком длинное имя");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new Shake(NameField).play();
            new FadeIn(SignInLabel).play();
            return false;
        }
        else if (surname == null || surname.trim().equals("")){
            new Shake(SurnameField).play();
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Вы не ввели фамилию");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new Shake(SurnameField).play();
            new FadeIn(SignInLabel).play();
            return false;
        }
        else if(surname.trim().length() < 2){
            new Shake(SurnameField).play();
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Слишком короткая фамилия");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new Shake(SurnameField).play();
            new FadeIn(SignInLabel).play();
            return false;
        }
        else if(surname.trim().length() > 20){
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Слишком длинная фамилия");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new Shake(SurnameField).play();
            new FadeIn(SignInLabel).play();
            return false;
        }


        else if(ComboBox.getValue() == null){
            new Shake(ComboBox).play();
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Вы не выбрали группу");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new Shake(ComboBox).play();
            return false;
        }
        else return true;
    }

    void setComboBox(ResultSet result) {
        try {
            result.last();
            int size = result.getRow();
            System.out.println(size);
            String[] groups = new String[size];
            for (result.first(); size != 0; size--) {
                groups[size-1] = "      "+result.getString(Const.GROUP_NAME);
                result.next();
            }
            ComboBox.getItems().addAll(groups);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setGroupId(ResultSet result) {
        try {
            result.next();
            group = result.getInt(Const.ID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

