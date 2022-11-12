package Model.HorseRacing;

import Model.Player.Player;

public class PlayerBet {
    private Player player;
    private Horse horse;
    private double betAmount;

    public PlayerBet(Player player, Horse horse, double betAmount){
        this.player = player;
        this.horse = horse;
        this.betAmount = betAmount;
    }
    public Player getPlayer(){return this.player;}
    public Horse getHorse(){return this.horse;}
    public double getBetAmount(){return this.betAmount;}
}
