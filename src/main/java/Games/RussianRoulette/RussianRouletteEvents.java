package Games.RussianRoulette;

import DAO.Database;
import Model.ChatLog;
import Model.Player.Player;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class RussianRouletteEvents extends ListenerAdapter
{
    private static RussianRoulette russianRoulette;
    private static Message rouletteMessage;
    private static List<Player> reactedPlayers = new ArrayList<>();
    private MessageReceivedEvent e;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e)
    {
        if (russianRoulette != null)
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
                            case "!me" -> registerPlayer(e);
                            case "!stop" -> stopGame();
                            case "!start" -> startGame();
                            case "!shoot", "!s" -> shoot();
                            case "!mix" -> russianRoulette.mix(e);
                        }
                }
            }
        }

    }

    private void setReactedPlayers()
    {
       reactedPlayers.clear();
        long msgId = rouletteMessage.getIdLong();

        e.getChannel().retrieveMessageById(msgId).queue((message) ->
        {

            for (MessageReaction r : message.getReactions())
            {
                for (User u : r.getJDA().getUsers())
                {

                    Player p = Database.getPlayerByName(u.getAsMention());
                    if (p != null)
                    {
                     //   System.out.println(p.getName());
                        if (!reactedPlayers.contains(p))
                            reactedPlayers.add(p);

                      //  System.out.println(reactedPlayers.size());

                    }
                }
            }
        });
        System.out.println(reactedPlayers.size());
    }

    private List<Player> getReactedPlayers()
    {
        return reactedPlayers;
    }

    public static void setRouletteMessage(Message rouletteMessage)
    {
        RussianRouletteEvents.rouletteMessage = rouletteMessage;
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
            Button shootButton = Button.success("shoot","SHOOT");
            russianRoulette = null;
            e.getChannel().sendMessage(String.format("%s get outta here, you filthy cheater!", e.getAuthor().getAsMention())).setActionRow(shootButton).queue();

            Database.getLogs().clear();
        }
    }

    public static void registerPlayer(Event btnEvent)
    {
      ///  System.out.println(reactedPlayers.size());
        ButtonInteractionEvent event = ButtonInteractionEvent.class.cast(btnEvent);
        String playerName = event.getUser().getAsMention();
/*if(btnEvent instanceof ButtonInteractionEvent){
btnEvent = ButtonInteractionEvent.class.cast(btnEvent);
playerName = ((ButtonInteractionEvent) btnEvent).getUser().getAsMention();
}  else {
    btnEvent = MessageReceivedEvent.class.cast(btnEvent);
    playerName = ((MessageReceivedEvent) btnEvent).getAuthor().getAsMention();
}*/
        Player p = Database.getPlayerByName(playerName);
        if (russianRoulette.getCanRegister())
        {
                if (!russianRoulette.checkIfPlayerExists(playerName))
                {

                    Player player = Database.getPlayerByName(playerName);
                    if (player != null)
                        russianRoulette.addPlayer(player, event.getChannel().asTextChannel());
                    else
                    {
                        Player newPlayer = new Player(playerName, event.getUser().getAvatarUrl());
                        russianRoulette.addPlayer(newPlayer, event.getChannel().asTextChannel());
                        Database.addPlayer(newPlayer);
                    }
                } else
                {
                    event.getChannel().sendMessage(String.format("%s you are already registered.", playerName)).queue();
                }

        }
    }


    private void addRogerToPlay()
    {
        Player newPlayer = new Player("<@774159565507919873>", "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/items/553790/b408661ef1867375b47972783f223336302460a4.jpg");
        newPlayer.setMoney(9999999);
        russianRoulette.addPlayer(newPlayer, e.getChannel().asTextChannel());
        Database.addPlayer(newPlayer);
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
        Player player = Database.getPlayerByName(e.getAuthor().getAsMention());
        if (player != null)
        {

                for (Player p : russianRoulette.getPlayers())
                    p.setMoney(p.getMoney() + russianRoulette.getEntryFee());

                russianRoulette = null;
                e.getChannel().sendMessage(String.format("%s stopped the game.", e.getAuthor().getAsMention())).queue();

                Database.savePlayersToFile();
        }
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

    public static RussianRoulette getRussianRoulette()
    {
        return russianRoulette;
    }

    public static void setRussianRoulette(RussianRoulette russianRoulette)
    {
        RussianRouletteEvents.russianRoulette = russianRoulette;
    }
}
