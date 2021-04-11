package controller;

import command.CommandValidator;
import model.LogEntryBuffer;
import model.gameelements.Player;
import model.state.Edit;
import model.state.End;
import model.state.play.LoadMap;

import java.io.File;
import java.util.Scanner;

/**
 *To implement the Tournament game Mode.
 */
public class TournamentController extends MainPlayController {
    /**
     *  list of map files that players are used.
     */
    private File listOfMapFiles;
    /**
     * list of players strategies
     */
    private Player listofplayerstrategies;
    /**
     *  for how many game that players played
     */
    private int numberOfGames;
    /**
     * set the max number of turns for each game.
     */
    private int maxNumberOfTurns;

    private MainPlayController myGame = new MainPlayController();

    /**
     * game logger
     */
    private LogEntryBuffer d_LogEntryBuffer = new LogEntryBuffer();

    @Override
    public void Start(){
        d_LogEntryBuffer.start();
        CommandValidator.setGameData(d_GameData);
        do {
            Scanner l_Scanner = new Scanner(System.in);
            System.out.println("Welcome to tournament mode game! ");
            System.out.println("Do you want to edit map or play game? (Edit/Play/Exit)");
            System.out.println("( Edit for edit map / Play for play the game / Exit for exit the game )");

            mystart = l_Scanner.nextLine();
            mycommand = "";

            switch (mystart.toLowerCase()) {
                case "edit":
                    // Set the state to Preload
                    setPhase(new Edit(this));
                    break;
                case "play":
                    // Set the state to PlaySetup
                    setPhase(new LoadMap(this));
                    break;
                case "exit":
                    System.out.println("Exiting Warzone Game see you next time!");
                    return;
                default:
                    continue;
            }
            do {
                System.out.println(" =====================================================");
                System.out.println("| #   PHASE                      : command           |");
                System.out.println(" =====================================================");
                System.out.println("| 1.  Edit                       : editmap           |");
                System.out.println("| 2.  Edit                       : savemap           |");
                System.out.println("| 3.  Play except for LoadMap    : showmap           |");
                System.out.println("| 4.  Play:Startup:LoadMap       : loadmap           |");
                System.out.println("| 5.  Play:Startup:AddPlayer     : addPlayer         |");
                System.out.println("| 6.  Play:Startup:AssignCountry : assign            |");
                System.out.println("| 8.  Play:MainPlay:IssueOrder   : issue             |");
                System.out.println("| 9.  Play:MainPlay:ExecuteOrder : execute           |");
                System.out.println("| 10. Any                        : end               |");
                System.out.println(" =====================================================");
                System.out.println("enter a " + gamePhase.getClass().getSimpleName() + " phase command: ");
                mycommand = l_Scanner.nextLine();
                System.out.println(" =====================================================");
                //
                // Calls the method corresponding to the action that the user has selected.reflection
                // Depending on what it the current state object, these method calls will
                // have a different implementation.
                //
                switch (mycommand) {
                    case "editmap":
                        gamePhase.editMap();
                        break;
                    case "savemap":
                        gamePhase.saveMap();
                        break;
                    case "showmap":
                        gamePhase.showMap();
                        break;
                    case "loadmap":
                        gamePhase.loadMap();
                        break;
                    case "addplayer":
                        gamePhase.setPlayers();
                        break;
                    case "assign":
                        gamePhase.assignCountries();
                        break;
                    case "issue":
                        gamePhase.issueOrder();
                        break;
                    case "execute":
                        gamePhase.executeOrder();
                        break;
                    case "end":
                        gamePhase.endGame();
                        break;
                    default:
                        System.out.println("this command does not exist");
                }
            } while (!(gamePhase instanceof End));
            System.out.println("\n");
        } while (!mycommand.equals("end"));
    }

}
