package Events;

import DAO.Database;
import Model.Player.Player;
import Model.PlayingCards.CardDeck;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class TalkEvent extends ListenerAdapter
{
    private final String ROGER_MENTION = "<@774159565507919873>";
    private final String ROGER_MENTION2 = "<@!774159565507919873>";
    private final String ROGER_MENTION3 = "<@&774159565507919873>";

    private GuildMessageReceivedEvent e;
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

    public void onGuildMessageReceived(GuildMessageReceivedEvent e)
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

        if (e.getMessage().getAuthor().getName().equalsIgnoreCase("groovy"))
            fuckGroovy();
        if (e.getMessage().getContentRaw().equalsIgnoreCase("bi"))
            e.getChannel().sendMessage("Den").queue();
        
        //  System.out.println(LocalTime.now());
        // if(e.getMessage().getContentRaw().equalsIgnoreCase("Roger, apologise!"))
        //  e.getChannel().sendMessage("Emre, i do apologise for my stupidity!").queue();


      /*  Player p = Database.getPlayerByName(e.getAuthor().getAsMention());
        if (p != null)
        {

            p.setMoney(30);
            Database.savePlayersToFile();
        }*/


/*
       List<Player> players = Database.getAllPlayers();
        for (Player p : players)
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
        }

        */
     /*   Player ege = new Player("<@619990218501652511>", "https://cdn.discordapp.com/avatars/619990218501652511/33392dd4f0519935a630126f66f49a98.png");
        ege.setMoney(100);
        for (int i = 0; i < 48; i++)
            ege.getRussianStats().addGameLost();
        for (int i = 0; i < 42; i++)
            ege.getRussianStats().addGameWon();

        ege.getRussianStats().setTotalMoneyLost(918);
        ege.getRussianStats().setTotalMoneyWon(628);
        ege.getRussianStats().setPlayerRank();


        for (int i = 0; i < 33; i++)
            ege.getRussianStats().addChambersMixed();

        for (int i = 0; i < 20; i++)
            ege.getRussianStats().addWeaponMalfunction();

        ege.getHighLowStats().setBestStreak(9);
        ege.getHighLowStats().setMoneyLost(627);
        ege.getHighLowStats().setMoneyWon(1036);

        for (int i = 0; i < 14; i++)
        {
            ege.getHighLowStats().addTotalGamesPlayed();
        }
        for (int i = 0; i < 64; i++)
        {
            ege.getHighLowStats().addCorrectGuess();
        }
        for (int i = 0; i < 6; i++)
            ege.getHighLowStats().addWrongGuess();

        ege.getInventory().addCigarette();
        ege.getInventory().addCigarette();
        ege.getInventory().addCigarette();
        ege.getInventory().addCigarette();
        ege.getInventory().addCoffee();
        ege.getInventory().addCoffee();
        ege.getInventory().addCoffee();
        for (int i = 0; i < 53; i++)
            ege.getRussianStats().addSurvivedShots();

        Player emre = new Player("<@466934252416532483>", "https://cdn.discordapp.com/avatars/466934252416532483/6cbe8c59980473d2b0fb7f5cf6f78c10.png");
        emre.setMoney(55);
        for (int i = 0; i < 101; i++)
            emre.getRussianStats().addGameLost();
        for (int i = 0; i < 108; i++)
            emre.getRussianStats().addGameWon();

        emre.getRussianStats().setTotalMoneyLost(1749);
        emre.getRussianStats().setTotalMoneyWon(1590);
        emre.getRussianStats().setPlayerRank();


        for (int i = 0; i < 13; i++)
            emre.getRussianStats().addChambersMixed();

        for (int i = 0; i < 50; i++)
            emre.getRussianStats().addWeaponMalfunction();

        emre.getHighLowStats().setBestStreak(2);
        emre.getHighLowStats().setMoneyLost(0);
        emre.getHighLowStats().setMoneyWon(2);

        for (int i = 0; i < 1; i++)
        {
            emre.getHighLowStats().addTotalGamesPlayed();
        }
        for (int i = 0; i < 2; i++)
        {
            emre.getHighLowStats().addCorrectGuess();
        }
        for (int i = 0; i < 0; i++)
            emre.getHighLowStats().addWrongGuess();

        for (int i = 0; i < 79; i++)
            emre.getRussianStats().addSurvivedShots();

        emre.getInventory().addCigarette();
        emre.getInventory().addCoffee();
        emre.getInventory().addBanana();
        emre.getInventory().addBeer();
        emre.getInventory().addToiletPaper();
        emre.getInventory().addToiletPaper();
        emre.getInventory().addToiletPaper();
        emre.getInventory().addToiletPaper();
        emre.getInventory().addToiletPaper();


        Player eu = new Player("<@178585741260095489>", "https://cdn.discordapp.com/avatars/178585741260095489/0bf24ccc5227b1a215f109fb23dc1cc6.png");
        eu.setMoney(15);
        for (int i = 0; i < 275; i++)
            eu.getRussianStats().addGameLost();
        for (int i = 0; i < 228; i++)
            eu.getRussianStats().addGameWon();

        eu.getRussianStats().setTotalMoneyLost(1220);
        eu.getRussianStats().setTotalMoneyWon(1348);
        eu.getRussianStats().setPlayerRank();


        for (int i = 0; i < 296; i++)
            eu.getRussianStats().addSurvivedShots();

        for (int i = 0; i < 62; i++)
            eu.getRussianStats().addChambersMixed();

        for (int i = 0; i < 96; i++)
            eu.getRussianStats().addWeaponMalfunction();


        eu.getHighLowStats().setBestStreak(8);
        eu.getHighLowStats().setMoneyLost(782);
        eu.getHighLowStats().setMoneyWon(324);

        for (int i = 0; i < 36; i++)
        {
            eu.getHighLowStats().addTotalGamesPlayed();
        }
        for (int i = 0; i < 124; i++)
        {
            eu.getHighLowStats().addCorrectGuess();
        }
        for (int i = 0; i < 29; i++)
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
        eu.getInventory().addCoffee();
        eu.getInventory().addToiletPaper();
        eu.getInventory().addToiletPaper();
        eu.getInventory().addToiletPaper();
        eu.getInventory().addToiletPaper();
        eu.getInventory().addToiletPaper();
        eu.getInventory().addToiletPaper();
        eu.getInventory().addToiletPaper();
        Database.getAllPlayers().clear();
        Database.addPlayer(eu);
        Database.addPlayer(ege);
        Database.addPlayer(emre);

        Database.savePlayersToFile();*/

    }

    private void fuckGroovy()
    {
        String[] phrases =
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
            e.getChannel().sendMessage(String.format("%s get your own channel, bitch.", e.getMessage().getAuthor().getAsMention())).queue();


    }

    private int getRandomNumber(int min, int max)
    {
        return new Random().nextInt((max + 1) - min) + min;
    }
}
