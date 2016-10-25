package ca.ulaval.glo2004.visualigue.ui.customcontrols;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResizableImageView extends ImageView {

    private double resizeWidth = -1;
    private double resizeHeight = -1;

    public ResizableImageView() {
        setPreserveRatio(true);
    }

    @Override
    public double minWidth(double height) {
        return 0;
    }

    @Override
    public double prefWidth(double height) {
        Image image = getImage();
        if (image == null) {
            return minWidth(height);
        }
        if (resizeHeight >= 0 && resizeHeight < image.getHeight()) {
            return resizeHeight * getAspectRatio();
        } else {
            return image.getWidth();
        }
    }

    @Override
    public double maxWidth(double height) {
        return Integer.MAX_VALUE;
    }

    @Override
    public double minHeight(double width) {
        return 0;
    }

    @Override
    public double prefHeight(double width) {
        Image image = getImage();
        if (image == null) {
            return minHeight(width);
        }
        if (resizeWidth >= 0 && resizeWidth < image.getWidth()) {
            return resizeWidth / getAspectRatio();
        } else {
            return image.getHeight();
        }
    }

    @Override
    public double maxHeight(double width) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    public Double getAspectRatio() {
        Image image = getImage();
        return image.getWidth() / image.getHeight();
    }

    @Override
    public void resize(double width, double height) {
        resizeWidth = width;
        resizeHeight = height;
        setFitWidth(width);
        setFitHeight(height);
        Platform.runLater(() -> {
            this.getParent().autosize();
        });
    }
}
