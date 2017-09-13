package ru.make.alex.ui.utils;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DaNetController
{
    @FXML
    private Label label;

    private Stage dialogStage;
    private Callback<Boolean, Boolean> callback;

    @FXML
    private void initialize()
    {
    }

    @FXML
    private void handleDa()
    {
        if (callback != null)
            callback.call(Boolean.TRUE);
        dialogStage.close();
    }

    @FXML
    private void handleNet()
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

    public void setCallback(Callback<Boolean, Boolean> callback)
    {
        this.callback = callback;
    }
}
