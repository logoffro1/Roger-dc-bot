package Model;

import java.io.Serializable;

public class Player implements Serializable
{
    private static final long serialVersionUID = 1L;
    private transient int survivedShots;
    private transient int timesMixed;
    private String name;
    private String avatarURL;
    private int money;
    private final RussianRouletteStats russianStats;
    private final HighLowStats highLowStats;


    public Player(String name, String avatarURL)
    {
        russianStats = new RussianRouletteStats();
        highLowStats = new HighLowStats();
        initStats();

        money = 100;
        this.name = name;
        this.avatarURL = avatarURL;
    }

    public void initStats()
    {
        this.survivedShots = 0;
        this.timesMixed = 0;
    }

    public RussianRouletteStats getRussianStats()
    {
        return russianStats;
    }

    public HighLowStats getHighLowStats()
    {
        return highLowStats;
    }

    public int getMoney()
    {
        return money;
    }

    public void setMoney(int money)
    {
        this.money = money;
    }

    public int getTimesMixed()
    {
        return timesMixed;
    }

    public void addTimesMixed()
    {
        this.timesMixed++;
    }

    public String getName()
    {
        return name;
    }

    public String getAvatarURL()
    {
        return avatarURL;
    }

    public int getSurvivedShots()
    {
        return survivedShots;
    }

    public void addSurvivedShot()
    {
        this.survivedShots++;
        russianStats.addSurvivedShots();
    }
}
