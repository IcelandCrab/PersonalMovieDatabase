package com.vienna.pmd.ui;

import com.google.inject.Inject;
import com.vienna.pmd.omdb.ISearchService;
import com.vienna.pmd.omdb.ITitleSearchRequest;
import com.vienna.pmd.omdb.ResponseType;
import com.vienna.pmd.omdb.SearchException;
import com.vienna.pmd.omdb.impl.SearchService;
import com.vienna.pmd.omdb.impl.TitleSearchRequest;
import com.vienna.pmd.omdb.xml.Result;
import com.vienna.pmd.omdb.xml.Root;
import com.vienna.pmd.ui.javafx.ExceptionDialog;
import com.vienna.pmd.ui.javafx.PosterCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class SearchController {

    @FXML
    Button searchButton;

    @FXML
    TextField searchText;

    @FXML
    TableView<Result> searchResultTable;

    @FXML
    TableColumn coverColumn;

    @FXML
    TableColumn titleColumn;

    @FXML
    TableColumn yearColumn;

    @FXML
    TableColumn runtimeColumn;

    @FXML
    TableColumn actorsColumn;

    @FXML
    AnchorPane leftPane;

    @FXML
    VBox leftPaneBox;

    @FXML
    AnchorPane rightPane;

    @FXML
    VBox rightPaneBox;

    @FXML
    AnchorPane searchTabPane;

    @FXML
    VBox searchTabPaneBox;

    @FXML
    AnchorPane libraryTabPane;

    @FXML
    VBox libraryTabPaneBox;

    ObservableList<Result> resultItems = null;

    @Inject
    private ISearchService searchService;

    @FXML
    public void initialize() {
        /*
        searchTabPaneBox.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        // searchtab pane ausrichten
        AnchorPane.setBottomAnchor(libraryTabPaneBox, 1.0d);
        AnchorPane.setTopAnchor(libraryTabPaneBox, 1.0d);
        AnchorPane.setLeftAnchor(libraryTabPaneBox, 1.0d);
        AnchorPane.setRightAnchor(libraryTabPaneBox, 1.0d);

        // searchtab pane ausrichten
        AnchorPane.setBottomAnchor(searchTabPaneBox, 1.0d);
        AnchorPane.setTopAnchor(searchTabPaneBox, 1.0d);
        AnchorPane.setLeftAnchor(searchTabPaneBox, 1.0d);
        AnchorPane.setRightAnchor(searchTabPaneBox, 1.0d);

        // linken teil der splitpane ausrichten
        AnchorPane.setBottomAnchor(leftPaneBox, 1.0d);
        AnchorPane.setTopAnchor(leftPaneBox, 1.0d);
        AnchorPane.setLeftAnchor(leftPaneBox, 1.0d);
        AnchorPane.setRightAnchor(leftPaneBox, 1.0d);

        // rechten teil der splitpane ausrichten
        AnchorPane.setBottomAnchor(rightPaneBox, 1.0d);
        AnchorPane.setTopAnchor(rightPaneBox, 1.0d);
        AnchorPane.setLeftAnchor(rightPaneBox, 1.0d);
        AnchorPane.setRightAnchor(rightPaneBox, 1.0d);
*/
        // spalten für tabelle initialisieren
        yearColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("year"));
        runtimeColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("runtime"));
        actorsColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("actors"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("title"));
        coverColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("poster"));
        coverColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                TableCell<Result, String> cell = new PosterCell();
                return cell;
            }
        });

        // observablelist for tabelleninhalt initialisieren
        resultItems = FXCollections.observableArrayList();
        searchResultTable.setItems(resultItems);
    }

    @FXML
    private void fireSearch(ActionEvent event) {
        ITitleSearchRequest request = new TitleSearchRequest(searchText.getText(), null, null, ResponseType.XML);
        try {
            Root response = searchService.search(request);

            resultItems.clear();
            resultItems.addAll(response.getResults());
        } catch(SearchException e) {
            ExceptionDialog dialog = new ExceptionDialog(Alert.AlertType.ERROR);
            dialog.setTitle("Fehler bei Suche");
            dialog.setHeaderText("Fehler bei Suche");
            dialog.setContentText("Bei der Suche ist ein Fehler aufgetreten. Bitte melden sie sich beim Administrator ;)");
            dialog.setException(e);

            dialog.showAndWait();
        }
    }
}
