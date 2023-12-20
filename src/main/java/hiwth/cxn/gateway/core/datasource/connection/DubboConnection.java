package hiwth.cxn.gateway.core.datasource.connection;

import hiwth.cxn.gateway.core.datasource.Connection;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;

public class DubboConnection implements Connection {
    private GenericService genericService;

    public DubboConnection(ApplicationConfig applicationConfig, RegistryConfig registryConfig, ReferenceConfig<GenericService> referenceConfig) {
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(applicationConfig)
                .registry(registryConfig)
                .reference(referenceConfig)
                .start();

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(referenceConfig);
        this.genericService = genericService;

    }

    @Override
    public Object execute(String method, String[] paramTypes, Object[] params) {

        return genericService.$invoke(method, paramTypes, params);
    }
}
