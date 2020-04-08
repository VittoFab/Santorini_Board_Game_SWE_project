package it.polimi.ingsw.controller.god;

import it.polimi.ingsw.controller.GodController;
import it.polimi.ingsw.controller.UnableToBuildException;
import it.polimi.ingsw.controller.UnableToMoveException;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class Hera extends God{

    public String description = "An opponent cannot win by moving into a perimeter space.";


    public Hera(GodController godController){
        super(godController);
    }


    @Override
    public void evolveTurn(Worker worker) throws UnableToMoveException, UnableToBuildException {
        move(worker);
        win(worker);
        build(worker);
        cannotWinInPerimeterRestriction(worker);
    }

    private void cannotWinInPerimeterRestriction(Worker worker) {
        for(Player p : worker.getPlayer().getGame().getPlayers()) {

            if (p != worker.getPlayer()) {
                p.setPermissionToWinInPerimeter(false);
            }
        }
    }


    public GodController getGodController() {
        return godController;
    }
}

