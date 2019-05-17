package sample;

import java.util.ArrayList;
import java.util.List;

public class StoneChain
{
    private List<Stone> stoneList;
    private int liberties;

    public StoneChain()
    {
        this.stoneList = new ArrayList<>();
    }

    public void addStone(Stone stone)
    {
        stoneList.add(stone);
    }

    public List<Stone> getStoneList() {
        return stoneList;
    }

    public StoneChain copy()
    {
        StoneChain copy = new StoneChain();
        for (Stone stone : this.getStoneList())
        {
            copy.addStone(new Stone(stone.getPane(), stone.getStonePosition().getX(), stone.getStonePosition().getY()));
        }
        return copy;
    }

    /*public void mergeStoneChains(StoneChain s)
    {
        stoneList.addAll(s.copy().getStoneList());
        s.destroyChain();
    }*/

    public void destroyChain()
    {
        this.stoneList = null;
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

    public void addAll(StoneChain chain)
    {
        this.stoneList.addAll(chain.getStoneList());
    }

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
        if (liberties == 0)// TODO: 18.05.2019 brakuje juz tylko usuwania kamieni gdy 0 oddechow 
            System.out.println(toString() + " 0 liberties");
    }

    public int getLiberties() {
        return liberties;
    }
}
