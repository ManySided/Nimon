package ru.make.alex.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.make.alex.db.DbUtils;
import ru.make.alex.model.TipStroki;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pas on 18.07.2017.
 */
public class TipStrokiDaoImpl implements TipStrokiDao
{
    @Autowired
    private DbUtils dbUtils;

    @Override
    public List<TipStroki> getAll()
    {
        return (List<TipStroki>) dbUtils.select(new TipStroki());
    }

    @Override
    public TipStroki saveTipStroki(TipStroki saveObj)
    {
        if (saveObj.getId() == null)
        {
            saveObj = (TipStroki) dbUtils.createData(saveObj);
        }
        else
        {
            dbUtils.updateData(saveObj);
        }
        return saveObj;
    }

    @Override
    public void deleteTipStroki(TipStroki delObj)
    {
        dbUtils.deleteData(delObj);
    }

    @Override
    public TipStroki findByName(String name)
    {
        List<TipStroki> select = dbUtils.select(new TipStroki(),
                Arrays.asList(TipStroki.ROW_NAZVANIE + " = '" + name + "'"));
        if (select != null && !select.isEmpty())
        {
            return select.get(0);
        }
        return null;
    }
}
