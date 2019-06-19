package sample;

/**
 * Position of a stone
 */
public class StonePosition
{
    /**
     * Enum - in what position type stone is
     */
    private Position stonePosition;
    /**
     * x coordiante of stone on grid
     */
    private int x;
    /**
     * y coordinate of stone on grid
     */
    private int y;

    /**
     * Stone position constructor
     * @param x x coordinate
     * @param y y coordinate
     */
    public StonePosition(int x, int y)
    {
        this.x = x;
        this.y = y;

        if(x == 0 && y == 0)
            stonePosition = Position.L_U_CORNER;
        else if(x == 0 && y == Board.MAX_SIZE - 1)
            stonePosition = Position.L_D_CORNER;
        else if(x == (Board.MAX_SIZE - 1) && y == 0)
            stonePosition = Position.R_U_CORNER;
        else if(x == (Board.MAX_SIZE - 1) && y == (Board.MAX_SIZE - 1))
            stonePosition = Position.R_D_CORNER;
        else if(x == 0)
            stonePosition = Position.LEFT_WALL;
        else if(x == Board.MAX_SIZE - 1)
            stonePosition = Position.RIGHT_WALL;
        else if(y == 0)
            stonePosition = Position.UP_WALL;
        else if(y == Board.MAX_SIZE - 1)
            stonePosition = Position.DOWN_WALL;
        else
            stonePosition = Position.CENTER;
    }

    public String toString()
    {
        return stonePosition.toString();
    }

    /**
     * Gets position type of a stone
     * @return Enum stone position
     */
    public Position getStonePosition() {
        return stonePosition;
    }

    /**
     * Gets x cooridnate on a grid
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y cooridnate on a grid
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the information if stone is placed on the top of a board
     * @return True if stone is placed on the top of a board
     */
    public boolean isOnTopWall()
    {
        if(stonePosition == Position.UP_WALL || stonePosition == Position.L_U_CORNER || stonePosition == Position.R_U_CORNER)
            return true;
        else
            return false;
    }

    /**
     * Gets the information if stone is placed on the bottom of a board
     * @return True if stone is placed on the bottom of a board
     */
    public boolean isOnBottomWall()
    {
        if(stonePosition == Position.DOWN_WALL|| stonePosition == Position.L_D_CORNER || stonePosition == Position.R_D_CORNER)
            return true;
        else
            return false;
    }

    /**
     * Gets the information if stone is placed on the left of a board
     * @return True if stone is placed on the left of a board
     */
    public boolean isOnLeftWall()
    {
        if(stonePosition == Position.LEFT_WALL || stonePosition == Position.L_U_CORNER || stonePosition == Position.L_D_CORNER)
            return true;
        else
            return false;
    }

    /**
     * Gets the information if stone is placed on the right of a board
     * @return True if stone is placed on the right of a board
     */
    public boolean isOnRightWall()
    {
        if(stonePosition == Position.RIGHT_WALL || stonePosition == Position.R_U_CORNER || stonePosition == Position.R_D_CORNER)
            return true;
        else
            return false;
    }
}
