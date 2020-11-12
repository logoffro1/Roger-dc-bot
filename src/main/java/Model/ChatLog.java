package Model;

import java.time.LocalTime;

public class ChatLog
{
    private Player player;
    private String message;
    private LocalTime tme;

    public ChatLog(Player player, String message, LocalTime tme)
    {
        this.player = player;
        this.message = message;
        this.tme = tme;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public LocalTime getTme()
    {
        return tme;
    }

    public void setTme(LocalTime tme)
    {
        this.tme = tme;
    }
}
