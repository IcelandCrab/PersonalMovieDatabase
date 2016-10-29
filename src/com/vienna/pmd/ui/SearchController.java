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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    @Inject
    private ISearchService searchService;

    @FXML
    private void fireSearch(ActionEvent event) {
        ITitleSearchRequest request = new TitleSearchRequest("Predator", null, null, ResponseType.XML);
        try {
            Root response = searchService.search(request);

            titleColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("title"));
            coverColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("poster"));
            ObservableList<Result> list = FXCollections.observableArrayList(response.getResults());
            searchResultTable.setItems(list);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Suchergebnis");
            alert.setHeaderText("Suchergebnis");
            alert.setContentText("Ergebnisse: " + response.getTotalResults());

            alert.showAndWait();
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
