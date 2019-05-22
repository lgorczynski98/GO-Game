package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Stone
{
    @FXML private Pane pane;
    @FXML private static Circle turnCircle;     //kolko kolorowe - jaki kolor tego gracza kolej
    @FXML private static Button passButton;     //przycisk pass
    @FXML private static Button newGameButton;  //przycisk nowa gra
    private Circle hint;
    private Button button;                      //przycisk do stawiania kamienia
    private static Player players;              //gracze
    private static Board board;
    private Color stoneColor;
    private boolean freePlace;                  //czy postawiony kamien
    private StoneChain stoneChain;              //w jakim jest lancuchu
    private StonePosition stonePosition;        //pozycja kamienia
    private Circle stone;                       //sam kamien
    private static int passCount = 0;           //ile passow
    private int boxWidth;

    public void initButton(int x, int y)
    {
        stonePosition = new StonePosition(x, y);
        boxWidth = 600 / (Board.MAX_SIZE - 1);
        button.setPrefSize(boxWidth - 10, boxWidth - 10);
        button.setLayoutX(x * boxWidth);          //9x9 --75
        button.setLayoutY(y * boxWidth);
        button.setOpacity(0);
        pane.getChildren().add(button);
        button.setOnAction(actionEvent -> {
            board.rememberState();
            freePlace = false;
            passCount = 0;
            stone = new Circle();
            stone.setRadius(boxWidth/2 - 2);
            stone.setLayoutX(button.getLayoutX() + 30);
            stone.setLayoutY(button.getLayoutY() + 30);
            stoneColor = players.getColor();
            stone.setFill(stoneColor);
            stone.setEffect(new DropShadow());
            button.setVisible(false);
            stone.setOnMouseClicked(mouseEvent -> System.out.println(stoneChain.toString() + " " + stoneChain.getLiberties()));

            StoneChain[] nearChains = stoneChainsNear();
            if(nearChains == null)  //tu jak nie ma zadnego lanucha dookola
            {
                stoneChain = new StoneChain();
                stoneChain.addStone(this);
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
            }
            pane.getChildren().add(stone);
            board.countAllLiberties();
            players.flipColor();//to na sam koniec trzeba zeby po wszystkim dopiero gracz sie zmienial
            turnCircle.setFill(players.getColor());
        });
        button.setOnMouseEntered(actionEvent -> {
            hint = new Circle();
            hint.setOpacity(0.5);
            hint.setRadius(boxWidth/2 - 2);
            hint.setFill(players.getColor());
            hint.setLayoutX(button.getLayoutX() + 30);
            hint.setLayoutY(button.getLayoutY() + 30);
            hint.setMouseTransparent(true);
            pane.getChildren().add(hint);
        });

        button.setOnMouseExited(actionEvent -> {
            pane.getChildren().remove(hint);
        });
        passButton.setOnAction(actionEvent -> {
            passCount++;
            players.flipColor();
            turnCircle.setFill(players.getColor());
            if(passCount == 2)
            {
                board.disableBoard();
                passButton.setVisible(false);
                displayScore();
            }
        });
    }

    public Button getButton() {
        return button;
    }

    public static void setPassButton(Button passButton) {
        Stone.passButton = passButton;
    }

    public static void setNewGameButton(Button newGameButton) {
        Stone.newGameButton = newGameButton;
    }

    public static void setTurnCircle(Circle c)
    {
        turnCircle = c;
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

    private void displayScore()
    {
        int blackScore = board.countScore(Color.BLACK);
        int whiteScore = board.countScore(Color.WHITE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Score");
        alert.setHeaderText("Scoring type: stone scoring");
        String score = "Black:\t" + blackScore + "\nWhite:\t" + whiteScore;
        alert.setContentText(score);
        alert.showAndWait();
    }

    public static Circle getTurnCircle() {
        return turnCircle;
    }
}
