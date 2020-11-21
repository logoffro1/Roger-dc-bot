package Events;

import DAO.Database;
import Games.HighLow.HighLow;
import Games.HighLow.HighLowEvents;
import Games.RussianRoulette.RussianRoulette;
import Games.RussianRoulette.RussianRouletteEvents;
import Model.Player.Player;
import Model.Reminder;
import Music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.xml.crypto.Data;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CommandEvent extends ListenerAdapter
{
    GuildMessageReceivedEvent e;
    private String[] phasmoMaps =
            {
                    "Tanglewood Street House",
                    "Edgefield Street House",
                    "Ridgeview Street House",
                    "Grafton Farmhouse",
                    "Bleasdale Farmhouse",
                    "Brownstone High school",
                    "Asylum"
            };
    private String[] phasmoItems =
            {
                    "Spirit Box",
                    "Ghost Writing Book",
                    "Photo Camera",
                    "EMF reader",
                    "Video Camera",
                    "UV Flashlight",
                    "Basic Flashlight",
                    "Candle + lighter",
                    "Crucifix",
                    "Glow Stick",
                    "Head Mounted Camera",
                    "Infrared Light Sensor",
                    "Motion Sensor",
                    "Parabolic Microphone",
                    "Salt",
                    "Sanity Pills",
                    "Smudge Sticks + lighter",
                    "Sound Sensor",
                    "Strong Flashlight",
                    "Thermometer",
                    "Tripod"
            };

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
                        case "!slots" -> slotsCommand(message);
                        case "!highlow" -> highLowCommand(message);
                        case "!roger" -> showHelpPanel();
                        case "!profile" -> showPlayerProfile(message);
                        case "!shop" -> shopCommand();
                        case "!buy" -> buyItem(message);
                        case "!sell" -> sellItem(message);
                        case "!give" -> giveMoney(message);
                        case "!admingive" -> adminGive(message);
                        case "!opengift" -> openGift();
                        case "!play", "!p" -> playMusic(message);
                        case "!skip" -> skipMusic();
                        case "!stop" -> stopMusic();
                    }
                } else
                    this.e.getChannel().sendMessage(String.format("%s, use the bot channel you slut!", this.e.getMessage().getAuthor().getAsMention())).queue();

            }
        }


    }

    private void stopMusic()
    {
        PlayerManager manager = PlayerManager.getINSTANCE();
        manager.stopMusic(e.getChannel());
        manager.getGuildMusicManager(e.getGuild()).player.setVolume(10);
    }

    private void skipMusic()
    {
        PlayerManager manager = PlayerManager.getINSTANCE();
        manager.playNextTrack(e.getChannel());
        manager.getGuildMusicManager(e.getGuild()).player.setVolume(10);
    }

    private void playMusic(String[] message)
    {
        if (message.length > 1)
        {

            List<String> titleWords = new ArrayList<>();
            for (String s : message)
            {
                if (s.equals("!play")) continue;
                titleWords.add(s);
            }
            String youtubeURL = Database.getYoutubeURL(titleWords);
            VoiceChannel channel = e.getGuild().getVoiceChannels().get(0);
            if (channel.getMembers().size() > 0)
            {

                e.getGuild().getAudioManager().openAudioConnection(channel);

                PlayerManager manager = PlayerManager.getINSTANCE();
                manager.loadAndPlay(e.getChannel(), youtubeURL, true);
                manager.getGuildMusicManager(e.getGuild()).player.setVolume(10);
            }
        }
    }

    private void sellItem(String[] message)
    {
        if (message.length == 2)
        {
            Player player = Database.getPlayerByName(e.getAuthor().getAsMention());
            if (player != null)
            {
                switch (message[1])
                {
                    case "coffee":
                        if (player.getInventory().getCoffee() > 0)
                        {
                            player.getInventory().setCoffee(player.getInventory().getCoffee() - 1);
                            player.setMoney(player.getMoney() + 5);
                            e.getChannel().sendMessage(String.format("%s you sold a coffee for $5", player.getName())).queue();
                        } else
                            e.getChannel().sendMessage(String.format("%s you don't have this product.", player.getName())).queue();
                        break;
                    case "beer":
                        if (player.getInventory().getBeers() > 0)
                        {
                            player.getInventory().setBeers(player.getInventory().getBeers() - 1);
                            player.setMoney(player.getMoney() + 10);
                            e.getChannel().sendMessage(String.format("%s you sold a beer for $10", player.getName())).queue();
                        } else
                            e.getChannel().sendMessage(String.format("%s you don't have this product.", player.getName())).queue();
                        break;
                    case "cigarette":
                        if (player.getInventory().getCigarettes() > 0)
                        {
                            player.getInventory().setCigarettes(player.getInventory().getCigarettes() - 1);
                            player.setMoney(player.getMoney() + 6);
                            e.getChannel().sendMessage(String.format("%s you sold a cigarette for $6", player.getName())).queue();
                        } else
                            e.getChannel().sendMessage(String.format("%s you don't have this product.", player.getName())).queue();
                        break;
                    case "banana":
                        if (player.getInventory().getBananas() > 0)
                        {
                            player.getInventory().setBananas(player.getInventory().getBananas() - 1);
                            player.setMoney(player.getMoney() + 3);
                            e.getChannel().sendMessage(String.format("%s you sold a banana for $3", player.getName())).queue();
                        } else
                            e.getChannel().sendMessage(String.format("%s you don't have this product.", player.getName())).queue();
                        break;
                    case "paper":
                        if (player.getInventory().getToiletPaper() > 0)
                        {
                            player.getInventory().setToiletPaper(player.getInventory().getToiletPaper() - 1);
                            player.setMoney(player.getMoney() + 25);
                            e.getChannel().sendMessage(String.format("%s you sold a roll of toilet paper for $25", player.getName())).queue();
                        } else
                            e.getChannel().sendMessage(String.format("%s you don't have this product.", player.getName())).queue();
                        break;
                    case "gift":
                        if (player.getInventory().getGifts() > 0)
                        {
                            player.getInventory().setGifts(player.getInventory().getGifts() - 1);
                            player.setMoney(player.getMoney() + 38);
                            e.getChannel().sendMessage(String.format("%s you sold a gift for $38", player.getName())).queue();

                        } else
                            e.getChannel().sendMessage(String.format("%s you don't have this product.", player.getName())).queue();
                        break;
                }
                Database.savePlayersToFile();
            }
        }
    }

    private void openGift()
    {
        Player player = Database.getPlayerByName(e.getAuthor().getAsMention());
        if (player != null)
        {
            if (player.getInventory().getGifts() > 0)
            {

                int rnd = getRandomNumber(1, 100);
                if (rnd >= 50)
                {
                    int rndMoney = getRandomNumber(1, 10);

                    switch (rndMoney)
                    {
                        case 1:
                            giftMoney(100);
                            break;
                        case 2:
                            giftMoney(40);
                            break;
                        case 3:
                            giftMoney(300);
                            break;
                        case 4:
                            giftMoney(200);
                            break;
                        case 5:
                            giftMoney(20);
                            break;
                        case 6:
                            giftMoney(10);
                            break;
                        case 7:
                            giftMoney(60);
                            break;
                        case 8:
                            giftMoney(80);
                            break;
                        case 9:
                            giftMoney(150);
                            break;
                        case 10:
                            giftMoney(55);
                            break;

                    }
                } else
                {
                    int rndItem = getRandomNumber(1, 5);
                    int rndAmount = getRandomNumber(1, 3);
                    switch (rndItem)
                    {

                        case 1:
                            for (int i = 0; i < rndAmount; i++)
                                player.getInventory().addCoffee();

                            e.getChannel().sendMessage(String.format("%s opened the gift and got %d cups of coffee. :coffee:", player.getName(), rndAmount)).queue();
                            break;
                        case 2:
                            for (int i = 0; i < rndAmount; i++)
                                player.getInventory().addCigarette();

                            e.getChannel().sendMessage(String.format("%s opened the gift and got %d cigarettes. :smoking:", player.getName(), rndAmount)).queue();
                            break;
                        case 3:
                            for (int i = 0; i < rndAmount; i++)
                                player.getInventory().addBeer();

                            e.getChannel().sendMessage(String.format("%s opened the gift and got %d beers. :beer:", player.getName(), rndAmount)).queue();
                            break;
                        case 4:
                            for (int i = 0; i < rndAmount; i++)
                                player.getInventory().addBanana();

                            e.getChannel().sendMessage(String.format("%s opened the gift and got %d bananas. :banana:", player.getName(), rndAmount)).queue();
                            break;
                        case 5:
                            for (int i = 0; i < rndAmount; i++)
                                player.getInventory().addToiletPaper();

                            e.getChannel().sendMessage(String.format("%s opened the gift and got %d rolls of toilet paper. :roll_of_paper:", player.getName(), rndAmount)).queue();
                            break;
                    }
                }
                player.getInventory().setGifts(player.getInventory().getGifts() - 1);
                Database.savePlayersToFile();
            }
        }
    }

    private void giftMoney(int amount)
    {
        Player player = Database.getPlayerByName(e.getAuthor().getAsMention());
        if (player != null)
        {
            player.setMoney(player.getMoney() + amount);
            e.getChannel().sendMessage(String.format("%s opened the gift and got $%d. :money_with_wings:", player.getName(), amount)).queue();
        }
    }

    private void adminGive(String[] message)
    {
        if (message.length == 3 && e.getAuthor().getAsMention().equalsIgnoreCase("<@178585741260095489>"))
        {
            Player moneyGiver = Database.getPlayerByName(e.getAuthor().getAsMention());
            Player moneyTaker = Database.getPlayerByName(message[1]);
            if (moneyGiver != null && moneyTaker != null)
            {
                if (isNumber(message[2]))
                {

                    int amount = Integer.parseInt(message[2]);
                    moneyTaker.setMoney(moneyTaker.getMoney() + amount);
                    Database.savePlayersToFile();
                    e.getChannel().sendMessage(String.format("%s just transferred $%d to %s", moneyGiver.getName(), amount, moneyTaker.getName())).queue();

                }
            }
        }
    }

    private void giveMoney(String[] message)
    {
        if (message.length == 3)
        {
            Player moneyGiver = Database.getPlayerByName(e.getAuthor().getAsMention());
            Player moneyTaker = Database.getPlayerByName(message[1]);
            if (moneyGiver != null && moneyTaker != null)
            {
                if (isNumber(message[2]))
                {

                    int amount = Integer.parseInt(message[2]);
                    if (amount <= moneyGiver.getMoney() && amount > 0)
                    {
                        moneyGiver.setMoney(moneyGiver.getMoney() - amount);
                        moneyTaker.setMoney(moneyTaker.getMoney() + amount);
                        Database.savePlayersToFile();
                        e.getChannel().sendMessage(String.format("%s just transferred $%d to %s", moneyGiver.getName(), amount, moneyTaker.getName())).queue();
                    }
                }
            }
        }

    }

    private void shopCommand()
    {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Roger's SHOP :shopping_cart:");
        eb.setDescription("Use **!buy** [item] to buy an item!");
        eb.setColor(Color.ORANGE);
        eb.setThumbnail("https://icons.iconarchive.com/icons/custom-icon-design/pretty-office-11/512/shop-icon.png");
        eb.addField("Items", ":coffee: coffee - $10\n\n:smoking: cigarette - $12\n\n:beer: beer - $20\n\n:banana: banana - $5\n\n:roll_of_paper: paper - $50\n\n:gift: gift - $75", true);
        eb.setFooter("Made by Cosmin Ilie");
        e.getChannel().sendMessage(eb.build()).queue();
    }

    private void buyItem(String[] message)
    {
        if (message.length == 2)
        {
            Player player = Database.getPlayerByName(e.getAuthor().getAsMention());
            if (player != null)
            {
                switch (message[1])
                {
                    case "coffee":
                        if (player.getMoney() >= 10)
                        {
                            player.getInventory().addCoffee();
                            player.setMoney(player.getMoney() - 10);
                            e.getChannel().sendMessage(String.format("%s you purchased a coffee for $10", player.getName())).queue();
                        } else
                            e.getChannel().sendMessage(String.format("%s you don't have enough money to buy this.", player.getName())).queue();
                        break;
                    case "beer":
                        if (player.getMoney() >= 20)
                        {
                            player.getInventory().addBeer();
                            player.setMoney(player.getMoney() - 20);
                            e.getChannel().sendMessage(String.format("%s you purchased a beer for $20", player.getName())).queue();
                        } else
                            e.getChannel().sendMessage(String.format("%s you don't have enough money to buy this.", player.getName())).queue();
                        break;
                    case "cigarette":
                        if (player.getMoney() >= 12)
                        {
                            player.getInventory().addCigarette();
                            player.setMoney(player.getMoney() - 12);
                            e.getChannel().sendMessage(String.format("%s you purchased a cigarette for $12", player.getName())).queue();
                        } else
                            e.getChannel().sendMessage(String.format("%s you don't have enough money to buy this.", player.getName())).queue();
                        break;
                    case "banana":
                        if (player.getMoney() >= 5)
                        {
                            player.getInventory().addBanana();
                            player.setMoney(player.getMoney() - 5);
                            e.getChannel().sendMessage(String.format("%s you purchased a banana for $5", player.getName())).queue();
                        } else
                            e.getChannel().sendMessage(String.format("%s you don't have enough money to buy this.", player.getName())).queue();
                        break;
                    case "paper":
                        if (player.getMoney() >= 50)
                        {
                            player.getInventory().addToiletPaper();
                            player.setMoney(player.getMoney() - 50);
                            e.getChannel().sendMessage(String.format("%s you purchased a roll of toilet paper for $50", player.getName())).queue();
                        } else
                            e.getChannel().sendMessage(String.format("%s you don't have enough money to buy this.", player.getName())).queue();
                        break;
                    case "gift":
                        if (player.getMoney() >= 75)
                        {
                            player.getInventory().addGift();
                            player.setMoney(player.getMoney() - 75);
                            e.getChannel().sendMessage(String.format("%s you purchased a gift for $75\nTo open a gift use !opengift", player.getName())).queue();

                        } else
                            e.getChannel().sendMessage(String.format("%s you don't have enough money to buy this.", player.getName())).queue();
                        break;
                    default:
                        e.getChannel().sendMessage(String.format("%s we don't sell this at the moment!", player.getName())).queue();
                        break;
                }
                Database.savePlayersToFile();
            }
        }

    }

    private void highLowCommand(String[] message)
    {
        if (message.length == 2)
        {
            if (isNumber(message[1]))
            {

                int entryFee = Integer.parseInt(message[1]);
                Player player = Database.getPlayerByName(e.getAuthor().getAsMention());
                HighLow highLow;
                if (player == null)
                {
                    player = new Player(e.getAuthor().getAsMention(), e.getAuthor().getAvatarUrl());
                    Database.addPlayer(player);
                }
                if (!HighLowEvents.playerAlreadyPlaying(player))
                {
                    if (entryFee <= player.getMoney())
                    {
                        if (entryFee >= 5)
                        {
                            //  e.getGuild().createTextChannel("High-Low " + e.getAuthor().getName()).queue();
                            EmbedBuilder eb = new EmbedBuilder();
                            eb.setThumbnail("https://www.pinclipart.com/picdir/big/194-1949141_arrow-arrows-direction-down-download-guidance-up-down.png");
                            eb.setTitle("High-Low");
                            eb.setDescription("The aim of High-Low is to guess whether the next number is higher or lower than the current card." +
                                    " Every time you guess correctly, the pot is increased by 20% of your entry fee.\nThe numbers are from 1 to 100\nIf you guess incorrectly, you leave with nothing\n```Entry fee: $" + entryFee + "```");
                            eb.addField("Commands", "!high - the next number is higher than the current one" +
                                    "\n!low - the next number is higher than the current one" +
                                    "\n!out - cash out with the current earned amount", true);
                            eb.setFooter("Made by Cosmin Ilie");
                            e.getChannel().sendMessage(eb.build()).queue();
                            highLow = new HighLow(player, entryFee, e.getChannel());
                            HighLowEvents.addHighLowGame(highLow);
                        } else
                            e.getChannel().sendMessage(String.format("%s minimum entry fee is $5", player.getName())).queue();

                    } else
                        e.getChannel().sendMessage(String.format("%s you don't have enough money for this.", player.getName())).queue();

                } else
                    e.getChannel().sendMessage(String.format("%s you are already playing High-Low", player.getName())).queue();
            }
        }

    }

    private void slotsCommand(String[] message)
    {

    }

    private void russianRouletteCommand(String[] message)
    {
        if (RussianRouletteEvents.getRussianRoulette() == null)
        {
            int entryFee = 0;

            if (message.length == 2)
            {
                if (isNumber(message[1]))
                {
                    entryFee = Integer.parseInt(message[1]);
                    if (entryFee <= 0)
                        return;
                } else
                {
                    if ((message[1].equalsIgnoreCase("leaders") || message[1].equalsIgnoreCase("leader")))
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

    private void showHelpPanel()
    {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Roger commands");
        eb.setColor(Color.CYAN);
        ///  eb.setThumbnail("");
        eb.addField("Help", "----------" +
                "\n**!profile** [user] - check a user's complete profile" +
                "\n----------" +
                "\n**!shop** - check the shop" +
                "\n**!buy** [item] - buy the specified item" +
                "\n----------" +
                "\n**!rnd** [min] [max] - get a random number between min and max" +
                "\n**!rnd** map - gives you a random map from Phasmophobia" +
                "\n**!rnd** item - gives you a random item from Phasmophobia" +
                "\n----------" +
                "\n**!roll** - roll the dice" +
                "\n----------" +
                "\n**!remind** [time] [message] - get a reminder in the specified time" +
                "\n----------" +
                "\n**!joke** - Roger will tell you a joke" +
                "\n----------" +
                "\n**!corona** [country] - get the corona cases for the specified country" +
                "\n----------" +
                "\n**!highlow** [entryFee] - start a game of High-Low" +
                "\n**!roulette** [entryFee] - start a game of russian roulette" +
                "\n----------", true);
        eb.setFooter("Made by Cosmin Ilie");
        e.getChannel().sendMessage(eb.build()).queue();
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
            if (p.getRussianStats().getTotalGamesWon() > nr1.getRussianStats().getTotalGamesWon())
                nr1 = p;
        }
        for (Player p : players)
        {
            if (p.getRussianStats().getTotalGamesWon() < nr3.getRussianStats().getTotalGamesWon())
                nr3 = p;
        }
        for (Player p : players)
        {
            if (p.getRussianStats().getTotalGamesWon() > nr2.getRussianStats().getTotalGamesWon())
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
            eb.addField(String.format("#%d ", count), String.format("%s - Games won: **%d**\nRank: **%s**\nMoney: **$%d**", p.getName(), p.getRussianStats().getTotalGamesWon(), p.getRussianStats().getPlayerRank().toString(), p.getMoney()), false);
            count++;
        }
        eb.setFooter("Made by Cosmin Ilie");
        e.getChannel().sendMessage(eb.build()).queue();
    }

    private void showPlayerProfile(String[] message)
    {
        Player player = null;
        if (message.length == 2)
            player = Database.getPlayerByName(message[1]);
        else if (message.length == 1)
            player = Database.getPlayerByName(e.getAuthor().getAsMention());

        if (player != null)
        {

            EmbedBuilder eb = new EmbedBuilder();
            eb.setThumbnail(player.getAvatarURL());
            eb.setDescription(String.format("Player %s\nMoney: **$%d**", player.getName(), player.getMoney()));
            eb.setColor(Color.BLUE);
            eb.addField("Russian Roulette", String.format("Rank: **%s**\nTotal games played: %d\nGames won: %d\nGames lost: %d\nWin percentage: %d%%\nMoney won: %d\nMoney lost: %d\nSurvived shots: %d\nWeapon malfunctions: %d\nChambers mixed: %d",
                    player.getRussianStats().getPlayerRank().toString(),
                    player.getRussianStats().getTotalGames(),
                    player.getRussianStats().getTotalGamesWon(),
                    player.getRussianStats().getTotalGamesLost(),
                    player.getRussianStats().getWinPercentage(),
                    player.getRussianStats().getTotalMoneyWon(),
                    player.getRussianStats().getTotalMoneyLost(),
                    player.getRussianStats().getTotalSurvivedShots(),
                    player.getRussianStats().getWeaponMalfunctions(),
                    player.getRussianStats().getChambersMixed()), true);

            eb.addField("High-Low", String.format("Best streak: %d\nTotal games played: %d\nCorrect guesses: %d\nWrong guesses: %d\nMoney won: %d\nMoney lost: %d",
                    player.getHighLowStats().getBestStreak(),
                    player.getHighLowStats().getTotalGamesPlayed(),
                    player.getHighLowStats().getCorrectGuesses(),
                    player.getHighLowStats().getWrongGuesses(),
                    player.getHighLowStats().getMoneyWon(),
                    player.getHighLowStats().getMoneyLost()
            ), true);
            eb.addField("Inventory", String.format(":coffee: - **%d**\n:beer: - **%d**\n:smoking: - **%d**\n:banana: - **%d**\n:roll_of_paper: - **%d**\n:gift: - **%d**",
                    player.getInventory().getCoffee(),
                    player.getInventory().getBeers(),
                    player.getInventory().getCigarettes(),
                    player.getInventory().getBananas(),
                    player.getInventory().getToiletPaper(),
                    player.getInventory().getGifts()
            ), false);

            eb.setFooter("Made by Cosmin Ilie");
            e.getChannel().sendMessage(eb.build()).queue();
        }
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
        if (message.length == 3 && !message[1].equalsIgnoreCase("item"))
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
        } else if (message.length >= 2)
        {
            if (message[1].equalsIgnoreCase("map"))
            {
                int rnd = getRandomNumber(0, phasmoMaps.length - 1);
                e.getChannel().sendMessage(String.format("%s %s", e.getAuthor().getAsMention(), phasmoMaps[rnd])).queue();
            } else if (message[1].equalsIgnoreCase("item"))
            {
                if (message.length == 3)
                {
                    if (isNumber(message[2]))
                    {
                        int nr = Integer.parseInt(message[2]);
                        if (nr > 1)
                        {

                            for (int i = 0; i < nr; i++)
                            {
                                int rnd = getRandomNumber(0, phasmoItems.length - 1);
                                e.getChannel().sendMessage(String.format("%s %s", e.getAuthor().getAsMention(), phasmoItems[rnd])).queue();
                            }
                        }
                    }
                } else
                {
                    int rnd = getRandomNumber(0, phasmoItems.length - 1);
                    e.getChannel().sendMessage(String.format("%s %s", e.getAuthor().getAsMention(), phasmoItems[rnd])).queue();
                }
            }
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
