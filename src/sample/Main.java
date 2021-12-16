package sample;

import com.itextpdf.text.DocumentException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static javafx.application.Application.launch;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/SignIn.fxml"));
//        String css = this.getClass().getResource("css/application.css").toExternalForm();
        Scene scene = new Scene(root);
//        scene.getStylesheets().add(css);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

//        createPDF obj = new createPDF();
//        obj.setTable("bot");


    }
}
