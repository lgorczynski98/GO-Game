package sample;

// TODO: 14.06.2019 javadoc comments in StonePosition

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Simple Controller Class generated from javafx
 */
public class Controller implements Initializable
{
    /**
     * Game pane
     */
    @FXML private Pane pane;
    /**
     * Grid for 9x9 game
     */
    @FXML private GridPane gridPane9;
    /**
     * Grid for 13x13 game
     */
    @FXML private GridPane gridPane13;
    /**
     * Hint cirlce showing whose turn it is
     */
    @FXML private Circle turnCircle;
    /**
     * Button to pass turn
     */
    @FXML private Button passButton;
    /**
     * Button to start new game
     */
    @FXML private Button newGameButton;
    /**
     * Check box to mute the music
     */
    @FXML private CheckBox muteCheckBox;

    /**
     * Main board of a game
     */
    private Board board;
    /**
     * Media player playing game music
     */
    private MediaPlayer mediaPlayer;

    /**
     * Preparing board and media player
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Stone.setTurnCircle(turnCircle);
        Stone.setNewGameButton(newGameButton);
        Stone.setPassButton(passButton);

        sizeSelection();

        newGameButton.setOnAction(actionEvent -> {
            passButton.setVisible(true);
            board.newGame();
            sizeSelection();
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

    /**
     *Changing board size
     */
    private void sizeSelection()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Board Size");
        alert.setHeaderText("Select size of GO board");
        alert.setContentText("Choose your option");

        ButtonType buttonType9 = new ButtonType("9x9");
        ButtonType buttonType13 = new ButtonType("13x13");
        alert.getButtonTypes().setAll(buttonType9, buttonType13);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonType9)
        {
            gridPane13.setVisible(false);
            gridPane9.setVisible(true);
            board = new Board(pane, 9);
        }
        else if(result.get() == buttonType13)
        {
            gridPane9.setVisible(false);
            gridPane13.setVisible(true);
            board = new Board(pane, 13);
        }
    }
}
