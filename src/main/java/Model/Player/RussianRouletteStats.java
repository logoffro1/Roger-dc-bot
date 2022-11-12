package Model.Player;

import java.io.Serializable;

public class RussianRouletteStats implements Serializable
{
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

    public void addGameWon()
    {
        totalGamesWon++;
        setWinPercentage();
    }

    public void addGameLost()
    {
        totalGamesLost++;
        setWinPercentage();
    }

    public void addWeaponMalfunction()
    {
        weaponMalfunctions++;
    }

    public void addChambersMixed()
    {
        chambersMixed++;
    }

    public void setTotalMoneyWon(int money)
    {
        totalMoneyWon += money;
    }

    public void setTotalMoneyLost(int money)
    {
        totalMoneyLost += money;
    }

    public void addSurvivedShots()
    {
        totalSurvivedShots++;
    }


    public int getTotalSurvivedShots()
    {
        return totalSurvivedShots;
    }

    public int getTotalGamesWon()
    {
        return totalGamesWon;
    }

    public int getTotalGamesLost()
    {
        return totalGamesLost;
    }

    public int getWeaponMalfunctions()
    {
        return weaponMalfunctions;
    }

    public int getChambersMixed()
    {
        return chambersMixed;
    }

    public int getTotalMoneyWon()
    {
        return totalMoneyWon;
    }

    public int getTotalMoneyLost()
    {
        return totalMoneyLost;
    }

    public PlayerRank getPlayerRank()
    {
        setPlayerRank();
        return playerRank;
    }

    public void setPlayerRank()
    {
        if (totalGamesWon < 10)
            playerRank = PlayerRank.Novice;
        else if (totalGamesWon >= 10 && totalGamesWon < 30)
            playerRank = PlayerRank.Outlaw;
        else if (totalGamesWon >= 30 && totalGamesWon < 100)
            playerRank = PlayerRank.Gunslinger;
        else if (totalGamesWon >= 100 && totalGamesWon<300)
            playerRank = PlayerRank.Executor;
        else if(totalGamesWon >=300)
            playerRank = playerRank.Legend;
    }
}
