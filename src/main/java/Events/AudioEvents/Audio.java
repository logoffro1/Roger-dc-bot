package Events.AudioEvents;

import Music.PlayerManager;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Audio extends ListenerAdapter
{
    protected GuildMessageReceivedEvent e;
    protected VoiceChannel channel;

    protected void playAudio(String trackURL,Boolean showMsg){
        if (channel.getMembers().size() > 0)
        {

            e.getGuild().getAudioManager().openAudioConnection(channel);

            PlayerManager manager = PlayerManager.getINSTANCE();
            manager.loadAndPlay(e.getChannel(), trackURL, showMsg);
            manager.getGuildMusicManager(e.getGuild()).player.setVolume(20);
        }
    }
}
