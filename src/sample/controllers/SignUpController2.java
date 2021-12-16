package sample.controllers;

import java.io.IOException;

import animatefx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.checkdigit.ABANumberCheckDigit;
import sample.animations.MyBackFlip;
import sample.database.DatabaseHandler;
import sample.database.StaticSubject;
import sample.database.User;

import static sample.controllers.SignInController.isValidPassword;

public class SignUpController2 {

    @FXML
    private Hyperlink SignInLink;

    @FXML
    private Button SignUpButton, BackButton;

    @FXML
    private TextField EmailField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private PasswordField RetypePasswordField;

    @FXML
    private Label SignInLabel;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private VBox Vbox;


    String pass;
    static String email;

    private Stage stage;
    private Scene scene;
    private Parent root;

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

        EmailField.setText(email);



        EmailField.setOnKeyReleased(event -> {
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Введите адрес электронной почты");
            SignInLink.setText("");
            SignInLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignInLabel.setAlignment(Pos.CENTER);
            SignInLink.setVisible(true);
            new FadeIn(SignInLabel).play();
            i = 0;
        });

        EmailField.setOnMouseClicked(event -> {
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Введите адрес электронной почты");
            SignInLink.setText("");
            SignInLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignInLabel.setAlignment(Pos.CENTER);
            SignInLink.setVisible(true);
            new FadeIn(SignInLabel).play();
            i = 0;
        });

        PasswordField.setOnKeyReleased(zoomEvent -> {
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Придумайте пароль от 8 до 25 символов.\nРазрешённые символы(a-z, A-Z, 0-9)");
            SignInLink.setText("");
            SignInLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignInLink.setVisible(true);
            SignInLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignInLabel).play();
            i = 0;
        });

        PasswordField.setOnMouseClicked(zoomEvent -> {
            i = 0;
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Придумайте пароль от 8 до 25 символов.\nРазрешённые символы(a-z, A-Z, 0-9)");
            SignInLink.setText("");
            SignInLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignInLink.setVisible(true);
            SignInLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignInLabel).play();
        });
        RetypePasswordField.setOnKeyReleased(zoomEvent -> {
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Повторите пароль");
            SignInLink.setText("");
            SignInLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignInLink.setVisible(true);
            SignInLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignInLabel).play();
            i = 0;
        });

        RetypePasswordField.setOnMouseClicked(zoomEvent -> {
            i = 0;
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Повторите пароль");
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

        SignUpButton.setOnAction (event -> {
            email = EmailField.getText();
            pass = PasswordField.getText();
            if(isDataValid()) {
                signUpUser();
                FadeOut animation = new FadeOut(Vbox);
                animation.setOnFinished(event1 -> showScene("/sample/fxml/reMain.fxml"));
                animation.play();
            }
        });
        BackButton.setOnAction(event -> {
            email = EmailField.getText();
            StaticSubject.conditionSignOut = 1;
            SignUpButton.setText("Далее");
            EmailField.setText(SignUpController1.name);
            PasswordField.setText(SignUpController1.surname);
            RetypePasswordField.setText(SignUpController1.group_mem.trim());
            MyBackFlip flip = new MyBackFlip(Vbox);
            flip.setOnFinished(event1 -> {
                showScene("/sample/fxml/SignUp1.fxml");
            });
            flip.setSpeed(2);
            flip.play();

        });
        SignInLink.setOnAction (event -> {
            StaticSubject.conditionSignIn = 1;
            FadeOutUpBig animation1 = new FadeOutUpBig(Vbox);
            animation1.setOnFinished(event1 -> {
                showScene("/sample/fxml/SignIn.fxml");
            });
            animation1.play();
            i = 0;
        });
    }

    private boolean isDataValid() {
        if (email == null || email.trim().equals("")){
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Вы не ввели адрес электронной почты");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new Shake(EmailField).play();
            new FadeIn(SignInLabel).play();
            return false;
        }
        else if(!EmailValidator.getInstance().isValid(email)) {
            new Shake(EmailField).play();
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Некорректный адрес электронной почты");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignInLabel).play();
            return false;
        }
        else if (pass == null || pass.trim().equals("")){
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Вы не ввели пароль");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new Shake(PasswordField).play();
            new FadeIn(SignInLabel).play();
            return false;
        }
        else if (pass.length() < 8){
            new Shake(PasswordField).play();
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Слишком короткий пароль");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignInLabel).play();
            return false;
        }
        else if (pass.length() > 25){
            new Shake(PasswordField).play();
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Слишком длинный пароль");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignInLabel).play();
            return false;
        }

        else if (!isValidPassword(pass)){
            new Shake(PasswordField).play();
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Некорректный пароль");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignInLabel).play();
            return false;
        }
        else if(!(pass.equals(RetypePasswordField.getText()))) {
            new Shake(PasswordField).play();
            SignInLabel.setPrefWidth(400);
            SignInLink.setPrefWidth(0);
            SignInLabel.setText("Пароли не совпадают");
            SignInLabel.setTextFill(Paint.valueOf("#b41107"));
            SignInLink.setVisible(false);
            SignInLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignInLabel).play();
            return false;
        }
        else return true;
    }

    private void signUpUser() {

        System.out.print("Email: "+email+"\nPassword: "+pass+"\n");

        DatabaseHandler Handler = new DatabaseHandler();

        User user = new User(SignUpController1.name, SignUpController1.surname, SignUpController1.group, email, pass);
        Handler.signUpUser(user);

    }
}

