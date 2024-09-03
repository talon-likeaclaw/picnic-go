/*
 * 2024 - Jave II: 210
 * Author: Talon Dunbar
 * Student ID: 2131651
 * Date: 05-12-2024
 */

public class Deck {
  // Deck Fields
  private CardPile cards;

  // Deck Constructor
  public Deck() {
    this.cards = new CardPile();
    initializeDeck(initializeCardTypes());
  }

  // Deck toString()
  public String toString() {
    int deckLength = this.cards.length();
    String builder = "";
    for (int i = 0; i < deckLength; i += 3) {
      builder +=
          this.cards.getCardAt(i)
              + ", "
              + this.cards.getCardAt(i + 1)
              + ", "
              + this.cards.getCardAt(i + 2)
              + "\n";
    }
    builder += "\u001b[0m";
    return builder;
  }

  // Deck Custom Methods
  /*
   * shuffleDeck Method:
   * This methods allows us to shuffle the Deck
   * using a method defined in the CardPIle class.
   */
  public void shuffleDeck() {
    this.cards.shuffle();
  }

  /*
   * drawTopCard Method:
   * This method takes no input parameters and returns a card.
   * It returns the to top Card of the Deck using a method defined in CardPile.
   */
  public Card drawTopCard() {
    return this.cards.drawTopCard();
  }

  /*
   * initializeCardTypes Method:
   * This method takes no input parameters and returns a CardType[].
   * The array holds each CardType in that game so that we can use it
   * to initialize the deck with the CardType amountInDeck field.
   */
  private CardType[] initializeCardTypes() {
    CardType[] cardTypes;
      cardTypes = new CardType[] {
        CardType.THREE_POTATO_CHIPS, // 8 Triple Potato Chips
        CardType.TWO_POTATO_CHIPS, // 12 Dual Potato Chips
        CardType.ONE_POTATO_CHIP, // 6 Single Potato Chips
        CardType.CHICKEN_SANDWICH, // 5 Chicken Sandwiches
        CardType.PORK_SANDWICH, // 10 Pork Sandwiches
        CardType.BEEF_SANDWICH, // 5 Beef Sandwiches
        CardType.FRIED_CHICKEN, // 14 Fried Chicken
        CardType.DEVILLED_EGGS, // 14 Devilled Eggs
        CardType.MAYONNAISE, // 6 Jars of Mayonnaise
        CardType.CUPCAKE, // 10 Cupcakes
        CardType.PIZZA, // 14 Pizzas
        CardType.FORK // 4 Forks
      };
      return cardTypes;
  }

  /*
   * initializeDeck Method:
   * This method takes a CardType[] as input and does not return anything.
   * It loops through each CardType within the array of CardTypes and uses
   * it's amountInDeck field to determine how many cards to add to the Deck,
   * it then loops through each CardType putting the correct amount in the Deck.
   */
  private void initializeDeck(CardType[] cardTypes) {
      for (CardType cardType : cardTypes) {
          Card nextCard = new Card(cardType);
          int amountOfCards = nextCard.getAmountInDeck();
          for (int j = 0; j < amountOfCards; j++) {
              this.cards.addCard(nextCard);
          }
      }
  }
}
