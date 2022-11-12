package Events;

import DAO.Database;
import Model.Player.Player;
import Model.PlayingCards.CardDeck;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Random;

public class TalkEvent extends ListenerAdapter
{
    private final String ROGER_MENTION = "<@774159565507919873>";
    private final String ROGER_MENTION2 = "<@!774159565507919873>";
    private final String ROGER_MENTION3 = "<@&774159565507919873>";

    private MessageReceivedEvent e;
    private String[] helloWords = {"hey", "hi", "yo", "sup", "wassup", "hello", "hola", "morning", "mornin'", "good morning"};
    private String[] byeWords = {"bye", "cya", "see ya", "goodbye", "pa", "byebye", "byee", "byeee", "night", "gnight", "good night", "sleep tight"};
    private String[] helloResponses =
            {
                    "Hey.",
                    "Heeeeey ^^",
                    "What can I help you with?",
                    "Wassup homie",
                    "Wassup",
                    "What's poppin' dawg",
                    "Not in the mood.",
                    "Will you ever stop mentioning me?",
                    "yea",
                    "yes?",
                    "What's crackin'?",
                    "What's crack-a-lackin'?",
                    "yello"
            };
    private String[] byeResponses =
            {
                    "Bye.",
                    "Byeeee",
                    "Cya",
                    "Cya later alligator",
                    "Cya homie",
                    "Take care",
                    "Good bye, take care",
                    "Bye, love you",
                    "don't gooooo :(",
                    "stay 5 more minutes? please? :pleading_face:",
                    "Everyone is leaving Roger...",
                    "We just need to take a break..."
            };

