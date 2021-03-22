package model.state.play;

import controller.GameEngineController;
import controller.MainPlayController;
import model.GameData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LoadMap extends Startup {

    public LoadMap(MainPlayController p_ml) {
        super(p_ml);
    }

    public void loadMap() {

        try {
            d_ml.d_MapFile = new File(d_ml.d_GameEngineController.getMapFilePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        d_ml.d_GameData = new GameData(d_ml.d_MapFile);
        d_ml.d_GameData.setCurrentPhase(this);
        d_ml.d_GameEngineController = new GameEngineController(d_ml.d_GameData);
        try {
            d_ml.d_GameData.loadMap();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        d_ml.getDLogEntryBuffer().updateFile();

        next();
    }

    @Override
    public void showMap() {
        printInvalidCommandMessage();
    }

    @Override
    public void next() {
        d_ml.setPhase(new AddPlayer(d_ml));
    }

    @Override
    public void previous() {
        super.previous();
    }
}