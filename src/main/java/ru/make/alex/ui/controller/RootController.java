package ru.make.alex.ui.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.make.alex.dao.StrokiDao;
import ru.make.alex.dao.TipStrokiDao;
import ru.make.alex.model.StrokaMonitoringa;
import ru.make.alex.model.TipStroki;
import ru.make.alex.ui.SpringFxmlLoader;
import ru.make.alex.ui.model.StrokaMonitoringaModel;
import ru.make.alex.ui.utils.UIUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by pas on 13.06.2017.
 */
@Component
public class RootController extends AController
{
    @Autowired
    private StrokiDao strokiDao;
    @Autowired
    private TipStrokiDao tipStrokiDao;

    private ObservableList<StrokaMonitoringaModel> stroki = FXCollections.observableArrayList();
    private ObservableList<TipStroki> tips = FXCollections.observableArrayList();

    @FXML
    private TableView<StrokaMonitoringaModel> monitoringTable;
    @FXML
    private TableColumn<StrokaMonitoringaModel, String> typeColumn;
    @FXML
    private TableColumn<StrokaMonitoringaModel, LocalDate> dateCreateColumn;
    @FXML
    private TableColumn<StrokaMonitoringaModel, LocalDate> dateLostColumn;
    @FXML
    private TableColumn<StrokaMonitoringaModel, String> nameColumn;
    @FXML
    private TableColumn<StrokaMonitoringaModel, String> kommentColumn;

    @FXML
    private DatePicker periodS;
    @FXML
    private DatePicker periodPo;
    @FXML
    private CheckBox periodEnter;
    @FXML
    private CheckBox periodGoden;
    @FXML
    private TextField nazvanie;
    @FXML
    private TextField koment;
    @FXML
    private ComboBox<TipStroki> tip;

    public RootController()
    {
        super();
    }

    @Override
    public void initDate()
    {
        refreshTips();

        clearFilterParametrs();
        findStroki();
    }

    @FXML
    private void initialize()
    {
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeStrokiProperty());
        dateCreateColumn.setCellValueFactory(cellData -> cellData.getValue().dateCreateProperty());
        dateLostColumn.setCellValueFactory(cellData -> cellData.getValue().dateLostProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nazvanieProperty());
        kommentColumn.setCellValueFactory(cellData -> cellData.getValue().kommentProperty());

