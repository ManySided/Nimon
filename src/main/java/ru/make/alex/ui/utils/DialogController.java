package ru.make.alex.ui.utils;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DialogController
{
    @FXML
    private Label label;

    private Stage dialogStage;

    @FXML
    private void initialize()
    {
    }

    @FXML
    private void handleCancel()
    {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public void setСообщение(String текст)
    {
        label.setText(текст);
    }
}
