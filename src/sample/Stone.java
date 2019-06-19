package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Stone button and circle of a stone
 */
public class Stone
{
    /**
     * Game pane
     */
    @FXML private Pane pane;
    /**
     * Circle showing whose turn it is
     */
    @FXML private static Circle turnCircle;     //kolko kolorowe - jaki kolor tego gracza kolej
    /**
     * Button to pass player turn
     */
    @FXML private static Button passButton;     //przycisk pass
    /**
     * Button to start new game
     */
    @FXML private static Button newGameButton;  //przycisk nowa gra
    /**
     * Circle shown as a hint where the stone will be placed
     */
    private Circle hint;
    /**
     * When clicked, stone is placed in that place (point on a cross of lines)
     */
    private Button button;                      //przycisk do stawiania kamienia
    /**
     * Player whose turn it is
     */
    private static Player players;              //gracze
    /**
     * Game main board
     */
    private static Board board;
    /**
     * Color of a stone
     */
    private Color stoneColor;
    /**
     * Stone is placed or doesn't
     */
    private boolean freePlace;                  //czy postawiony kamien
    /**
     * Stone chain which contains this stone
     */
    private StoneChain stoneChain;              //w jakim jest lancuchu
    /**
     * Position in which stone is (used to count liberties)
     */
    private StonePosition stonePosition;        //pozycja kamienia
    /**
     * Circle that represents a stone
     */
    private Circle stone;                       //sam kamien
    /**
     * Count of how many passes there were
     */
    private static int passCount = 0;           //ile passow
    /**
     * Width of a boxes that are made on the board with grid lines
     */
    private int boxWidth;

    /**
     * Initiatization of a stone button, stone hints and pass button
     * @param x x position on the board
     * @param y y position on the board
     */
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
        });     //what happens when placins a stone

        button.setOnMouseEntered(actionEvent -> {
            hint = new Circle();
            hint.setOpacity(0.5);
            hint.setRadius(boxWidth/2 - 2);
            hint.setFill(players.getColor());
            hint.setLayoutX(button.getLayoutX() + 30);
            hint.setLayoutY(button.getLayoutY() + 30);
            hint.setMouseTransparent(true);
            pane.getChildren().add(hint);
        });     //what happens when entering a point where stone can be placed

        button.setOnMouseExited(actionEvent -> {
            pane.getChildren().remove(hint);
        });     //what happens when exiting a point where stone can be placed

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
        });     //what happens after pressing pass button
    }

    /**
     * Get the button on the stone posiiton
     * @return Stone button
     */
    public Button getButton() {
        return button;
    }

    /**
     * Sets the button that is responsible for passing turns
     * @param passButton Button that is responsible for passing turns by players
     */
    public static void setPassButton(Button passButton) {
        Stone.passButton = passButton;
    }

    /**
     * Sets the button that is responsible for starting new game
     * @param newGameButton Button that will be starting new game
     */
    public static void setNewGameButton(Button newGameButton) {
        Stone.newGameButton = newGameButton;
    }

    /**
     * Sets the cirlce that shows whose turn it si
     * @param c Circle that will show whose turn it is
     */
    public static void setTurnCircle(Circle c)
    {
        turnCircle = c;
    }

    /**
     * Sets players of the game
     * @param p Players
     */
    public static void setPlayers(Player p)
    {
        players = p;
    }

    /**
     * Sets the board of the game
     * @param b Game board
     */
    public static void setBoard(Board b)
    {
        board = b;
    }

    /**
     * Stone constructor
     * @param pane Game pane
     * @param x x value of stone position
     * @param y y value of stone position
     */
    public Stone(Pane pane ,int x, int y)
    {
        this.pane = pane;
        button = new Button();
        freePlace = true;
        initButton(x, y);
    }

    /**
     * Checks if there is any stone chain near the stone. If there are some chains, then it merge them into one.
     *  @return Stone chain which contains now this stone (after placing it)
     */
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

    /**
     * Gets the color of this stone
     * @return Color of the stone
     */
    public Color getStoneColor()
    {
        return stoneColor;
    }

    /**
     * Sets the stone chain that stone will be a member
     * @param s Stone chain
     */
    public void setStoneChain(StoneChain s)
    {
        stoneChain = s;
    }

    /**
     * Gets the stone chain that stone is a member
     * @return Stone chain
     */
    public StoneChain getStoneChain()
    {
        return stoneChain;
    }

    /**
     * Checks if stone is on the top of the board
     * @return Returns true if stone is on the top of the board
     */
    public boolean isStoneUP()
    {
        if(!stonePosition.isOnTopWall() && board.getStone(stonePosition.getX(), stonePosition.getY() - 1).freePlace)
            return false;
        else
            return true;
    }

    /**
     * Checks if stone is on the bottom of the board
     * @return Returns true if stone is on the bottom of the board
     */
    public boolean isStoneDOWN()
    {
        if(!stonePosition.isOnBottomWall() && board.getStone(stonePosition.getX(), stonePosition.getY() + 1).freePlace)
            return false;
        else
            return true;
    }

    /**
     * Checks if stone is on the left of the board
     * @return Returns true if stone is on the left of the board
     */
    public boolean isStoneLEFT()
    {
        if(!stonePosition.isOnLeftWall() && board.getStone(stonePosition.getX() - 1, stonePosition.getY()).freePlace)
            return false;
        else
            return true;
    }

    /**
     * Checks if stone is on the right of the board
     * @return Returns true if stone is on the right of the board
     */
    public boolean isStoneRIGHT()
    {
        if(!stonePosition.isOnRightWall() && board.getStone(stonePosition.getX() + 1, stonePosition.getY()).freePlace)
            return false;
        else
            return true;
    }

    /**
     * Destroys the stone (takes stone off the board and makes it free space)
     */
    public void destroyStone()
    {
        pane.getChildren().remove(stone);
        freePlace = true;
        stoneColor = null;
        button.setVisible(true);
        stoneChain = null;
    }

    /**
     * Displaying score of the game (both players points)
     */
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

    /**
     * Gets cirlce that shows whose turn it is
     * @return Circle that shows whose turn it is
     */
    public static Circle getTurnCircle() {
        return turnCircle;
    }
}
