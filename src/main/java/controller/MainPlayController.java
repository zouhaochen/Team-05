package controller;


import command.CommandValidator;
import model.GameData;
import model.LogEntryBuffer;
import model.Observable;
import model.state.Edit;
import model.state.End;
import model.state.Phase;
import model.state.play.LoadMap;

import java.io.File;
import java.util.Scanner;

/**
 * Context of the State pattern.
 * It contains a State object.
 */
public class MainPlayController extends Observable {
    /**
     * State object of the MainLoop
     */
    public Phase gamePhase;

    String mystart;
    String mycommand;
    /**
     * model.map file that use to load represent game model.map.
     */
    public File d_MapFile = new File(".//domination//test_02.map");
    /**
     * get Game data as an object, used to be the input parameter for GameEngineController class
     */
    public GameData d_GameData = new GameData(d_MapFile);
    /**
     * get game engine as an object that used to call the function from GameEngineController class
     */
    public GameEngineController d_GameEngineController = new GameEngineController(d_GameData);

    /**
     * game logger
     */
    private LogEntryBuffer d_LogEntryBuffer = new LogEntryBuffer();


    /**
     * Method that allows the GameEngine object to change its state.
     *
     * @param p_phase new state to be set for the GameEngine object.
     */
    public void setPhase(Phase p_phase) {
        gamePhase = p_phase;
        d_GameData.setCurrentPhase(p_phase);
        System.out.println("Current game phase: " + p_phase.getClass().getSimpleName());
    }

    /**
     * LogEntryBuffer obj getter
     *
     * @return LogEntryBuffer obj
     */
    public LogEntryBuffer getDLogEntryBuffer() {
        return d_LogEntryBuffer;
    }


    /**
     * According to user input to check in which model that user are going to get in.
     */
    public void Start() {
        d_LogEntryBuffer.start();
        CommandValidator.setGameData(d_GameData);
        do {
            Scanner l_Scanner = new Scanner(System.in);
            System.out.println("Welcome to warzone! ");
            System.out.println("Do you want to edit map or play game? (Edit/Play/Exit)");
            System.out.println("( Edit for edit map / Play for play the game / Exit for exit the game )");

            mystart = l_Scanner.nextLine();

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
            }
            do {
                System.out.println(" =====================================================");
                System.out.println("| #   PHASE                      : command           |");
                System.out.println(" =====================================================");
                System.out.println("| 1.  Edit                       : editmap           |");
                System.out.println("| 2.  Edit                       : savemap           |");
                System.out.println("| 3.  Play except for LoadMap    : showmap           |");
                System.out.println("| 4.  Play:Startup:LoadMap       : loadmap           |");
                System.out.println("| 5.  Play:Startup:AddPlayer     : addPlayers        |");
                System.out.println("| 6.  Play:MainPlay:IssueOrder   : issueorders       |");
                System.out.println("| 7.  Play:MainPlay:advance      : advance           |");
                System.out.println("| 8.  Play:MainPlay:cards        : cards             |");
                System.out.println("| 9. Any                        : end                |");
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
                    case "issueorders":
                        gamePhase.issueOrder();
                        break;
                    case "advance":
                        gamePhase.advance();
                        break;
                    case "cards":
                        gamePhase.cards();
                        break;
                    case "end":
                        gamePhase.endGame();
                        break;
                    default:
                        System.out.println("this command does not exist");
                }
            } while (!(gamePhase instanceof End));


        } while (mycommand.equals("end"));

    }

}
