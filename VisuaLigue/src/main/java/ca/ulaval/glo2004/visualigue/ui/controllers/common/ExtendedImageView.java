package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public class ExtendedImageView extends ImageView {

    private Boolean autoResize = false;
    private Boolean initialAutoSizeDone = false;

    public ExtendedImageView() {
        this.parentProperty().addListener(this::parentChangedListener);
    }

    public Boolean getAutoResize() {
        return autoResize;
    }

    public void setAutoResize(Boolean autoResize) {
        this.autoResize = autoResize;
    }

    public void parentChangedListener(ObservableValue<? extends Parent> value, Parent oldPropertyValue, Parent newPropertyValue) {
        Region parentRegion = (Region) this.getParent();
        if (parentRegion != null) {
            parentRegion.widthProperty().addListener(this::parentWidthChangedListener);
            this.imageProperty().addListener(this::imageChangedListener);
        }
    }

    public void parentWidthChangedListener(ObservableValue<? extends Number> value, Number oldPropertyValue, Number newPropertyValue) {
        if (newPropertyValue.doubleValue() != 0) {
            setFitSize(newPropertyValue.doubleValue());
            if (!initialAutoSizeDone) {
                initialAutoSizeDone = true;
                Platform.runLater(() -> {
                    Region parentRegion = (Region) this.getParent();
                    parentRegion.autosize();
                });
            }
        }
    }

    public void imageChangedListener(ObservableValue<? extends Image> value, Image oldPropertyValue, Image newPropertyValue) {
        Region parentRegion = (Region) this.getParent();
        setFitSize(parentRegion.getWidth());
    }

    private void setFitSize(Double width) {
        Image image = this.getImage();
        if (image != null) {
            if (width <= image.getWidth()) {
                this.setFitWidth(width);
                this.setFitHeight(width / (image.getWidth() / image.getHeight()));
            } else {
                this.setFitWidth(image.getWidth());
                this.setFitHeight(image.getHeight());
            }
        }
    }

    @Override
    public double minWidth(double height) {
        if (autoResize) {
            return 0;
        } else {
            return super.minWidth(height);
        }
    }

    @Override
    public double maxWidth(double height) {
        if (autoResize) {
            return Integer.MAX_VALUE;
        } else {
            return super.maxWidth(height);
        }
    }

    @Override
    public double minHeight(double width) {
        if (autoResize) {
            return 0;
        } else {
            return super.minHeight(width);
        }
    }

    @Override
    public double maxHeight(double width) {
        if (autoResize) {
            return Integer.MAX_VALUE;
        } else {
            return super.maxHeight(width);
        }
    }

    @Override
    public boolean isResizable() {
        if (autoResize) {
            return true;
        } else {
            return super.isResizable();
        }
    }

    @Override
    public void resize(double width, double height) {
        if (!autoResize) {
            super.resize(width, height);
        }
    }
}
