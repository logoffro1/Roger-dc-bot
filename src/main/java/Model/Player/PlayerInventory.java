package Model.Player;

import java.io.Serializable;

public class PlayerInventory implements Serializable
{
    private int coffee = 0;
    private int cigarettes = 0;
    private int beers = 0;
    private int bananas = 0;
    private int toiletPaper = 0;

    public int getCoffee()
    {
        return coffee;
    }

    public void addCoffee()
    {
        this.coffee++;
    }

    public int getCigarettes()
    {
        return cigarettes;
    }

    public void addCigarette()
    {
        this.cigarettes++;
    }

    public int getBeers()
    {
        return beers;
    }

    public void addBeer()
    {
        this.beers++;
    }

    public int getBananas()
    {
        return bananas;
    }

    public void addBanana()
    {
        this.bananas++;
    }

    public int getToiletPaper()
    {
        return toiletPaper;
    }

    public void addToiletPaper()
    {
        this.toiletPaper++;
    }
}
