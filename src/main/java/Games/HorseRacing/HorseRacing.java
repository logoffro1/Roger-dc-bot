package Games.HorseRacing;

import DAO.Database;
import Model.HorseRacing.Horse;
import Model.HorseRacing.PlayerBet;
import Model.Player.Player;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HorseRacing
{
    private List<PlayerBet> playerBets = new ArrayList<>();
    private List<Horse> horses = new ArrayList<>();
    private int nrOfHorses = 4;
    private Player currentTurn = null;
    private boolean gameStarted = false;
    private int currentPlayerIndex = 0;
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


    public HorseRacing()
    {
        initHorses();
/*        this.x = x;
        this.entryFee = entryFee;*/
    }
private void initHorses(){

    Random rnd = new Random();
    for (int i = 0; i<= Database.getAllHorses().size();i++){
        Horse horse = Database.getAllHorses().get(rnd.nextInt(Database.getAllHorses().size()));
        if(horses.contains(horse))
            continue;

        horses.add(Database.getAllHorses().get(rnd.nextInt(Database.getAllHorses().size())));
        if(horses.size()>=nrOfHorses)
            break;
    }
}


    private void giveGift()
    {
        message += String.format("%s found a GIFT down the barrel of the gun :gift:\n", currentTurn.getName());
        currentTurn.getInventory().addGift();
    }


    private EmbedBuilder gameOver()
    {
        for (PlayerBet pb : playerBets){
            if(pb.getPlayer().getInventory().getWands() <= 0)
            {
                pb.getPlayer().getInventory().setWands(0);
                break;
            }
            pb.getPlayer().getInventory().setWands(pb.getPlayer().getInventory().getWands()-1);
        }


        currentTurn.getRussianStats().addGameWon();
        moneyPot += 5;
        currentTurn.setMoney(currentTurn.getMoney() + moneyPot);
      //  currentTurn.getRussianStats().setTotalMoneyWon(moneyPot - entryFee);
        currentTurn.getRussianStats().setPlayerRank();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Game Over");
        eb.setThumbnail(currentTurn.getAvatarURL());
        eb.setColor(Color.GREEN);
        eb.addField("Winner", String.format("The winner is %s :partying_face: :partying_face:\nYou win: $%d\nSurvived shots: %d", currentTurn.getName(), moneyPot, currentTurn.getSurvivedShots()), true);
        eb.addField("Congratulations!", "You are one step closer to becoming a true russian!", false);

        eb.setFooter("-----------------------\nCosmin Ilie");
       // RussianRouletteEvents.setRussianRoulette(null);
        Database.savePlayersToFile();
        return eb;
    }

    private void death()
    {
        currentTurn.getRussianStats().addGameLost();
      //  currentTurn.getRussianStats().setTotalMoneyLost(entryFee);
        currentTurn.getRussianStats().setPlayerRank();
        message += String.format("%s %s\n", currentTurn.getName(), deathText[getRandomNumber(0, deathText.length - 2)]);
        playerBets.remove(currentTurn);

        currentPlayerIndex++;
        if (currentPlayerIndex > playerBets.size() - 1)
            currentPlayerIndex = 0;
        currentTurn = playerBets.get(currentPlayerIndex).getPlayer();

    }

    private void scramblePlayerBetsList()
    {
        int nrPlayers = playerBets.size();
        List<PlayerBet> tempBets = new ArrayList<>();
        for (int i = 0; i < nrPlayers; i++)
        {
            int rnd = 0;
            if (playerBets.size() > 1)
                rnd = getRandomNumber(0, playerBets.size() - 1);

            tempBets.add(playerBets.get(rnd));
            playerBets.remove(playerBets.get(rnd));
        }
        playerBets = tempBets;

    }

    public boolean checkIfPlayerExists(String playerName)
    {
        for (PlayerBet pb : playerBets)
            if (pb.getPlayer().getName().equals(playerName))
                return true;

        return false;
    }

    public List<PlayerBet> getPlayerBets()
    {
        return playerBets;
    }

    public void addPlayer(Player player,Horse horse, double playerBet, GuildMessageReceivedEvent e)
    {
        channel = e.getChannel();
        int playerMoney = player.getMoney();
        if (playerMoney < playerBet)
            channel.sendMessage(String.format("%s you don't have enough money for this bet.", player.getName(), playerBet)).queue();
        else
        {
            playerBets.add(new PlayerBet(player,horse,playerBet));
            player.setMoney(playerMoney - (int)playerBet);
            player.initStats();
         //   moneyPot += entryFee;
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
    public List<Horse> getHorses(){return this.horses;}
}
