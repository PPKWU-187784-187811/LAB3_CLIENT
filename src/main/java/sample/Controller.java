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
        disableControls();
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                String text = iLibrary.readFile(fileName.getText(), (result) -> {
                    Platform.runLater(() -> {
                        if (resultIsSuccess(result)) {
                            setStatus("Read from finished!");
                        } else if (resultIsFailure(result)) {
                            setStatus("Read from failed!");
                        } else {
                            setStatus(result);
                        }
                    });
                });
                fileContent.setText(text);
                enableButtons();
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    private boolean resultIsSuccess(String result) {
        return result.equals(SUCCESS);
    }

    @FXML
    public void writeClicked() {
        disableControls();
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                iLibrary.writeFile(fileName.getText(), fileContent.getText(), (result) -> {
                    Platform.runLater(() -> {
                        if (resultIsSuccess(result)) {
                            setStatus("Write to file finished!");
                        } else if (resultIsFailure(result)) {
                            setStatus("Write to file failed!");
                        } else {
                            setStatus(result);
                        }
                    });
                });
                enableButtons();
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    private void setStatus(String value) {
        statusLabel.setText(value);
    }

    private boolean resultIsFailure(String result) {
        return result.equals(FAILURE);
    }

    private void enableButtons() {
        readBtn.setDisable(false);
        writeBtn.setDisable(false);
        fileContent.setDisable(false);
        fileName.setDisable(false);
    }

    private void disableControls() {
        writeBtn.setDisable(true);
        readBtn.setDisable(true);
        fileContent.setDisable(true);
        fileName.setDisable(true);
    }
}
