package hiwth.cxn.gateway.core.datasource.unpooled;

import hiwth.cxn.gateway.core.datasource.DataSourceType;
import hiwth.cxn.gateway.core.datasource.connection.DubboConnection;
import hiwth.cxn.gateway.core.datasource.Connection;
import hiwth.cxn.gateway.core.datasource.DataSource;
import hiwth.cxn.gateway.core.mapping.HttpStatement;
import hiwth.cxn.gateway.core.session.Configuration;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

public class UnpooledDataSource implements DataSource {

    private DataSourceType dataSourceType;

    private Configuration configuration;

    private HttpStatement httpStatement;

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setHttpStatement(HttpStatement httpStatement) {
        this.httpStatement = httpStatement;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        switch(dataSourceType) {

            case Http:{
                // TODO 待实现
                break;
            }
            case Dubbo:{
                ApplicationConfig applicationConfig = configuration.getApplicationConfig(httpStatement.getApplicationName());
                RegistryConfig registryConfig = configuration.getRegistryConfig(httpStatement.getApplicationName());
                ReferenceConfig<GenericService> reference = configuration.getReference(httpStatement.getReferenceName());

                connection = new DubboConnection(applicationConfig, registryConfig, reference);
                break;
            }

        }
        return connection;
    }
}
