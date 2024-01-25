package project1;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TextController {
    @FXML
    private AnchorPane popupWindow;

    @FXML
    public void switchToNumber() throws IOException {
        ConverterApp.setRoot("number.fxml");
    }

    @FXML
    public void switchToText() throws IOException {
        ConverterApp.setRoot("text.fxml");
    }

    @FXML
    public void openPopup() {
        popupWindow.setVisible(!popupWindow.isVisible());
    }
}
