package Events.AudioEvents;

import DAO.Database;
import Music.PlayerManager;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MusicEvents extends Audio
{

    public void onGuildMessageReceived(GuildMessageReceivedEvent e)
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
                        case "!play", "!p" -> playMusic(message);
                        case "!skip" -> skipMusic();
                        case "!stop" -> stopMusic();
                    }
                } else
                    this.e.getChannel().sendMessage(String.format("%s, use the bot channel you slut!", this.e.getMessage().getAuthor().getAsMention())).queue();

            }
        }
    }

    private void stopMusic()
    {
        PlayerManager manager = PlayerManager.getINSTANCE();
        manager.stopMusic(e.getChannel());
    }

    private void skipMusic()
    {
        PlayerManager manager = PlayerManager.getINSTANCE();
        manager.playNextTrack(e.getChannel());
        manager.getGuildMusicManager(e.getGuild()).player.setVolume(10);
    }

    private void playMusic(String[] message)
    {
        if (message.length > 1)
        {

            List<String> titleWords = new ArrayList<>();
            for (String s : message)
            {
                if (s.equals("!play")) continue;
                titleWords.add(s);
            }
            String youtubeURL = Database.getYoutubeURL(titleWords);
            String lyrics = Database.getSongLyrics(titleWords);
            channel = e.getGuild().getVoiceChannels().get(0);

            playAudio(youtubeURL, true);
        }
    }
}
