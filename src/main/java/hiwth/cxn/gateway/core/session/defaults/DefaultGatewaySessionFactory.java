package hiwth.cxn.gateway.core.session.defaults;

import hiwth.cxn.gateway.core.datasource.unpooled.UnpooledDataSourceFactory;
import hiwth.cxn.gateway.core.datasource.Connection;
import hiwth.cxn.gateway.core.datasource.DataSource;
import hiwth.cxn.gateway.core.executor.Executor;
import hiwth.cxn.gateway.core.session.Configuration;
import hiwth.cxn.gateway.core.session.IGatewaySessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultGatewaySessionFactory implements IGatewaySessionFactory {
    private Logger logger = LoggerFactory.getLogger(DefaultGatewaySessionFactory.class);

    private Configuration configuration;

    public DefaultGatewaySessionFactory(Configuration configuration){this.configuration = configuration;}


    @Override
    public DefaultGatewaySession openSession(String uri) {
        UnpooledDataSourceFactory dataSourceFactory = new UnpooledDataSourceFactory();
        dataSourceFactory.setProperties(configuration, uri);
        DataSource dataSource = dataSourceFactory.getDataSource();

        Connection connection = dataSource.getConnection();
        Executor executor = configuration.newExecutor(connection);
        return new DefaultGatewaySession(executor,configuration, uri);

    }
}
