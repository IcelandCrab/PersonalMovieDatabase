package com.vienna.pmd.ui;

import com.google.inject.Inject;
import com.vienna.pmd.omdb.*;
import com.vienna.pmd.omdb.impl.IDSearchRequest;
import com.vienna.pmd.omdb.impl.TitleSearchRequest;
import com.vienna.pmd.omdb.json.Movie;
import com.vienna.pmd.omdb.json.Movies;
import com.vienna.pmd.omdb.json.Poster;
import com.vienna.pmd.omdb.xml.Result;
import com.vienna.pmd.omdb.xml.Root;
import com.vienna.pmd.ui.javafx.ExceptionDialog;
import com.vienna.pmd.ui.javafx.PosterCell;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchController {

    @FXML
    Button searchButton;

    @FXML
    TextField searchText;

    @FXML
    TableView<Movie> searchResultTable;

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


    private ObservableList<Movie> resultItems = null;

    private ObjectProperty<Movie> detailResultProperty = new SimpleObjectProperty<Movie>();

    @Inject
    private ISearchService searchService;

    @FXML
    public void initialize() {
        // spalten für tabelle initialisieren
        yearColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("year"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
        titleColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                TableCell<Movie, String> cell = new TableCell<Movie, String>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(cell.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                return cell;
            }

        });
        coverColumn.setCellValueFactory(new PropertyValueFactory<Movie, String>("poster"));
        coverColumn.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                TableCell<Movie, Poster> cell = new PosterCell();
                return cell;
            }
        });

        // observablelist for tabelleninhalt initialisieren
        resultItems = FXCollections.observableArrayList();
        searchResultTable.setItems(resultItems);

        StringBinding actorsBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ?
                Arrays.stream(detailResultProperty.get().getCast()).map((cast) -> cast.getName()).collect(Collectors.joining(",")) : "", detailResultProperty);
        actorsTextLabel.textProperty().bind(actorsBinding);

        StringBinding titleBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getTitle() : "", detailResultProperty);
        titleTextLabel.textProperty().bind(titleBinding);

        StringBinding directorBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getDirector() : "", detailResultProperty);
        directorTextLabel.textProperty().bind(directorBinding);

        StringBinding writerBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ?
                Arrays.stream(detailResultProperty.get().getWriters()).collect(Collectors.joining(",")) : "", detailResultProperty);
        writerTextLabel.textProperty().bind(writerBinding);

        StringBinding plotBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getDescription() : "", detailResultProperty);
        plotTextLabel.textProperty().bind(plotBinding);

        StringBinding genreBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ?
                Arrays.stream(detailResultProperty.get().getGenre()).collect(Collectors.joining(",")) : "", detailResultProperty);
        genreTextLabel.textProperty().bind(genreBinding);

        StringBinding awardsBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? "" : "", detailResultProperty);
        awardsTextLabel.textProperty().bind(awardsBinding);

        StringBinding ratingBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getRating() : "", detailResultProperty);
        ratingTextLabel.textProperty().bind(ratingBinding);

        StringBinding votesBinding = Bindings.createStringBinding(() -> detailResultProperty.get() != null ? detailResultProperty.get().getRating_count() : "", detailResultProperty);
        votesTextLabel.textProperty().bind(votesBinding);
    }

    @FXML
    private void searchResultTableClicked() {
        Movie selectedItem = searchResultTable.getSelectionModel().getSelectedItem();
        String imdbId = selectedItem.getImdb_id();

        IIDSearchRequest request = new IDSearchRequest(imdbId, ResponseType.JSON);

        try {
            Movies response = (Movies) searchService.search(request);
            Optional<Movie> movie = response.getMovies().stream().findFirst();
            if(movie.isPresent() && movie.get().getPoster() != null && movie.get().getPoster().getThumb() != null) {
                searchDetailImage.setImage(new Image(movie.get().getPoster().getThumb()));
                setDetailResult(movie.get());
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
            Movies response = (Movies) searchService.search(request);

            resultItems.clear();
            resultItems.addAll(response.getMovies());
        } catch(SearchException e) {
            ExceptionDialog dialog = new ExceptionDialog(Alert.AlertType.ERROR);
            dialog.setTitle("Fehler bei Suche");
            dialog.setHeaderText("Fehler bei Suche");
            dialog.setContentText("Bei der Suche ist ein Fehler aufgetreten. Bitte melden sie sich beim Administrator ;)");
            dialog.setException(e);

            dialog.showAndWait();
        }
    }

    public Movie getDetailResult() {
        return detailResultProperty.get();
    }

    public void setDetailResult(Movie detailResult) {
        detailResultProperty.set(detailResult);
    }

    public ObjectProperty<Movie> getDetailResultProperty() {
        return detailResultProperty;
    }
}
