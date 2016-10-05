package ca.ulaval.glo2004.visualigue.ui.controllers;

import java.io.File;
import javafx.stage.FileChooser;

public class FileSelectionEventArgs {

    public FileChooser fileChooser;
    public File selectedFile;

    public FileSelectionEventArgs(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }
}
