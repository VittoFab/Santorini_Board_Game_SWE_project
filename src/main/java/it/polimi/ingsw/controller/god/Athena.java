package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.controller.UnableToBuildException;
import it.polimi.ingsw.controller.UnableToMoveException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class Athena extends God {

    public String description = "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";


    public Athena(GodController godController) {
        super(godController);
    }


    @Override
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException {
        move(worker);
        win(worker);
        build(worker);
        cannotMoveUpRestriction(worker);
    }

    /**
     * Forbids other players to move up if worker has moved up this turn.
     *
     * @param worker Worker selected to act in the turn.
     */
    private void cannotMoveUpRestriction(Worker worker) {


        for (Player p : worker.getPlayer().getGame().getPlayers()) {

            //if worker moved up, other workers can't move up
            if (worker.getLevelVariation() > 0 && p != worker.getPlayer()) {
                p.setPermissionToMoveUp(false);
            } else
                p.setPermissionToMoveUp(true);
        }

    }


    public GodController getGodController() {
        return godController;
    }

    public String getDescription() {
        return description;
    }

}
