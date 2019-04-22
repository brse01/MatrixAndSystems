package br.ufc.algebra.matrixandsystems.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class ImageEditor {

    public void setImageOn(ImageView imageView, String path, int fitWidth, int fitHeight) {
        imageView.setImage(new Image(path));
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);
    }

    public void setRectangularClipOf(ImageView imageView, int arcWidth, int arcHeight) {
        Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
        clip.setArcHeight(arcHeight);
        clip.setArcWidth(arcWidth);
        imageView.setClip(clip);
    }
}
