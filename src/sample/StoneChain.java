package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains al the stones in one chain and the number of its liberties
 */
public class StoneChain
{
    /**
     * List of stones in the chain
     */
    private List<Stone> stoneList;
    /**
     * Number of chain liberties
     */
    private int liberties;

    /**
     * Stone chain constructor
     */
    public StoneChain()
    {
        this.stoneList = new ArrayList<>();
    }

    /**
     * Adding stone to the chain after placing it in the right place
     * @param stone Stone that is added
     */
    public void addStone(Stone stone)
    {
        stoneList.add(stone);
    }

    /**
     * Method to get the stones of the stone chain
     * @return List of stones in the chain
     */
    public List<Stone> getStoneList() {
        return stoneList;
    }

    /**
     * Method that destroys the stone chain
     */
    public void destroyChain()
    {
        for (Stone stone : stoneList)
        {
            stone.destroyStone();
        }
    }

    public String toString()
    {
        String ret = "";
        for (Stone stone: stoneList)
        {
            ret = ret + stone.toString() + " ";
        }
        return ret;
    }

    /**
     * Method that counts liberties of this stone chain
     */
    public void countLiberties()
    {
        int l = 0;
        for (Stone stone : stoneList)
        {
            if (!stone.isStoneUP())
                l++;
            if (!stone.isStoneDOWN())
                l++;
            if (!stone.isStoneLEFT())
                l++;
            if (!stone.isStoneRIGHT())
                l++;
        }
        liberties = l;
        if (liberties == 0)
        {
            System.out.println(toString() + " 0 liberties");
            destroyChain();
        }
    }

    /**
     * Get the liberties of the stone chain
     * @return liberties
     */
    public int getLiberties() {
        return liberties;
    }
}
