package ru.make.alex.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.make.alex.dao.StrokiDao;
import ru.make.alex.dao.TipStrokiDao;
import ru.make.alex.model.StrokaMonitoringa;
import ru.make.alex.model.TipStroki;
import ru.make.alex.ui.model.TipStrokiModel;
import ru.make.alex.ui.utils.UIUtils;

import java.util.List;

@Component
public class SettingController extends AController
{
    @Autowired
    private StrokiDao strokiDao;
    @Autowired
    private TipStrokiDao tipStrokiDao;

    private ObservableList<TipStrokiModel> tips = FXCollections.observableArrayList();
    private TipStrokiModel editPosition = null;

    @FXML
    private TextField tipEnter;
    @FXML
    private Button btnTipAdd;
    @FXML
    private Button btnTipEdit;
    @FXML
    private Button btnTipDel;

    @FXML
    private TableView<TipStrokiModel> tipTable;
    @FXML
    private TableColumn<TipStrokiModel, Number> tipIdColumn;
    @FXML
    private TableColumn<TipStrokiModel, String> tipNameColumn;

    @FXML
    private void initialize()
    {
        tipIdColumn.setCellValueFactory(cellData -> cellData.getValue().idTipProperty());
        tipNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameTipProperty());
    }

    @FXML
    private void handleAddTip()
    {
        String text = tipEnter.getText();
        if (text != null && !text.isEmpty())
        {
            TipStroki old = tipStrokiDao.findByName(text);
            if (old == null)
            {
                tipStrokiDao.saveTipStroki(new TipStroki(text));
                refreshTips();
            }
            else
            {
                UIUtils.INST.getDialogMessage(getPrimaryStage(), "Куда лепишь ещё, такой уже есть!");
            }
        }
        else
        {
            UIUtils.INST.getDialogMessage(getPrimaryStage(), "Текст вводить я сам должен?!");
        }
    }

    @FXML
    private void handleEditTip()
    {

    }

    @FXML
    private void handleDelTip()
    {
        int selectedIndex = tipTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0)
        {
            editPosition = tipTable.getItems().get(selectedIndex);
            List<StrokaMonitoringa> all = strokiDao.getAllByFiltr(null, null, false, false, null, null,
                    editPosition.getGeneral());
            if (all == null || all.isEmpty())
            {
                tipStrokiDao.deleteTipStroki(editPosition.getGeneral());
                refreshTips();
            }
            else
            {
                UIUtils.INST.getDialogMessage(primaryStage, "Не могу я удалить тип, т.к. есть строки с таким типом");
            }
        }
        else
        {
            UIUtils.INST.getDialogMessage(primaryStage,
                    "Запись в таблице, кто выделять будет?! Я мысли читать не умею.");
        }
    }

    @Override
    public void initDate()
    {
        refreshTips();
    }

    private void refreshTips()
    {
        tips.clear();
        for (TipStroki tipStr : tipStrokiDao.getAll())
        {
            tips.add(new TipStrokiModel(tipStr));
        }
        tipTable.setItems(tips);
    }
}
