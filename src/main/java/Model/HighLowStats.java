package Model;

import java.io.Serializable;

public class HighLowStats implements Serializable
{
    private int bestStreak;
    private int moneyWon;
    private int moneyLost;
    private int correctGuesses;
    private int wrongGuesses;

    public int getBestStreak()
    {
        return bestStreak;
    }

    public void setBestStreak(int bestStreak)
    {
        this.bestStreak = bestStreak;
    }

    public int getMoneyWon()
    {
        return moneyWon;
    }

    public void setMoneyWon(int moneyWon)
    {
        this.moneyWon = moneyWon;
    }

    public int getMoneyLost()
    {
        return moneyLost;
    }

    public void setMoneyLost(int moneyLost)
    {
        this.moneyLost = moneyLost;
    }

    public int getCorrectGuesses()
    {
        return correctGuesses;
    }

    public void setCorrectGuesses(int correctGuesses)
    {
        this.correctGuesses = correctGuesses;
    }

    public int getWrongGuesses()
    {
        return wrongGuesses;
    }

    public void setWrongGuesses(int wrongGuesses)
    {
        this.wrongGuesses = wrongGuesses;
    }
}
