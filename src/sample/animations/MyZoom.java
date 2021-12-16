package sample.animations;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class MyZoom{
    private ScaleTransition tt1;

    public MyZoom(Node node, String direction) {
        if (direction.equals("in")) {
            tt1 = new ScaleTransition(Duration.millis(100), node);
            tt1.setFromX(1.05);
            tt1.setFromY(1.05);
            tt1.setToX(1.05);
            tt1.setToY(1.05);
        }
        else if (direction.equals("out")){
            tt1 = new ScaleTransition(Duration.millis(100), node);
            tt1.setFromX(1.05);
            tt1.setFromY(1.05);
            tt1.setToX(1);
            tt1.setToY(1);
        }
    }


//    public Drag(Node node, int cntr) {
//        tt1 = new TranslateTransition(Duration.millis(100), node);
//
//        if(cntr % 2 == 0) {
//            tt1.setFromX(-2.5);
//            tt1.setByX(5);
//        }
//        else{
//            tt1.setFromX(5);
//            tt1.setByX(-2.5);
//        }
//    }

    public void play() {
        tt1.play();
    }
}
