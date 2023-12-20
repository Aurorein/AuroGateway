package hiwth.cxn.gateway.core.session;

import hiwth.cxn.gateway.core.auth.AuthService;
import hiwth.cxn.gateway.core.auth.IAuth;
import hiwth.cxn.gateway.core.bind.MapperRegistry;
import hiwth.cxn.gateway.core.datasource.Connection;
import hiwth.cxn.gateway.core.executor.Executor;
import hiwth.cxn.gateway.core.executor.GatewayExecutor;
import hiwth.cxn.gateway.core.mapping.HttpStatement;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private Map<String, HttpStatement> statementMap = new HashMap<>();

    private IAuth authService = new AuthService();
    private MapperRegistry referenceRegistry;
    private Map<String, ApplicationConfig> applicationConfigMap = new HashMap<>();

    private Map<String, RegistryConfig> registryConfigMap = new HashMap<>();

    private Map<String, ReferenceConfig<GenericService>> referenceConfigMap = new HashMap<>();

    public void setReferenceRegistry(MapperRegistry referenceRegistry) {
        this.referenceRegistry = referenceRegistry;
    }

    public MapperRegistry getReferenceRegistry() {
        return this.referenceRegistry;
    }

    public Configuration() {

        // TODO 改为从配置文件获取
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("hello-service");
        applicationConfig.setQosEnable(false);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setRegister(false);

        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface("hitwh.cxn.rpc.provider.interfaces.HelloService");
        referenceConfig.setVersion("1.0.0");
        referenceConfig.setGeneric("true");

        applicationConfigMap.put("hello-service", applicationConfig);
        registryConfigMap.put("hello-service", registryConfig);
        referenceConfigMap.put("hitwh.cxn.rpc.provider.interfaces.HelloService", referenceConfig);

    }

    public Executor newExecutor(Connection connection) {return new GatewayExecutor(connection, this);}

    public ApplicationConfig getApplicationConfig(String key) {
        return applicationConfigMap.get(key);
    }

    public RegistryConfig getRegistryConfig(String key) {
        return registryConfigMap.get(key);
    }

    public ReferenceConfig<GenericService> getReference(String key) {
        return referenceConfigMap.get(key);
    }

    public void addHttpStatement(HttpStatement statement) {
        statementMap.put(statement.getUri(), statement);
    }

    public HttpStatement getHttpStatement(String uri) {
        return statementMap.get(uri);
    }

    public boolean auth(String uId, String token) {return authService.validate(uId, token);}
}
