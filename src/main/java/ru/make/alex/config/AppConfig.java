package ru.make.alex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.make.alex.dao.StrokiDao;
import ru.make.alex.dao.StrokiDaoImpl;
import ru.make.alex.dao.TipStrokiDao;
import ru.make.alex.dao.TipStrokiDaoImpl;
import ru.make.alex.db.DbUtils;
import ru.make.alex.db.DbUtilsImpl;
import ru.make.alex.db.table.ITableApp;
import ru.make.alex.db.versions.IObnovlenie;
import ru.make.alex.db.versions.VersionController;
import ru.make.alex.db.versions.VersionControllerImpl;
import ru.make.alex.model.StrokaMonitoringa;
import ru.make.alex.model.TipStroki;
import ru.make.alex.ui.controller.EditStrokaController;
import ru.make.alex.ui.controller.RootController;
import ru.make.alex.ui.controller.SettingController;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pas on 13.06.2017.
 */
@Configuration
public class AppConfig
{
    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:Nikimonit_DB");
        return dataSource;
    }

    @Bean
    public DbUtils dbUtils()
    {
        return new DbUtilsImpl();
    }

    @Bean
    public VersionController versionController()
    {
        IObnovlenie obnovlenie[] = {new IObnovlenie()
        {
            @Override
            public void runUpdsteSystem(DbUtils dbUtils)
            {
                Map<String, TipStroki> map = new HashMap<String, TipStroki>();
                List<TipStroki> list = dbUtils.select(new TipStroki());
                for (TipStroki t : list)
                {
                    map.put(t.getNazvanie(), t);
                }

                List<StrokaMonitoringa> stroki = (List<StrokaMonitoringa>) dbUtils.select(new StrokaMonitoringa());
                for (StrokaMonitoringa row : stroki)
                {
                    String tipStroki = row.getTipStroki();
                    TipStroki data = null;
                    if (map.containsKey(tipStroki))
                    {
                        data = map.get(tipStroki);
                    }
                    else
                    {
                        data = (TipStroki) dbUtils.createData(new TipStroki(tipStroki));
                        map.put(tipStroki, data);
                    }
                    row.setTipStrokiObj(data);
                    dbUtils.updateData(row);
                }
            }
        }};
        return new VersionControllerImpl(obnovlenie);
    }

    @Bean
    public RootController rootController()
    {
        return new RootController();
    }

    @Bean
    public EditStrokaController editStrokaController()
    {
        return new EditStrokaController();
    }

    @Bean
    public SettingController settingController()
    {
        return new SettingController();
    }

    @Bean
    public TipStrokiDao tipStrokiDao()
    {
        return new TipStrokiDaoImpl();
    }

    @Bean
    public StrokiDao strokiDao()
    {
        return new StrokiDaoImpl();
    }
}
