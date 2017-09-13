package ru.make.alex.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.make.alex.db.DbUtils;
import ru.make.alex.model.StrokaMonitoringa;
import ru.make.alex.model.TipStroki;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pas on 18.07.2017.
 */
@Service("StrokiDao")
public class StrokiDaoImpl implements StrokiDao
{
    @Autowired
    private DbUtils dbUtils;

    @Override
    public List<StrokaMonitoringa> getAll()
    {
        return dbUtils.select(new StrokaMonitoringa());
    }

    @Override
    public List<StrokaMonitoringa> getAllByFiltr(LocalDate begin, LocalDate end, boolean isCreate, boolean isLost,
            String name, String comment, TipStroki tip)
    {
        List<String> conditions = new ArrayList<String>();
        if (isCreate)
        {
            addDateCondition(begin, end, conditions, StrokaMonitoringa.ROW_DATE_CREATE);
        }
        if (isLost)
        {
            addDateCondition(begin, end, conditions, StrokaMonitoringa.ROW_DATE_LAST);
        }
        if (name != null && !name.isEmpty())
        {
            conditions.add(StrokaMonitoringa.ROW_NAZVANIE + " like '%" + name + "%'");
        }
        if (comment != null && !comment.isEmpty())
        {
            conditions.add(StrokaMonitoringa.ROW_COMMENT + " like '%" + comment + "%'");
        }
        if (tip != null)
        {
            conditions.add(StrokaMonitoringa.ROW_TIP_REF + " = " + tip.getId());
        }
        return dbUtils.select(new StrokaMonitoringa(), conditions);
    }

    private void addDateCondition(LocalDate begin, LocalDate end, List<String> conditions, String fieldName)
    {
        if (begin != null)
        {
            conditions.add(fieldName + " >= '" + begin + "'");
        }
        if (end != null)
        {
            conditions.add(fieldName + " <= '" + end + "'");
        }
    }

    @Override
    public StrokaMonitoringa saveStroka(StrokaMonitoringa saveObj)
    {
        if (saveObj.getId() == null) saveObj = (StrokaMonitoringa) dbUtils.createData(saveObj);
        else dbUtils.updateData(saveObj);
        return saveObj;
    }

    @Override
    public void deleteStroka(StrokaMonitoringa delObj)
    {
        dbUtils.deleteData(delObj);
    }
}
