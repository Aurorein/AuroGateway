package hiwth.cxn.gateway.core.datasource;

import hiwth.cxn.gateway.core.session.Configuration;

public interface DataSourceFactory {

    DataSource getDataSource();

    public void setProperties(Configuration configuration, String uri);
}
