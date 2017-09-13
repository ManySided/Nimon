package ru.make.alex.ui.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AboutController extends AController
{
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

    @Override
    public void initDate()
    {

    }
}
