package ru.make.alex.ui;

import javafx.fxml.FXMLLoader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.make.alex.config.AppConfig;
import ru.make.alex.ui.controller.AController;

import java.io.IOException;
import java.net.URL;

/**
 * Created by pas on 17.07.2017.
 */
public class SpringFxmlLoader {
    public static final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

    private FXMLLoader loader;

    public <T> T load(URL url) {
        try {
            loader = new FXMLLoader(url);
            loader.setControllerFactory(applicationContext::getBean);
            return loader.load();
        } catch (Exception e) {
            System.err.print(" --- Ошибка загрузки шаблона --- ");
            e.printStackTrace();
            System.err.print(" --- !Ошибка загрузки шаблона --- ");
            throw new RuntimeException(e);
        }
    }

    public AController getController() {
        if (loader != null)
            return loader.getController();
        return null;
    }
}
