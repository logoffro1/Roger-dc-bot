package Events;

import DAO.Database;
import Games.RussianRoulette;
import Games.RussianRouletteEvents;
import Model.Player;
import Model.Reminder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CommandEvent extends ListenerAdapter
{
    GuildMessageReceivedEvent e;

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
                        case "!rnd" -> randomCommand(message);
                        case "!roll" -> rollTheDiceCommand();
                        case "!remind" -> remindCommand(message);
                        case "!joke" -> jokeCommand();
                        case "!corona" -> coronaCommand(message);
                        case "!roulette" -> russianRouletteCommand(message);
                    }
                } else
                    this.e.getChannel().sendMessage(String.format("%s, use the bot channel you slut!", this.e.getMessage().getAuthor().getAsMention())).queue();

            }
        }


    }

    private void russianRouletteCommand(String[] message)
    {
        if (RussianRouletteEvents.getRussianRoulette() == null)
        {
            int entryFee = 0;

            if (message.length == 2)
            {
                if (isNumber(message[1]))
                    entryFee = Integer.parseInt(message[1]);
                else
                {
                    if (!(message[1].equalsIgnoreCase("leaders") || message[1].equalsIgnoreCase("leader")))
                    {
                        Player player = Database.getPlayerByName(message[1]);
                        if (player != null)
                        {
                            showPlayerProfile(player);
                            return;
                        }
                    } else
                    {
                        showLeaderboards();
                        return;
                    }


                }

            }
            RussianRoulette russianRoulette = new RussianRoulette(entryFee);
            RussianRouletteEvents.setRussianRoulette(russianRoulette);
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Russian Roulette");
            eb.setThumbnail("https://pngimg.com/uploads/gun/gun_PNG1354.png");
            eb.setColor(Color.BLACK);
            eb.addField(String.format("%s started a new game of russian roulette!\n```Entry fee: $%d```", e.getAuthor().getAsTag(), entryFee), "Below you can find information about how to play", true);
            eb.addField("Commands", "```!me - register to play\n!stop - stop the current game\n!start - start the game\n!shoot - fire the weapon\n!mix - mix the chambers\n!give [player] [amount] - transfer a player money```", false);
            eb.appendDescription("There are 6 chambers, one of them has a bullet in it, the other 5 are empty, shoot and try to outlive your friends!\n **Each player can only mix the chambers once per game**");
            eb.setFooter("Good luck everyone, may the luckiest one survive!\nMade by Cosmin Ilie");
            e.getChannel().sendMessage(eb.build()).queue();
        } else
        {
            e.getChannel().sendMessage(String.format("%s a russian roulette game is already in progress.\nType !stop to stop the current game", e.getAuthor().getAsMention())).queue();
        }

    }

    private void showLeaderboards()
    {
        List<Player> players = Database.getAllPlayers();
        List<Player> playersTemp = new ArrayList<>();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Leaderboard :trophy:");
        eb.setThumbnail("https://purepng.com/public/uploads/large/gold-medal-5ez.png");
        eb.setColor(Color.YELLOW);

        Player nr1 = new Player("test", "test");
        Player nr2 = new Player("test", "test");
        Player nr3 = new Player("test", "test");

        for (Player p : players)
        {
            if (p.getGamesWon() > nr1.getGamesWon())
                nr1 = p;
        }
        for (Player p : players)
        {
            if (p.getGamesWon() < nr3.getGamesWon())
                nr3 = p;
        }
        for (Player p : players)
        {
            if (p.getGamesWon() > nr2.getGamesWon())
            {
                if (nr1 == p || nr3 == p) continue;
                nr2 = p;
            }

        }
        playersTemp.add(nr1);
        playersTemp.add(nr2);
        playersTemp.add(nr3);
        int count = 1;
        for (Player p : playersTemp)
        {
            eb.addField(String.format("#%d ", count), String.format("%s - Games won: **%d**\nRank: **%s**\nMoney: **$%d**", p.getName(), p.getGamesWon(),p.getRank(),p.getMoney()), false);
            count++;
        }
        eb.setFooter("Made by Cosmin Ilie");
        e.getChannel().sendMessage(eb.build()).queue();
    }

    private void showPlayerProfile(Player player)
    {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setThumbnail(player.getAvatarURL());
        eb.setDescription("Player " + player.getName());
        eb.setColor(Color.BLUE);
        eb.addField("Stats", String.format("Rank: **%s**\nMoney: $%d\nTotal games played: %d\nGames won: %d\nGames lost: %d\nWin percentage: %d%%\nMoney won: %d\nMoney lost: %d\nSurvived shots: %d\nWeapon malfunctions: %d\nChambers mixed: %d",
                player.getRank(),
                player.getMoney(),
                player.getTotalGames(),
                player.getGamesWon(),
                player.getGamesLost(),
                player.getWinPercentage(),
                player.getMoneyWon(),
                player.getMoneyLost(),
                player.getSurvivedShots(),
                player.getWeaponMalfunctions(),
                player.getChambersMixed()), true);

        eb.setFooter("Made by Cosmin Ilie");
        e.getChannel().sendMessage(eb.build()).queue();
    }

    private void coronaCommand(String[] message)
    {
        String coronaCase = Database.getCoronaCases(message[1]);

        if (coronaCase.equals(""))
            e.getChannel().sendMessage(String.format("%s country not found", e.getAuthor().getAsMention())).queue();
        else
            e.getChannel().sendMessage(String.format("%s Latest coronavirus cases for %s\n%s", e.getAuthor().getAsMention(), message[1], coronaCase)).queue();

    }

    private void jokeCommand()
    {
        Map<String, String> joke = Database.getRandomJoke();
        String jokeQ = "";
        String punchline = "";
        for (Map.Entry<String, String> entry : joke.entrySet())
        {
            jokeQ = entry.getKey();
            punchline = entry.getValue();
        }

        e.getChannel().sendMessage(String.format("%s\n%s\n%s", e.getAuthor().getAsMention(), jokeQ, punchline)).queue();
    }

    private void remindCommand(String[] message)
    {

        if (message.length >= 2)
        {
            if (isNumber(message[1]))
            {
                StringBuilder reminder = new StringBuilder();
                for (int i = 2; i < message.length; i++)
                {
                    reminder.append(message[i]).append(" ");
                }

                int minutes = Integer.parseInt(message[1]);
                if (message.length == 2)
                {
                    this.e.getChannel().sendMessage(String.format("%s I will remind you in %d minutes", e.getAuthor().getAsMention(), minutes)).queue();
                } else
                {

                    this.e.getChannel().sendMessage(String.format("%s I will remind you in %d minutes of '%s'", e.getAuthor().getAsMention(), minutes, reminder.toString())).queue();
                }
                Database.addReminder(new Reminder(e.getAuthor().getAsMention(), reminder.toString(), minutes, e));

            } else
            {

                if (message[1].equalsIgnoreCase("cancel") || message[1].equalsIgnoreCase("delete"))
                {
                    List<Reminder> reminders = new ArrayList<>();
                    for (Reminder r : Database.getReminders())
                    {
                        if (r.getUser().equals(e.getAuthor().getAsMention()))
                            reminders.add(r);
                    }
                    for (Reminder r : reminders)
                    {
                        Database.deleteReminder(r);
                    }
                    if (reminders.size() > 0)
                        e.getChannel().sendMessage(String.format("%s all reminders canceled", e.getAuthor().getAsMention())).queue();
                    else
                        e.getChannel().sendMessage(String.format("%s you don't have any reminders", e.getAuthor().getAsMention())).queue();
                }

            }

        }
    }

    private void rollTheDiceCommand()
    {
        int roll = getRandomNumber(1, 6);
        this.e.getChannel().sendMessage(String.format("%s rolled a %d", e.getAuthor().getAsMention(), roll)).queue();
    }

    private void randomCommand(String[] message)
    {
        if (message.length == 3)
        {
            if (isNumber(message[1]) && isNumber(message[2]))
            {
                int min = Integer.parseInt(message[1]);
                int max = Integer.parseInt(message[2]);
                if (max > min)
                    this.e.getChannel().sendMessage(String.format("%d", getRandomNumber(min, max))).queue();
                else
                    this.e.getChannel().sendMessage("You stupid duck, first put the lower number").queue();
            }

        } else if (message.length == 1)
        {
            this.e.getChannel().sendMessage(String.format("%d", getRandomNumber(0, 10000000))).queue();
        }

    }

    private int getRandomNumber(int min, int max)
    {
        return new Random().nextInt((max + 1) - min) + min;
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
}
