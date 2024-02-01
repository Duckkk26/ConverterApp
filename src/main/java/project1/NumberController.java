package project1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import project1.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NumberController implements Initializable {
    @FXML
    private ComboBox<String> fromComboBox;
    @FXML
    private ComboBox<String> toComboBox;
    private final ObservableList<String> list = FXCollections.observableArrayList("Binary", "Decimal", "Octal", "Hexadecimal");
    @FXML
    private TextField numberField;
    @FXML
    private Text label1;
    @FXML
    private Text label2;
    @FXML
    private Text label3;
    @FXML
    private TextArea textArea1;
    @FXML
    private TextField textField1;
    @FXML
    private TextArea textArea2;
    @FXML
    private TextField textField2;
    @FXML
    private TextArea textArea3;
    @FXML
    private TextField textField3;

    @FXML
    public void switchToNumber() throws IOException {
        ConverterApp.setRoot("number.fxml");
    }

    @FXML
    public void switchToText() throws IOException {
        ConverterApp.setRoot("text.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fromComboBox.setItems(list);
        fromComboBox.setValue("Decimal");
        toComboBox.setItems(list);
        toComboBox.setValue("Hexadecimal");
        label1.setText("Hex number");
        textField1.setText("16");
        label2.setText("Hex signed 2's complement");
        textField2.setText("16");
        label3.setText("Binary number");
        textField3.setText("2");
    }

    @FXML
    public void convertNum() {
        String from = fromComboBox.getValue();
        String numStr = numberField.getText();
        switch (from) {
            case "Binary" -> Binary.parse(numStr);
            case "Decimal" -> Decimal.parse(numStr);
            case "Octal" -> Octal.parse(numStr);
            case "Hexadecimal" -> Hexadecimal.parse(numStr);
        }

        switch (textField1.getText()) {
            case "2" -> textArea1.setText(Binary.convert());
            case "10" -> textArea1.setText(Decimal.convert());
            case "8" -> textArea1.setText(Octal.convert());
            case "16" -> textArea1.setText(Hexadecimal.convert());
        }

        if (textField1.getText().equals(textField2.getText())) {
            switch (textField2.getText()) {
                case "2" -> textArea2.setText(Binary.convertSigned());
                case "10" -> {
                    if (from.equals("Binary"))
                        textArea2.setText(Binary.parseDecimalFromSigned(numStr));
                    else if (from.equals("Hexadecimal"))
                        textArea2.setText(Hexadecimal.parseDecimalFromSigned(numStr));
                }
                case "16" -> textArea2.setText(Hexadecimal.convertSigned());
            }
        } else {
            switch (textField2.getText()) {
                case "2" -> textArea2.setText(Binary.convert());
                case "10" -> textArea2.setText(Decimal.convert());
                case "8" -> textArea2.setText(Octal.convert());
                case "16" -> textArea2.setText(Hexadecimal.convert());
            }
        }

        switch (textField3.getText()) {
            case "2" -> textArea3.setText(Binary.convert());
            case "10" -> textArea3.setText(Decimal.convert());
            case "8" -> textArea3.setText(Octal.convert());
            case "16" -> textArea3.setText(Hexadecimal.convert());
        }
    }

    @FXML
    public void comboBoxChange(ActionEvent event) {
        String from = fromComboBox.getValue();
        String to = toComboBox.getValue();
        if (!from.equals(to)) {
            numberField.setText(null);
            switch (to) {
                case "Binary" -> {
                    label1.setText("Binary number");
                    textArea1.setText(null);
                    textField1.setText("2");
                    if (from.equals("Decimal")) {
                        label2.setText("Binary signed 2's complement");
                        textArea2.setText(null);
                        textField2.setText("2");
                        label3.setText("Hex number");
                        textArea3.setText(null);
                        textField3.setText("16");
                        label3.setVisible(true);
                        textArea3.setVisible(true);
                        textField3.setVisible(true);
                    } else {
                        label2.setText("Decimal number");
                        textArea2.setText(null);
                        textField2.setText("10");
                        label3.setVisible(false);
                        textArea3.setVisible(false);
                        textField3.setVisible(false);
                    }
                }
                case "Decimal" -> {
                    label1.setText("Decimal number");
                    textArea1.setText(null);
                    textField1.setText("10");
                    label3.setVisible(true);
                    textArea3.setVisible(true);
                    textField3.setVisible(true);
                    if (from.equals("Octal")) {
                        label2.setText("Hex number");
                        textArea2.setText(null);
                        textField2.setText("16");
                        label3.setVisible(false);
                        textArea3.setVisible(false);
                        textField3.setVisible(false);
                    } else {
                        label2.setText("Decimal from signed 2's complement");
                        textArea2.setText(null);
                        textField2.setText("10");
                        if (from.equals("Binary")) {
                            label3.setText("Hex number");
                            textArea3.setText(null);
                            textField3.setText("16");
                        } else {
                            label3.setText("Binary number");
                            textArea3.setText(null);
                            textField3.setText("2");
                        }
                    }
                }
                case "Octal" -> {
                    label1.setText("Octal number");
                    textArea1.setText(null);
                    textField1.setText("8");
                    label3.setVisible(false);
                    textArea3.setVisible(false);
                    textField3.setVisible(false);
                    if (from.equals("Decimal")) {
                        label2.setText("Hex number");
                        textArea2.setText(null);
                        textField2.setText("16");
                    } else {
                        label2.setText("Decimal number");
                        textArea2.setText(null);
                        textField2.setText("10");
                    }
                }
                case "Hexadecimal" -> {
                    label1.setText("Hex number");
                    textArea1.setText(null);
                    textField1.setText("16");
                    if (from.equals("Decimal")) {
                        label2.setText("Hex signed 2's complement");
                        textArea2.setText(null);
                        textField2.setText("16");
                        label3.setText("Binary number");
                        textArea3.setText(null);
                        textField3.setText("2");
                        label3.setVisible(true);
                        textArea3.setVisible(true);
                        textField3.setVisible(true);
                    } else {
                        label2.setText("Decimal number");
                        textArea2.setText(null);
                        textField2.setText("10");
                        label3.setVisible(false);
                        textArea3.setVisible(false);
                        textField3.setVisible(false);
                    }
                }
            }
        }
    }
}
