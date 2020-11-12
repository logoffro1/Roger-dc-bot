package Model;

import java.util.Random;

public class Revolver
{
    private int chambersNr = 6;
    private int currentChamber = 0;
    private int[] chambers = new int[chambersNr];
    private int bulletChamber;

    public Revolver()
    {
        initChambers();
    }

    public int getBulletChamber()
    {
        return bulletChamber;
    }

    public void initChambers()
    {
        currentChamber = 0;
        bulletChamber = getRandomNumber(0, chambersNr - 1);
        for (int i = 0; i < chambersNr; i++)
        {
            if (i == bulletChamber)
                chambers[i] = 1;
            else
                chambers[i] = 0;
        }
    }

    public String getChambersText()
    {
        String chambers = "\n ";
        for (int i = 0; i < chambersNr; i++)
        {
            if (i == currentChamber)
                chambers += String.format(":red_circle: ", i + 1);
            else
                chambers += ":white_circle: ";
        }
        return chambers;
    }

    private int getRandomNumber(int min, int max)
    {
        return new Random().nextInt((max + 1) - min) + min;
    }

    public int getCurrentChamber()
    {
        return currentChamber;
    }

    public boolean shoot()
    {
        if (chambers[currentChamber] == 1)
        {
            initChambers();
            return true;
        } else
        {
            currentChamber++;
            return false;
        }
    }
}
