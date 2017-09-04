package com.vienna.pmd.ui.javafx;

import com.vienna.pmd.omdb.json.Movie;
import com.vienna.pmd.omdb.json.Poster;
import com.vienna.pmd.omdb.xml.Result;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Created by bobmo on 30.10.2016.
 */
public class PosterCell extends TableCell<Movie, Poster> {
    VBox box;
    ImageView imageView;

    public PosterCell() {
        box =  new VBox();
        box.setAlignment(Pos.CENTER);
        imageView = new ImageView();
        imageView.setFitHeight(120);
        imageView.setFitWidth(90);
        box.getChildren().add(imageView);
        setGraphic(imageView);
    }

    @Override
    protected void updateItem(Poster item, boolean empty) {
        if(item != null && item.getThumb() != null)
            imageView.setImage(new Image(item.getThumb(), true));
    }
}
