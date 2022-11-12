package Events.AudioEvents;

import DAO.Database;
import Music.PlayerManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class MusicEvents extends Audio
{

    public void onGuildMessageReceived(MessageReceivedEvent e)
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
                        case "!play", "!p" -> playMusic(message);
                        case "!skip" -> skipMusic();
                        case "!stop" -> stopMusic();
                    }

            }
        }
    }

    private void stopMusic()
    {
        PlayerManager manager = PlayerManager.getINSTANCE();
        manager.stopMusic(e.getChannel().asTextChannel());
    }

    private void skipMusic()
    {
        PlayerManager manager = PlayerManager.getINSTANCE();
        manager.playNextTrack(e.getChannel().asTextChannel());
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
