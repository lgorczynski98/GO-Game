package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

public class Board
{
    public static final int MAX_SIZE = 10;

    @FXML private Pane pane;
    private Stone[][] stones;
    private Player players;

    public Board(Pane pane)
    {
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

    public Stone getStone(int x, int y)
    {
        return stones[x][y];
    }

    public boolean stonePlaced(int x, int y)
    {
        if(stones[x][y].isPlaced())
            return true;
        else
            return false;
    }

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

    public void disableBoard()
    {
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                stones[i][j].getButton().setVisible(false);
            }
        }
    }

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

    public void newGame()
    {
        players.setColor(Color.BLACK);
        Stone.getTurnCircle().setFill(players.getColor());
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                stones[i][j].destroyStone();
            }
        }
    }
}
