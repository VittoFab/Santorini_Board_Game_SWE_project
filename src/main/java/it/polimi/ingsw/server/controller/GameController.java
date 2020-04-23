package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.ClientView;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.controller.god.*;


import java.util.ArrayList;

/**
 * Handles the input given by the view and sends them to the model to change the Game status
 */
public class GameController {

    private Game game;
    private TurnHandler turnHandler;
    private GodController godController;
    private final ArrayList<God> godsDeck;

    public GameController() {
        game = null;
        turnHandler = null;
        //viewSelector = new ViewSelector();
        godsDeck = new ArrayList<>(14);
    }


    /**
     * Sets up game and starts the logic flow.
     */
    public synchronized void setUpGame(ClientView firstClient) {

        /*
        String viewType = viewSelector.askTypeofView();

        if (viewType.toUpperCase().equals("CLI"))

        else if(viewType.toUpperCase().equals("GUI"))
            view = new GUIMainView(this);
        else
            viewSelector.genericError();
        */

        godController = new GodController(this);
        createDeckGods();

        firstClient.beginningView();

        int numOfPlayers = firstClient.askNumberOfPlayers();

        game = new Game(numOfPlayers);

        turnHandler = new TurnHandler(game, this);

    }


    public void addPlayer(ClientView client) {
        setUpObserverView(client);

        setPlayerNickname(client);
        setPlayerColor(client);


    }




    //todo alberto
    private void setUpObserverView(ClientView client) {

        for (int i = 0; i < Board.SIDE; i++) {
            for (int j = 0; j < Board.SIDE; j++) {

                game.getBoard().findCell(i, j).register(client);
            }
        }

    }


    private void setPlayerNickname(ClientView client) {

        while (true) {

            String chosenNickname = client.askPlayerNickname();

            if (nicknameIsAvailable(chosenNickname) && chosenNickname.length() > 0) {
                Player newPlayer = game.addPlayer(chosenNickname, client);
                client.setPlayer(newPlayer);
                return;
            }

            client.notAvailableNickname();
        }
    }


    public void setPlayerColor(ClientView client) {

        boolean colorCorrectlyChosen = false;

        while (!colorCorrectlyChosen) {

            String chosenColor = client.askPlayerColor(client.getPlayer().getNickname());

            if (colorIsAvailable(chosenColor) && colorIsValid(chosenColor)) {
                client.getPlayer().setColor(Color.StringToColor(chosenColor));
                colorCorrectlyChosen = true;
            } else
                client.notAvailableColor();

        }

    }


    private boolean colorIsValid(String chosenColor) {
        return chosenColor.equals(Color.BLUE.name())
                || chosenColor.equals(Color.BEIGE.name())
                || chosenColor.equals(Color.WHITE.name());
    }

    private boolean nicknameIsAvailable(String chosenNickname) {

        for (Player player : game.getPlayers()) {

            if (chosenNickname.equals(player.getNickname()))
                return false;
        }

        return true;
    }

    private boolean colorIsAvailable(String chosenColor) {

        for (Player player : game.getPlayers()) {

            if (player.getColor() != null
                    && chosenColor.equals(player.getColor().toString()))
                return false;
        }

        return true;
    }


    public ArrayList<God> getGodsDeck() {
        return godsDeck;
    }

    /**
     * Creates the deck where we can find the God cards of the game.
     */
    private void createDeckGods() {
        godsDeck.add(new Apollo(godController));
        godsDeck.add(new Artemis(godController));
        godsDeck.add(new Athena(godController));
        godsDeck.add(new Atlas(godController));
        godsDeck.add(new Charon(godController));
        godsDeck.add(new Demeter(godController));
        godsDeck.add(new Hephaestus(godController));
        godsDeck.add(new Hera(godController));
        godsDeck.add(new Hestia(godController));
        godsDeck.add(new Minotaur(godController));
        godsDeck.add(new Pan(godController));
        godsDeck.add(new Prometheus(godController));
        godsDeck.add(new Triton(godController));
        godsDeck.add(new Zeus(godController));
    }

    public Game getGame() {
        return game;
    }

    public GodController getGodController() {
        return godController;
    }

    public TurnHandler getTurnHandler() {
        return turnHandler;
    }

}

