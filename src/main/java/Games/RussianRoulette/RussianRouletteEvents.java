package Games.RussianRoulette;

import DAO.Database;
import Model.ChatLog;
import Model.Player;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;


public class RussianRouletteEvents extends ListenerAdapter
{
    private static RussianRoulette russianRoulette;
    private GuildMessageReceivedEvent e;

    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent e)
    {
        if (russianRoulette != null)
        {

            this.e = e;
            String[] message = e.getMessage().getContentRaw().split(" ");
            if (!e.getMessage().getAuthor().isBot())
            {

                if (message[0].charAt(0) == '!')
                {
                    if (this.e.getChannel().getName().equalsIgnoreCase("bottest"))
                    {

                        if (message.length > 1)
                        {
                            message[1] = message[1].replace("!", "");
                            message[1] = message[1].replace("&", "");
                        }
                        switch (message[0].toLowerCase())
                        {
                            case "!me" -> registerPlayer();
                            case "!stop" -> stopGame();
                            case "!start" -> startGame();
                            case "!shoot" -> shoot();
                            case "!mix" -> russianRoulette.mix(e);
                            case "!give" -> giveMoney(message);
                        }

                    } else
                        e.getChannel().sendMessage(String.format("%s, use the bot channel you slut!", this.e.getMessage().getAuthor().getAsMention())).queue();

                }
            }
        }

    }

    private void shoot()
    {
        Player p = Database.getPlayerByName(e.getAuthor().getAsMention());
        Database.addLog(new ChatLog(p, e.getMessage().getContentRaw(), LocalTime.now()));
        Player cheater = Database.getCheater();

        if (cheater == null)
        {
            russianRoulette.shoot(e.getAuthor().getAsMention());
        } else
        {
            russianRoulette = null;
            System.out.println("Cheater found: " + e.getAuthor().getAsMention());
            e.getChannel().sendMessage(String.format("%s get outta here, you filthy cheater!", e.getAuthor().getAsMention())).queue();
            Database.getLogs().clear();
        }
    }

    private void registerPlayer()
    {
        Player p = Database.getPlayerByName(e.getAuthor().getAsMention());
        Database.addLog(new ChatLog(p, e.getMessage().getContentRaw(), LocalTime.now()));
        if (russianRoulette.getCanRegister())
        {
            if (!russianRoulette.checkIfPlayerExists(e.getAuthor().getAsMention()))
            {

                Player player = Database.getPlayerByName(e.getAuthor().getAsMention());
                if (player != null)
                    russianRoulette.addPlayer(player, e);
                else
                {
                    Player newPlayer = new Player(e.getAuthor().getAsMention(), e.getAuthor().getAvatarUrl());
                    russianRoulette.addPlayer(newPlayer, e);
                    Database.addPlayer(newPlayer);
                }
            } else
            {
                e.getChannel().sendMessage(String.format("%s you are already registered.", e.getAuthor().getAsMention())).queue();
            }

        }
    }

    private void addRogerToPlay()
    {
        Player newPlayer = new Player("<@774159565507919873>", "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/items/553790/b408661ef1867375b47972783f223336302460a4.jpg");
        russianRoulette.addPlayer(newPlayer, e);
        Database.addPlayer(newPlayer);
    }

    private void giveMoney(String[] message)
    {
        if (message.length == 3)
        {
            Player moneyGiver = Database.getPlayerByName(e.getAuthor().getAsMention());
            Player moneyTaker = Database.getPlayerByName(message[1]);
            if (moneyGiver != null && moneyTaker != null)
            {
                if (isNumber(message[2]))
                {

                    int amount = Integer.parseInt(message[2]);
                    if (amount <= moneyGiver.getMoney())
                    {
                        moneyGiver.setMoney(moneyGiver.getMoney() - amount);
                        moneyTaker.setMoney(moneyTaker.getMoney() + amount);
                        Database.savePlayersToFile();
                        e.getChannel().sendMessage(String.format("%s just transferred $%d to %s", moneyGiver.getName(), amount, moneyTaker.getName())).queue();
                    }
                }
            }
        }

    }

    private boolean isNumber(String text)
    {
        try
        {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }

    private void stopGame()
    {
        russianRoulette = null;
        e.getChannel().sendMessage(String.format("%s stopped the game.", e.getAuthor().getAsMention())).queue();
    }

    private void startGame()
    {
        Player p = Database.getPlayerByName(e.getAuthor().getAsMention());
        Database.addLog(new ChatLog(p, e.getMessage().getContentRaw(), LocalTime.now()));
        if (russianRoulette.getPlayers().size() > 0)
        {

            if (!russianRoulette.hasGameStarted())
            {
                if (russianRoulette.getPlayers().size() == 1)
                {
                    addRogerToPlay();
                }
                e.getChannel().sendMessage(String.format("%s started the game.\nGood luck everyone!", e.getAuthor().getAsMention())).queue();
                russianRoulette.play(e);


            }
        } else
        {
            e.getChannel().sendMessage(String.format("%s can't start the game, nobody registered.", e.getAuthor().getAsMention())).queue();
        }

    }

    public static RussianRoulette getRussianRoulette()
    {
        return russianRoulette;
    }

    public static void setRussianRoulette(RussianRoulette russianRoulette)
    {
        RussianRouletteEvents.russianRoulette = russianRoulette;
    }
}
