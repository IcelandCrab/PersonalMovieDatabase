package com.vienna.pmd.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    Button searchButton;

    @FXML
    private void fireSearch(ActionEvent event) {
        System.out.println("Hello");

    }
}
