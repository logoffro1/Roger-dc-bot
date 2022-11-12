package Model.PlayingCards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CardDeck
{
    private List<Card> deck = new ArrayList<>();

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

    public Card takeCard()
    {
        Card card = deck.get(deck.size()-1);
        deck.remove(card);
        return card;
    }

    private void initDeck()
    {
        deck.clear();
        for (int i = 0; i < 13; i++)
        {
            for (CardSuit suit : CardSuit.values())
            {
                deck.add(new Card(CardSign.values()[i], suit,""));
            }
        }
    }

    private void shuffleDeck()
    {

        Card[] tempDeck = new Card[52];
        List<Card> tempList = deck;
        for (int i = 0; i < 52; i++)
        {
            int rnd = getRandomNumber(0, tempList.size() - 1);
            tempDeck[i] = tempList.get(rnd);
            tempList.remove(rnd);
        }
        deck = Arrays.asList(tempDeck);
    }

    private int getRandomNumber(int min, int max)
    {
        return new Random().nextInt((max + 1) - min) + min;
    }
}
