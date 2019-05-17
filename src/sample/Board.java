package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.List;

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
                stones[i][j] = new Stone(pane, j, i);
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
}
