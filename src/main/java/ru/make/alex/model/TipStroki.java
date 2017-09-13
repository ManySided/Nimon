package ru.make.alex.model;

import org.springframework.jdbc.core.RowMapper;
import ru.make.alex.db.table.FieldTableApp;
import ru.make.alex.db.table.ITableApp;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by pas on 13.06.2017.
 */
public class TipStroki implements ITableApp
{
    public static final String TABLE_NAME = "tip_stroki";
    public static final String ROW_ID = "id";
    public static final String ROW_NAZVANIE = "nazvanie";

    private Long id;
    private String nazvanie;

    public TipStroki()
    {
    }

    public TipStroki(String nazvanie)
    {
        setNazvanie(nazvanie);
    }

    public TipStroki(Long id)
    {
        setId(id);
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

    public String getNazvanie()
    {
        return nazvanie;
    }

    public void setNazvanie(String nazvanie)
    {
        this.nazvanie = nazvanie;
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
                new FieldTableApp(ROW_NAZVANIE, FieldTableApp.STRING)};
    }

    @Override
    public RowMapper getRowMapper()
    {
        return new RowMapper<TipStroki>()
        {
            @Override
            public TipStroki mapRow(ResultSet resultSet, int i) throws SQLException
            {
                TipStroki tipStroki = new TipStroki();
                tipStroki.setId(resultSet.getLong(ROW_ID));
                tipStroki.setNazvanie(resultSet.getString(ROW_NAZVANIE));
                return tipStroki;
            }
        };
    }

    @Override
    public Object[] getListData()
    {
        return new Object[]{ getNazvanie()};
    }

    @Override
    public String toString()
    {
        return getNazvanie();
    }
}
