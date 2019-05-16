package sample;

import java.util.ArrayList;
import java.util.List;

public class StoneChain
{
    private List<Stone> stoneList;

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

    public void mergeStoneChains(StoneChain s)
    {
        stoneList.addAll(s.copy().getStoneList());
        s.destroyChain();
    }

    public void destroyChain()
    {
        this.stoneList = null;
    }
}
