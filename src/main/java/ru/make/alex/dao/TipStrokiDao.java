package ru.make.alex.dao;

import ru.make.alex.model.TipStroki;

import java.util.List;

/**
 * Created by pas on 18.07.2017.
 */
public interface TipStrokiDao
{
    List<TipStroki> getAll();

    TipStroki saveTipStroki(TipStroki saveObj);

    void deleteTipStroki(TipStroki delObj);

    TipStroki findByName(String name);
}
