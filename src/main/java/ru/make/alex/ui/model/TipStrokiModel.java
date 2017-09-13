package ru.make.alex.ui.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.make.alex.model.TipStroki;

public class TipStrokiModel
{
    private TipStroki tipStroki;

    private final LongProperty idTip;
    private final StringProperty nameTip;

    public TipStrokiModel(Long id, String name)
    {
        super();
        this.idTip = new SimpleLongProperty(id);
        this.nameTip = new SimpleStringProperty(name);
    }

    public TipStrokiModel(TipStroki tipStroki)
    {
        this(tipStroki.getId(), tipStroki.getNazvanie());
        this.tipStroki = tipStroki;
    }

    public TipStroki getGeneral()
    {
        return this.tipStroki;
    }

    public LongProperty idTipProperty()
    {
        return this.idTip;
    }

    public StringProperty nameTipProperty()
    {
        return this.nameTip;
    }

    public Long getIdTip()
    {
        return this.idTip.get();
    }

    public void getIdTip(Long idTip)
    {
        this.idTip.set(idTip);
    }

    public String getNameTip()
    {
        return this.nameTip.get();
    }

    public void getNameTip(String nameTip)
    {
        this.nameTip.set(nameTip);
    }
}
