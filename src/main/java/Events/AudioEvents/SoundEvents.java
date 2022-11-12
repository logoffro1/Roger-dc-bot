package Events.AudioEvents;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class SoundEvents extends Audio
{

    public void onGuildMessageReceived(MessageReceivedEvent e)
    {
        this.e = e;
        channel = e.getGuild().getVoiceChannels().get(0);
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
                        case "!why" -> whyYouRunning(); //works
                        case "!coffin" -> coffinDance(); //works
                        case "!bloody" -> fuckenBloody(); // DOES NOT WORK
                        case "!fbi" -> fbiOpenUp(); //works
                        case "!wow" -> woooow(); //works
                        case "!haha" -> laughter(); //works
                        case "!wait" -> elevatorMusic(); //works
                        case "!triple" -> aTriple(); // works
                        case "!illuminati" -> illuminati();//works
                        case "!lying" -> lying();//works
                        case "!shots" -> shotsFired(); //works
                        case "!crickets" -> crickets(); //works
                        case "!imfine" -> imFine(); //works
                        case "!sad" -> sadMusic(); //works
                        case "!passed" -> passed(); //works
                        case "!leave" -> discordLeave(); //works
                        case "!cena" -> johnCena(); //works
                        case "!cheer" -> cheering(); //works
                        case "!hello" -> indianHello(); //works
                        case "!boo" -> booing(); //works
                    }
            }

        }
    }

    private void sadMusic()
    {
        playAudio("https://www.youtube.com/watch?v=i3MJ5loj0Bg", false);
    }

    private void booing()
    {
        playAudio("https://www.youtube.com/watch?v=PfriI_DDifE", false);
    }

    private void indianHello()
    {
        playAudio("https://www.youtube.com/watch?v=P45cmvvAO2Q", false);
    }

    private void cheering()
    {
        playAudio("https://www.youtube.com/watch?v=barWV7RWkq0", false);
    }

    private void johnCena()
    {
        playAudio("https://www.youtube.com/watch?v=2D-ZO2rGcSA", false);
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
        playAudio("https://www.youtube.com/watch?v=3IC76o_lhFw", false);
    }

    private void imFine()
    {
        playAudio("https://www.youtube.com/watch?v=77sS5IuR0Gs", false);
    }

    private void lying()
    {
        playAudio("https://www.youtube.com/watch?v=RRq3sdibmuM", false);
    }

    private void shotsFired()
    {
        playAudio("https://www.youtube.com/watch?v=U76-3RQAHPg", false);
    }

    private void crickets()
    {
        playAudio("https://www.youtube.com/watch?v=CpGtBnVZLSk", false);
    }

    private void elevatorMusic()
    {
        playAudio("https://www.youtube.com/watch?v=xy_NKN75Jhw", false);
    }

    private void aTriple()
    {
        playAudio("https://www.youtube.com/watch?v=XlLbsTP0C_U", false);
    }

    private void illuminati()
    {
        playAudio("https://www.youtube.com/watch?v=sahAbxq8WPw", false);
    }

    private void woooow()
    {
        playAudio("https://www.youtube.com/watch?v=OMm1RLF32ig", false);
    }

    private void fbiOpenUp()
    {
        playAudio("https://www.youtube.com/watch?v=QQR7t712Mhg", false);
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
