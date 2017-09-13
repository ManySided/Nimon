package ru.make.alex.model.system;

import org.springframework.jdbc.core.RowMapper;
import ru.make.alex.db.table.FieldTableApp;
import ru.make.alex.db.table.ITableApp;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemProperties implements ITableApp
{
    public static final String TABLE_NAME = "system_properties";
    public static final String ROW_ID = "id";
    public static final String ROW_VERSION = "version";

    private Long id;
    private Long version;

    public SystemProperties()
    {
    }

    public SystemProperties(Long version)
    {
        setVersion(version);
    }

    public Long getVersion()
    {
        return version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
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
                new FieldTableApp(ROW_VERSION, FieldTableApp.INT)};
    }

    @Override
    public RowMapper getRowMapper()
    {
        return new RowMapper()
        {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException
            {
                SystemProperties systemProperties = new SystemProperties();
                systemProperties.setId(resultSet.getLong(ROW_ID));
                systemProperties.setVersion(resultSet.getLong(ROW_VERSION));
                return systemProperties;
            }
        };
    }

    @Override
    public Object[] getListData()
    {
        return new Object[]{getVersion()};
    }

    @Override
    public Long getId()
    {
        return this.id;
    }

    @Override
    public void setId(Long id)
    {
        this.id = id;
    }
}
