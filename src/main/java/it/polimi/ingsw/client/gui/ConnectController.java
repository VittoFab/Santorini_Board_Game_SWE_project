package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnectController implements Initializable {

    public ConnectController() {
    }

    @FXML
    private TextField IPText;

    @FXML
    private void play() {
        System.out.println("play");
    }

    @FXML
    private void connect() {
        System.out.println("connect");
        System.out.println(IPText.getCharacters());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //IPText.setFont(Font.loadFont("/fonts/negotiate-free.tff",12));
    }

}