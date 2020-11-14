package Games.HighLow;

import DAO.Database;
import Model.Player;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Random;

public class HighLow
{
    private Player player;
    private final int min = 1;
    private final int max = 100;
    private int currentNumber = 1;
    private int entryFee = 0;
    private int multiplier = 20;
    private int moneyPot = 0;
    private int streak = 0;
    private TextChannel channel;

    public HighLow(Player player, int entryFee, TextChannel channel)
    {
        this.player = player;
        this.entryFee = entryFee;
        this.channel = channel;
        player.setMoney(player.getMoney() - entryFee);
        player.getHighLowStats().addTotalGamesPlayed();
    }

    public Player getPlayer()
    {
        return player;
    }

    public void play()
    {
        channel.sendMessage(String.format("%s the current number is **%d**\nChoose !high or !low if you think the next number will be higher or lower", player.getName(), currentNumber)).queue();
        channel.sendMessage("Choose !out if you want to cash out now.").queue();
    }

    public void leaveGame()
    {
        player.getHighLowStats().setMoneyWon(player.getHighLowStats().getMoneyWon() + moneyPot);
        channel.sendMessage(String.format("%s you decided to leave the game.\nYou leave with $%d", player.getName(), moneyPot)).queue();
        player.setMoney(player.getMoney() + moneyPot);
        Database.savePlayersToFile();
        HighLowEvents.removeGame(this);
    }

    public void chooseHigh()
    {
        int nextNumber = getRandomNumber(min, max);

        if (nextNumber > currentNumber)
            goodGuess(nextNumber);
        else
            lose(nextNumber);
    }

    public void chooseLow()
    {
        int nextNumber = getRandomNumber(min, max);

        if (nextNumber < currentNumber)
            goodGuess(nextNumber);
        else
            lose(nextNumber);
    }

    private void lose(int nextNumber)
    {
        player.getHighLowStats().addWrongGuess();
        player.getHighLowStats().setMoneyLost(player.getHighLowStats().getMoneyLost() + entryFee);
        channel.sendMessage(String.format("%s you guessed wrong! The number was **%d**", player.getName(), nextNumber)).queue();
        channel.sendMessage(String.format("%s you leave with nothing.", player.getName())).queue();
        channel.sendMessage("The game has ended.").queue();
        Database.savePlayersToFile();
        HighLowEvents.removeGame(this);
    }

    private void goodGuess(int nextNumber)
    {
        player.getHighLowStats().addCorrectGuess();
        channel.sendMessage(String.format("%s you guessed right!", player.getName())).queue();
        streak++;
        if (streak > player.getHighLowStats().getBestStreak())
            player.getHighLowStats().setBestStreak(streak);

        if (streak % 5 == 0)
        {
            channel.sendMessage(String.format("%s you are on a streak of %d correct guess! Money pot doubled.", player.getName(), streak)).queue();
            moneyPot *= 2;
        } else
            moneyPot += ((Double.valueOf(multiplier) / 100.0) * Double.valueOf(entryFee));

        currentNumber = nextNumber;
        channel.sendMessage(String.format("%s the current number is **%d**\nChoose !high or !low if you think the next number will be higher or lower" +
                "\nChoose !out if you want to cash out now." +
                "\nIf you cash out now, you get $%d", player.getName(), currentNumber, moneyPot)).queue();

    }

    private int getRandomNumber(int min, int max)
    {
        return new Random().nextInt((max + 1) - min) + min;
    }
}
