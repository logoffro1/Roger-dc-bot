package DAO;
import Model.Player;
import Model.Reminder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
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

add highlow game
add slots game
gift items to everyone at a specific time/day
make a !profile command where you can see the profile for all the games at once
 */
/*
Change log:
Added !roger (help) command
Changed the chamber colours from red-green to red-white
Added a phasmophobia randomizer (with !rnd map and !rnd item)
Added the !highlow game


 */
public class Database
{
    private static List<Reminder> reminders = new ArrayList<>();
    private static Map<String, String> jokes = new HashMap<>();
    private static File playersFile = new File("players.dat");
    private static List<Player> allPlayers = new ArrayList<>();
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
