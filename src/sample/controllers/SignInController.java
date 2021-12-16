package sample.controllers;

import animatefx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javafx.util.Duration;
import sample.database.DatabaseHandler;
import sample.database.StaticSubject;
import sample.database.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

public class SignInController {

    @FXML
    private Hyperlink SignUpLink;

    @FXML
    private Button SignInButton, BackButton;

    @FXML
    private TextField EmailField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Label SignUpLabel;

    @FXML
    private VBox Vbox;

    @FXML
    private AnchorPane MainPane;


    private Stage stage;
    private Scene scene;
    private Parent root;

    int i = 0;

    private String password;
    public static String email;


    int j = 0;
    int k = 0;


    @FXML
    void initialize() {

        if(StaticSubject.conditionSignIn == 0) {
            Vbox.setOpacity(0);
            new FadeIn(Vbox).play();
            StaticSubject.conditionSignIn = 0;
        }
        else if (StaticSubject.conditionSignIn == 1){
            Vbox.setOpacity(0);
            new FadeInUpBig(Vbox).play();
            StaticSubject.conditionSignIn = 1;

        }


        EmailField.setOnKeyReleased(event -> {
            SignUpLabel.setPrefWidth(400);
            SignUpLink.setPrefWidth(0);
            SignUpLabel.setText("Введите адрес электронной почты");
            SignUpLink.setText("");
            SignUpLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignUpLabel.setAlignment(Pos.CENTER);
            SignUpLink.setVisible(true);
            new FadeIn(SignUpLabel).play();
            i = 0;
        });

        EmailField.setOnMouseClicked(event -> {
            SignUpLabel.setPrefWidth(400);
            SignUpLink.setPrefWidth(0);
            SignUpLabel.setText("Введите адрес электронной почты");
            SignUpLink.setText("");
            SignUpLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignUpLabel.setAlignment(Pos.CENTER);
            SignUpLink.setVisible(true);
            new FadeIn(SignUpLabel).play();
            i = 0;
        });

        PasswordField.setOnKeyReleased(zoomEvent -> {
            SignUpLabel.setPrefWidth(400);
            SignUpLink.setPrefWidth(0);
            SignUpLabel.setText("Введите пароль");
            SignUpLink.setText("");
            SignUpLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignUpLink.setVisible(true);
            SignUpLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignUpLabel).play();
            i = 0;
        });

        PasswordField.setOnMouseClicked(zoomEvent -> {
            SignUpLabel.setPrefWidth(400);
            SignUpLink.setPrefWidth(0);
            SignUpLabel.setText("Введите пароль");
            SignUpLink.setText("");
            SignUpLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
            SignUpLink.setVisible(true);
            SignUpLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignUpLabel).play();
            i = 0;
        });

        MainPane.setOnMouseReleased(mouseEvent -> {
            if (i == 0){
                SignUpLabel.setPrefWidth(240);
                SignUpLink.setPrefWidth(200);
                SignUpLabel.setText("Нет аккаунта? ");
                SignUpLink.setText("Создать");
                SignUpLabel.setTextFill(Paint.valueOf("rgba(255,255,255,0.8)"));
                SignUpLink.setVisible(true);
                SignUpLabel.setAlignment(Pos.CENTER_RIGHT);
                new FadeIn(SignUpLabel).play();
                new FadeIn(SignUpLink).play();
                i = 1;
            }

        });


        SignInButton.setOnAction (event -> {
            email = EmailField.getText();
            password = PasswordField.getText();
            System.out.println("Login!\nEmail: "+email+"\nPassword: "+password+"\n");
            signInUser(email, password, event);
            i = 0;
        });

        SignUpLink.setOnAction(event -> {
            FadeOutDownBig animation = new FadeOutDownBig(Vbox);
            animation.setOnFinished(event1 -> showScene("/sample/fxml/SignUp1.fxml"));
            animation.play();
            i = 0;
        });

    }

    public static boolean isValidPassword(String password)
    {
        Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{7,26}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    private void signInUser(String email, String pass, ActionEvent event) {
        DatabaseHandler Handler = new DatabaseHandler();
        if (StaticSubject.error_code == 0) {
            new Shake(Vbox).play();
            SignUpLabel.setPrefWidth(400);
            SignUpLink.setPrefWidth(0);
            SignUpLabel.setText("Ошибка при подключении.\nПроверьте интернет-соединение");
            SignUpLabel.setTextFill(Paint.valueOf("#b41107"));
            SignUpLink.setVisible(false);
            SignUpLabel.setTextAlignment(TextAlignment.CENTER);
            SignUpLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignUpLabel).play();
        }
        else if(!EmailValidator.getInstance().isValid(email)) {
            new Shake(EmailField).play();
            SignUpLabel.setPrefWidth(400);
            SignUpLink.setPrefWidth(0);
            SignUpLabel.setText("Некорректный адрес электронной почты");
            SignUpLabel.setTextFill(Paint.valueOf("#b41107"));
            SignUpLink.setVisible(false);
            SignUpLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignUpLabel).play();
        }
        else if (!isValidPassword(password)){
            new Shake(PasswordField).play();
            SignUpLabel.setPrefWidth(400);
            SignUpLink.setPrefWidth(0);
            SignUpLabel.setText("Некорректный пароль");
            SignUpLabel.setTextFill(Paint.valueOf("#b41107"));
            SignUpLink.setVisible(false);
            SignUpLabel.setAlignment(Pos.CENTER);
            new FadeIn(SignUpLabel).play();
        }
        else {
            FadeOut animation = new FadeOut(SignUpLabel);
            new FadeOut(SignUpLink).play();
            animation.play();

            animation.setOnFinished(event1 -> {
                User user = new User(email, pass);
                int cntr = 0;
                try {
                    ResultSet result = Handler.getUser(user);
                    while(result.next()) {
                        System.out.print(result.getString("user_name"));
                        cntr++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();

                }
                if (cntr == 1) {

                    SignUpLabel.setPrefWidth(400);
                    SignUpLink.setPrefWidth(0);
                    SignUpLabel.setOpacity(0);
                    SignUpLabel.setText("Успешно");
                    SignUpLabel.setTextFill(Paint.valueOf("green"));
                    SignUpLink.setVisible(false);
                    SignUpLabel.setAlignment(Pos.CENTER);
                    FadeIn signUpLabelAnimation = new FadeIn(SignUpLabel);
                    signUpLabelAnimation.setOnFinished(event2 -> {
                        FadeOut animation2 = new FadeOut(Vbox);
                        animation2.setOnFinished(event3 -> showScene("/sample/fxml/reMain.fxml"));
                        animation2.play();
                    });
                    signUpLabelAnimation.setDelay(Duration.millis(750));
                    signUpLabelAnimation.play();
                }
                else {
                    new FadeIn(SignUpLabel).play();
                    new Shake(EmailField).play();
                    new Shake(PasswordField).play();
                    SignUpLabel.setPrefWidth(365);
                    SignUpLink.setPrefWidth(75);
                    SignUpLabel.setText("Неверный пароль или адрес электронной почты");
                    SignUpLabel.setTextFill(Paint.valueOf("#b41107"));
                    SignUpLink.setVisible(false);
                    SignUpLabel.setAlignment(Pos.CENTER_RIGHT);

                }
            });

        }
    }

    public void showScene(String window) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(window)));
            scene = new Scene(root);
            stage = (Stage) MainPane.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}




