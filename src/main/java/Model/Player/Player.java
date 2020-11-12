package Model.Player;

import java.io.Serializable;

public class Player implements Serializable
{
    private static final long serialVersionUID = 1L;
    private transient int survivedShots;
    private transient int timesMixed;
    private final PlayerStats playerStats;


    public Player(String name, String avatarURL)
    {
        playerStats = new PlayerStats();
        initStats();

        playerStats.setMoney(100);
        playerStats.setName(name);
        playerStats.setAvatarURL(avatarURL);
    }

    public void initStats()
    {
        this.survivedShots = 0;
        this.timesMixed = 0;
    }

    public int getWinPercentage()
    {
        return playerStats.getWinPercentage();
    }

    public void setPlayerRank()
    {
        playerStats.setPlayerRank();
    }

    public int getGamesWon()
    {
        return playerStats.getTotalGamesWon();
    }

    public void addGameWon()
    {
        playerStats.addGameWon();
        playerStats.setWinPercentage();
    }

    public void addGameLost()
    {
        playerStats.addGameLost();
        playerStats.setWinPercentage();
    }

    public void addWeaponMalfunction()
    {
        playerStats.addWeaponMalfunction();
    }

    public void addChambersMixed()
    {
        playerStats.addChambersMixed();
    }

    public void setTotalMoneyWon(int money)
    {
        playerStats.setTotalMoneyWon(money);
    }

    public void setTotalMoneyLost(int money)
    {
        playerStats.setTotalMoneyLost(money);
    }

    public int getGamesLost()
    {
        return playerStats.getTotalGamesLost();
    }

    public int getMoneyWon()
    {
        return playerStats.getTotalMoneyWon();
    }

    public int getMoneyLost()
    {
        return playerStats.getTotalMoneyLost();
    }

    public int getWeaponMalfunctions()
    {
        return playerStats.getWeaponMalfunctions();
    }

    public int getChambersMixed()
    {
        return playerStats.getChambersMixed();
    }

    public String getRank()
    {
        return playerStats.getPlayerRank().toString();
    }

    public int getTotalGames()
    {
        return playerStats.getTotalGames();
    }

    public int getMoney()
    {
        return playerStats.getMoney();
    }

    public void setMoney(int money)
    {
        playerStats.setMoney(money);
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
        return playerStats.getName();
    }

    public String getAvatarURL()
    {
        return playerStats.getAvatarURL();
    }

    public int getSurvivedShots()
    {
        return survivedShots;
    }

    public void addSurvivedShot()
    {
        this.survivedShots++;
        playerStats.addSurvivedShots();
    }
}
