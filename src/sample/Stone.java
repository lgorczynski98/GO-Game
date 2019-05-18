package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Stone
{
    @FXML private Pane pane;
    private Button button;
    private static Player players;
    private static Board board;
    private Color stoneColor;
    private boolean freePlace;
    private StoneChain stoneChain;
    private StonePosition stonePosition;
    private Circle stone;

    public void initButton(int x, int y)
    {
        stonePosition = new StonePosition(x, y);
        button.setPrefSize(60, 60);
        button.setLayoutX(x * 66);
        button.setLayoutY(y * 66);
        button.setOpacity(0);
        pane.getChildren().add(button);
        button.setOnAction(actionEvent -> {
            freePlace = false;
            stone = new Circle();
            stone.setRadius(30);
            stone.setLayoutX(button.getLayoutX() + 30);
            stone.setLayoutY(button.getLayoutY() + 30);
            stoneColor = players.getColor();
            stone.setFill(stoneColor);
            players.flipColor();
            stone.setEffect(new DropShadow());
            stone.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println(stoneChain.toString() + " " + stoneChain.getLiberties());
                }
            });
            button.setVisible(false);

            StoneChain[] nearChains = stoneChainsNear();
            if(nearChains == null)  //tu jak nie ma zadnego lanucha dookola
            {
                stoneChain = new StoneChain();
                stoneChain.addStone(this);
                //System.out.println(stoneChain.getStoneList());
            }
            else//tutaj trzeba polaczyc ze soba wszystkie lancuchy
            {
                StoneChain newStoneChain = new StoneChain();
                stoneChain = newStoneChain;
                newStoneChain.addStone(this);
                for (int i = 0; i < 4; i++)
                {
                    if(nearChains[i] != null)
                    {
                        for (Stone stone : nearChains[i].getStoneList())
                        {
                            stone.setStoneChain(newStoneChain);
                            newStoneChain.addStone(stone);
                        }
                    }
                }
                //System.out.println(stoneChain.toString());
            }
            pane.getChildren().add(stone);
            board.countAllLiberties();
            //System.out.println(stonePosition.toString());
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
        this.pane = pane;
        button = new Button();
        freePlace = true;
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
        if(!stonePosition.isOnTopWall())       //jesli nie jest gdzies na gorze to sprawdzamy czy powyzej jest lancuch
        {
            if(isStoneUP() && board.getStone(x, y - 1).getStoneColor() == stoneColor)       //jesli jest tam kamien i jest on tego samego koloru
            {
                Stone s = board.getStone(x , y - 1);
                if(s.getStoneChain() != stoneChain)            //jesli nie jest to kamien z tego samego lancucha
                {
                    chains[0] = s.getStoneChain();
                    nothingNear = false;
                }
            }
        }

        if(!stonePosition.isOnBottomWall())     //jesli nie jest gdzies na dole to sprawdzamy czy ponizej jest lancuch
        {
            if(isStoneDOWN() && board.getStone(x, y + 1).getStoneColor() == stoneColor)       //jesli jest tam kamien i jest on tego samego koloru
            {
                Stone s = board.getStone(x, y + 1);
                if(s.getStoneChain() != stoneChain)            //jesli nie jest to kamien z tego samego lancucha
                {
                    chains[1] = s.getStoneChain();
                    nothingNear = false;
                }
            }
        }

        if(!stonePosition.isOnLeftWall())     //jesli nie jest gdzies po lewej to sprawdzamy czy po lewej jest lancuch
        {
            if(isStoneLEFT() && board.getStone(x - 1, y).getStoneColor() == stoneColor)       //jesli jest tam kamien i jest on tego samego koloru
            {
                Stone s = board.getStone(x - 1, y);
                if(s.getStoneChain() != stoneChain)            //jesli nie jest to kamien z tego samego lancucha
                {
                    chains[2] = s.getStoneChain();
                    nothingNear = false;
                }
            }
        }

        if(!stonePosition.isOnRightWall())    //jesli nie jest gdzies po prawej to sprawdzamy czy po prawej jest lancuch
        {
            if(isStoneRIGHT() && board.getStone(x + 1, y).getStoneColor() == stoneColor)       //jesli jest tam kamien i jest on tego samego koloru
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
        return !freePlace;
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

    public boolean isStoneUP()
    {
        if(!stonePosition.isOnTopWall() && board.getStone(stonePosition.getX(), stonePosition.getY() - 1).freePlace)
            return false;
        else
            return true;
    }

    public boolean isStoneDOWN()
    {
        if(!stonePosition.isOnBottomWall() && board.getStone(stonePosition.getX(), stonePosition.getY() + 1).freePlace)
            return false;
        else
            return true;
    }

    public boolean isStoneLEFT()
    {
        if(!stonePosition.isOnLeftWall() && board.getStone(stonePosition.getX() - 1, stonePosition.getY()).freePlace)
            return false;
        else
            return true;
    }

    public boolean isStoneRIGHT()
    {
        if(!stonePosition.isOnRightWall() && board.getStone(stonePosition.getX() + 1, stonePosition.getY()).freePlace)
            return false;
        else
            return true;
    }

    public void destroyStone()
    {
        pane.getChildren().remove(stone);
        freePlace = true;
        stoneColor = null;
        button.setVisible(true);
        stoneChain = null;
    }
}
