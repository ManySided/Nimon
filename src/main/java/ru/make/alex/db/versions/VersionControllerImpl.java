package ru.make.alex.db.versions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.make.alex.db.DbUtils;
import ru.make.alex.model.system.SystemProperties;

import java.util.List;

@Service("versionController")
public class VersionControllerImpl implements VersionController
{
    @Autowired
    private DbUtils dbUtils;

    private IObnovlenie[] list;

    public VersionControllerImpl(IObnovlenie[] list)
    {
        this.list = list;
    }

    @Override
    public void checkAndUpdate()
    {
        SystemProperties systemProperties = null;
        List select = dbUtils.select(new SystemProperties());
        if (select == null || select.isEmpty())
        {
            dbUtils.createData(new SystemProperties(Long.valueOf(0)));
            select = dbUtils.select(new SystemProperties());
        }
        systemProperties = (SystemProperties) select.get(0);

        int versionCurrent = list.length;
        int versionSystem = systemProperties.getVersion().intValue();
        if (versionSystem < versionCurrent)
        {
            for (int i = versionSystem; i < versionCurrent; i++)
            {
                list[i].runUpdsteSystem(dbUtils);
            }
            systemProperties.setVersion(Long.valueOf(versionCurrent));
            dbUtils.updateData(systemProperties);
        }
    }
}
