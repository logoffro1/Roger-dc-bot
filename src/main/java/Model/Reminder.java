package Model;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.LocalTime;

public class Reminder
{
    private String user;
    private String title;
    private int time;
    private LocalTime reminderDate = LocalTime.now();
    private GuildMessageReceivedEvent e;


    public Reminder(String user, String title, int time, GuildMessageReceivedEvent e)
    {
        this.user = user;
        this.title = title;
        this.time = time;
        this.e = e;
        this.reminderDate = this.reminderDate.plusMinutes(time);
    }

    public GuildMessageReceivedEvent getE()
    {
        return e;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public boolean canSendReminder()
    {
        return LocalTime.now().getMinute() == reminderDate.getMinute() && LocalTime.now().getSecond() == reminderDate.getSecond();
    }
}
