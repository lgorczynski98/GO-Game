package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Stone
{
    @FXML private Pane pane;
    private Button button;
    private static Player players;
    private Color stoneColor;

    private StoneChain stoneChain;
    private StonePosition stonePosition;

    public void initButton(int x, int y)
    {
        stonePosition = new StonePosition(x, y);
        button.setPrefSize(60, 60);
        button.setLayoutX(x * 66);
        button.setLayoutY(y * 66);
        button.setOpacity(0);
        pane.getChildren().add(button);
        button.setOnAction(actionEvent -> {
            Circle c = new Circle();
            c.setRadius(30);
            c.setLayoutX(button.getLayoutX() + 30);
            c.setLayoutY(button.getLayoutY() + 30);
            stoneColor = players.getColor();
            c.setFill(stoneColor);
            players.flipColor();
            c.setEffect(new DropShadow());
            button.setVisible(false);

            if(checkIfStoneChainNear() == null)
            {
                stoneChain = new StoneChain();
                stoneChain.addStone(this);
                //System.out.println(stoneChain.getStoneList());
            }

            pane.getChildren().add(c);
            System.out.println(stonePosition.toString());
        });
    }

    public static void setPlayers(Player p)
    {
        players = p;
    }

    public Stone(Pane pane ,int x, int y)
    {
        players = new Player();
        this.pane = pane;
        button = new Button();
        initButton(x, y);
    }

    public StonePosition getStonePosition() {
        return stonePosition;
    }

    public Pane getPane() {
        return pane;
    }

    private StoneChain checkIfStoneChainNear()      //sprawdzac czy jakis lancuch obok tam dodac kamien
    {
        return null;
    }
}
