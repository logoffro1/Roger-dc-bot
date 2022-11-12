package Model.HorseRacing;

import Model.Player.HighLowStats;
import Model.Player.PlayerInventory;
import Model.Player.RussianRouletteStats;

import java.awt.*;
import java.io.Serializable;

public class Horse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private String color;
    private boolean isMale;
private double speed;
private double weight;
private double jumpHeight;

    public Horse(String name, int age, String color, boolean isMale, double speed, double weight, double jumpHeight)
    {
        this.name = name;
        this.age = age;
        this.color = color;
        this.isMale = isMale;
        this.speed = speed;
        this.weight = weight;
        this.jumpHeight = jumpHeight;
    }
    public String getName() {return this.name;}
    public int getAge() {return this.age;}
    public String getColor(){return this.color;}
    public boolean getGender(){return this.isMale;}
    public double getSpeed() {return this.speed;}
    public double getWeight(){return this.weight;}
    public double getJumpHeight(){return this.jumpHeight;}
}
