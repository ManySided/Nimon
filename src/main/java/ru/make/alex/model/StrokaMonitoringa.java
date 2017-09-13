package ru.make.alex.model;

import org.springframework.jdbc.core.RowMapper;
import ru.make.alex.db.table.FieldTableApp;
import ru.make.alex.db.table.ITableApp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by pas on 13.06.2017.
 */
public class StrokaMonitoringa implements ITableApp
{
    public static final String TABLE_NAME = "stroki";
    public static final String ROW_ID = "id";
    public static final String ROW_TIP = "tip";
    public static final String ROW_TIP_REF = "tip_id";
    public static final String ROW_DATE_CREATE = "dateCreate";
    public static final String ROW_DATE_LAST = "dateLast";
    public static final String ROW_NAZVANIE = "nazvanie";
    public static final String ROW_COMMENT = "comment";

    private Long id;
    private String tipStroki;
    private TipStroki tipStrokiObj;
    private LocalDate dateCreate;
    private LocalDate dateLost;
    private String nazvanie;
    private String komment;

    public StrokaMonitoringa()
    {
    }

    @Override
    public Long getId()
    {
        return id;
    }

    @Override
    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTipStroki()
    {
        return tipStroki;
    }

    public void setTipStroki(String tipStroki)
    {
        this.tipStroki = tipStroki;
    }

    public TipStroki getTipStrokiObj()
    {
        return tipStrokiObj;
    }

    public void setTipStrokiObj(TipStroki tipStrokiObj)
    {
        this.tipStrokiObj = tipStrokiObj;
    }

    public LocalDate getDateCreate()
    {
        return dateCreate;
    }

    public void setDateCreate(LocalDate dateCreate)
    {
        this.dateCreate = dateCreate;
    }

    public LocalDate getDateLost()
    {
        return dateLost;
    }

    public void setDateLost(LocalDate dateLost)
    {
        this.dateLost = dateLost;
    }

    public String getNazvanie()
    {
        return nazvanie;
    }

    public void setNazvanie(String nazvanie)
    {
        this.nazvanie = nazvanie;
    }

    public String getKomment()
    {
        return komment;
    }

    public void setKomment(String komment)
    {
        this.komment = komment;
    }

    @Override
    public String getName()
    {
        return TABLE_NAME;
    }

    @Override
    public FieldTableApp[] getListField()
    {
        return new FieldTableApp[]{new FieldTableApp(ROW_ID, FieldTableApp.INT, true),
                new FieldTableApp(ROW_DATE_CREATE, FieldTableApp.DATE),
                new FieldTableApp(ROW_DATE_LAST, FieldTableApp.DATE),
                new FieldTableApp(ROW_NAZVANIE, FieldTableApp.STRING),
                new FieldTableApp(ROW_COMMENT, FieldTableApp.STRING), new FieldTableApp(ROW_TIP, FieldTableApp.STRING),
                new FieldTableApp(ROW_TIP_REF, FieldTableApp.INT),};
    }

    @Override
    public Object[] getListData()
    {
        return new Object[]{getDateCreate(), getDateLost(), getNazvanie(), getKomment(),
                getTipStroki(), getTipStrokiObj().getId()
        };
    }

    @Override
    public RowMapper getRowMapper()
    {
        return new RowMapper<StrokaMonitoringa>()
        {
            @Override
            public StrokaMonitoringa mapRow(ResultSet resultSet, int i) throws SQLException
            {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                StrokaMonitoringa strokaMonitoringa = new StrokaMonitoringa();
                strokaMonitoringa.setId(resultSet.getLong(ROW_ID));
                strokaMonitoringa.setTipStroki(resultSet.getString(ROW_TIP));
                strokaMonitoringa.setTipStrokiObj(new TipStroki(resultSet.getLong(ROW_TIP_REF)));
                strokaMonitoringa.setDateCreate(LocalDate.parse(resultSet.getString(ROW_DATE_CREATE), formatter));
                strokaMonitoringa.setDateLost(LocalDate.parse(resultSet.getString(ROW_DATE_LAST), formatter));
                strokaMonitoringa.setNazvanie(resultSet.getString(ROW_NAZVANIE));
                strokaMonitoringa.setKomment(resultSet.getString(ROW_COMMENT));
                return strokaMonitoringa;
            }
        };
    }
}
