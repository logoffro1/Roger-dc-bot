package Music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackState;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager
{
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;
    private AudioPlaylist trackList;

    private PlayerManager()
    {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild guild)
    {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null)
        {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }
        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void playNextTrack(TextChannel channel)
    {
        if (trackList == null)
            System.out.println("IT'S NULL iin next trackk");
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());
        AudioTrack currentTrack = trackList.getSelectedTrack();
        if (currentTrack != null)
        {
            int currentTrackIndex = trackList.getTracks().indexOf(currentTrack);
            trackList.getTracks().remove(currentTrack);
            play(musicManager, trackList.getTracks().get(0));
        }

    }

    public void loadAndPlay(TextChannel channel, String trackURL,Boolean showMessages)
    {
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());
        playerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler()
        {
            @Override
            public void trackLoaded(AudioTrack audioTrack)
            {
                if(showMessages)
                channel.sendMessage("Adding to queue " + audioTrack.getInfo().title).queue();
                play(musicManager, audioTrack);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist)
            {
                trackList = audioPlaylist;
                if (trackList == null)
                    System.out.println("IT'S NULL");
                else
                    System.out.println("NOT NULL IN LIST LOADED");
                AudioTrack firstTrack = audioPlaylist.getSelectedTrack();

                if (firstTrack == null)
                {
                    firstTrack = audioPlaylist.getTracks().get(0);
                }
                if(showMessages)
                channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + audioPlaylist.getName() + ")").queue();
                play(musicManager, firstTrack);
            }

            @Override
            public void noMatches()
            {
                if(showMessages)
                channel.sendMessage("Nothing found by " + trackURL).queue();

            }

            @Override
            public void loadFailed(FriendlyException e)
            {
                if(showMessages)
                channel.sendMessage("Could not play: " + e.getMessage()).queue();
            }

        });
    }
    private void play(GuildMusicManager musicManager, AudioTrack track)
    {
        musicManager.scheduler.queue(track);
    }

    public static synchronized PlayerManager getINSTANCE()
    {
        if (INSTANCE == null)
        {

            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }
}