    @Override
    public void onMessageReceived(MessageReceivedEvent e)
    {
        this.e = e;
        String message = e.getMessage().getContentRaw();

        /*
        if (message.contains(ROGER_MENTION) || message.contains(ROGER_MENTION2) || message.contains(ROGER_MENTION3))
        {
            if (Arrays.stream(helloWords).anyMatch(message.toLowerCase()::contains))
                e.getChannel().sendMessage(String.format("%s %s", helloResponses[getRandomNumber(0, helloResponses.length - 2)], e.getAuthor().getAsMention())).queue();
            else if (Arrays.stream(byeWords).anyMatch(message.toLowerCase()::contains))
                e.getChannel().sendMessage(String.format("%s %s", byeResponses[getRandomNumber(0, helloResponses.length - 2)], e.getAuthor().getAsMention())).queue();
        }
        */

        if (e.getMessage().getAuthor().getName().equalsIgnoreCase("magica"))
            fuckGroovy();
        if (e.getMessage().getContentRaw().equalsIgnoreCase("bi"))
            e.getChannel().sendMessage("Den").queue();
        
        //  System.out.println(LocalTime.now());
        // if(e.getMessage().getContentRaw().equalsIgnoreCase("Roger, apologise!"))
        //  e.getChannel().sendMessage("Emre, i do apologise for my stupidity!").queue();

/*

        Player p = Database.getPlayerByName(e.getAuthor().getAsMention());
        if (p != null)
        {

            p.setMoney(30);
            Database.savePlayersToFile();
        }
*/


       List<Player> players = Database.getAllPlayers();
/*        for (Player p : players)
        {
            System.out.println(p.getName());
            System.out.println(p.getAvatarURL());
            System.out.println(p.getMoney());
            System.out.println(p.getGamesLost());
            System.out.println(p.getGamesWon());
            System.out.println(p.getMoneyLost());
            System.out.println(p.getMoneyWon());
            System.out.println(p.getSurvivedShots());
            System.out.println(p.getChambersMixed());
            System.out.println(p.getWeaponMalfunctions());
        }*/
/*

        Player ege = new Player("<@619990218501652511>", "https://cdn.discordapp.com/avatars/619990218501652511/33392dd4f0519935a630126f66f49a98.png");
        ege.setMoney(50);
        for (int i = 0; i < 153; i++)
            ege.getRussianStats().addGameLost();
        for (int i = 0; i < 136; i++)
            ege.getRussianStats().addGameWon();

        ege.getRussianStats().setTotalMoneyLost(1075);
        ege.getRussianStats().setTotalMoneyWon(1228);
        ege.getRussianStats().setPlayerRank();


        for (int i = 0; i < 38; i++)
            ege.getRussianStats().addChambersMixed();

        for (int i = 0; i < 54; i++)
            ege.getRussianStats().addWeaponMalfunction();

        ege.getHighLowStats().setBestStreak(14);
        ege.getHighLowStats().setMoneyLost(6457);
        ege.getHighLowStats().setMoneyWon(3483);

        for (int i = 0; i < 68; i++)
        {
            ege.getHighLowStats().addTotalGamesPlayed();
        }
        for (int i = 0; i < 236; i++)
        {
            ege.getHighLowStats().addCorrectGuess();
        }
        for (int i = 0; i < 49; i++)
            ege.getHighLowStats().addWrongGuess();

        ege.getInventory().addBeer();
        ege.getInventory().addBeer();
        ege.getInventory().addCoffee();
        ege.getInventory().addCoffee();
        ege.getInventory().setWands(1);
        for (int i = 0; i < 298; i++)
            ege.getRussianStats().addSurvivedShots();

        Player emre = new Player("<@466934252416532483>", "https://cdn.discordapp.com/avatars/466934252416532483/6cbe8c59980473d2b0fb7f5cf6f78c10.png");
        emre.setMoney(125);
        for (int i = 0; i < 307; i++)
            emre.getRussianStats().addGameLost();
        for (int i = 0; i < 309; i++)
            emre.getRussianStats().addGameWon();

        emre.getRussianStats().setTotalMoneyLost(3220);
        emre.getRussianStats().setTotalMoneyWon(3347);
        emre.getRussianStats().setPlayerRank();


        for (int i = 0; i < 22; i++)
            emre.getRussianStats().addChambersMixed();

        for (int i = 0; i < 104; i++)
            emre.getRussianStats().addWeaponMalfunction();

        emre.getHighLowStats().setBestStreak(8);
        emre.getHighLowStats().setMoneyLost(5451);
        emre.getHighLowStats().setMoneyWon(2767);

        for (int i = 0; i < 30; i++)
        {
            emre.getHighLowStats().addTotalGamesPlayed();
        }
        for (int i = 0; i < 80; i++)
        {
            emre.getHighLowStats().addCorrectGuess();
        }
        for (int i = 0; i < 21; i++)
            emre.getHighLowStats().addWrongGuess();

        for (int i = 0; i < 591; i++)
            emre.getRussianStats().addSurvivedShots();

        emre.getInventory().setWands(1);
        Player eu = new Player("<@178585741260095489>", "https://cdn.discordapp.com/avatars/178585741260095489/0bf24ccc5227b1a215f109fb23dc1cc6.png");
        eu.setMoney(75);
        for (int i = 0; i < 373; i++)
            eu.getRussianStats().addGameLost();
        for (int i = 0; i < 338; i++)
            eu.getRussianStats().addGameWon();

        eu.getRussianStats().setTotalMoneyLost(2525);
        eu.getRussianStats().setTotalMoneyWon(2460);
        eu.getRussianStats().setPlayerRank();


        for (int i = 0; i < 574; i++)
            eu.getRussianStats().addSurvivedShots();

        for (int i = 0; i < 71; i++)
            eu.getRussianStats().addChambersMixed();

        for (int i = 0; i < 123; i++)
            eu.getRussianStats().addWeaponMalfunction();


        eu.getHighLowStats().setBestStreak(11);
        eu.getHighLowStats().setMoneyLost(32610);
        eu.getHighLowStats().setMoneyWon(32495);

        for (int i = 0; i < 78; i++)
        {
            eu.getHighLowStats().addTotalGamesPlayed();
        }
        for (int i = 0; i < 262; i++)
        {
            eu.getHighLowStats().addCorrectGuess();
        }
        for (int i = 0; i < 60; i++)
            eu.getHighLowStats().addWrongGuess();

        eu.getInventory().addCigarette();
        eu.getInventory().addCigarette();
        eu.getInventory().addCigarette();
        eu.getInventory().addCigarette();
        eu.getInventory().addCigarette();
        eu.getInventory().addCoffee();
        eu.getInventory().addCoffee();
        eu.getInventory().addCoffee();
        eu.getInventory().addCoffee();
        eu.getInventory().addBanana();
        eu.getInventory().addBanana();
        eu.getInventory().addBanana();
        eu.getInventory().addBanana();
        eu.getInventory().addBeer();
        eu.getInventory().addBeer();
        eu.getInventory().addBeer();
        eu.getInventory().setWands(1);
        Database.getAllPlayers().clear();
        Database.addPlayer(eu);
        Database.addPlayer(ege);
        Database.addPlayer(emre);

        Database.savePlayersToFile();
*/

    }

    private void fuckGroovy()
    {
        return;
/*        String[] phrases =
                {
                        "beep boop nobody cares",
                        "fuck off",
                        "you adopted fuck.",
                        "Roses are red, Violets are blue, fuck you in the ass too",
                        "Roses are red, Violets are blue, god made me pretty, what happened to you?",
                        "Roses are red, Violets are blue, faces like yours belong in the zoo",
                        "booooooring",
                        "do you kiss your mother with that mouth?"
                };
        if (!e.getChannel().getName().equalsIgnoreCase("bottest"))
            e.getChannel().sendMessage(String.format("%s %s", e.getMessage().getAuthor().getAsMention(), phrases[getRandomNumber(0, phrases.length - 1)])).queue();
        else
            e.getChannel().sendMessage(String.format("%s get your own channel, bitch.", e.getMessage().getAuthor().getAsMention())).queue();*/


    }

    private int getRandomNumber(int min, int max)
    {
        return new Random().nextInt((max + 1) - min) + min;
    }
}
