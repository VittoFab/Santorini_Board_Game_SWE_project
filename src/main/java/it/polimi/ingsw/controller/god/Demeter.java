package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.controller.UnableToBuildException;
import it.polimi.ingsw.controller.UnableToMoveException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.WorkerBuildMap;


public class Demeter extends God {

    public String description = "Your Worker may build one additional time, but not on the same space.";

    Cell firstBuildCell;

    public Demeter (GodController godController) {
        super(godController);
        firstBuildCell = null;
    }

    @Override
    public void evolveTurn(Worker w) throws UnableToBuildException, UnableToMoveException {
        move(w);
        win(w);
        firstBuildCell = buildCell(w);
        buildAgain(w);
    }


    public Cell buildCell(Worker worker) throws UnableToBuildException {
        WorkerBuildMap buildMap = updateBuildMap(worker);
        Board board = worker.getPlayer().getGame().getBoard();

        while (true) {
            //returns build position + type: block/dome
            int[] buildInput = godController.getBuildingInput();

            int xBuild = buildInput[0];
            int yBuild = buildInput[1];
            int buildType = buildInput[2]; //0 is block, 1 is dome

            Cell buildPosition = board.findCell(xBuild, yBuild);

            //build Dome
            if (buildType == 1) {

                if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() == 3) {
                    worker.buildDome(xBuild, yBuild);
                    return buildPosition;
                } else {
                    godController.errorBuildDomeScreen();
                }

            } else if (buildType == 0) {    //build Block
                if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() < 3) {
                    worker.buildBlock(xBuild, yBuild);
                    return buildPosition;
                } else {
                    godController.errorBuildBlockScreen();
                }
            } else
                godController.errorBuildScreen();

        }
    }


    private void buildAgain(Worker worker) {
        WorkerBuildMap buildMap;

        if (godController.getBuildAgain(this))
            return;

        while (true) {

            try {
                buildMap = updateBuildMap(worker);
            } catch (UnableToBuildException ex) {
                godController.errorBuildScreen();
                return;
            }

            Board board = worker.getPlayer().getGame().getBoard();


            int[] buildInput = godController.getBuildingInput();  //returns build position + type: block/dome
            int xBuild = buildInput[0];
            int yBuild = buildInput[1];
            int buildType = buildInput[2]; //0 is block, 1 is dome

            Cell buildPosition = board.findCell(xBuild, yBuild);

            if (buildPosition != firstBuildCell) {

                //build Dome
                if (buildType == 1) {

                    if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() == 3) {
                        worker.buildDome(xBuild, yBuild);
                        return;
                    } else
                        godController.errorBuildDomeScreen();


                } else if (buildType == 0) {    //build Block
                    if (buildMap.isAllowedToBuildBoard(xBuild, yBuild) && buildPosition.getLevel() < 3) {
                        worker.buildBlock(xBuild, yBuild);
                        return;
                    } else
                        godController.errorBuildBlockScreen();

                }
            } else
                godController.errorBuildInSamePosition();

            // Asks again to the player if he still wants to build again:
            // if not the method ends, otherwise the player decides to try to build another time.
            if (!godController.errorBuildDecisionScreen())
                return;

        }
    }


    public GodController getGodController(){
        return godController;
    }


    public String getDescription() {
        return description;
    }

}