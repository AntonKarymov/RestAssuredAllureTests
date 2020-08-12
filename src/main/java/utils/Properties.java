package utils;

import ru.qatools.properties.Property;
import ru.qatools.properties.PropertyLoader;
import ru.qatools.properties.Resource;

@Resource.Classpath("config.properties")
public interface Properties {
    Properties PROPERTIES = PropertyLoader.newInstance().populate(Properties.class);

    @Property("service.host")
    String getHost();

    @Property("service.port")
    String getPort();

    @Property("service.username")
    String getUsername();

    @Property("service.password")
    String getPassword();

    @Property("service.dbName")
    String getDbName();
}