package Events;

import DAO.Database;
import Model.Player;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;
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
        Player ege = new Player("<@619990218501652511>", "https://cdn.discordapp.com/avatars/619990218501652511/33392dd4f0519935a630126f66f49a98.png");
        ege.setMoney(420);
        for (int i = 0; i < 19; i++)
            ege.addGameLost();
        for (int i = 0; i < 21; i++)
            ege.addGameWon();

        ege.setTotalMoneyLost(341);
        ege.setTotalMoneyWon(531);
        ege.setPlayerRank();

        for (int i = 0; i < 1; i++)
            ege.addSurvivedShot();

        for (int i = 0; i < 11; i++)
            ege.addChambersMixed();

        for (int i = 0; i < 8; i++)
            ege.addWeaponMalfunction();


        Player emre = new Player("<@466934252416532483>", "https://cdn.discordapp.com/avatars/466934252416532483/6cbe8c59980473d2b0fb7f5cf6f78c10.png");
        emre.setMoney(0);
        for (int i = 0; i < 26; i++)
            emre.addGameLost();
        for (int i = 0; i < 14; i++)
            emre.addGameWon();

        emre.setTotalMoneyLost(532);
        emre.setTotalMoneyWon(362);
        emre.setPlayerRank();

        for (int i = 0; i < 1; i++)
            emre.addSurvivedShot();

        for (int i = 0; i < 7; i++)
            emre.addChambersMixed();

        for (int i = 0; i < 9; i++)
            emre.addWeaponMalfunction();

        Player eu = new Player("<@178585741260095489>", "https://cdn.discordapp.com/avatars/178585741260095489/0bf24ccc5227b1a215f109fb23dc1cc6.png");
        eu.setMoney(0);
        for (int i = 0; i < 20; i++)
            eu.addGameLost();
        for (int i = 0; i < 19; i++)
            eu.addGameWon();

        eu.setTotalMoneyLost(469);
        eu.setTotalMoneyWon(449);
        eu.setPlayerRank();

        for (int i = 0; i < 1; i++)
            eu.addSurvivedShot();

        for (int i = 0; i < 9; i++)
            eu.addChambersMixed();

        for (int i = 0; i < 9; i++)
            eu.addWeaponMalfunction();

        Database.getAllPlayers().clear();
        Database.addPlayer(eu);
        Database.addPlayer(ege);
        Database.addPlayer(emre);

        Database.savePlayersToFile();
*/

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
