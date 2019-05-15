package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class Board
{
    private static final int MAX_SIZE = 10;

    @FXML private Pane pane;
    private Stone[][] stones;

    //private Player playerTuner = Player.BLACK;

    public Board(Pane pane)
    {
        this.pane = pane;
        stones = new Stone[MAX_SIZE][MAX_SIZE];
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                stones[i][j] = new Stone(pane, j * 66, i * 66);
            }
        }
    }
}
