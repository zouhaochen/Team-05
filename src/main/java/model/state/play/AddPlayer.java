package model.state.play;

import controller.MainPlayController;

import java.util.Scanner;

public class AddPlayer extends Startup{
    public AddPlayer(MainPlayController p_ml) {
        super(p_ml);
    }

    @Override
    public void setPlayers() {
        Scanner l_scanner = new Scanner(System.in);

        boolean l_isTrue = true;
        while (l_isTrue) {
            System.out.println("do you want to add or remove player (Number of player limit is 2 to 5)? (y/n) ");
            String l_askUser = l_scanner.nextLine().trim();

            if (l_askUser.equalsIgnoreCase("y")) {
                if (d_ml.d_GameData.getPlayerList().size() < 5) {
                    d_ml.d_GameEngineController.gamePlayerCommand();
                }
                // since the number of player range is 2 to 5. no more player can be add in.
                else if (d_ml.d_GameData.getPlayerList().size() >= 5) {
                    System.out.println("number of player out of limit ");
                    continue;
                }
            } else if (l_askUser.equalsIgnoreCase("n")) {
                // since the number of player range is 2 to 5. no more player can be remove in.
                if (d_ml.d_GameData.getPlayerList().size() < 2) {
                    System.out.println("number of player is not enough, please add more ");
                } else {
                    System.out.println("All player have already set ");
                    l_isTrue = false;
                }
            } else {
                System.out.println("Invalid command, please try again (y/n): ");
            }
        }
        d_ml.getDLogEntryBuffer().updateFile();

        next();
    }

    @Override
    public void next() {
        d_ml.setPhase(new AssignCountry(d_ml));
        d_ml.gamePhase.assignCountries();
    }

    @Override
    public void previous() {
        d_ml.setPhase(new LoadMap(d_ml));
    }
}