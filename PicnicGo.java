/*
 * Welcome to Picnic Go!
 * 2024 - Jave II: 210
 * Author: Talon Dunbar
 * Student ID: 2131651
 * Date: 05-12-2024
 */

public class PicnicGo {
  public static void main(String[] args) {
    GameManager.greetPlayers(); // Greet the players
    int playerCount = GameManager.getNumOfPlayers(); // Ask the players how many people will be playing
    GameManager picnicGo = new GameManager(playerCount); // Use the playerCount to initialize the GameManager.
    picnicGo.runGame(); // Start the game
  }
}
