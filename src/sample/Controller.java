package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{

    @FXML private Pane pane;
    @FXML private GridPane gridPane;
    @FXML private Circle turnCircle;
    @FXML private Button passButton;
    @FXML private Button newGameButton;
    private Board board;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        passButton.setCursor(Cursor.HAND);
        newGameButton.setCursor(Cursor.HAND);
        Stone.setTurnCircle(turnCircle);
        Stone.setNewGameButton(newGameButton);
        Stone.setPassButton(passButton);
        board = new Board(pane);
        newGameButton.setOnAction(actionEvent -> {
            passButton.setVisible(true);
            board.newGame();
        });
    }
}