        monitoringTable.setRowFactory(tv -> new TableRow<StrokaMonitoringaModel>()
        {
            @Override
            protected void updateItem(StrokaMonitoringaModel item, boolean empty)
            {
                super.updateItem(item, empty);
                if (item != null)
                {
                    LocalDate dateLost = item.getDateLost();
                    LocalDate now = LocalDate.now();
                    long difDays = ChronoUnit.DAYS.between(now, dateLost);

                    if (difDays <= 0)
                    {
                        setStyle("-fx-background-color: #b08a6f;");
                    }
                    if (difDays > 0 && difDays <= 7)
                    {
                        setStyle("-fx-background-color: #ffb48f;");
                    }
                    if (difDays > 7 && difDays <= 30)
                    {
                        setStyle("-fx-background-color: #ffdca3;");
                    }
                }
            }
        });
    }

    @FXML
    private void handleMenuSetting()
    {
        SpringFxmlLoader loader = new SpringFxmlLoader();
        Parent root = loader.load(getClass().getResource("/SettingWindow.fxml"));

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Настройки системы");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        SettingController controller = (SettingController) loader.getController();
        controller.setPrimaryStage(dialogStage);
        controller.initDate();

        dialogStage.showAndWait();
        refreshTips();
    }

    @FXML
    private void handleMenuExit()
    {
        Platform.exit();
    }

    @FXML
    private void handleMenuEdit()
    {
        editRow();
    }

    @FXML
    private void handleMenuAdd()
    {
        addRow();
    }

    @FXML
    private void handleMenuDel()
    {
        deleteRow();
    }

    @FXML
    private void handleMenuAbout()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/AboutWindow.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("О программе");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AboutController editStrokaController = loader.getController();
            editStrokaController.setDialogStage(dialogStage);

            dialogStage.showAndWait();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            UIUtils.INST.getDialogMessage(primaryStage, "Ошибка произошла: " + e.getMessage());
        }
    }

    @FXML
    private void handleFind()
    {
        findStroki();
    }

    @FXML
    private void handleClear()
    {
        clearFilterParametrs();
    }

    @FXML
    private void handleBtnEdit()
    {
        editRow();
    }

    @FXML
    private void handleBtnAdd()
    {
        addRow();
    }

    @FXML
    private void handleBtnDel()
    {
        deleteRow();
    }

    private void clearFilterParametrs()
    {
        LocalDate now = LocalDate.now();
        periodS.setValue(LocalDate.of(now.getYear(), now.getMonthValue(), 1));
        periodPo.setValue(LocalDate.of(now.getYear(), now.getMonthValue(), now.getMonth().maxLength()));
        periodEnter.setSelected(false);
        periodGoden.setSelected(true);
        nazvanie.setText(null);
        koment.setText(null);
        tip.setValue(null);
    }

    private void refreshTips()
    {
        tips.clear();
        tips.setAll(tipStrokiDao.getAll());
        tip.setItems(tips);
    }

    private void findStroki()
    {
        List<StrokaMonitoringa> strokiByFiltr = strokiDao.getAllByFiltr(periodS.getValue(), periodPo.getValue(),
                periodEnter.isSelected(), periodGoden.isSelected(), nazvanie.getText(), koment.getText(),
                tip.getValue());
        stroki.clear();
        for (StrokaMonitoringa stroka : strokiByFiltr)
        {
            stroki.add(new StrokaMonitoringaModel(stroka));
        }
        monitoringTable.setItems(stroki);
    }

    private void addRow()
    {
        StrokaMonitoringa stroka = new StrokaMonitoringa();
        stroka.setId(Long.valueOf(0));
        stroka.setDateLost(LocalDate.now());
        stroka.setDateCreate(LocalDate.now());
        StrokaMonitoringaModel strokaMonitoringa = new StrokaMonitoringaModel(stroka);
        openEditWindow(strokaMonitoringa, "Добавление нового пункта", false);
    }

    private void editRow()
    {
        int selectedIndex = monitoringTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0)
        {
            StrokaMonitoringaModel strokaMonitoringa = monitoringTable.getItems().get(selectedIndex);
            openEditWindow(strokaMonitoringa, "Редактирование строки", true);

        }
        else
        {
            UIUtils.INST.getDialogMessage(primaryStage,
                    "Запись в таблице, кто выделять будет?! Я мысли читать не умею.");
        }
    }

    private void deleteRow()
    {
        int selectedIndex = monitoringTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0)
        {
            UIUtils.INST.getDaNetMessage(primaryStage, "Удаление строки",
                    "Уверен, что хочешь это сделать? Вижу, ты решительный, но подумай стоит ли это делать...",
                    new Callback<Boolean, Boolean>()
                    {
                        @Override
                        public Boolean call(Boolean param)
                        {
                            if (param)
                            {
                                StrokaMonitoringaModel strokaMonitoringa = monitoringTable.getItems().get(selectedIndex);
                                strokiDao.deleteStroka(strokaMonitoringa.getStroka());
                                findStroki();
                            }
                            return null;
                        }
                    });
        }
        else
        {
            UIUtils.INST.getDialogMessage(primaryStage,
                    "Запись в таблице, кто выделять будет?! Я мысли читать не умею.");
        }
    }

    private void openEditWindow(StrokaMonitoringaModel row, String title, boolean isEdit)
    {
        SpringFxmlLoader loader = new SpringFxmlLoader();
        Parent root = loader.load(getClass().getResource("/EditStrokaWindow.fxml"));

        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        EditStrokaController controller = (EditStrokaController) loader.getController();
        controller.setPrimaryStage(dialogStage);
        controller.setStrokaMonitoringa(row);
        controller.setCallback(new Callback<StrokaMonitoringa, StrokaMonitoringa>()
        {
            @Override
            public StrokaMonitoringa call(StrokaMonitoringa param)
            {
                if (param != null)
                {
                    findStroki();
                }
                return null;
            }
        });
        controller.initDate();

        dialogStage.showAndWait();
    }
}
