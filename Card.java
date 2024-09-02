/*
 * 2024 - Jave II: 210
 * Author: Talon Dunbar
 * Student ID: 2131651
 * Date: 05-12-2024
 */

public class Card {
  // Card Fields
  private CardType type;

  // Card Constructor
  public Card(CardType type) {
    this.type = type;
  }

  // Card Getters
  public CardType getType() {
    return this.type;
  }

  public String getName() {
    return this.type.getName();
  }

  public int getAmountInDeck() {
    return this.type.getAmountInDeck();
  }

  // Card toString()
  public String toString() {
    return this.type.getName();
  }
}
