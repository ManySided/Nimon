package ru.make.alex.ui.model;

import javafx.beans.property.*;
import ru.make.alex.model.StrokaMonitoringa;

import java.time.LocalDate;

public class StrokaMonitoringaModel
{
    private StrokaMonitoringa stroka;

    private final LongProperty idStroki;
    private final StringProperty typeStroki;
    private final ObjectProperty<LocalDate> dateCreate;
    private final ObjectProperty<LocalDate> dateLost;
    private final StringProperty nazvanie;
    private final StringProperty komment;

    public StrokaMonitoringaModel(Long idStroki, String typeStroki, LocalDate dateCreate, LocalDate dateLost,
            String nazvanie, String komment)
    {
        super();
        this.idStroki = new SimpleLongProperty(idStroki);
        this.typeStroki = new SimpleStringProperty(typeStroki);
        this.dateCreate = new SimpleObjectProperty<LocalDate>(dateCreate);
        this.dateLost = new SimpleObjectProperty<LocalDate>(dateLost);
        this.nazvanie = new SimpleStringProperty(nazvanie);
        this.komment = new SimpleStringProperty(komment);
    }

    public StrokaMonitoringaModel(StrokaMonitoringa stroka)
    {
        this(stroka.getId(), stroka.getTipStroki(), stroka.getDateCreate(), stroka.getDateLost(), stroka.getNazvanie(),
                stroka.getKomment());
        this.stroka = stroka;
    }

    public StrokaMonitoringa getStroka()
    {
        return stroka;
    }

    public LongProperty idStrokiProperty()
    {
        return this.idStroki;
    }

    public StringProperty typeStrokiProperty()
    {
        return this.typeStroki;
    }

    public ObjectProperty<LocalDate> dateCreateProperty()
    {
        return this.dateCreate;
    }

    public ObjectProperty<LocalDate> dateLostProperty()
    {
        return this.dateLost;
    }

    public StringProperty nazvanieProperty()
    {
        return this.nazvanie;
    }

    public StringProperty kommentProperty()
    {
        return this.komment;
    }

    public Long getIdStroki()
    {
        return this.idStroki.get();
    }

    public void setIdStroki(Long idStroki)
    {
        this.idStroki.set(idStroki);
    }

    public String getTypeStroki()
    {
        return this.typeStroki.get();
    }

    public void setTypeStroki(String typeStroki)
    {
        this.typeStroki.set(typeStroki);
    }

    public LocalDate getDateCreate()
    {
        return this.dateCreate.get();
    }

    public void setDateCreate(LocalDate dateCreate)
    {
        this.dateCreate.set(dateCreate);
    }

    public LocalDate getDateLost()
    {
        return this.dateLost.get();
    }

    public void setDateLost(LocalDate dateLost)
    {
        this.dateLost.set(dateLost);
    }

    public String getNazvanie()
    {
        return this.nazvanie.get();
    }

    public void setNazvanie(String nazvanie)
    {
        this.nazvanie.set(nazvanie);
    }

    public String getKomment()
    {
        return this.komment.get();
    }

    public void setKomment(String komment)
    {
        this.komment.set(komment);
    }
}
