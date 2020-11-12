package Model.Player;

import java.io.Serializable;

public class PlayerStats implements Serializable
{
    private String name;
    private String avatarURL;
    private int money;
    private int totalSurvivedShots = 0;
    private int totalGamesWon = 0;
    private int totalGamesLost = 0;
    private int winPercentage = 0;
    private int weaponMalfunctions = 0;
    private int chambersMixed = 0;
    private int totalMoneyWon = 0;
    private int totalMoneyLost = 0;
    private PlayerRank playerRank;

    public int getWinPercentage()
    {
        return winPercentage;
    }

    public void setWinPercentage()
    {
        this.winPercentage = (int) ((Double.valueOf(totalGamesWon) / Double.valueOf(getTotalGames())) * 100);
    }

    public int getTotalGames()
    {
        return totalGamesWon + totalGamesLost;
    }

    protected void addGameWon()
    {
        totalGamesWon++;
    }

    protected void addGameLost()
    {
        totalGamesLost++;
    }

    protected void addWeaponMalfunction()
    {
        weaponMalfunctions++;
    }

    protected void addChambersMixed()
    {
        chambersMixed++;
    }

    protected void setTotalMoneyWon(int money)
    {
        totalMoneyWon += money;
    }

    protected void setTotalMoneyLost(int money)
    {
        totalMoneyLost += money;
    }

    protected void addSurvivedShots()
    {
        totalSurvivedShots++;
    }

    protected String getName()
    {
        return name;
    }

    protected String getAvatarURL()
    {
        return avatarURL;
    }

    protected int getMoney()
    {
        return money;
    }

    protected int getTotalSurvivedShots()
    {
        return totalSurvivedShots;
    }

    protected int getTotalGamesWon()
    {
        return totalGamesWon;
    }

    protected int getTotalGamesLost()
    {
        return totalGamesLost;
    }

    protected int getWeaponMalfunctions()
    {
        return weaponMalfunctions;
    }

    protected int getChambersMixed()
    {
        return chambersMixed;
    }

    protected int getTotalMoneyWon()
    {
        return totalMoneyWon;
    }

    protected int getTotalMoneyLost()
    {
        return totalMoneyLost;
    }

    protected PlayerRank getPlayerRank()
    {
        setPlayerRank();
        return playerRank;
    }

    protected void setName(String name)
    {
        this.name = name;
    }

    protected void setAvatarURL(String avatarURL)
    {
        this.avatarURL = avatarURL;
    }

    protected void setMoney(int money)
    {
        this.money = money;
    }

    public void setPlayerRank()
    {
        if (totalGamesWon < 10)
            playerRank = PlayerRank.Novice;
        else if (totalGamesWon >= 10 && totalGamesWon < 30)
            playerRank = PlayerRank.Outlaw;
        else if (totalGamesWon >= 30 && totalGamesWon < 100)
            playerRank = PlayerRank.Gunslinger;
        else if (totalGamesWon >= 100)
            playerRank = PlayerRank.Legend;
    }
}
