package Games;

import DAO.Database;
import Model.Player;
import Model.Revolver;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RussianRoulette
{
    private List<Player> players = new ArrayList<>();
    private Revolver revolver = new Revolver();
    private Player currentTurn = null;
    private boolean gameStarted = false;
    private int currentPlayerIndex = 0;
    private int entryFee = 0;

    public boolean hasGameStarted()
    {
        return gameStarted;
    }

    private int moneyPot = 0;
    private TextChannel channel;

    private String[] deathText = {"blew his brains out :brain::gun:", "ate some lead, he's out :frowning::gun:", "was shot :frowning::gun:", "took the room temperature challenge :skull::gun:", "died with a smile :upside_down::gun:", "is fucking gone :poo::gun:"};

    private boolean canRegister = true;


    public RussianRoulette(int entryFee)
    {
        this.entryFee = entryFee;
    }

    public void shoot(String player)
    {
        if (gameStarted)
        {

            if (currentTurn.getName().equals(player))
            {
                int rnd = getRandomNumber(0, 100); //random chance for other event
                if (rnd >= 10)
                {
                    if (revolver.shoot())
                    {
                        death();
                        if (players.size() == 1)
                            gameOver();
                        else
                        {
                             channel.sendMessage(String.format("%s is your turn now %s", currentTurn.getName(), revolver.getChambersText())).queue();
                        }

                    } else
                    {
                        currentTurn.addSurvivedShot();
                        channel.sendMessage(String.format("%s gets to fight another day!", currentTurn.getName())).queue();

                        changeCurrentPlayer();
                    }
                } else
                    randomEvent();
            }

        }
    }

    public void mix(GuildMessageReceivedEvent e)
    {
        if (gameStarted)
        {
            if (e.getAuthor().getAsMention().equalsIgnoreCase(currentTurn.getName()))
            {
                if (currentTurn.getTimesMixed() < 2)
                {
                    revolver.initChambers();
                    currentTurn.addTimesMixed();
                    currentTurn.addChambersMixed();
                    channel.sendMessage(String.format("%s the chambers have been mixed %s", currentTurn.getName(), revolver.getChambersText())).queue();
                } else
                    channel.sendMessage(String.format("%s you can't mix anymore :stop_sign:", currentTurn.getName())).queue();
            }

        }
    }

    private void randomEvent()
    {
        int rnd = 1;
        switch (rnd)
        {
            case 1 -> revolverMisfire();
        }
    }

    private void revolverMisfire()
    {
        currentTurn.addWeaponMalfunction();
        if (revolver.getCurrentChamber() == 5)
        {
            int rnd = getRandomNumber(0, 100);
            int amount = 0;
            if (rnd >= 50)
            {
                amount += 70;
            } else if (rnd < 50 && rnd >= 20)
                amount += 90;
            else if (rnd < 20 && amount >= 5)
                amount += 120;
            else
                amount += 300;

            channel.sendMessage(String.format("%s stroke of LUCK!:partying_face: The weapon malfunctioned on the last chamber!\n%s Gets a bonus of $%d from the impressed host :moneybag:", currentTurn.getName(), currentTurn.getName(), amount)).queue();
            currentTurn.setMoney(currentTurn.getMoney() + amount);
        } else
        {
                channel.sendMessage(String.format("%s gets lucky! The weapon malfunctioned!", currentTurn.getName())).queue();
        }


        changeCurrentPlayer();
    }

    private void changeCurrentPlayer()
    {
        currentPlayerIndex++;
        if (currentPlayerIndex > players.size() - 1)
            currentPlayerIndex = 0;

        currentTurn = players.get(currentPlayerIndex);
        channel.sendMessage(String.format("%s is your turn now %s", currentTurn.getName(), revolver.getChambersText())).queue();

        if (currentTurn.getName().equalsIgnoreCase("<@774159565507919873>"))
            shoot("<@774159565507919873>");

    }

    private void gameOver()
    {
        currentTurn.addGameWon();
        currentTurn.setMoney(currentTurn.getMoney() + moneyPot);
        currentTurn.setTotalMoneyWon(moneyPot - entryFee);
        currentTurn.setPlayerRank();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Game Over");
        eb.setThumbnail(currentTurn.getAvatarURL());
        eb.setColor(Color.GREEN);
        eb.addField("Winner", String.format("The winner is %s :partying_face: :partying_face:\nYou win: $%d\nSurvived shots: %d", currentTurn.getName(), moneyPot, currentTurn.getSurvivedShots()), true);
        eb.addField("Congratulations!", "You are one step closer to becoming a true russian!", false);
        eb.setFooter("Made by Cosmin Ilie");
        channel.sendMessage(eb.build()).queue();
        RussianRouletteEvents.setRussianRoulette(null);
        Database.savePlayersToFile();
    }

    private void death()
    {
        currentTurn.addGameLost();
        currentTurn.setTotalMoneyLost(entryFee);
        currentTurn.setPlayerRank();
        channel.sendMessage(String.format("%s %s", currentTurn.getName(), deathText[getRandomNumber(0, deathText.length - 2)])).queue();
        players.remove(currentTurn);

        currentPlayerIndex++;
        if (currentPlayerIndex > players.size() - 1)
            currentPlayerIndex = 0;
        currentTurn = players.get(currentPlayerIndex);

    }

    public void play(GuildMessageReceivedEvent e)
    {
        channel = e.getChannel();

        gameStarted = true;
        canRegister = false;
        scramblePlayerList();
        currentTurn = players.get(currentPlayerIndex);
        channel.sendMessage(String.format("%s is your turn now %s", currentTurn.getName(), revolver.getChambersText())).queue();

        if (currentTurn.getName().equalsIgnoreCase("<@774159565507919873>"))
            shoot("<@774159565507919873>");
    }

    private void scramblePlayerList()
    {
        int nrPlayers = players.size();
        List<Player> tempPlayers = new ArrayList<>();
        for (int i = 0; i < nrPlayers; i++)
        {
            int rnd = 0;
            if (players.size() > 1)
                rnd = getRandomNumber(0, players.size() - 1);

            tempPlayers.add(players.get(rnd));
            players.remove(players.get(rnd));
        }
        players = tempPlayers;

    }

    public boolean checkIfPlayerExists(String playerName)
    {
        for (Player p : players)
            if (p.getName().equals(playerName))
                return true;

        return false;
    }

    public List<Player> getPlayers()
    {
        return players;
    }

    public void addPlayer(Player player, GuildMessageReceivedEvent e)
    {
        channel = e.getChannel();
        int playerMoney = player.getMoney();
        if (playerMoney < entryFee)
            channel.sendMessage(String.format("%s doesn't have enough money to join. Entry fee: $%d", player.getName(), entryFee)).queue();

        else
        {
            players.add(player);
            player.setMoney(playerMoney - entryFee);
            player.initStats();
            moneyPot += entryFee;
            channel.sendMessage(String.format("%s has been registered. Good luck!", player.getName())).queue();
        }
    }

    public boolean getCanRegister()
    {
        return canRegister;
    }

    public void setCanRegister(boolean canRegister)
    {
        this.canRegister = canRegister;
    }

    private int getRandomNumber(int min, int max)
    {
        return new Random().nextInt((max + 1) - min) + min;
    }
}
