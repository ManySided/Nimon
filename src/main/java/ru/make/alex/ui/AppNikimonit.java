package ru.make.alex.ui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.make.alex.db.DbUtils;
import ru.make.alex.db.table.ITableApp;
import ru.make.alex.db.versions.VersionController;
import ru.make.alex.model.StrokaMonitoringa;
import ru.make.alex.model.TipStroki;
import ru.make.alex.model.system.SystemProperties;
import ru.make.alex.ui.controller.RootController;

/**
 * Created by pas on 13.06.2017.
 */
public class AppNikimonit extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        settingDb();
        updateDb();

        SpringFxmlLoader loader = new SpringFxmlLoader();
        Parent root = loader.load(getClass().getResource("/RootWindow.fxml"));

        RootController controller = (RootController) loader.getController();
        controller.setPrimaryStage(primaryStage);
        controller.initDate();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Nikimonit");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception
    {
        super.stop();
        if (SpringFxmlLoader.applicationContext != null)
        {
            SpringFxmlLoader.applicationContext.close();
        }
    }

    private void settingDb()
    {
        DbUtils dbUtils = (DbUtils) SpringFxmlLoader.applicationContext.getBean("dbUtils");
        dbUtils.checkDB();
        dbUtils.updateTables(new ITableApp[]{new StrokaMonitoringa(), new TipStroki(), new SystemProperties()});
    }

    private void updateDb()
    {
        VersionController versionController = (VersionController) SpringFxmlLoader.applicationContext.getBean("versionController");
        versionController.checkAndUpdate();
    }
}
