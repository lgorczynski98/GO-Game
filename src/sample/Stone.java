package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Stone
{
    @FXML private Pane pane;
    private Button button;
    private static Player player;

    public void initButton(double x, double y)
    {
        button.setPrefSize(60, 60);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setOpacity(0);
        pane.getChildren().add(button);
        button.setOnAction(actionEvent -> {
            Circle c = new Circle();
            c.setRadius(30);
            c.setLayoutX(button.getLayoutX() + 30);
            c.setLayoutY(button.getLayoutY() + 30);
            c.setFill(player.getColor());
            player.flipColor();
            c.setEffect(new DropShadow());
            pane.getChildren().add(c);
        });
    }

    public Stone(Pane pane ,double x, double y)
    {
        player = new Player();
        this.pane = pane;
        button = new Button();
        initButton(x, y);
    }
}
