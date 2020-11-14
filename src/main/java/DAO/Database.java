package DAO;
///import Model.Player.Player;

import Model.ChatLog;
import Model.Player.Player;
import Model.Reminder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

/*
IDEAS
Maybe while in a round, give players roles with different colors to see who's dead and who's alive (or just a way to see better who dead)
You can buy stuff with money (like cigs, beer, coffee, drugs, maybe real estate)
LOTTO every sunday
Notification when you rank up
Leaderboard
Maybe add MMR system
Maybe add a job system
add more ways to make money
add achievements
maybe add the ability to have extra shots
make the game more responsive
add slots game
add quizzes to earn money (maybe about programming)
 Make highlow game to create a new channel for each game
 */
/*
Change log:
Added !roger (help) command
Changed the chamber colours from red-green to red-white
Added a Phasmophobia randomizer (with !rnd map and !rnd item)
Added the !highlow game
Added !shop command
Added !buy command
Added inventory
added High-Low permanent stats
Added the !profile [user] command to see the user's complete profile
From now on, every day at 20:00 everyone will get $50
Added a bonus of $5 whenever you win a game of russian roulette (even if the entry fee was 0)
Fixed a bug where the total survived shots was incorrect
Other minor bug fixes
 */
public class Database
{
    private static List<Reminder> reminders = new ArrayList<>();
    private static Map<String, String> jokes = new HashMap<>();
    private static File playersFile = new File("players.dat");
    private static List<Player> allPlayers = new ArrayList<>();
    private static List<ChatLog> logs = new ArrayList<>();

    public static void addLog(ChatLog log)
    {
        logs.add(log);
    }

    public static List<ChatLog> getLogs()
    {
        return logs;
    }

    public static Player getCheater()
    {
        for (int i = 0; i < logs.size(); i++)
        {
            Player p = logs.get(i).getPlayer();
            List<LocalTime> times = new ArrayList<>();
            for (ChatLog log : logs)
            {
                if (log.getPlayer() == p)
                {
                    times.add(log.getTme());
                }
            }
            if (times.size() >= 5)
            {
                int seconds1 = times.get(0).getMinute() * 60 + times.get(0).getSecond();
                int seconds2 = times.get(1).getMinute() * 60 + times.get(1).getSecond();
                int seconds3 = times.get(2).getMinute() * 60 + times.get(2).getSecond();
               // System.out.println(seconds2 - seconds1);
               // System.out.println(seconds3 - seconds2);

                if (seconds2 - seconds1 == seconds3 - seconds2)
                {

                    ///  int seconds = times.get(1).getSecond() - times.get(0).getSecond();
                    ///  if (seconds == times.get(3).getSecond() - times.get(2).getSecond())
                    return p;


                }
            }

        }

        return null;
    }

    public static void giveEveryoneMoney(int money)
    {
        for (Player p : allPlayers)
            p.setMoney(p.getMoney() + money);

        Database.savePlayersToFile();
    }

    public static void initJokes()
    {
        String URL = "https://www.readersdigest.ca/culture/10-short-jokes-anyone-can-remember/";
        try
        {
            final Document document = Jsoup.connect(URL).get();
            Elements divs = document.select("div.card-content");
            for (Element div : divs)
            {
                Element joke = div.selectFirst("h2");
                Element punchline = div.selectFirst("p");
                if (punchline.text().isEmpty())
                    punchline = div.select("p").get(1);

                jokes.put(joke.text(), punchline.text());
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getRandomJoke()
    {
        int rnd = new Random().nextInt(jokes.size());
        Object[] keys = jokes.keySet().toArray();
        Object[] values = jokes.values().toArray();
        Map<String, String> joke = new HashMap<>();
        joke.put(keys[rnd].toString(), values[rnd].toString());
        return joke;
    }

    public static String getCoronaCases(String findCountry)
    {
        final String[] columnNames = {"#", "Country", "Total Cases", "New Cases"};
        final String URL = "https://www.worldometers.info/coronavirus";

        try
        {
            final Document document = Jsoup.connect(URL).get();
            outterloop:
            for (Element row : document.select("table.main_table_countries tr"))
            {
                final String country = row.select("td:nth-of-type(2)").text();
                if (country.equals("")) continue;

                if (country.toLowerCase().equals(findCountry.toLowerCase()))
                {
                    StringBuilder coronaCases = new StringBuilder();
                    for (int i = 1; i <= 4; i++)
                    {
                        String columnData = row.select(String.format("td:nth-of-type(%d)", i)).text();
                        if (columnData.equals("")) continue outterloop;

                        coronaCases.append(String.format(" %s: %s |", columnNames[i - 1], columnData));
                    }
                    return coronaCases.toString();

                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }

    public static void addReminder(Reminder reminder)
    {
        reminders.add(reminder);
    }

    public static void deleteReminder(Reminder reminder)
    {
        reminders.remove(reminder);
    }

    public static List<Reminder> getReminders()
    {
        return reminders;
    }

    public static void savePlayersToFile()
    {
        try (FileOutputStream fos = new FileOutputStream(playersFile); ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            for (Player p : allPlayers)
            {
                oos.writeObject(p);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static List<Player> getAllPlayers()
    {
        return allPlayers;
    }

    public static void addPlayer(Player player)
    {
        allPlayers.add(player);
    }

    public static Player getPlayerByName(String name)
    {

        for (Player p : allPlayers)
            if (p.getName().equals(name))
                return p;

        return null;
    }

    public static void readPlayersFromFile()
    {
        try (FileInputStream fis = new FileInputStream(playersFile); ObjectInputStream ois = new ObjectInputStream(fis))
        {
            while (true)
            {
                try
                {
                    Player player = (Player) ois.readObject();
                    allPlayers.add(player);
                } catch (EOFException eofe)
                {
                    break;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void initPlayers()
    {
        if (playersFile.exists())
            readPlayersFromFile();
    }
}
