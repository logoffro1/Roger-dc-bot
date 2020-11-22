package Events.AudioEvents;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.concurrent.TimeUnit;

public class SoundEvents extends Audio
{

    public void onGuildMessageReceived(GuildMessageReceivedEvent e)
    {
        this.e = e;
        channel = e.getGuild().getVoiceChannels().get(0);
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
                        case "!why" -> whyYouRunning();
                        case "!coffin" -> coffinDance();
                        case "!bloody" -> fuckenBloody();
                        case "!fbi" -> fbiOpenUp();
                        case "!wow" -> woooow();
                        case "!haha" -> laughter();
                        case "!wait" -> elevatorMusic();
                        case "!triple" -> aTriple();
                        case "!illuminati" -> illuminati();
                        case "!lying" -> lying();
                        case "!shots" -> shotsFired();
                        case "!crickets" -> crickets();
                        case "!imfine" -> imFine();
                        case "!sad" -> sadMusic();
                        case "!passed" -> passed();
                        case "!leave" -> discordLeave();
                    }
                }
            } else
                this.e.getChannel().sendMessage(String.format("%s, use the bot channel you slut!", this.e.getMessage().getAuthor().getAsMention())).queue();
        }
    }

    private void sadMusic()
    {
        playAudio("https://www.youtube.com/watch?v=i3MJ5loj0Bg&list=RDCMUCi-xN4ZB6e-0JcXzvBEomlw&index=4", false);
    }

    private void discordLeave()
    {
        playAudio("https://www.youtube.com/watch?v=AY7LPwk3lE4", false);
    }

    private void passed()
    {
        playAudio("https://www.youtube.com/watch?v=sA3juLMhv3A", false);
    }

    private void laughter()
    {
        playAudio("https://www.youtube.com/watch?v=3IC76o_lhFw&list=PLWL3FzHaRRMkQqUhks8Y9l35rqY_kKCto&index=19", false);
    }

    private void imFine()
    {
        playAudio("https://www.youtube.com/watch?v=77sS5IuR0Gs&list=PLWL3FzHaRRMkQqUhks8Y9l35rqY_kKCto&index=34", false);
    }

    private void lying()
    {
        playAudio("https://www.youtube.com/watch?v=RRq3sdibmuM&list=PLWL3FzHaRRMkQqUhks8Y9l35rqY_kKCto&index=39", false);
    }

    private void shotsFired()
    {
        playAudio("https://www.youtube.com/watch?v=U76-3RQAHPg&list=PLWL3FzHaRRMkQqUhks8Y9l35rqY_kKCto&index=38", false);
    }

    private void crickets()
    {
        playAudio("https://www.youtube.com/watch?v=CpGtBnVZLSk&list=PLWL3FzHaRRMkQqUhks8Y9l35rqY_kKCto&index=37", false);
    }

    private void elevatorMusic()
    {
        playAudio("https://www.youtube.com/watch?v=xy_NKN75Jhw&list=PLWL3FzHaRRMkQqUhks8Y9l35rqY_kKCto&index=21", false);
    }

    private void aTriple()
    {
        playAudio("https://www.youtube.com/watch?v=XlLbsTP0C_U&list=PLWL3FzHaRRMkQqUhks8Y9l35rqY_kKCto&index=29", false);
    }

    private void illuminati()
    {
        playAudio("https://www.youtube.com/watch?v=sahAbxq8WPw&list=PLWL3FzHaRRMkQqUhks8Y9l35rqY_kKCto&index=30", false);
    }

    private void woooow()
    {
        playAudio("https://www.youtube.com/watch?v=OMm1RLF32ig&list=PLWL3FzHaRRMkQqUhks8Y9l35rqY_kKCto&index=16", false);
    }

    private void fbiOpenUp()
    {
        playAudio("https://www.youtube.com/watch?v=QQR7t712Mhg&list=PLWL3FzHaRRMkQqUhks8Y9l35rqY_kKCto&index=8", false);
    }

    private void fuckenBloody()
    {
        playAudio("https://www.youtube.com/watch?v=GULrVGpJVKg", false);
    }

    private void whyYouRunning()
    {
        playAudio("https://www.youtube.com/watch?v=vSX713CJb3Y", false);
    }

    private void coffinDance()
    {
        playAudio("https://www.youtube.com/watch?v=j9V78UbdzWI", false);
        if (channel.getMembers().size() > 0)
        {
            Thread gifs = new Thread(this::postCoffinGifs);

            gifs.start();
        }
    }

    private void postCoffinGifs()
    {
        try
        {
            TimeUnit.SECONDS.sleep(33);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        for (int i = 0; i < 20; i++)
        {
            e.getChannel().sendMessage("https://tenor.com/view/dancing-coffin-coffin-dance-funeral-funny-farewell-gif-16737844").queue();
            try
            {
                TimeUnit.MILLISECONDS.sleep(500);


            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
