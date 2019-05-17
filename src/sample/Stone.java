package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Stone
{
    @FXML private Pane pane;
    private Button button;
    private static Player players;
    private static Board board;
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

            StoneChain[] nearChains = stoneChainsNear();
            if(nearChains == null)
            {
                stoneChain = new StoneChain();
                stoneChain.addStone(this);
                System.out.println(stoneChain.getStoneList());
            }
            else
            {
                //tutaj trzeba polaczyc ze soba wszystkie lancuchy
                StoneChain newStoneChain = new StoneChain();
                for (int i = 0; i < 4; i++)
                {
                    if(nearChains[i] != null)
                    {

                    }
                }
            }

            pane.getChildren().add(c);
            System.out.println(stonePosition.toString());
        });
    }

    public static void setPlayers(Player p)
    {
        players = p;
    }

    public static void setBoard(Board b)
    {
        board = b;
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

    private StoneChain[] stoneChainsNear()      //sprawdzac czy jakis lancuch obok tam dodac kamien
    {
        Position p = stonePosition.getStonePosition();
        StoneChain[] chains = new StoneChain[4];
        int x = stonePosition.getX();
        int y = stonePosition.getY();
        boolean nothingNear = true;
        if(p != Position.UP_WALL && p != Position.L_U_CORNER && p != Position.R_U_CORNER)       //jesli nie jest gdzies na gorze to sprawdzamy czy powyzej jest lancuch
        {
            if(board.stonePlaced(x, y - 1) && board.getStone(x, y - 1).getStoneColor() == stoneColor)       //jesli jest tam kamien i jest on tego samego koloru
            {
                Stone s = board.getStone(x , y - 1);
                if(s.getStoneChain() != stoneChain)            //jesli nie jest to kamien z tego samego lancucha
                {
                    chains[0] = s.getStoneChain();
                    nothingNear = false;
                }
            }
        }

        if(p != Position.DOWN_WALL && p != Position.L_D_CORNER && p != Position.R_D_CORNER)     //jesli nie jest gdzies na dole to sprawdzamy czy ponizej jest lancuch
        {
            if(board.stonePlaced(x, y + 1) && board.getStone(x, y + 1).getStoneColor() == stoneColor)       //jesli jest tam kamien i jest on tego samego koloru
            {
                Stone s = board.getStone(x, y + 1);
                if(s.getStoneChain() != stoneChain)            //jesli nie jest to kamien z tego samego lancucha
                {
                    chains[1] = s.getStoneChain();
                    nothingNear = false;
                }
            }
        }

        if(p != Position.LEFT_WALL && p != Position.L_U_CORNER && p != Position.L_D_CORNER)     //jesli nie jest gdzies po lewej to sprawdzamy czy po lewej jest lancuch
        {
            if(board.stonePlaced(x - 1,y) && board.getStone(x - 1, y).getStoneColor() == stoneColor)       //jesli jest tam kamien i jest on tego samego koloru
            {
                Stone s = board.getStone(x - 1, y);
                if(s.getStoneChain() != stoneChain)            //jesli nie jest to kamien z tego samego lancucha
                {
                    chains[2] = s.getStoneChain();
                    nothingNear = false;
                }
            }
        }

        if(p != Position.RIGHT_WALL && p != Position.R_U_CORNER && p != Position.R_D_CORNER)    //jesli nie jest gdzies po prawej to sprawdzamy czy po prawej jest lancuch
        {
            if(board.stonePlaced(x + 1 ,y) && board.getStone(x + 1, y).getStoneColor() == stoneColor)       //jesli jest tam kamien i jest on tego samego koloru
            {
                Stone s = board.getStone(x + 1, y);
                if(s.getStoneChain() != stoneChain)            //jesli nie jest to kamien z tego samego lancucha
                {
                    chains[3] = s.getStoneChain();
                    nothingNear = false;
                }
            }
        }
        if(nothingNear)
            return null;
        return chains;
    }

    public String toString()
    {
        return ("[ " + stonePosition.getX() + ", " + stonePosition.getY() + " ]");
    }

    public boolean isPlaced()
    {
        if(this.stoneColor != null)
            return true;
        else
            return false;
    }

    public Color getStoneColor()
    {
        return stoneColor;
    }

    public void setStoneChain(StoneChain s)
    {
        stoneChain = s;
    }

    public StoneChain getStoneChain()
    {
        return stoneChain;
    }
}
