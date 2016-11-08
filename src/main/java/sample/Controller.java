package sample;

import implementation.SimpleLibrary;
import interfaces.ILibrary;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import static utils.Const.FAILURE;
import static utils.Const.SUCCESS;

public class Controller {
    private ILibrary iLibrary = new SimpleLibrary();
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
        disableButtons();
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                String text = iLibrary.readFile(fileName.getText(), (result) -> {
                    Platform.runLater(() -> {
                        if (resultIsSuccess(result)) {
                            statusLabel.setText("Read from finished!");
                        } else if (resultIsFaiulure(result)) {
                            statusLabel.setText("Read from failed!");
                        } else {
                            statusLabel.setText(result);
                        }
                    });
                });
                fileContent.setText(text);
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        enableButtons();
    }

    private boolean resultIsSuccess(String result) {
        return result.equals(SUCCESS);
    }

    @FXML
    public void writeClicked() {
        System.out.println("WRITE CLICKED");
        disableButtons();

        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                iLibrary.writeFile(fileName.getText(), fileContent.getText(), (result) -> {
                    Platform.runLater(() -> {
                        if (resultIsSuccess(result)) {
                            statusLabel.setText("Write to file finished!");
                        } else if (resultIsFaiulure(result)) {
                            statusLabel.setText("Write to file failed!");
                        } else {
                            statusLabel.setText(result);
                        }
                    });
                });
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        enableButtons();

    }

    private boolean resultIsFaiulure(String result) {
        return result.equals(FAILURE);
    }

    private void enableButtons() {
        readBtn.setDisable(false);
        writeBtn.setDisable(false);
    }

    private void disableButtons() {
        writeBtn.setDisable(true);
        readBtn.setDisable(true);
    }
}
