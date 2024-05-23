/*
 * 2024 - Jave II: 210
 * Author: Talon Dunbar
 * Student ID: 2131651
 * Date: 05-12-2024
 */

public enum CardType {
  // Card Types
  CHICKEN_SANDWICH("\u001B[32mChicken Sandwich\u001B[37m", 1, 5, 1),
  PORK_SANDWICH("\u001B[32mPork Sandwich\u001B[37m", 2, 10, 2),
  BEEF_SANDWICH("\u001B[32mBeef Sandwich\u001B[37m", 3, 5, 3),
  MAYONNAISE("\u001B[36mMayonnaise\u001B[37m", 0, 6, 4),
  ONE_POTATO_CHIP("\u001B[33mOne Potato Chip\u001B[37m", 1, 6, 5),
  TWO_POTATO_CHIPS("\u001B[33mTwo Potato Chips\u001B[37m", 2, 12, 6),
  THREE_POTATO_CHIPS("\u001B[33mThree Potato Chips\u001B[37m", 3, 8, 7),
  DEVILLED_EGGS("\u001B[0mDevilled Eggs\u001B[37m", 5, 14, 8),
  FRIED_CHICKEN("\u001B[34mFried Chicken\u001B[37m", 10, 14, 9),
  PIZZA("\u001B[31mPizza\u001B[37m", 0, 14, 10),
  CUPCAKE("\u001B[35mCupcake\u001B[37m", 0, 10, 11),
  FORK("\u001B[0mFork\u001B[37m", 0, 4, 12);

  // CardType Fields
  private String prettyName;
  private int cardValue;
  private int amountInDeck;
  private int numericalOrder;

  // CardType Constructor
  CardType(String name, int value, int amountInDeck, int numericalOrder) {
    this.prettyName = name;
    this.cardValue = value;
    this.amountInDeck = amountInDeck;
    this.numericalOrder = numericalOrder;
  }

  // CardType Getters
  public String getName() {
    return this.prettyName;
  }

  public int getValue() {
    return this.cardValue;
  }

  public int getAmountInDeck() {
    return this.amountInDeck;
  }

  public int getNumericalOrder() {
    return this.numericalOrder;
  }
}
