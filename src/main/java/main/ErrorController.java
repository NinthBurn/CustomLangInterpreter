package main;

import controller.ProgramStateController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ErrorController {
    @FXML
    private Label error_label;
    @FXML
    private Button return_button;

    private String error_message;
    public void initialize(){
        Platform.runLater(() -> {
            error_label.setText(error_message);
        });
    }

    public void setMessage(String message){
        error_message = message;
    }

    public void onReturnButtonClick(){
        ((Stage)(return_button.getScene().getWindow())).close();
    }
}