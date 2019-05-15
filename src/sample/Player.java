package sample;

import javafx.scene.paint.Color;

public class Player
{
    private Color color;

    public Player()
    {
        this.color = Color.BLACK;
    }

    public void flipColor()
    {
        if(this.color == Color.BLACK)
            this.color = Color.WHITE;
        else
            this.color = Color.BLACK;
    }

    public Color getColor()
    {
        return this.color;
    }

}
