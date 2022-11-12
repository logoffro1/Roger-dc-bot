package Model.PlayingCards;

public class Card
{
    private CardSign cardSign;
    private CardSuit cardSuit;
    private String emojiCode;

    public Card(CardSign cardSign, CardSuit cardSuit,String emojiCode)
    {
        this.cardSign = cardSign;
        this.cardSuit = cardSuit;
        this.emojiCode = emojiCode;
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
