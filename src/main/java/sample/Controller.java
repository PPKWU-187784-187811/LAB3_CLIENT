package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField fileName;
    @FXML
    private TextArea fileContent;
    @FXML
    private Button readBtn;
    @FXML
    private Button writeBtn;
    @FXML
    private Label statusLabel;

    @FXML
    public void readClicked() {
        System.out.println("READ CLICKED");
    }

    @FXML
    public void writeClicked() {
        System.out.println("WRITE CLICKED");
    }
}
