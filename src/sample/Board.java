package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

/**
 * Board class containing all the stones and players
 */
public class Board
{
    /**
     * Size of a board - MAX_SIZE x MAX_SIZE
     */
    public static int MAX_SIZE;
    /**
     * Game Pane
     */
    @FXML private Pane pane;
    /**
     * Table containing all the stones (places where stone can be placed)
     */
    private Stone[][] stones;
    /**
     * Player whose turn it currently is
     */
    private Player players;

    /**
     * Constructor preparing the board
     * @param pane - game pane
     * @param size - size of a board
     */
    public Board(Pane pane, int size)
    {
        this.MAX_SIZE = size;
        this.pane = pane;
        this.players = new Player();
        Stone.setBoard(this);
        Stone.setPlayers(this.players);
        stones = new Stone[MAX_SIZE][MAX_SIZE];
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                stones[i][j] = new Stone(pane, i, j);
            }
        }
    }

    /**
     * Method that return stone which is on the given position
     * @param x x coordinate
     * @param y y coordinate
     * @return Stone
     */
    public Stone getStone(int x, int y)
    {
        return stones[x][y];
    }

    /**
     * Method that counts all the liberties of every stone chain on the board
     */
    public void countAllLiberties()
    {
        Set<StoneChain> nowPlacing = new HashSet<>();
        Set<StoneChain> other = new HashSet<>();
        for (int i = 0; i < MAX_SIZE; i++)
        {
            for (int j = 0; j < MAX_SIZE; j++)
            {
                if(stones[i][j] != null)
                {
                    if(stones[i][j].getStoneColor() == players.getColor())
                        nowPlacing.add(stones[i][j].getStoneChain());
                    else
                        other.add(stones[i][j].getStoneChain());
                }
            }
        }

        for (StoneChain chain : other)
        {
            if(chain != null) chain.countLiberties();
        }

        for (StoneChain chain : nowPlacing)
        {
            if(chain != null) chain.countLiberties();
        }
    }

    /**
     * Method that disable ability to place stones on the board
     */
    public void disableBoard()
    {
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                stones[i][j].getButton().setVisible(false);
            }
        }
    }

    /**
     * Method count score of player
     * @param playerColor player whose points are counted
     * @return player score
     */
    public int countScore(Color playerColor)
    {
        int score = 0;
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                if(stones[i][j].getStoneColor() == playerColor)
                    score++;
            }
        }
        return score;
    }

    /**
     * Start new game
     */
    public void newGame()
    {
        players.setColor(Color.BLACK);
        Stone.getTurnCircle().setFill(players.getColor());
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                stones[i][j].destroyStone();
                stones[i][j].getButton().setVisible(false);
            }
        }
    }
}
