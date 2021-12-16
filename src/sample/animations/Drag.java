package sample.animations;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Drag{
    private TranslateTransition tt1;

    public Drag(Node node) {
        tt1 = new TranslateTransition(Duration.millis(100), node);
        tt1.setFromX(-2.5);
        tt1.setByX(5);
    }

    public Drag(Node node, int cntr) {
        tt1 = new TranslateTransition(Duration.millis(100), node);

        if(cntr % 2 == 0) {
            tt1.setFromX(-2.5);
            tt1.setByX(5);
        }
        else{
            tt1.setFromX(5);
            tt1.setByX(-2.5);
        }
    }

    public void play() {

        tt1.play();
    }
}
