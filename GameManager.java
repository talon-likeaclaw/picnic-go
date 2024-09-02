/*
 * 2024 - Jave II: 210
 * Author: Talon Dunbar
 * Student ID: 2131651
 * Date: 05-12-2024
 */

import java.util.Scanner;

public class GameManager {
  // GameManager Fields
  private Player[] players;
  private Deck deck;
  private CardPile discardPile;
  private Scanner reader;
  private boolean roundOver;

  // GameManager Constructor
  public GameManager(int numOfPlayers) {
    if (numOfPlayers >= 3 && numOfPlayers <= 5) {
      this.players = new Player[numOfPlayers];
      this.deck = new Deck();
      this.discardPile = new CardPile();
      this.reader = new Scanner(System.in);
      this.roundOver = false;
    } else {
      throw new IllegalArgumentException("Illegal number of players.");
    }
  }

  // GameManager Custom Methods
  /*
   * greetPlayers Method:
   * The greetPlayers method is a static method that is called within the
   * application class. The method provides the players with information about the
   * game and it's creator.
   * Java colour ANSI codes taken from:
   * https://www.geeksforgeeks.org/how-to-print-colored-text-in-java-console/
   */
  public static void greetPlayers() {
    System.out.println(
        "Hello! Welcome to \u001B[31mP\u001B[33mi\u001B[32mc\u001B[36mn\u001B[34mi\u001B[35mc"
            + " \u001B[34mG\u001B[36mo\u001B[37m!\n");
    System.out.println(
        "Picnic Go is a deck-building game where you try to make the most delicious meal from a"
            + " deck of 108 cards containing 10 picnic treats and forks!");
    System.out.println(
        "Each round, players will take turns chosing a card from their moving hand for their set"
            + " hand. Moving hands rotate after each player picks a card.");
    System.out.println(
        "At the end of each round, each player's set hand's points are calculated and then"
            + " discarded. New moving hands are automatically drawn from the deck.");
    System.out.println(
        "The game ends when three rounds have been completed. The player with the most points after"
            + " three rounds wins!");
    System.out.println("\nThis game was built in Java by Talon Dunbar.");
  }

