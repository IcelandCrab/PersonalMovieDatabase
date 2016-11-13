package com.vienna.pmd.ui;

import com.google.inject.Inject;
import com.sun.javafx.property.PropertyReference;
import com.vienna.pmd.omdb.*;
import com.vienna.pmd.omdb.impl.IDSearchRequest;
import com.vienna.pmd.omdb.impl.SearchService;
import com.vienna.pmd.omdb.impl.TitleSearchRequest;
import com.vienna.pmd.omdb.xml.Movie;
import com.vienna.pmd.omdb.xml.Result;
import com.vienna.pmd.omdb.xml.Root;
import com.vienna.pmd.ui.javafx.ExceptionDialog;
import com.vienna.pmd.ui.javafx.PosterCell;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    @FXML
    Label actorsTextLabel;

    @FXML
    Label directorTextLabel;

    @FXML
    Label plotTextLabel;

    @FXML
    Label titleTextLabel;

    @FXML
    Label writerTextLabel;

    @FXML
    Label genreTextLabel;

    @FXML
    Label awardsTextLabel;

    @FXML
    Label votesTextLabel;

    @FXML
    Label ratingTextLabel;


    ObservableList<Result> resultItems = null;

    ObjectProperty<Result> detailResultProperty = new SimpleObjectProperty<Result>();

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

        StringBinding actorsBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getActors() : "", detailResultProperty);
        actorsTextLabel.textProperty().bind(actorsBinding);

        StringBinding titleBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getTitle() : "", detailResultProperty);
        titleTextLabel.textProperty().bind(titleBinding);

        StringBinding directorBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getDirector() : "", detailResultProperty);
        directorTextLabel.textProperty().bind(directorBinding);

        StringBinding writerBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getWriter() : "", detailResultProperty);
        writerTextLabel.textProperty().bind(writerBinding);

        StringBinding plotBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getPlot() : "", detailResultProperty);
        plotTextLabel.textProperty().bind(plotBinding);

        StringBinding genreBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getGenre() : "", detailResultProperty);
        genreTextLabel.textProperty().bind(genreBinding);

        StringBinding awardsBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getAwards() : "", detailResultProperty);
        awardsTextLabel.textProperty().bind(awardsBinding);

        StringBinding ratingBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getImdbRating() : "", detailResultProperty);
        ratingTextLabel.textProperty().bind(ratingBinding);

        StringBinding votesBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getImdbVotes() : "", detailResultProperty);
        votesTextLabel.textProperty().bind(votesBinding);
    }

    @FXML
    private void searchResultTableClicked() {
        Result selectedItem = searchResultTable.getSelectionModel().getSelectedItem();
        String imdbId = selectedItem.getImdbID();

        IIDSearchRequest request = new IDSearchRequest(imdbId, ResponseType.XML);

        try {
            Root response = searchService.search(request);
            if(response.getMovies().size() > 0) {
                searchDetailImage.setImage(new Image(response.getMovies().get(0).getPoster()));
                setDetailResult(response.getMovies().get(0));
            }
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
        search();
    }

    @FXML
    private void searchTextKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            search();
        }
    }

    private void search() {
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

    public Result getDetailResult() {
        return detailResultProperty.get();
    }

    public void setDetailResult(Result detailResult) {
        detailResultProperty.set(detailResult);
    }

    public ObjectProperty<Result> getDetailResultProperty() {
        return detailResultProperty;
    }
}
