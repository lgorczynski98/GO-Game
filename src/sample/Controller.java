package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{

    @FXML private Pane pane;
    @FXML private GridPane gridPane;
    @FXML private Circle turnCircle;
    @FXML private Button passButton;
    @FXML private Button newGameButton;
    @FXML private CheckBox muteCheckBox;
    private Board board;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Stone.setTurnCircle(turnCircle);
        Stone.setNewGameButton(newGameButton);
        Stone.setPassButton(passButton);

        board = new Board(pane);
        newGameButton.setOnAction(actionEvent -> {
            passButton.setVisible(true);
            board.newGame();
        });

        String path = "src/music.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        muteCheckBox.setOnAction(actionEvent -> {
            if(muteCheckBox.isSelected())
                mediaPlayer.setMute(true);
            else
                mediaPlayer.setMute(false);
        });
    }
}
