package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class ErrorWindow {
    public ErrorWindow(String message){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("error_window.fxml"));
            Parent root1 = (Parent)loader.load();

            Stage stage_popup = new Stage();
            stage_popup.initModality(Modality.APPLICATION_MODAL);
            stage_popup.setResizable(false);
            stage_popup.setTitle("Error message");

            stage_popup.setScene(new Scene(root1));

            ErrorController controller = loader.getController();
            controller.setMessage(message);

            stage_popup.showAndWait();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}