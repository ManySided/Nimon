package ru.make.alex.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import ru.make.alex.db.table.FieldTableApp;
import ru.make.alex.db.table.ITableApp;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pas on 13.06.2017.
 */
@Service("dbUtils")
public class DbUtilsImpl implements DbUtils
{
    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initJdbs()
    {
        jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Override
    public void checkDB()
    {
        try
        {
            Connection connection = dataSource.getConnection();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTables(ITableApp[] listTables)
    {
        for (int i = 0; i < listTables.length; i++)
        {
            ITableApp table = listTables[i];
            String tableName = table.getName();
            FieldTableApp[] listField = table.getListField();

            String sql_count = "SELECT count(*) FROM sqlite_master " + "WHERE type='table' AND name='" + tableName + "'";
            System.out.println(sql_count);
            Integer count = jdbcTemplate.queryForObject(sql_count, Integer.class);
            if (count.equals(Integer.valueOf(0)))
            {
                String sql = "CREATE TABLE " + tableName;
                String fieldDescr = "";
                String forIndex = "";
                for (int j = 0; j < listField.length; j++)
                {
                    if (j == 0)
                    {
                        fieldDescr += getFieldOfTable(listField[j]);
                    }
                    else
                    {
                        fieldDescr += "," + getFieldOfTable(listField[j]);
                    }

                    if (listField[j].isPk())
                    {
                        forIndex = tableName + "_" + listField[j].getTitle() + " ON " + tableName + " (" + listField[j].getTitle() + ")";
                    }
                }
                sql += " (" + fieldDescr + ")";
                System.out.println(sql);
                jdbcTemplate.execute(sql);
                sql = "CREATE UNIQUE INDEX " + forIndex;
                System.out.println(sql);
                jdbcTemplate.execute(sql);
            }
            else
            {
                String sql = "pragma table_info(" + tableName + ")";
                System.out.println(sql);
                List<FieldTableApp> oldFields = jdbcTemplate.query(sql, new RowMapper<FieldTableApp>()
                {
                    @Override
                    public FieldTableApp mapRow(ResultSet resultSet, int i) throws SQLException
                    {
                        FieldTableApp field = new FieldTableApp(resultSet.getString("name"), "");
                        return field;
                    }
                });
                for (FieldTableApp newField : listField)
                {
                    if (!isExitsField(newField.getTitle(), oldFields))
                    {
                        sql = "ALTER TABLE " + tableName + " ADD COLUMN " + newField.getTitle() + " " + newField.getTip();
                        System.out.println(sql);
                        jdbcTemplate.execute(sql);
                    }
                }
            }
        }
    }

    private String getFieldOfTable(FieldTableApp fieldTableApp)
    {
        String strField = fieldTableApp.getTitle() + " " + fieldTableApp.getTip();
        if (fieldTableApp.isPk())
        {
            strField += " PRIMARY KEY AUTOINCREMENT";
        }
        return strField;
    }

    private boolean isExitsField(String titleField, List<FieldTableApp> listField)
    {
        for (FieldTableApp field : listField)
        {
            if (field.getTitle().equals(titleField))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public List select(ITableApp table)
    {
        String sql = "SELECT * FROM " + table.getName();
        return makeSelect(table, sql);
    }

    @Override
    public List select(ITableApp table, List<String> conditions)
    {
        String sql = "SELECT * FROM " + table.getName();
        sql += " WHERE ";
        for (int i = 0; i < conditions.size(); i++)
        {
            if(i!=0)
                sql+=" AND ";
            sql += conditions.get(i);
        }
        return makeSelect(table, sql);
    }

    private List makeSelect(ITableApp table, String sql)
    {
        System.out.println(sql);
        List list = jdbcTemplate.query(sql, table.getRowMapper());
        if (list != null && !list.isEmpty())
        {
            return list;
        }
        return new ArrayList();
    }

    @Override
    public ITableApp createData(ITableApp table)
    {
        String sql = "INSERT INTO " + table.getName() + " (";
        FieldTableApp[] listField = table.getListField();
        String params = "";
        for (int i = 0; i < listField.length; i++)
        {
            if (listField[i].isPk())
            {
                continue;
            }
            if (!params.equals(""))
            {
                sql += ",";
                params += ",";
            }
            sql += listField[i].getTitle();
            params += "?";
        }
        sql += ") VALUES (" + params + ")";
        Long id = makeInsert(sql, table.getListData());
        table.setId(id);
        return table;
    }

    private Long makeInsert(final String sql, final Object[] params)
    {
        System.out.println(sql);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator()
        {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException
            {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, new int[]{1});
                for (int i = 0; i < params.length; i++)
                {
                    preparedStatement.setObject(i + 1, params[i]);
                }
                return preparedStatement;
            }
        };
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void updateData(ITableApp table)
    {
        String sql = "UPDATE " + table.getName() + " SET ";
        FieldTableApp[] listField = table.getListField();
        boolean isNotStart = false;
        for (int i = 0; i < listField.length; i++)
        {
            if (listField[i].isPk())
            {
                continue;
            }
            if (isNotStart)
            {
                sql += ", ";
            }
            sql += listField[i].getTitle() + " = ?";
            isNotStart = true;
        }
        sql += " WHERE id=" + table.getId();
        System.out.println(sql);
        jdbcTemplate.update(sql, table.getListData());
    }

    @Override
    public void deleteData(ITableApp table)
    {
        String sql = "DELETE FROM " + table.getName() + " WHERE id=" + table.getId();
        System.out.println(sql);
        jdbcTemplate.update(sql);
    }
}
