package controller;

import model.GameData;
import model.map.MapEdit;
import model.gameelements.Country;
import model.gameelements.Player;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import gameplay.*;

/**
 * The Main game loop controls program to execute each of the game phase.
 */
public class MainLoopController {
    /**
     * get Game data as an object, used to be the input parameter for GameEngineController class
     */
    public GameData d_GameData;
    /**
     * get game engine as an object that used to call the function from GameEngineController class
     */
    public GameEngineController d_GameEngine;
    /**
     * model.map file that use to load represent game model.map.
     */
    public File d_MapFile;

    /**
     * Constructor
     * the main game loop that to control game phases.
     *public MainLoopController(String p_FilePath) {
     *         this.d_MapFile = new File(p_FilePath);
     *         this.d_GameData = new GameData(d_MapFile);
     *         this.d_GameEngine = new GameEngineController(d_GameData);
     *     }
     * @param p_FilePath the model.map file path that use to load game model.map.
     */
    public MainLoopController(String p_FilePath) {
        this.d_MapFile = new File(p_FilePath);
        this.d_GameData = new GameData(d_MapFile);
        this.d_GameEngine = new GameEngineController(d_GameData);
    }

    /**
     * call function from GameEngineController class, and according to the game rule to execute the game.
     */
    public void mainGamePhaseLoop() {
        Scanner l_scanner = new Scanner(System.in);

        boolean l_isTrue = true;
        while (l_isTrue) {
            System.out.println("do you want to add or remove player (Number of player limit is 2 to 5)? (y/n) ");
            String l_askUser = l_scanner.nextLine().trim();

            if (l_askUser.equalsIgnoreCase("y")) {
                if (d_GameData.getPlayerList().size() < 5) {
                    d_GameEngine.gamePlayerCommand();
                }
                // since the number of player range is 2 to 5. no more player can be add in.
                else if (d_GameData.getPlayerList().size() >= 5) {
                    System.out.println("number of player out of limit ");
                    continue;
                }
            } else if (l_askUser.equalsIgnoreCase("n")) {
                // since the number of player range is 2 to 5. no more player can be remove in.
                if (d_GameData.getPlayerList().size() < 2) {
                    System.out.println("number of player is not enough, please add more ");
                } else {
                    System.out.println("All player have already set ");
                    l_isTrue = false;
                }
            } else {
                System.out.println("Invalid command, please try again (y/n): ");
            }
        }

        // randomly assign countries for each player
        d_GameEngine.assignCountries();

        //start up the game, according to the game rules to start game engine, and determine if any players are eliminated at the end of each round.
        int l_CurrentReinforcement = 5;
        while (d_GameData.getCurrentPhase() != GamePhase.END_OF_GAME) {
            int l_TempReinforcementArmy;

            // Assign Reinforcement phase, Call the method in gameplay to allocate the number of ReinforcementArmies in each round to each player
            this.d_GameEngine.d_GameData.setCurrentPhase(GamePhase.REINFORCEMENT);
            System.out.println(d_GameData.getCurrentPhase());

            for (Player l_Player : this.d_GameEngine.d_GameData.getPlayerList()) {
                l_CurrentReinforcement += d_GameEngine.getReinforcementBonus(l_Player);
                l_Player.setReinforcementArmies(l_CurrentReinforcement);
                System.out.println(l_CurrentReinforcement + " Reinforcement Armies are assigned to " + " Player [" + l_Player.getColour() + "]  ");

                for (Map.Entry<String, Country> l_CountryEntry : l_Player.getCountriesInControl().entrySet()) {
                    System.out.println("Controlling countries: " + l_CountryEntry.getKey());
                }
            }

            System.out.println("---------REINFORCEMENT PHASE COMPLETE-----------\n");

            // Issue order phase,Loop through all players, until all players finish issuing the instructions, and save the order in player`s order list.
            for (Player l_Player : this.d_GameEngine.d_GameData.getPlayerList()) {
                this.d_GameEngine.d_GameData.setCurrentPhase(GamePhase.ISSUE_ORDER);
                System.out.println(this.d_GameEngine.d_GameData.getCurrentPhase());

                l_TempReinforcementArmy = l_Player.getReinforcementArmies();
                while (l_TempReinforcementArmy > 0) {
                    System.out.println("==== Now Player [" + l_Player.getColour() + "]'s turn to issue order ====");
                    System.out.println(" Player [" + l_Player.getColour() + "] have " + l_TempReinforcementArmy
                            + " Reinforcement Armies.");
                    l_Player.issueOrder();

                    //Reduce the corresponding amount of reinforcements after deploying.
                    l_TempReinforcementArmy -= l_Player.getLastOrderFromQueue().getOrderInfo().getNumberOfArmy();
                }
            }
            //execute orders phase,  execute player`s order, assigning a number of armies to move towards the target country.
            this.d_GameEngine.phaseProcess();
            //game phase turning to END OF GAME.
            d_GameEngine.d_GameData.setCurrentPhase(GamePhase.END_OF_GAME);
            System.out.println(d_GameEngine.d_GameData.getCurrentPhase());
        }
        l_scanner.close();
    }

    /**
     * According to user input to check in which model that user are going to get in.
     *
     * @throws IOException if files are not found
     */
    public void MainLogic() throws Exception {
        Scanner l_Scanner = new Scanner(System.in);

        int l_CheckState = 1;
        while (l_CheckState != 0) {
            System.out.println("Welcome to warzone! ");
            System.out.println("Do you want to edit model.map or play game (Edit/Play/Exit)");
            System.out.println("( Edit for edit model.map / Play for play the game / Exit for exit the game )");
            String l_GameOptionCommand = l_Scanner.nextLine();
            // input edit to get into model.map editing model.
            if (l_GameOptionCommand.equalsIgnoreCase("Edit")) {
                MapEdit.mapEditLoop();
            }

            // input edit to get into model.map edit model playing game model.
            else if (l_GameOptionCommand.equalsIgnoreCase("Play")) {
                // mainloop for game play
                d_GameData.setCurrentPhase(GamePhase.STARTUP);
                this.d_MapFile = new File(d_GameEngine.getMapFilePath());
                d_GameData = new GameData(d_MapFile);
                d_GameData.setCurrentPhase(GamePhase.STARTUP);
                d_GameEngine = new GameEngineController(d_GameData);
                d_GameData.loadMap();
                mainGamePhaseLoop();
                System.out.println("------The End of Game------ ");
                break;
            }
            // input exit to close the game.
            else if (l_GameOptionCommand.equalsIgnoreCase("Exit")) {
                l_CheckState = 0;
                System.out.println("Exiting Warzone Game see you next time!");
            }
            // Error handling for user input
            else
                System.out.println("Invalid input, try again !");
        }
        l_Scanner.close();
    }

    /**
     * main method, Show each game phase from GameEngineController, and run the game according to the game rules
     *
     * @param args To get parameters from console
     * @throws IOException if file does not exist
     */

    /**
    public static void main(String[] args) throws Exception {
        //filename initial
        String file = "domination/test_02.map";
        MainLoopController mainLoop = new MainLoopController(file);
        mainLoop.MainLogic();
    }
     **/

}