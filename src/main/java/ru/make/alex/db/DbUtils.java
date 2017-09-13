package ru.make.alex.db;

import ru.make.alex.db.table.ITableApp;

import java.util.List;

/**
 * Created by pas on 13.06.2017.
 */
public interface DbUtils
{
    void checkDB();

    void updateTables(ITableApp[] listTables);

    List select(ITableApp table);

    List select(ITableApp table, List<String> conditions);

    ITableApp createData(ITableApp table);

    void updateData(ITableApp table);

    void deleteData(ITableApp table);
}
