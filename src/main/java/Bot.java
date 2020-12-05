import DAO.Database;
import Events.AudioEvents.MusicEvents;
import Events.AudioEvents.SoundEvents;
import Events.CommandEvent;
import Events.TalkEvent;
import Games.HighLow.HighLowEvents;
import Games.RussianRoulette.RussianRouletteEvents;
import Model.Reminder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Bot
{
    public static void main(String[] args) throws LoginException
    {
        init();
    }

    private static void init() throws LoginException
    {
        Database.initJokes();
        Database.initPlayers();
        final String TOKEN = "Nzc0MTU5NTY1NTA3OTE5ODcz.X6TuRw.zjfCA9qQcCR9BNRMllUmLuVRNo8";
        JDA jda = JDABuilder.createDefault(TOKEN).build();
        jda.getPresence().setPresence(Activity.playing("with Jeff"), true);
        jda.addEventListener(new TalkEvent());
        jda.addEventListener(new CommandEvent());
        jda.addEventListener(new RussianRouletteEvents());
        jda.addEventListener(new HighLowEvents());
        jda.addEventListener(new MusicEvents());
        jda.addEventListener(new SoundEvents());
        Thread reminders = new Thread(() ->
                checkReminders());

        reminders.start();

    }

    private static void checkReminders()
    {
        while (true)
        {
            Reminder reminder = null;
            for (Reminder r : Database.getReminders())
            {
                if (r.canSendReminder())
                {
                    reminder = r;
                    final String REMINDER_URL = "https://lh3.googleusercontent.com/proxy/dqfsVdQjhPSK-Gv2YxlVrzWMlQSbZatsCMfnjoy1jO8esz2xUWdEXztwtZJZyjKTFTuM5qHx_fO6wHHssazbIoP2v-MSO5MZqm6ilcY45LkwxAtNyoM";
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Reminder");
                    eb.setThumbnail(REMINDER_URL);
                    eb.setColor(Color.RED);
                    eb.addField(r.getTitle(), String.format("%s this is the reminder that you made me set for %d minutes!", r.getUser(), r.getTime()), true);
                    String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    eb.addField("Current time", String.format("Current time is %s", formattedDate), false);
                    if (r.getUser().equalsIgnoreCase("<@178585741260095489>"))
                        eb.addField("Sir, Yes Sir!", "You got it, boss!", false);

                    eb.setFooter("Roger, always at your service!");
                    r.getE().getGuild().getTextChannelsByName("reminders", true).get(0).sendMessage(eb.build()).queue();

                }
            }
            if (reminder != null)
                Database.deleteReminder(reminder);

            if (LocalTime.now().getHour() == 20 && LocalTime.now().getMinute() == 0 && LocalDateTime.now().getSecond() == 0)
            {

                Database.giveEveryoneMoney(50);
                Database.giveEveryoneGift();
            }

            try
            {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

}
