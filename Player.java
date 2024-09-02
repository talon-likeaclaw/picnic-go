/*
 * 2024 - Jave II: 210
 * Author: Talon Dunbar
 * Student ID: 2131651
 * Date: 05-12-2024
 */

public class Player {
  // Player Fields
  private String name;
  private int points;
  private CardPile movingHand;
  private CardPile setHand;
  private boolean forkUsed;
  private int cupcakeCounter;

  // Player Constructor
  public Player(String name) {
    if (name.length() == 0) {
      throw new IllegalArgumentException("Name cannot be empty!");
    } else {
      this.name = name;
    }
    this.points = 0;
    this.movingHand = new CardPile();
    this.setHand = new CardPile();
    this.cupcakeCounter = 0;
  }

  // Player Getters
  public String getName() {
    return this.name;
  }

  public int getPoints() {
    return this.points;
  }

  public CardPile getMovingHand() {
    return this.movingHand;
  }

  public CardPile getSetHand() {
    return this.setHand;
  }

  public boolean getForkUsed() {
    return this.forkUsed;
  }

  public int getCupcakeCounter() {
    return this.cupcakeCounter;
  }

  // Player Setters
  public void setMovingHand(CardPile movingHand) {
    this.movingHand = movingHand;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public void setForkUsed(boolean forkUsed) {
    this.forkUsed = forkUsed;
  }

  // Player Custom Methods
  /*
   * drawMovingHand Method:
   * This method takes as input a Card object and returns nothing.
   * The method adds the input Card to their moving hand.
   */
  public void drawMovingHand(Card card) {
    this.movingHand.addCard(card);
  }

  /*
   * chooseCard Method:
   * This method takes as input an int and returns nothing.
   * The method chooses a card from the specified index of their moving hand
   * and moves the card into their set hand.
   */
  public void chooseCard(int chosenCard) {
    this.setHand.addCard(this.movingHand.removeCardAt(chosenCard));
  }

  /*
   * addPoints Method:
   * This method takes as input an int and returns nothing.
   * The method adds the input points to the player's points field.
   */
  public void addPoints(int points) {
    this.points += points;
  }

  /*
   * removePoints Method:
   * This method takes as input an int and returns nothing.
   * The method removes the amount of points input from
   * the player's points field.
   */
  public void removePoints(int points) {
    this.points -= points;
  }

  /*
   * addCupcakes Method:
   * This method takes an int as input and returns nothing.
   * The method adds the amount of cupcakes input into the
   * player's cupcakeCounter filed.
   */
  public void addCupcakes(int cupcakes) {
    this.cupcakeCounter += cupcakes;
  }

  /*
   * beginTurn Method:
   * The beginTurn helper method takes input of type Player and returns nothing.
   * It erforms the initialization of the turn for the player that was input.
   * This ensures that the player is ready to start their turn and that the other
   * players a not looking at the screen before starting their turn.
   */
  public void beginTurn() {
    // Skip turn because player has no cards left to play and someone else does
    if (this.movingHand.length() == 0) {
      System.out.println(
          "\n" + this.name + " you have no cards to play, so it the next player's turn!");
    } else {
      System.out.println("It is " + this.name + "'s turn:");
      boolean isSuccess = false;
      while (!isSuccess) {
        try {
          int userInput = GameManager.confirmBeginTurn();
          if (userInput == 1) {
            isSuccess = true;
            performTurn();
          }
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

  /*
   * performTurn Method:
   * The performTurn helper method takes input of type Player, that is passed
   * through from the beginTurn method. It returns nothing. The method clears
   * the screen, prints the rules for the cards for the player to reference, then
   * prints their moving and set hands. It then asks the user to choose the card
   * they want to pick from their moving hand to move into their set hand.
   * If the player has a fork in their hand they can pick two cards.
   */
  private void performTurn() {
    // Initialize turn
    GameManager.clearScreen();
    int playerHandLength = this.movingHand.length();
    System.out.println("Starting " + this.name + "'s turn!\n");
    GameManager.displayCardValues();
    printMovingHand();
    printSetHand();
    // Check if player has fork and more than two cards and perform fork turn instead
    Card fork = new Card(CardType.FORK);
    if (this.setHand.cardPileContains(fork) && !this.forkUsed && playerHandLength >= 2) {
      performForkTurn();
    } else {
      // Perform turn by chosing a card from moving hand
      boolean isSuccess = false;
      while (!isSuccess) {
        try {
          int movingHandLength = this.movingHand.length();
          int chosenCard = GameManager.askCardChoice(movingHandLength);
          chooseCard(chosenCard);
          isSuccess = true;
          sortSetHand();
          GameManager.clearScreen();
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

  /*
   * performForkTurn Method:
   * This method takes no input parameters and returns nothing.
   * The method performs a player's turn if they have a fork in their hand
   * and they have no already used one fork, and they have enough cards in their
   * moving hand to use their fork.
   */
  private void performForkTurn() {
    boolean isSuccess = false;
    while (!isSuccess) {
      try {
        int movingHandLength = this.movingHand.length();
        int chosenCard = GameManager.askForkCardChoice(movingHandLength);
        if (chosenCard == -1) {
          this.forkUsed = true;
          useFork();
          isSuccess = true;
          sortSetHand();
        } else {
          chooseCard(chosenCard);
          GameManager.clearScreen();
          isSuccess = true;
          sortSetHand();
        }
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /*
   * useFork Method:
   * The useFork Method helper takes input of type Player, which is passed through
   * from the performTurn method. The method enables the player to draw two cards
   * within their turn. Only onefork may be used within a round.
   */
  private void useFork() {
    // Choose first card
    boolean firstSuccess = false;
    while (!firstSuccess) {
      try {
        int movingHandLength = this.movingHand.length();
        int chosenCard = GameManager.askCardChoice(movingHandLength);
        chooseCard(chosenCard);
        firstSuccess = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    printMovingHand();
    printSetHand();
    // Choose second card
    boolean secondSuccess = false;
    while (!secondSuccess) {
      try {
        int movingHandLength = this.movingHand.length();
        int chosenCard = GameManager.askCardChoice(movingHandLength);
        chooseCard(chosenCard);
        secondSuccess = true;
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    GameManager.clearScreen();
  }

  /*
   * printMovingHand Method:
   * This helper method takes as input type Player and does not return anything.
   * The method prints out the moving hand of the player that was input as a parameter.
   */
  private void printMovingHand() {
    System.out.println("\n" + this.name + "'s Moving Hand:");
    System.out.println(this.movingHand);
  }

  /*
   * printSetHand()
   * This helper method takes as input type Player and does not return anything.
   * It uses the input player to to print their set ahdn with this name.
   */
  public void printSetHand() {
    System.out.println(this.name + "'s Set Hand:");
    System.out.println(this.setHand);
  }

  /*
   * sortSetHand Method:
   * This method takes no input parameters and does not return anything
   * The method loops through the players set hand and uses the Card Type enum
   * numerical order field in order to perform a selection sort on the players
   * set hand, to enable them to make informed decisions during their turn.
   */
  public void sortSetHand() {
    // Variables
    int comparingNum = 0;
    int smallestNum = 0;
    // Logic
    for (int i = 0; i < this.setHand.length(); i++) {
      int smallestIndex = i;
      // If inner loop j index order value is smaller that the i index order value, replace i with j
      for (int j = i + 1; j < this.setHand.length(); j++) {
        comparingNum = this.setHand.getCardAt(j).getType().getNumericalOrder();
        smallestNum = this.setHand.getCardAt(smallestIndex).getType().getNumericalOrder();
        if (comparingNum < smallestNum) {
          smallestIndex = j;
        }
      }
      this.setHand.swapCards(i, smallestIndex);
    }
  }

  /*
   * calculatePoints Method:
   * This helper method is called at the end of a round and provides the points for each player.
   * It takes as input type Player and returns their points as an int value.
   */
  public void calculatePlayerPoints() {
    calculateSandwiches();
    calculateChips();
    calculateDevilledEggs();
    calculateFriedChicken();
    calculatePizza();
    calculateCupcakes();
  }

  /*
   * calculateSandwiches Method:
   * This method takes a player as input and does not return anything.
   * The method fills an int array with a count of each type of sandwich
   * and checks to see if there are mayonnaise and calculates the players points.
   */
  private void calculateSandwiches() {
    // Variables
    int[] sandwichCounter = new int[4];
    int chickenSandwichValue = CardType.CHICKEN_SANDWICH.getValue();
    int porkSandwichValue = CardType.PORK_SANDWICH.getValue();
    int beefSandwichValue = CardType.BEEF_SANDWICH.getValue();
    String chickenSandwich = CardType.CHICKEN_SANDWICH.getName();
    String porkSandwich = CardType.PORK_SANDWICH.getName();
    String beefSandwich = CardType.BEEF_SANDWICH.getName();
    String mayonnaise = CardType.MAYONNAISE.getName();
    // Logic
    for (int i = 0; i < this.setHand.length(); i++) {
      Card testCard = this.setHand.getCardAt(i);
      if (testCard.getName().equals(chickenSandwich)) {
        sandwichCounter[0]++; // Chicken Sandwich increment
      }
      if (testCard.getName().equals(porkSandwich)) {
        sandwichCounter[1]++; // Pork Sandwich increment
      }
      if (testCard.getName().equals(beefSandwich)) {
        sandwichCounter[2]++; // Beef Sandwich increment
      }
      if (testCard.getName().equals(mayonnaise)) {
        sandwichCounter[3]++; // Mayonnaise increment
      }
    }
    int chickenPoints = sandwichCounter[0] * chickenSandwichValue;
    int porkPoints = sandwichCounter[1] * porkSandwichValue;
    int beefPoints = sandwichCounter[2] * beefSandwichValue;
    if (sandwichCounter[3] > 0) {
      calculateMayo(sandwichCounter);
    } else {
      addPoints(chickenPoints + porkPoints + beefPoints);
    }
  }

  /*
   * calculateMayo Method:
   * This method takes as input a player and an int array of sandwich and mayo counts.
   * It loops through each mayo card and applys it's point tripling effect to the players
   * sandwiched from best sandwich to worst.
   */
  private void calculateMayo(int[] sandwichCounter) {
    // Variables
    int beefSandwichValue = CardType.BEEF_SANDWICH.getValue();
    int porkSandwichValue = CardType.PORK_SANDWICH.getValue();
    int chickenSandwichValue = CardType.CHICKEN_SANDWICH.getValue();
    // Logic
    for (int i = sandwichCounter[3]; i > 0; i--) {
      if (sandwichCounter[2] > 0) {
        addPoints(beefSandwichValue * 3);
        sandwichCounter[2]--; // Beef Sandwich decrement
      } else if (sandwichCounter[1] > 0) {
        addPoints(porkSandwichValue * 3);
        sandwichCounter[1]--; // Pork Sandwich decrement
      } else if (sandwichCounter[0] > 0) {
        addPoints(chickenSandwichValue * 3);
        sandwichCounter[0]--; // Chicken Sandwich decrement
      }
    }
  }

  /*
   * calculateChips Method:
   * This helper method takes a player as input and returns nothing
   * It determines how many potato chips the input player has in their set hand
   * and calculates how many points to give to the player and awards them
   */
  private void calculateChips() {
    // Variables
    int[] chipCounter = new int[3];
    int oneChipValue = CardType.ONE_POTATO_CHIP.getValue();
    int twoChipsValue = CardType.TWO_POTATO_CHIPS.getValue();
    int threeChipsValue = CardType.THREE_POTATO_CHIPS.getValue();
    String onePotatoChip = CardType.ONE_POTATO_CHIP.getName();
    String twoPotatoChips = CardType.TWO_POTATO_CHIPS.getName();
    String threePotatoChips = CardType.THREE_POTATO_CHIPS.getName();
    // Logic
    for (int i = 0; i < this.setHand.length(); i++) {
      Card testCard = this.setHand.getCardAt(i);
      if (testCard.getName().equals(onePotatoChip)) {
        chipCounter[0]++; // One Chip increment
      }
      if (testCard.getName().equals(twoPotatoChips)) {
        chipCounter[1]++; // Two Chips increment
      }
      if (testCard.getName().equals(threePotatoChips)) {
        chipCounter[2]++; // Three Chips increment
      }
    }
    int oneChipPoints = chipCounter[0] * oneChipValue;
    int twoChipPoints = chipCounter[1] * twoChipsValue;
    int threeChipPoints = chipCounter[2] * threeChipsValue;
    int totalChips = oneChipPoints + twoChipPoints + threeChipPoints;
    addPoints(totalChips);
  }

  /*
   * calculateDevilledEggs Method:
   * This helper method takes a player as input and returns nothing
   * It determines how many devilled eggs are within the input players set hand
   * and calculates how many pairs they have to apply the points to the player
   */
  private void calculateDevilledEggs() {
    // Variables
    int devilledEggCounter = 0;
    int devilledEggValue = CardType.DEVILLED_EGGS.getValue();
    String devilledEgg = CardType.DEVILLED_EGGS.getName();
    // Logic
    for (int i = 0; i < this.setHand.length(); i++) {
      Card testCard = this.setHand.getCardAt(i);
      if (testCard.getName().equals(devilledEgg)) {
        devilledEggCounter++;
      }
    }
    int devilledEggPairs = devilledEggCounter / 2;
    int totalPoints = devilledEggPairs * devilledEggValue;
    addPoints(totalPoints);
  }

  /*
   * calculateFriedChicken Method:
   * This helper method takes a player as input and returns nothing
   * It determines how many fried chicken cards are in the input players set hand
   * and calculates how many triplets they have to apply the points to the player
   */
  private void calculateFriedChicken() {
    // Variables
    int friedChickenCounter = 0;
    int friedChickenValue = CardType.FRIED_CHICKEN.getValue();
    String friedChicken = CardType.FRIED_CHICKEN.getName();
    // Logic
    for (int i = 0; i < this.setHand.length(); i++) {
      Card testCard = this.setHand.getCardAt(i);
      if (testCard.getName().equals(friedChicken)) {
        friedChickenCounter++;
      }
    }
    int friedChickenTriplets = friedChickenCounter / 3;
    int totalPoints = friedChickenTriplets * friedChickenValue;
    addPoints(totalPoints);
  }

  /*
   * calculatePizza Method:
   * This helper method takes a player as input and does not return anything
   * It determines how many pizza cards the input player has in their set hand
   * and calculates how many points the player will get and applys the points
   */
  private void calculatePizza() {
    // Variables
    int pizzaCounter = 0;
    String pizza = CardType.PIZZA.getName();
    // Logic
    for (int i = 0; i < this.setHand.length(); i++) {
      Card testCard = this.setHand.getCardAt(i);
      if (testCard.getName().equals(pizza)) {
        pizzaCounter++;
      }
    }
    if (pizzaCounter == 1) {
      addPoints(1);
    } else if (pizzaCounter == 2) {
      addPoints(3);
    } else if (pizzaCounter == 3) {
      addPoints(6);
    } else if (pizzaCounter == 4) {
      addPoints(10);
    } else if (pizzaCounter >= 5) {
      addPoints(15);
    }
  }

  /*
   * This helper method takes a player as input and returns nothing
   * It calculates how many cupcakes the input player has in their set hand
   * and adds it to their cupcake counter field
   */
  private void calculateCupcakes() {
    // Variables
    int cupcakeCounter = 0;
    String cupcake = CardType.CUPCAKE.getName();
    // Logic
    for (int i = 0; i < this.setHand.length(); i++) {
      Card testCard = this.setHand.getCardAt(i);
      if (testCard.getName().equals(cupcake)) {
        cupcakeCounter++;
      }
    }
    addCupcakes(cupcakeCounter);
  }
}
