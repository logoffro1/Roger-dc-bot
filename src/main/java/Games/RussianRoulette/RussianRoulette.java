package Games.RussianRoulette;

import DAO.Database;
import Model.Player.Player;
import Model.Revolver;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RussianRoulette
{
    private List<Player> players = new ArrayList<>();
    private final Revolver revolver = new Revolver();
    private Player currentTurn = null;
    private boolean gameStarted = false;
    private int currentPlayerIndex = 0;
    private final int entryFee;
    private String message = "";
    private int x;

    public boolean hasGameStarted()
    {
        return gameStarted;
    }

    private int moneyPot = 0;
    private TextChannel channel;
    private final String[] deathText =
            {"DIE, TRASH! :gun:",
                    "blew his brains out :brain::gun:",
                    "ate some lead, he's out :frowning::gun:",
                    "was shot :frowning::gun:",
                    "took the room temperature challenge :skull::gun:",
                    "died with a smile :upside_down::gun:",
                    "is fucking gone :poo::gun:"};

    private boolean canRegister = true;


    public RussianRoulette(int entryFee, int x)
    {
        this.x = x;
        this.entryFee = entryFee;
    }


    public void shoot(String player)
    {
        if (gameStarted)
        {

            if (currentTurn.getName().equals(player))
            {
                EmbedBuilder ebDeath = null;
                int rnd = getRandomNumber(0, 100); //random chance for other event
                if(currentTurn.getInventory().getWands() > 0)
                {
                    if (rnd >= 45)
                    {
                        if (revolver.shoot())
                        {
                            death();
                            if (players.size() == 1)
                                ebDeath = gameOver();
                            else
                            {
                                message += String.format("%s is your turn now %s\n", currentTurn.getName(), revolver.getChambersText());
                            }

                        } else
                        {
                            currentTurn.addSurvivedShot();
                            message += String.format("%s gets to fight another day!\n", currentTurn.getName());
                            changeCurrentPlayer();
                        }
                    } else
                        randomEvent();
                } else
                if (rnd >= 10)
                {
                    if (revolver.shoot())
                    {
                        death();
                        if (players.size() == 1)
                            ebDeath = gameOver();
                        else
                        {
                            message += String.format("%s is your turn now %s\n", currentTurn.getName(), revolver.getChambersText());
                        }

                    } else
                    {
                        currentTurn.addSurvivedShot();
                        message += String.format("%s gets to fight another day!\n", currentTurn.getName());
                        changeCurrentPlayer();
                    }
                } else
                    randomEvent();
                if (!message.equals(""))
                    channel.sendMessage(message).queue();

                if (ebDeath != null)
                    channel.sendMessageEmbeds(ebDeath.build()).queue();

                message = "";
            }

        }
    }

    public void mix(MessageReceivedEvent e)
    {
        if (gameStarted)
        {
            if (e.getAuthor().getAsMention().equalsIgnoreCase(currentTurn.getName()))
            {
                if (currentTurn.getTimesMixed() < 1)
                {
                    revolver.initChambers();
                    currentTurn.addTimesMixed();
                    currentTurn.getRussianStats().addChambersMixed();
                    channel.sendMessage(String.format("%s the chambers have been mixed %s", currentTurn.getName(), revolver.getChambersText())).queue();
                } else
                    channel.sendMessage(String.format("%s you can't mix anymore :stop_sign:", currentTurn.getName())).queue();
            }

        }
    }

    private void randomEvent()
    {
        int rnd = getRandomNumber(1, 100);
if(currentTurn.getInventory().getWands() > 0){
    if (rnd <= 80)
        giveGift();
    else
        revolverMisfire();

} else
        if (rnd <= 30)
            giveGift();
        else
            revolverMisfire();

        changeCurrentPlayer();
    }

    private void giveGift()
    {
        message += String.format("%s found a GIFT down the barrel of the gun :gift:\n", currentTurn.getName());
        currentTurn.getInventory().addGift();
    }

    private void revolverMisfire()
    {
        currentTurn.getRussianStats().addWeaponMalfunction();
        if (revolver.getCurrentChamber() == 5)
        {
            int rnd = getRandomNumber(0, 100);
            int amount = 0;
            if (rnd >= 50)
                amount += 70;
            else if (rnd >= 20)
                amount += 90;
            else if (rnd >= 5)
                amount += 120;
            else
                amount += 300;

            message += String.format("%s stroke of LUCK!:partying_face: The weapon malfunctioned on the last chamber!\n%s Gets a bonus of $%d from the impressed host :moneybag:\n", currentTurn.getName(), currentTurn.getName(), amount);
            currentTurn.setMoney(currentTurn.getMoney() + amount);
        } else
        {
            message += String.format("%s gets lucky! The weapon malfunctioned!\n", currentTurn.getName());
        }

    }

    private void changeCurrentPlayer()
    {
        currentPlayerIndex++;
        if (currentPlayerIndex > players.size() - 1)
            currentPlayerIndex = 0;

        currentTurn = players.get(currentPlayerIndex);
        message += String.format("%s is your turn now %s\n", currentTurn.getName(), revolver.getChambersText());
        if (currentTurn.getName().equalsIgnoreCase("<@774159565507919873>"))
            shoot("<@774159565507919873>");

    }

    public int getEntryFee()
    {
        return entryFee;
    }

    private EmbedBuilder gameOver()
    {
        for (Player p : players){
            if(p.getInventory().getWands() <= 0)
            {
                p.getInventory().setWands(0);
                break;
            }
            System.out.println(p.getInventory().getWands());
            p.getInventory().setWands(p.getInventory().getWands()-1);
        }


        currentTurn.getRussianStats().addGameWon();
        moneyPot += 5;
        currentTurn.setMoney(currentTurn.getMoney() + moneyPot);
        currentTurn.getRussianStats().setTotalMoneyWon(moneyPot - entryFee);
        currentTurn.getRussianStats().setPlayerRank();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Game Over");
        eb.setThumbnail(currentTurn.getAvatarURL());
        eb.setColor(Color.GREEN);
        eb.addField("Winner", String.format("The winner is %s :partying_face: :partying_face:\nYou win: $%d\nSurvived shots: %d", currentTurn.getName(), moneyPot, currentTurn.getSurvivedShots()), true);
        eb.addField("Congratulations!", "You are one step closer to becoming a true russian!", false);

        eb.setFooter("-----------------------\nCosmin Ilie");
        RussianRouletteEvents.setRussianRoulette(null);
        Database.savePlayersToFile();
        return eb;
    }

    private void death()
    {
        currentTurn.getRussianStats().addGameLost();
        currentTurn.getRussianStats().setTotalMoneyLost(entryFee);
        currentTurn.getRussianStats().setPlayerRank();
        message += String.format("%s %s\n", currentTurn.getName(), deathText[getRandomNumber(0, deathText.length - 2)]);
        players.remove(currentTurn);

        currentPlayerIndex++;
        if (currentPlayerIndex > players.size() - 1)
            currentPlayerIndex = 0;
        currentTurn = players.get(currentPlayerIndex);

    }

    public void play(MessageReceivedEvent e)
    {
        channel = e.getChannel().asTextChannel();

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

    public void addPlayer(Player player, TextChannel channel)
    {
        this.channel =channel;
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

    private int getRandomNumber(int min, int max)
    {
        return new Random().nextInt((max + 1) - min) + min;
    }
}
