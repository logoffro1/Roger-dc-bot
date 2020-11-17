package Model.PlayingCards;

public class Card
{
    private CardSign cardSign;
    private CardSuit cardSuit;

    public Card(CardSign cardSign, CardSuit cardSuit)
    {
        this.cardSign = cardSign;
        this.cardSuit = cardSuit;
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("Card{");
        sb.append("cardSign=").append(cardSign);
        sb.append(", cardSuit=").append(cardSuit);
        sb.append('}');
        return sb.toString();
    }
}
