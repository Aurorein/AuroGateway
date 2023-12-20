package hiwth.cxn.gateway.core.datasource.unpooled;

import hiwth.cxn.gateway.core.datasource.DataSourceType;
import hiwth.cxn.gateway.core.datasource.DataSource;
import hiwth.cxn.gateway.core.datasource.DataSourceFactory;
import hiwth.cxn.gateway.core.session.Configuration;

public class UnpooledDataSourceFactory implements DataSourceFactory {
    private UnpooledDataSource dataSource;

    public UnpooledDataSourceFactory() {this.dataSource = new UnpooledDataSource();}

    @Override
    public void setProperties(Configuration configuration, String uri) {
        this.dataSource.setConfiguration(configuration);
        this.dataSource.setHttpStatement(configuration.getHttpStatement(uri));
        this.dataSource.setDataSourceType(DataSourceType.Dubbo);
    }
    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
