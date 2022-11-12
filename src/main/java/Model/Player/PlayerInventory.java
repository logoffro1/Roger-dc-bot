package Model.Player;

import java.io.Serializable;

public class PlayerInventory implements Serializable
{
    private int coffee = 0;
    private int cigarettes = 0;
    private int beers = 0;
    private int bananas = 0;
    private int toiletPaper = 0;
    private int gifts = 0;
    private int wands = 0;

    public int getWands() {
        return wands;
    }
    public void setWands(int wands){this.wands=wands;}

    public int getGifts()
    {
        return gifts;
    }

    public void setGifts(int gifts)
    {
        this.gifts = gifts;
    }

    public void setCoffee(int coffee)
    {
        this.coffee = coffee;
    }

    public void setCigarettes(int cigarettes)
    {
        this.cigarettes = cigarettes;
    }

    public void setBeers(int beers)
    {
        this.beers = beers;
    }

    public void setBananas(int bananas)
    {
        this.bananas = bananas;
    }

    public void setToiletPaper(int toiletPaper)
    {
        this.toiletPaper = toiletPaper;
    }

    public void addGift()
    {
        this.gifts++;
    }

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
