package sample;

public class StonePosition
{
    private Position stonePosition;
    private int x;
    private int y;

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

    public Position getStonePosition() {
        return stonePosition;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOnTopWall()
    {
        if(stonePosition == Position.UP_WALL || stonePosition == Position.L_U_CORNER || stonePosition == Position.R_U_CORNER)
            return true;
        else
            return false;
    }

    public boolean isOnBottomWall()
    {
        if(stonePosition == Position.DOWN_WALL|| stonePosition == Position.L_D_CORNER || stonePosition == Position.R_D_CORNER)
            return true;
        else
            return false;
    }

    public boolean isOnLeftWall()
    {
        if(stonePosition == Position.LEFT_WALL || stonePosition == Position.L_U_CORNER || stonePosition == Position.L_D_CORNER)
            return true;
        else
            return false;
    }

    public boolean isOnRightWall()
    {
        if(stonePosition == Position.RIGHT_WALL || stonePosition == Position.R_U_CORNER || stonePosition == Position.R_D_CORNER)
            return true;
        else
            return false;
    }
}
