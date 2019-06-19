package sample;

import javafx.scene.paint.Color;

/**
 * Describing whose turn it is and what is the color of a current player
 */
public class Player
{
    /**
     * Color of a player whose turn it is at the moment
     */
    private Color color;

    /**
     * Constructor setting color to BLACK because black stones starts the game
     */
    public Player()
    {
        this.color = Color.BLACK;
    }

    /**
     * Flipping the color and setting the other players turn
     */
    public void flipColor()
    {
        if(this.color == Color.BLACK)
            this.color = Color.WHITE;
        else
            this.color = Color.BLACK;
    }

    /**
     * Getting the current player color
     * @return Current player color
     */
    public Color getColor()
    {
        //return this.color;
        if(color == Color.BLACK)
            return Color.BLACK;
        else
            return Color.WHITE;
    }

    /**
     * Setting player and his color
     * @param color color of a new current player
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
