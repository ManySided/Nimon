package ru.make.alex.ui.controller;

import javafx.stage.Stage;

/**
 * Created by pas on 13.06.2017.
 */
public abstract class AController {
    public Stage primaryStage;
    private String viewName;

    public AController() {
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public String getViewName() {
        return viewName;
    }

    public abstract void initDate();
}
