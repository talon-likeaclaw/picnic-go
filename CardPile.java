/*
 * 2024 - Jave II: 210
 * Author: Talon Dunbar
 * Student ID: 2131651
 * Date: 05-12-2024
 */

import java.util.Random;

public class CardPile {
  // CardPile Fields
  private Card[] cards;
  private int next; // Next available position in dynamic array
  private Random rng;
  private final int MAX_SIZE;

  // CardPile Constructor
  public CardPile() {
    this.MAX_SIZE = 1000;
    this.next = 0;
    this.rng = new Random();
    this.cards = new Card[MAX_SIZE];
  }

  // CardPile toString()
  public String toString() {
    String builder = "";
    for (int i = 0; i < this.next; i++) {
      builder += (i + 1) + " " + this.cards[i] + "\n";
    }
    builder += "\u001b[0m";
    return builder;
  }

  // CardPile Custom Methods
  /*
   * length Method:
   * This method takes no input parameters and returns and integer.
   * The integer that is returns represents the length of the CardPile.
   */
  public int length() {
    return this.next;
  }

  /*
   * addCard Method:
   * This method takes a Card object as input and returns nothing.
   * The method adds a Card object to the end of the CardPile.
   */
  public void addCard(Card inputCard) {
    this.cards[this.next] = inputCard;
    this.next++;
  }

  /*
   * removeCardAt Method:
   * This method takes an integer as input and returns a Card object.
   * The method removes the Card at the chosen index and returns it.
   * When the card is removed from the CardPile, all of the cards to the right
   * of the specified index shift to the left.
   */
  public Card removeCardAt(int index) {
    if (index >= 0 & index < this.next) {
      Card chosenCard = this.cards[index];
      for (int i = index; i < this.next; i++) {
        this.cards[i] = this.cards[i + 1];
      }
      this.next--;
      return chosenCard;
    } else {
      throw new IllegalArgumentException("\nInvalid index while removing card.");
    }
  }

  /*
   * setCardAt Method:
   * This method takes a Card object and an integer as input and returns nothing.
   * The method sets the Card at the specified index to the input Card. (Replacing it).
   */
  public void setCardAt(Card card, int index) {
    if (index >= 0 && index < this.next) {
      this.cards[index] = card;
    } else {
      throw new IllegalArgumentException("\nIndex out of bounds.");
    }
  }

  /*
   * insertCardAt Method:
   * This method takes a Card object and an integer as input and returns nothing.
   * The method inserts the input Card at the input index and shifts all of the
   * Cards to the right from the index where the Card was inserted.
   */
  public void insertCardAt(Card card, int index) {
    if (index >= 0 && index < this.next) {
      for (int i = this.next; i > index; i--) {
        this.cards[i] = this.cards[i - 1];
      }
      this.cards[index] = card;
      this.next++;
    } else {
      throw new IllegalArgumentException("\nIndex out of bounds.");
    }
  }

  /*
   * getCardAt Method:
   * This method takes an integer as input and returns a Card object.
   * The method returns the Card object at the specified index.
   */
  public Card getCardAt(int index) {
    if (index >= 0 && index < this.next) {
      return this.cards[index];
    } else {
      throw new IllegalArgumentException("\nIndex out of bounds.");
    }
  }

  /*
   * cardPileContains Method:
   * This method takes a Card as input and returns a boolean.
   * The method loops through the array and checks to see if the input
   * Card is found at any index, once the first Card is found it returns true.
   * Otherwise, it returns false.
   */
  public boolean cardPileContains(Card card) {
    for (int i = 0; i < this.next; i++) {
      if (this.cards[i].getName().equals(card.getName())) {
        return true;
      }
    }
    return false;
  }

  /*
   * drawTopCard Method:
   * This method takes no input parameters and returns a Card.
   * It "draws" the top Card from the deck and returns it.
   */
  public Card drawTopCard() {
    Card drawCard = this.cards[this.next - 1];
    this.next--;
    return drawCard;
  }

  /*
   * shuffle Method:
   * This method takes no parameters and returns nothing.
   * It shuffles the CardPile by looping through each index of the CardPile
   * and then chooses a random index for each and swaps the card from the looping
   * index with the random index using a container Card.
   */
  public void shuffle() {
    for (int i = 0; i < this.next; i++) {
      int random = rng.nextInt(this.next);
      Card storage = this.cards[random];
      this.cards[random] = this.cards[i];
      this.cards[i] = storage;
    }
  }

  /*
   * swapCards Method:
   * This method takes as input two integers and returns nothing.
   * The method swaps the Card objects in the CardPile using the input
   * index numbers for each Card.
   */
  public void swapCards(int firstIndex, int secondIndex) {
    Card storage = this.cards[firstIndex];
    this.cards[firstIndex] = this.cards[secondIndex];
    this.cards[secondIndex] = storage;
  }
}
