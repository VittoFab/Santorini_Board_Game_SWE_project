package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.Player;
import javafx.application.Platform;
import org.junit.*;
import it.polimi.ingsw.server.ViewClient;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class GameControllerTest {

    private GameController gameController;

    @Mock
    private ViewClient client;

    @Mock
    private ViewClient client2;

    @Mock
    private Player player1;

    @Mock
    private Player player2;


    @Before
    public void setUp() {
        gameController = new GameController();
        client = mock(ViewClient.class);
        client2 = mock(ViewClient.class);

    }


    @After
    public void tearDown() {
        gameController = null;
        client = null;
    }


    @Test
    public void setUpGame() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);

        assertNotNull(gameController.getGodController());
        assertNotNull(gameController.getGame());
        assertNotNull(gameController.getTurnHandler());
        assertEquals(gameController.getGodsDeck().size(), 14);
    }


    @Test
    public void create() {
        when(client.askNumberOfPlayers()).thenReturn(2);

        when(client.askPlayerNickname()).thenReturn("Nick1");
        when(client.askPlayerColor()).thenReturn("BEIGE");

        doNothing().when(client).setPlayer(any(Player.class));
        doNothing().when(client).createGame();

        player1 = mock(Player.class);
        when(client.getPlayer()).thenReturn(player1);
        when(player1.getClient()).thenReturn(client);
        when(player1.getColor()).thenReturn(Color.BEIGE);
        when(player1.getNickname()).thenReturn("Nick1");

        gameController.create(client);

        verify(client, times(1)).createGame();
    }


    @Test
    public void join() {
        when(client.askNumberOfPlayers()).thenReturn(4);

        when(client.askPlayerNickname()).thenReturn("Nick1");
        when(client.askPlayerColor()).thenReturn("BEIGE");

        doNothing().when(client).setPlayer(any(Player.class));
        doNothing().when(client).createGame();

        player1 = mock(Player.class);
        when(client.getPlayer()).thenReturn(player1);
        when(player1.getClient()).thenReturn(client);
        when(player1.getColor()).thenReturn(Color.BEIGE);
        when(player1.getNickname()).thenReturn("Nick1");

        when(client2.askPlayerNickname()).thenReturn("Nick2");
        when(client2.askPlayerColor()).thenReturn("BLUE");

        player2 = mock(Player.class);
        when(client2.getPlayer()).thenReturn(player2);
        when(player2.getClient()).thenReturn(client2);
        when(player2.getColor()).thenReturn(Color.BLUE);
        when(player2.getNickname()).thenReturn("Nick2");

        ViewClient client3 = mock(ViewClient.class);
        when(client3.askPlayerNickname()).thenReturn("Nick3");
        when(client3.askPlayerColor()).thenReturn("WHITE");

        Player player3 = mock(Player.class);
        when(client3.getPlayer()).thenReturn(player3);
        when(player3.getClient()).thenReturn(client3);
        when(player3.getColor()).thenReturn(Color.WHITE);
        when(player3.getNickname()).thenReturn("Nick3");

        gameController.create(client);

        gameController.addPlayer(client2);

        gameController.join(client3);

        //assertEquals(gameController.getGameClients().size(), 1);
        assertFalse(gameController.isFull());
    }


    @Test
    public void addPlayer() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);

        doNothing().when(client).connected();
        doNothing().when(client).beginningView();

        when(client.askPlayerNickname()).thenReturn("", "Nick1");
        when(client.askPlayerColor()).thenReturn("BLACK", "BEIGE");

        doNothing().when(client).setPlayer(any(Player.class));

        Player player = new Player(gameController.getGame(), "Nick1", client);
        when(client.getPlayer()).thenReturn(player);

        doNothing().when(client).notAvailableNickname();
        doNothing().when(client).notAvailableColor();

        gameController.addPlayer(client);

        verify(client, times(1)).connected();
        verify(client, times(1)).beginningView();
        verify(client, times(1)).setPlayer(any(Player.class));
        verify(client, times(1)).notAvailableNickname();
        verify(client, times(1)).notAvailableColor();

    }


    @Test
    public void winGame() {
        when(client.askNumberOfPlayers()).thenReturn(3);
        gameController.setUpGame(client);

        client2 = mock(ViewClient.class);

        Player player1 = new Player(gameController.getGame(), "Nick1", client);
        Player player2 = new Player(gameController.getGame(), "Nick2", client2);

        when(client.winningView()).thenReturn(true);
        doNothing().when(client).killClient();
        doNothing().when(client2).killClient();
        when(client2.losingView(player1.getNickname())).thenReturn(true);


        //Setting the configuration for 2 players of the game and adding them to the game
        when(client.askPlayerNickname()).thenReturn("Nick1");
        when(client.askPlayerColor()).thenReturn("BEIGE");
        when(client.getPlayer()).thenReturn(player1);
        doNothing().when(client).setPlayer(any(Player.class));
        doNothing().when(client).connected();
        doNothing().when(client).beginningView();
        doNothing().when(client).setPlayer(any(Player.class));
        when(client.winningView()).thenReturn(true);

        when(client2.askPlayerNickname()).thenReturn("Nick2");
        when(client2.askPlayerColor()).thenReturn("WHITE");
        when(client2.getPlayer()).thenReturn(player2);
        doNothing().when(client2).setPlayer(any(Player.class));
        doNothing().when(client2).connected();
        doNothing().when(client2).beginningView();
        doNothing().when(client2).setPlayer(any(Player.class));
        when(client2.losingView(anyString())).thenReturn(true);

        gameController.addPlayer(client);
        gameController.addPlayer(client2);

        gameController.winGame(player1);

        verify(client, times(2)).killClient();
        assertFalse(gameController.getTurnHandler().getGameAlive());
    }


    @Test
    public void getGodsDeck() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);
        assertNotNull(gameController.getGodsDeck());
    }


    @Test
    public void getGame() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);
        assertNotNull(gameController.getGame());
    }


    @Test
    public void getGodController() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);
        assertNotNull(gameController.getGodController());

    }


    @Test
    public void getTurnHandler() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);
        assertNotNull(gameController.getTurnHandler());
    }


    @Test
    public void getExecutorPlayerAdder() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);
        assertNotNull(gameController.getExecutorPlayerAdder());
    }


    @Test
    public void removeClientObserver() {
        //it's the same as when a player is added to the game, but then
        //the test on the method allows to see that also the other
        //things have been done correctly.

        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);

        when(client.askPlayerNickname()).thenReturn("Nick1");
        when(client.askPlayerColor()).thenReturn("BEIGE");
        doNothing().when(client).setPlayer(any(Player.class));

        Player player = new Player(gameController.getGame(), "Nick1", client);
        when(client.getPlayer()).thenReturn(player);
        gameController.addPlayer(client);

        assertFalse(gameController.getGame().getBoard().findCell(0, 0).getCellObservers().isEmpty());

        gameController.removeClientObserver(client);

        assertTrue(gameController.getGame().getBoard().findCell(0, 0).getCellObservers().isEmpty());
    }


    @Test
    public void handleGameDisconnection() {

        //preliminary settings to add players to the game

        when(client.askNumberOfPlayers()).thenReturn(3);
        gameController.setUpGame(client);

        when(client.askPlayerNickname()).thenReturn("Nick1");
        when(client.askPlayerColor()).thenReturn("BEIGE");
        when(client2.askPlayerNickname()).thenReturn("Nick2");
        when(client2.askPlayerColor()).thenReturn("BLUE");

        doNothing().when(client).setPlayer(any(Player.class));
        doNothing().when(client2).setPlayer(any(Player.class));

        Player player = new Player(gameController.getGame(), "Nick1", client);
        Player player2 = new Player(gameController.getGame(), "Nick2", client2);
        when(client.getPlayer()).thenReturn(player);
        when(client2.getPlayer()).thenReturn(player2);

        gameController.addPlayer(client);
        gameController.addPlayer(client2);
        //end of preliminary settings

        when(client.isInGame()).thenReturn(true);
        when(client2.isInGame()).thenReturn(false);

        doNothing().when(client).killClient();
        doNothing().when(client2).killClient();

        gameController.handleGameDisconnection("Nick2");

        verify(client, times(1)).killClient();
        verify(client, times(1)).notifyOtherPlayerDisconnection(player2.getNickname());
    }


    @Test
    public void notifyPlayersOfLoss() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);

        when(client.askPlayerNickname()).thenReturn("Nick1");
        when(client.askPlayerColor()).thenReturn("BEIGE");

        doNothing().when(client).setPlayer(any(Player.class));

        Player player = new Player(gameController.getGame(), "Nick1", client);

        when(client.getPlayer()).thenReturn(player);

        gameController.addPlayer(client);

        doNothing().when(client).notifyPlayersOfLoss(anyString());

        gameController.notifyPlayersOfLoss("Nick2");

    }


    @Test
    public void isFull() {
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);

        when(client.askPlayerNickname()).thenReturn("Nick1");
        when(client.askPlayerColor()).thenReturn("BEIGE");
        Player player = new Player(gameController.getGame(), "Nick1", client);
        when(client.getPlayer()).thenReturn(player);


        gameController.addPlayer(client);

        assertFalse(gameController.isFull());
    }


    @Test
    public void setFull(){
        when(client.askNumberOfPlayers()).thenReturn(2);
        gameController.setUpGame(client);

        when(client.askPlayerNickname()).thenReturn("Nick1");
        when(client.askPlayerColor()).thenReturn("BEIGE");
        Player player = new Player(gameController.getGame(), "Nick1", client);
        when(client.getPlayer()).thenReturn(player);


        gameController.addPlayer(client);

        assertFalse(gameController.isFull());

        gameController.setFull(true);

        assertTrue(gameController.isFull());
    }


    @Test
    public void getGameClients() {
        assertTrue(gameController.getGameClients().isEmpty());
    }
}