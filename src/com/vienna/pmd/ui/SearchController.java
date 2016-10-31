package com.vienna.pmd.ui;

import com.google.inject.Inject;
import com.vienna.pmd.omdb.*;
import com.vienna.pmd.omdb.impl.IDSearchRequest;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.Collections;

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

    @FXML
    ImageView searchDetailImage;

    ObservableList<Result> resultItems = null;

    @Inject
    private ISearchService searchService;

    @FXML
    public void initialize() {
        // spalten für tabelle initialisieren
        yearColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("year"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("title"));
        titleColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                TableCell<Result, String> cell = new TableCell<Result, String>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(cell.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            }

        });
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
    private void searchResultTableClicked() {
        Result selectedItem = searchResultTable.getSelectionModel().getSelectedItem();
        String imdbId = selectedItem.getImdbID();

        IIDSearchRequest request = new IDSearchRequest(imdbId, ResponseType.XML);

        try {
            Root response = searchService.search(request);
            searchDetailImage.setImage(new Image(response.getMovies().get(0).getPoster()));
        } catch(SearchException e) {
            ExceptionDialog dialog = new ExceptionDialog(Alert.AlertType.ERROR);
            dialog.setTitle("Fehler bei Suche");
            dialog.setHeaderText("Fehler bei Suche");
            dialog.setContentText("Bei der Suche ist ein Fehler aufgetreten. Bitte melden sie sich beim Administrator ;)");
            dialog.setException(e);

            dialog.showAndWait();
        }
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
