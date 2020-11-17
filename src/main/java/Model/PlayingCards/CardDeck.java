package Model.PlayingCards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CardDeck
{
    private Card[] deck = new Card[52];

    public CardDeck()
    {
        initDeck();
    /*    for (Card c : deck)
            System.out.println(c.toString());

        System.out.println("--BREAKK--");
        shuffleDeck();
        for (Card c : deck)
            System.out.println(c.toString());*/


    }

    private void initDeck()
    {

        int count = 0;
        for (int i = 0; i < 13; i++)
        {
            for (CardSuit suit : CardSuit.values())
            {
                deck[count] = new Card(CardSign.values()[i], suit);
                count++;
            }
        }
    }

    private void shuffleDeck()
    {

        Card[] tempDeck = new Card[52];
        List<Card> tempList = new ArrayList<>(Arrays.asList(deck));
        for (int i = 0; i < 52; i++)
        {
            int rnd = getRandomNumber(0, tempList.size() - 1);
            tempDeck[i] = tempList.get(rnd);
            tempList.remove(rnd);
        }
        deck = tempDeck;
    }

    private int getRandomNumber(int min, int max)
    {
        return new Random().nextInt((max + 1) - min) + min;
    }
}