  /*
   * getNumOfPlayers Method:
   * The getNumOfPlayers method is a static method that is called in the
   * application class after greetPlayers. It enables the players to input the
   * number of players that will be playing the game and returns it.
   */
  public static int getNumOfPlayers() {
    // Variables
    Scanner input = new Scanner(System.in);
    int numOfPlayers = 0;
    boolean isSuccess = false;
    // Logic
    while (!isSuccess) {
      try {
        System.out.println("\nChoose the number of players for your game: ");
        System.out.println("(3) Players get 9 cards each.");
        System.out.println("(4) Players get 8 cards each.");
        System.out.println("(5) Players get 7 cards each.");
        numOfPlayers = Integer.parseInt(input.nextLine());
        if (numOfPlayers == 3 || numOfPlayers == 4 || numOfPlayers == 5) {
          isSuccess = true;
          System.out.println("Perfect, lets start a game with " + numOfPlayers + " players!");
        }
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    return numOfPlayers;
  }

  /*
   * initalizePlayers Method:
   * This helper method takes no input parameters and does not return anything.
   * It asks the players to input their names in order to initalize each player in
   * the this.players array.
   */
  private void initializePlayers() {
    for (int i = 0; i < this.players.length; i++) {
      boolean isSuccess = false;
      while (!isSuccess) {
        try {
          System.out.println("\nPlayer #" + (i + 1) + " please enter your name: ");
          String playerName = this.reader.nextLine();
          this.players[i] = new Player(playerName);
          isSuccess = true;
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

  /*
   * shuffleDeck Method:
   * This method takes no input parameters nor returns anything.
   * The method prints the deck prior to shuffling and then
   * allows the users to shuffle the deck by pressing enter and then
   * prints the shuffled deck.
   */
  private void shuffleDeck() {
    System.out.println(this.deck);
    System.out.println("Here is the deck of cards all neatly organized! Press enter to shuffle:");
    this.reader.nextLine();
    clearScreen();
    this.deck.shuffleDeck();
    System.out.println(this.deck);
    System.out.println(
        "I'm going to shuffle again though, I can't just reveal the order of the cards to you..");
    System.out.println("*shuffling cards*");
    this.deck.shuffleDeck(); // Shuffle again so people with photographic memories don't have an
    // advantage
  }

  /*
   * initializeMovingHands Method:
   * This helper method takes no input parameters and does not return anything.
   * It takes the length of the this.players array in order to determine the
   * number of cards to draw into each players moving hands. Once the number of
   * cards has been determined the method draws the cards into each players moving
   * hand from the deck.
   */
  private void initializeMovingHands() {
    // Variables
    int numOfPlayers = this.players.length;
    int numOfCards = 0;
    // Logic
    if (numOfPlayers == 3) {
      numOfCards = 9;
    } else if (numOfPlayers == 4) {
      numOfCards = 8;
    } else {
      numOfCards = 7;
    }
    System.out.println("Drawing " + numOfCards + " cards into everyone's moving hands.");
    for (int i = 0; i < numOfPlayers; i++) {
      for (int j = 0; j < numOfCards; j++) {
        this.players[i].drawMovingHand(this.deck.drawTopCard());
      }
    }
  }

  /*
   * runGame Method:
   * The runGame method takes not input parameters. It initializes the players in
   * the game, shuffles the deck, draws every player's moving hand and performs
   * the main game loop for the game to function. Looping through three rounds
   * and within each round looping through each player until everyone has used
   * all of their moving hand cards.
   */
  public void runGame() {
    // Initialize game
    initializePlayers();
    clearScreen();
    shuffleDeck();
    initializeMovingHands();
    final int MAX_ROUNDS = 3;
    // Game loop
    for (int roundNumber = 1; roundNumber <= MAX_ROUNDS; roundNumber++) {
      this.roundOver = false;
      // Round loop
      while (!roundOver) {
        System.out.println("\nRound #" + roundNumber);
        for (Player player : this.players) {
          player.beginTurn();
          if (checkRoundOver()) {
            endRound();
          }
        }
        rotateMovingHand();
        displaySetHands();
      }
      if (roundNumber == MAX_ROUNDS) {
        endGame();
      } else {
        printPlayerPoints();
        initializeMovingHands();
        discardSetHands();
      }
    }
    this.reader.close();
  }

  /*
   * checkRoundOver Method:
   * The checkRoundOver helper method takes no input and returns a boolean.
   * It determines if the round is over by analyzing each player's moving hand.
   * If all of the players do not have any cards remaining then it returns true.
   */
  private boolean checkRoundOver() {
    boolean isRoundOver = true;
    for (Player player : this.players) {
      if (player.getMovingHand().length()
          > 0) { // Round not over if anyone's moving hand is not empty
        isRoundOver = false;
      }
    }
    return isRoundOver;
  }

  /*
   * endRound Method:
   * The endRound helper method takes no input parameters and does not return anything.
   * When called it will calculate all of the player's points, reset everything,
   * and start the next round by turns the roundOver boolean field to true.
   */
  private void endRound() {
    for (Player player : this.players) {
      player.calculatePlayerPoints();
      player.setForkUsed(false);
    }
    this.roundOver = true;
  }

  /*
   * disobedienceCheck Method:
   * This method takes no input parameters and returns nothing.
   * This method is an easter egg that yells at the users if they input text
   * before the end of game results are displayed.
   */
  private void disobedienceCheck() {
    int disobedienceCounter = 0;
    String userInput = "The user better not input text >:(";
    while (userInput.length() != 0) {
      if (disobedienceCounter == 0) {
        System.out.println(
            "Alright.. *drum roll* Let's see who won! Press enter to find out! Please don't type"
                + " anything.");
        userInput = reader.nextLine();
        disobedienceCounter++;
      } else if (disobedienceCounter == 1) {
        System.out.println("\nStop playing around! Just press enter! >:(");
        userInput = reader.nextLine();
        disobedienceCounter++;
      } else if (disobedienceCounter == 2) {
        System.out.println("\nThis isn't a joke, I'm trying to make this fun for us!");
        userInput = reader.nextLine();
        disobedienceCounter++;
      } else {
        System.out.println("\nFine, I'll just show the results without your confirmation!");
        System.out.println("This is why we can't have nice things...\n");
        userInput = "";
      }
    }
  }

  /*
   * endGame Method:
   * This helper method takes no parameters and returns nothing
   * It holds the end game conditions and prints the final results to the screen.
   */
  private void endGame() {
    int tieCounter = 0;
    System.out.println("We have reached the end of the game! Great job everyone!!");
    System.out.println(
        "Let's count everyone's cupcakes and see who will be awarded 6 points, and who will lose 6"
            + " points!\n");
    System.out.println("Press enter to display results:");
    reader.nextLine();
    applyCupcakePoints();
    disobedienceCheck();
    printPlayerPoints();
    Player winner = decideWinner();
    for (Player player : this.players) {
      if (player.getPoints() == winner.getPoints()) {
        tieCounter++;
      }
    }
    if (tieCounter >= 2) {
      System.out.println(
          "It's a tie! Which is an unfortunate way to end! In that case, everybody wins!!!!");
    } else {
      System.out.println(
          "\nThe winner is " + winner.getName() + " with " + winner.getPoints() + " points!");
      System.out.println("You really know how to craft a delicious picnic!\n");
      System.out.println("Better luck next time everyone else! Thank you for playing!!");
    }
  }

  /*
   * decideWinner Method:
   * This method takes no input parameters and returns a player object
   * It determines who is the winner by comparing all of the player's points!
   */
  private Player decideWinner() {
    Player winner = new Player("Winner!");
    for (Player player : this.players) {
      if (player.getPoints() > winner.getPoints()) {
        winner = player;
      }
    }
    return winner;
  }

  /*
   * cupcakeWinner Method:
   * This helper method takes no input parameters and returns a player object
   * It determines who is the winner with the most cupcakes
   */
  private Player cupcakeWinner() {
    // Variables
    Player cupcakeWinner = this.players[0];
    // Logic
    for (Player player : this.players) {
      if (player.getCupcakeCounter() > cupcakeWinner.getCupcakeCounter()) {
        cupcakeWinner = player;
      }
    }
    return cupcakeWinner;
  }

  /*
   * cupcakeLoser Method:
   * This helper method takes not input parameters and returns a player object
   * It determines who is the player with the least cupcakes
   */
  private Player cupcakeLoser() {
    // Variables
    Player cupcakeLoser = this.players[0];
    // Logic
    for (Player player : this.players) {
      if (player.getCupcakeCounter() < cupcakeLoser.getCupcakeCounter()) {
        cupcakeLoser = player;
      }
    }
    return cupcakeLoser;
  }

  /*
   * applyCupcakePoints Method:
   * This helper method takes no input parameters and does not return anything
   * It is a helper method that apples 6 points to the players with the most cupcakes
   * and remove 6 points for those with the least cupcakes.
   */
  private void applyCupcakePoints() {
    // Variables
    Player cupcakeWinner = cupcakeWinner();
    Player cupcakeLoser = cupcakeLoser();
    int winnerCupcakeAmount = cupcakeWinner.getCupcakeCounter();
    // Logic
    for (Player player : this.players) {
      if (player.getCupcakeCounter() == winnerCupcakeAmount && winnerCupcakeAmount != 0) {
        player.addPoints(6);
        System.out.println(player.getName() + " was awarded 6 points!");
      } else if (player.getCupcakeCounter() == cupcakeLoser.getCupcakeCounter()) {
        player.removePoints(6);
        ;
        System.out.println(player.getName() + " lost 6 points!");
      }
    }
    System.out.println("\n");
  }

  /*
   * printPlayerPoints Method:
   * This helper method takes no input parameters and does not return anything.
   * It loops through each player in the this.players array and prints their points and cupcakes.
   */
  private void printPlayerPoints() {
    for (Player player : this.players) {
      System.out.println(
          player.getName()
              + "'s Points: "
              + player.getPoints()
              + "  "
              + "Cupcakes: "
              + player.getCupcakeCounter()
              + "\n");
    }
  }

  /*
   * rotateMovingHand Method:
   * This helped method takes no input parameters and does not return anything.
   * It rotates each the moving hand Card Pile of each player.
   */
  private void rotateMovingHand() {
    // Varaibles
    CardPile firstPlayersHand = this.players[0].getMovingHand();
    // Logic
    for (int i = 0; i < this.players.length; i++) {
      if (i != this.players.length - 1) {
        this.players[i].setMovingHand(this.players[i + 1].getMovingHand());
      } else {
        this.players[i].setMovingHand(firstPlayersHand);
      }
    }
  }

  /*
   * discardSetHands Method:
   * This helper method takes no input parameters and does not return anything.
   * It discards each player's set hand at the end of each round by discarding
   * each of the card into the discard pile.
   */
  private void discardSetHands() {
    System.out.println("Discarding Set Hands.");
    for (Player player : this.players) {
      int setHandLength = player.getSetHand().length();
      for (int i = 0; i < setHandLength; i++) {
        this.discardPile.addCard(player.getSetHand().drawTopCard());
      }
    }
  }

  /*
   * clearScreen Method
   * This helper method takes no input parameters and does not return anything.
   * It clears the screen so that each player can maintain confidentiality when performing their turn.
   * Code taken from:
   * https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java
   */
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  /*
   * displayCardValues Method:
   * This helper method takes no input parameters and does not return anything.
   * It prints the card values in a cute format for the player to make an
   * informed decision when they are chosing their card during their turn.
   */
  public static void displayCardValues() {
    System.out.println("Card Values:");
    System.out.println("\u001B[32mSandwiches\u001B[37m:");
    System.out.println("- Chicken \u001B[32mSandwiches\u001B[37m are worth 1 point.");
    System.out.println("- Pork \u001B[32mSandwiches\u001B[37m are worth 2 points.");
    System.out.println("- Beef \u001B[32mSandwiches\u001B[37m are worth 3 points.");
    System.out.println("\u001B[36mMayonnaise\u001B[37m:");
    System.out.println(
        "- Triples your best sandwich. You can have multiple \u001B[36mmayonnaise\u001B[37m"
            + " played.");
    System.out.println(
        "- They cannot stack - only one \u001B[36mmayonnaise\u001B[37m is allowed per sandwich.");
    System.out.println("\u001B[33mPotato Chips\u001B[37m:");
    System.out.println(
        "- \u001B[33mChips\u001B[37m give 1, 2, or 3 points, according to the number shown on the"
            + " card.");
    System.out.println("\u001B[0mDevilled Eggs\u001B[37m:");
    System.out.println("- Each set of 2 is worth 5 points, otherwise, worth no points.");
    System.out.println(
        "- You can score multiple sets of \u001B[0mdevilled eggs\u001B[37m in one round.");
    System.out.println("\u001B[34mFried Chicken\u001B[37m:");
    System.out.println("- Each set of 3 is worth 10 points, otherwise, worth no points.");
    System.out.println(
        "- You can score multiple sets of \u001B[34mfried chicken\u001B[37m in one round.");
    System.out.println("\u001B[31mPizza\u001B[37m:");
    System.out.println(
        "- A set of 1, 2, 3, 4, and 5 or more is worth 1, 3, 6, 10, and 15 points, respectively.");
    System.out.println(
        "- You cannot score multiple sets of \u001B[31mpizza\u001B[37m in one round");
    System.out.println("- Any \u001B[31mpizza\u001B[37m you get past the fifth has no effect.");
    System.out.println("\u001B[35mCupcake\u001B[37m:");
    System.out.println("- Cupcakes are tallied until the end of the game.");
    System.out.println(
        "- After all 3 rounds have passed, the player(s) with the most \u001B[35mcupcakes\u001B[37m"
            + " scores 6 points.");
    System.out.println(
        "- The player(s) with the least \u001B[35mcupcakes\u001B[37m loses 6 points. If there is a"
            + " tie both players win (or lose) 6 points.");
    System.out.println("\u001B[0mFork\u001B[37m:");
    System.out.println(
        "- Scores nothing, but can be used at a later turn. You can only use one fork per round.");
    System.out.println("- On use, you're allowed to pick 2 cards for that turn.");
  }

  /*
   * displaySetHands Method:
   * This helper method takes no input parameters and does not return anything.
   * It loops through each player in this.players and prints their set hands in one message.
   */
  private void displaySetHands() {
    for (Player player : this.players) {
      player.printSetHand();
    }
  }

  /*
   * askCardChoice Method:
   * This method takes an integer as input and returns and integer.
   * The method takes the length of the player's hand and allows them to choose
   * what card they want to pick from their moving hand via an integer.
   */
  public static int askCardChoice(int movingHandLength) {
    if (movingHandLength >= 1 && movingHandLength <= 9) {
      Scanner scanner = new Scanner(System.in);
      System.out.println(
          "\nPick your card by entering a number from 1 to "
              + movingHandLength
              + " representing the card you want to draw into your set hand:");
      int chosenCard = Integer.parseInt(scanner.nextLine()) - 1;
      return chosenCard;
    } else {
      throw new IllegalArgumentException("Moving hand length must be between 1 and 9 inclusive.");
    }
  }

  /*
   * askForkCardChoice Method:
   * This method takes as input an integer and returns and integer.
   * The method takes the length of the player's hand and allows them to choose
   * what card they want to pick from their moving hand via an integer.
   * They may also pick a fork.
   */
  public static int askForkCardChoice(int movingHandLength) {
    if (movingHandLength >= 0 && movingHandLength <= 9) {
      Scanner scanner = new Scanner(System.in);
      System.out.println(
          "\n"
              + "Enter 0 if you would like to use your fork to pick two cards. You may only use one"
              + " fork per round.");
      System.out.println(
          "Or you can pick a number from 1 to "
              + movingHandLength
              + " representing the card you want to draw into your set hand:");
      int chosenCard = Integer.parseInt(scanner.nextLine()) - 1;
      return chosenCard;
    } else {
      throw new IllegalArgumentException("Moving hand length must be between 0 and 9 inclusive.");
    }
  }

  /*
   * confirmBeginTurn Method:
   * This method takes no input parameters and returns and integer.
   * The method prompts the player to enter 1 to begin their turn.
   */
  public static int confirmBeginTurn() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter 1 to begin your turn:");
    int userInput = Integer.parseInt(scanner.nextLine());
    return userInput;
  }
}
