package ru.make.alex.dao;

import ru.make.alex.model.StrokaMonitoringa;
import ru.make.alex.model.TipStroki;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by pas on 18.07.2017.
 */
public interface StrokiDao
{
    List<StrokaMonitoringa> getAll();

    List<StrokaMonitoringa> getAllByFiltr(LocalDate begin, LocalDate end, boolean isCreate, boolean isLost, String name,
            String comment, TipStroki type);

    StrokaMonitoringa saveStroka(StrokaMonitoringa saveObj);

    void deleteStroka(StrokaMonitoringa delObj);
}
