package project1;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project1.model.Binary;
import project1.model.Decimal;
import project1.model.Hexadecimal;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class TextController implements Initializable {
    private File selectedFile;
    @FXML
    private Label fileName;
    @FXML
    private ComboBox<String> delimiterComboBox;
    @FXML
    private TextField delimiterTextField;
    private String delimiter;
    @FXML
    private ComboBox<String> bitComboBox;
    @FXML
    private ComboBox<String> operationComboBox;
    @FXML
    private TextField resTextField;
    @FXML
    private TextArea asciiTextArea;
    @FXML
    private TextArea hexTextArea;
    @FXML
    private TextArea binTextArea;
    @FXML
    private TextArea decTextArea;
    @FXML
    private TextField length;
    private List<String> binList = new ArrayList<>();

    @FXML
    public void switchToNumber() throws IOException {
        ConverterApp.setRoot("number");
    }

    @FXML
    public void switchToText() throws IOException {
        ConverterApp.setRoot("text");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        delimiterComboBox.setItems(
                FXCollections.observableArrayList("None", "Space", "Comma", "User defined"));
        delimiterComboBox.setValue("Space");
        delimiterTextField.setText(" ");
        delimiterTextField.setEditable(false);
        delimiterTextField.textProperty().addListener((observableValue, s, t1) -> {
            delimiter = delimiterTextField.getText();
            String text = asciiTextArea.getText();
            asciiTextArea.setText("");
            asciiTextArea.setText(text);
        });
        delimiter = " ";

        bitComboBox.setItems(FXCollections.observableArrayList("8-bit", "16-bit", "32-bit"));
        bitComboBox.setValue("8-bit");
        operationComboBox.setItems(FXCollections.observableArrayList("Sum", "2's complement", "XOR"));
        operationComboBox.setValue("Sum");

        AtomicBoolean enableChangeListening = new AtomicBoolean(true);

        asciiTextArea.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (enableChangeListening.get()) {
                if (!asciiTextArea.getText().isEmpty()) {
                    String text = asciiTextArea.getText();
                    enableChangeListening.set(false);
                    hexTextArea.setText(convertToHex(text));
                    binTextArea.setText(convertToBinary(text));
                    decTextArea.setText(convertToDec(text));
                    enableChangeListening.set(true);
                    length.setText(String.valueOf(asciiTextArea.getText().length()));
                }
            }
        });

        hexTextArea.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (enableChangeListening.get()) {
                if (!hexTextArea.getText().isEmpty()) {
                    String str = hexTextArea.getText();
                    List<String> strList = new ArrayList<>();
                    String[] text;
                    if (delimiter.isEmpty()) {
                        for (int i = 0; i < str.length(); i += 2) {
                            if (i + 2 >= str.length()) {
                                strList.add("0" + str.substring(i));
                                break;
                            }
                            strList.add(str.substring(i, i + 2));
                        }
                        text = strList.toArray(new String[0]);
                    } else {
                        text = str.split(delimiter);
                    }
                    StringBuilder asciiText = new StringBuilder();
                    for (String s : text) {
                        Hexadecimal.parse(s);
                        asciiText.append((char) Integer.parseInt(Decimal.convert()));
                    }
                    enableChangeListening.set(false);
                    asciiTextArea.setText(asciiText.toString());
                    binTextArea.setText(convertToBinary(asciiText.toString()));
                    decTextArea.setText(convertToDec(asciiText.toString()));
                    enableChangeListening.set(true);
                    length.setText(String.valueOf(asciiTextArea.getText().length()));
                }
            }
        });

        binTextArea.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (enableChangeListening.get()) {
                if (!binTextArea.getText().isEmpty()) {
                    String str = binTextArea.getText();
                    List<String> strList = new ArrayList<>();
                    String[] text;
                    if (delimiter.isEmpty()) {
                        for (int i = 0; i < str.length(); i += 8) {
                            if (i + 8 >= str.length()) {
                                StringBuilder c = new StringBuilder(str.substring(i));
                                while (c.length() != 8)
                                    c.insert(0, 0);
                                strList.add(c.toString());
                                break;
                            }
                            strList.add(str.substring(i, i + 8));
                        }
                        text = strList.toArray(new String[0]);
                    } else {
                        text = str.split(delimiter);
                    }
                    StringBuilder asciiText = new StringBuilder();
                    for (String s : text) {
                        Binary.parse(s);
                        asciiText.append((char) Integer.parseInt(Decimal.convert()));
                    }
                    enableChangeListening.set(false);
                    asciiTextArea.setText(asciiText.toString());
                    hexTextArea.setText(convertToHex(asciiText.toString()));
                    decTextArea.setText(convertToDec(asciiText.toString()));
                    enableChangeListening.set(true);
                    length.setText(String.valueOf(asciiTextArea.getText().length()));
                }
            }
        });

        decTextArea.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (enableChangeListening.get()) {
                if (!decTextArea.getText().isEmpty()) {
                    String str = decTextArea.getText();
                    String[] text = str.split(delimiter);
                    StringBuilder asciiText = new StringBuilder();
                    for (String s : text) {
                        asciiText.append((char) Integer.parseInt(s));
                    }
                    enableChangeListening.set(false);
                    asciiTextArea.setText(asciiText.toString());
                    hexTextArea.setText(convertToBinary(asciiText.toString()));
                    binTextArea.setText(convertToBinary(asciiText.toString()));
                    enableChangeListening.set(true);
                    length.setText(String.valueOf(asciiTextArea.getText().length()));
                }
            }
        });
    }

    @FXML
    public void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Text File");
        Stage stage = null;
        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String strFileName = selectedFile.getName();
            fileName.setText(strFileName);
            fileName.setVisible(true);
        }
    }

    public void handleFile() {

    }

    @FXML
    public void delimiterChange(ActionEvent event) {
        switch (delimiterComboBox.getValue()) {
            case "None" -> {
                delimiterTextField.setText("");
                delimiterTextField.setEditable(false);
                delimiter = "";
            }
            case "Space" -> {
                delimiterTextField.setText(" ");
                delimiterTextField.setEditable(false);
                delimiter = " ";
            }
            case "Comma" -> {
                delimiterTextField.setText(",");
                delimiterTextField.setEditable(false);
                delimiter = ",";
            }
            case "User defined" -> {
                delimiterTextField.setText("");
                delimiter = "";
                delimiterTextField.setEditable(true);
            }
        }
    }

    public String convertToHex(String text) {
        if (text.isEmpty()) return text;
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            Decimal.parse("" + (int) (text.charAt(i)));
            hex.append(Hexadecimal.convert()).append(delimiter);
        }
        if(!delimiter.isEmpty()) hex.deleteCharAt(hex.length() - delimiter.length());
        return hex.toString();
    }

    public String convertToBinary(String text) {
        if (text.isEmpty()) return text;
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            Decimal.parse("" + (int) (text.charAt(i)));
            StringBuilder c = new StringBuilder(Binary.convert());
            while (c.length() != 8) {
                c.insert(0, "0");
            }
            binary.append(c).append(delimiter);
            binList.add(c.toString());
        }
        if(!delimiter.isEmpty()) binary.deleteCharAt(binary.length() - delimiter.length());
        return binary.toString();
    }

    public String convertToDec(String text) {
        if (text.isEmpty()) return text;
        StringBuilder dec = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            dec.append((int) text.charAt(i)).append(delimiter);
        }
        if(!delimiter.isEmpty()) dec.deleteCharAt(dec.length() - delimiter.length());
        return dec.toString();
    }

    @FXML
    public void checksum() {
        int bit = 8;
        String res;
        if (bitComboBox.getValue().equals("8-bit")) {
            bit = 8;
        }
        else if (bitComboBox.getValue().equals("16-bit")) {
            bit = 16;
        }
        else if (bitComboBox.getValue().equals("32-bit")) {
            bit = 32;
        }
        for (int i = 0; i < binList.size(); i++) {
            StringBuilder str = new StringBuilder(binList.get(i));
            while (str.length() < bit) {
                str.insert(0, "0");
            }
            while (str.length() > bit) {
                str.deleteCharAt(0);
            }
            binList.set(i, str.toString());
        }
        if (operationComboBox.getValue().equals("Sum")) {
            res = Binary.sum(binList);
            Binary.parse(res);
            res = Hexadecimal.convert();
            resTextField.setText(formatRes(res, bit));
        }
        else if (operationComboBox.getValue().equals("2's complement")) {
            res = "-" + Binary.sum(binList);
            Binary.parse(res);
            res = Hexadecimal.convertSigned();
            resTextField.setText(formatRes(res, bit));
        }
        else if (operationComboBox.getValue().equals("XOR")) {
            res = Binary.xor(binList);
            Binary.parse(res);
            res = Hexadecimal.convert();
            resTextField.setText(formatRes(res, bit));
        }
    }

    private String formatRes(String res, int bit) {
        bit = bit / 4;
        StringBuilder str = new StringBuilder(res);
        while (str.length() < bit) {
            str.insert(0, "0");
        }
        while (str.length() > bit) {
            str.deleteCharAt(0);
        }
        return str.toString();
    }
}
