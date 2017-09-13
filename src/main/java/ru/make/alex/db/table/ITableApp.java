package ru.make.alex.db.table;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * Created by pas on 13.06.2017.
 */
public interface ITableApp {
    String getName();
    FieldTableApp[] getListField();
    RowMapper getRowMapper();
    Object[] getListData();
    Long getId();
    void setId(Long id);
}
