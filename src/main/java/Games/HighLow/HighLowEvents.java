package Games.HighLow;

import DAO.Database;
import Model.Player.Player;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HighLowEvents extends ListenerAdapter
{
    private static List<HighLow> highLowGames = new ArrayList<>();
    private GuildMessageReceivedEvent e;

    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent e)
    {
        this.e = e;

        String[] message = e.getMessage().getContentRaw().split(" ");
        if (!e.getMessage().getAuthor().isBot())
        {

            if (message[0].charAt(0) == '!')
            {
                if (e.getChannel().getName().equalsIgnoreCase("bottest"))
                {

                    if (message.length > 1)
                    {
                        message[1] = message[1].replace("!", "");
                        message[1] = message[1].replace("&", "");
                    }
                    Player player = Database.getPlayerByName(e.getAuthor().getAsMention());
                    if (player != null)
                    {
                        if (playerAlreadyPlaying(player))
                        {
                            switch (message[0].toLowerCase())
                            {
                                case "!high" -> chooseHigh(player);
                                case "!low" -> chooseLow(player);
                                case "!out" -> cashOut(player);
                            }
                        }

                    }

                } else
                    e.getChannel().sendMessage(String.format("%s, use the bot channel you slut!", e.getMessage().getAuthor().getAsMention())).queue();

            }

        }
    }

    public static void addHighLowGame(HighLow highLow)
    {
        highLowGames.add(highLow);
        highLow.play();
    }

    private void cashOut(Player player)
    {
        HighLow highLow = getGameForPlayer(player);
        highLow.leaveGame();
    }

    private void chooseHigh(Player player)
    {
        HighLow highLow = getGameForPlayer(player);
        highLow.chooseHigh();
    }

    private void chooseLow(Player player)
    {
        HighLow highLow = getGameForPlayer(player);
        highLow.chooseLow();
    }

    public static boolean playerAlreadyPlaying(Player player)
    {
        for (HighLow highLow : highLowGames)
        {
            if (highLow.getPlayer() == player)
                return true;
        }
        return false;
    }

    private HighLow getGameForPlayer(Player player)
    {
        for (HighLow highLow : highLowGames)
        {
            if (highLow.getPlayer() == player)
                return highLow;
        }
        return null;
    }

    public static void removeGame(HighLow highLow)
    {
        highLowGames.remove(highLow);
    }
}
