package Games.HorseRacing;

import DAO.Database;
import Model.ChatLog;
import Model.HorseRacing.Horse;
import Model.HorseRacing.PlayerBet;
import Model.Player.Player;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class HorseRacingEvents extends ListenerAdapter
{
    private static HorseRacing horseRacing;
    private static Message horseRacingMessage;
    private static List<Player> reactedPlayers = new ArrayList<>();
    private MessageReceivedEvent e;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e)
    {
        if (horseRacing != null)
        {

            this.e = e;
            String[] message = e.getMessage().getContentRaw().split(" ");
            if (!e.getMessage().getAuthor().isBot())
            {

                if (message[0].charAt(0) == '!')
                {

                    if (message.length > 1)
                    {
                        message[1] = message[1].replace("!", "");
                        message[1] = message[1].replace("&", "");
                    }
                    switch (message[0].toLowerCase())
                    {
                        case "!bet" -> placeBet(message);
                        case "!stop" -> stopGame();
                        case "!start" -> startGame();
                        case "!shoot", "!s" -> shoot();
                    }
                }
            }
        }

    }
private void placeBet(String[] message){
    if(message.length == 3){
        if(!Database.doesHorseExist(message[1])){
            e.getChannel().sendMessage(String.format("%s horse named '%s' does not exist.", e.getAuthor().getAsMention(),message[1])).queue();
            return;
        }
        if(!isNumber(message[2]))
        {
            e.getChannel().sendMessage(String.format("%s invalid amount.", e.getAuthor().getAsMention())).queue();
            return;
        }
        if (horseRacing.getCanRegister())
        {
            if (!horseRacing.checkIfPlayerExists(e.getAuthor().getAsMention()))
            {
                Horse horse = Database.getHorseByName(message[1]);
                Player player = Database.getPlayerByName(e.getAuthor().getAsMention());
                if (player != null)
                    horseRacing.addPlayer(player,horse,Integer.parseInt(message[2]),e);
                else
                {
                    Player newPlayer = new Player(e.getAuthor().getAsMention(), e.getAuthor().getAvatarUrl());
                    horseRacing.addPlayer(newPlayer,horse,Integer.parseInt(message[2]), e);
                    Database.addPlayer(newPlayer);
                }
            } else
            {
                e.getChannel().sendMessage(String.format("%s you are already registered.", e.getAuthor().getAsMention())).queue();
            }

        }
    }

}

    public static void setHorseRacingMessage(Message horseRacingMessage)
    {
        HorseRacingEvents.horseRacingMessage = horseRacingMessage;
    }

    private void shoot()
    {
        Player p = Database.getPlayerByName(e.getAuthor().getAsMention());
        Database.addLog(new ChatLog(p, e.getMessage().getContentRaw(), LocalTime.now()));
        Player cheater = Database.getCheater();

        if (cheater == null)
        {
          //  horseRacing.shoot(e.getAuthor().getAsMention());
        } else
        {
            horseRacing = null;
            e.getChannel().sendMessage(String.format("%s get outta here, you filthy cheater!", e.getAuthor().getAsMention())).queue();
            Database.getLogs().clear();
        }
    }

/*    private void registerPlayer(String[] message)
    {
        Player p = Database.getPlayerByName(e.getAuthor().getAsMention());
        if (horseRacing.getCanRegister())
        {
            if (!horseRacing.checkIfPlayerExists(e.getAuthor().getAsMention()))
            {

                Player player = Database.getPlayerByName(e.getAuthor().getAsMention());
                if (player != null)
                    horseRacing.addPlayer(player, e);
                else
                {
                    Player newPlayer = new Player(e.getAuthor().getAsMention(), e.getAuthor().getAvatarUrl());
                    horseRacing.addPlayer(newPlayer, e);
                    Database.addPlayer(newPlayer);
                }
            } else
            {
                e.getChannel().sendMessage(String.format("%s you are already registered.", e.getAuthor().getAsMention())).queue();
            }

        }
    }*/
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
        Player player = Database.getPlayerByName(e.getAuthor().getAsMention());
        if (player != null)
        {

            for (PlayerBet p : horseRacing.getPlayerBets())
                p.getPlayer().setMoney(p.getPlayer().getMoney() + (int)p.getBetAmount());

            horseRacing = null;
            e.getChannel().sendMessage(String.format("%s stopped the game.", e.getAuthor().getAsMention())).queue();

            Database.savePlayersToFile();
        }
    }

    private void startGame()
    {

        Player p = Database.getPlayerByName(e.getAuthor().getAsMention());
        Database.addLog(new ChatLog(p, e.getMessage().getContentRaw(), LocalTime.now()));
        if (horseRacing.getPlayerBets().size() > 0)
        {

            if (!horseRacing.hasGameStarted())
            {
                e.getChannel().sendMessage(String.format("%s started the game.\nGood luck everyone!", e.getAuthor().getAsMention())).queue();
               // horseRacing.play(e);

               /* VoiceChannel channel = e.getGuild().getVoiceChannels().get(0);

                if (channel.getMembers().size() > 0)
                {

                    e.getGuild().getAudioManager().openAudioConnection(channel);
                    PlayerManager manager = PlayerManager.getINSTANCE();
                    manager.loadAndPlay(e.getChannel(), "https://www.youtube.com/watch?v=AFa1-kciCb4", false);
                    manager.getGuildMusicManager(e.getGuild()).player.setVolume(10);
                }*/
            }
        } else
        {
            e.getChannel().sendMessage(String.format("%s can't start the game, nobody registered.", e.getAuthor().getAsMention())).queue();
        }

    }

    public static HorseRacing getHorseRacing()
    {
        return horseRacing;
    }

    public static void setHorseRacing(HorseRacing horseRacing)
    {
        HorseRacingEvents.horseRacing = horseRacing;
    }
}
