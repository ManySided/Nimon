package ru.make.alex.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.make.alex.dao.StrokiDao;
import ru.make.alex.dao.TipStrokiDao;
import ru.make.alex.model.StrokaMonitoringa;
import ru.make.alex.model.TipStroki;
import ru.make.alex.ui.model.StrokaMonitoringaModel;
import ru.make.alex.ui.utils.UIUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class EditStrokaController extends AController
{
    @Autowired
    private TipStrokiDao tipStrokiDao;

    @Autowired
    private StrokiDao strokiDao;

    private Callback<StrokaMonitoringa, StrokaMonitoringa> callback;
    private StrokaMonitoringaModel strokaMonitoringa;
    private List<TipStroki> allTips = new ArrayList<TipStroki>();

    private ObservableList<TipStroki> tips = FXCollections.observableArrayList();

    @FXML
    private ComboBox<TipStroki> typeStroki;
    @FXML
    private DatePicker dateCreate;
    @FXML
    private DatePicker dateLost;
    @FXML
    private TextField nazvanieField;
    @FXML
    private TextArea kommentariField;

    @FXML
    private void initialize()
    {
    }

    @FXML
    private void handleOK()
    {
        if (isCorrectData())
        {
            StrokaMonitoringa strokaToSave = new StrokaMonitoringa();
            TipStroki value = typeStroki.getValue();
            strokaToSave.setTipStroki(value.getNazvanie());
            strokaToSave.setTipStrokiObj(value);
            if (strokaMonitoringa.getIdStroki().compareTo(Long.valueOf(0)) != 0)
            {
                strokaToSave.setId(strokaMonitoringa.getIdStroki());
            }
            strokaToSave.setDateCreate(dateCreate.getValue());
            strokaToSave.setDateLost(dateLost.getValue());
            strokaToSave.setNazvanie(nazvanieField.getText());
            strokaToSave.setKomment(kommentariField.getText());
            strokiDao.saveStroka(strokaToSave);

            callback.call(strokaMonitoringa.getStroka());
            getPrimaryStage().close();
        }
    }

    @FXML
    private void handleCancel()
    {
        getPrimaryStage().close();
    }

    @FXML
    private void handleAdd7D()
    {
        LocalDate value = dateLost.getValue();
        value = value.plusDays(7);
        dateLost.setValue(value);
    }

    @FXML
    private void handleAdd1M()
    {
        LocalDate value = dateLost.getValue();
        value = value.plusMonths(1);
        dateLost.setValue(value);
    }

    @FXML
    private void handleAdd6M()
    {
        LocalDate value = dateLost.getValue();
        value = value.plusMonths(6);
        dateLost.setValue(value);
    }

    @FXML
    private void handleAdd1Y()
    {
        LocalDate value = dateLost.getValue();
        value = value.plusYears(1);
        dateLost.setValue(value);
    }

    @Override
    public void initDate()
    {
        allTips = tipStrokiDao.getAll();
        tips.setAll(allTips);
        typeStroki.setItems(tips);

        StrokaMonitoringa stroka = strokaMonitoringa.getStroka();
        if (stroka.getTipStrokiObj() != null)
        {
            for (TipStroki ts : allTips)
            {
                if (ts.getId().compareTo(stroka.getTipStrokiObj().getId()) == 0)
                {
                    typeStroki.setValue(ts);
                    break;
                }
            }
        }
        dateCreate.setValue(strokaMonitoringa.getDateCreate());
        dateLost.setValue(strokaMonitoringa.getDateLost());
        nazvanieField.setText(strokaMonitoringa.getNazvanie());
        kommentariField.setText(strokaMonitoringa.getKomment());
    }

    public void setStrokaMonitoringa(StrokaMonitoringaModel strokaMonitoringa)
    {
        this.strokaMonitoringa = strokaMonitoringa;
    }

    public void setCallback(Callback<StrokaMonitoringa, StrokaMonitoringa> callback)
    {
        this.callback = callback;
    }

    private boolean isCorrectData()
    {
        if (typeStroki.getValue() == null)
        {
            UIUtils.INST.getDialogMessage(getPrimaryStage(), "Необходимо указать \"Тип\"");
            return false;
        }
        if (dateLost.getValue() == null)
        {
            UIUtils.INST.getDialogMessage(getPrimaryStage(), "Необходимо заполнить поле \"Годен до\"");
            return false;
        }
        if (nazvanieField.getText() == null || nazvanieField.getText().equals(""))
        {
            UIUtils.INST.getDialogMessage(getPrimaryStage(), "Необходимо заполнить поле \"Название\"");
            return false;
        }
        return true;
    }
}
