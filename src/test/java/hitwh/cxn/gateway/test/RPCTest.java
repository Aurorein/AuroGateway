package hitwh.cxn.gateway.test;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.Test;

public class RPCTest {

    @Test
    public void test_rpc() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("hello-service");
        applicationConfig.setQosEnable(false);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setRegister(false);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("hitwh.cxn.rpc.provider.interfaces.HelloService");
        reference.setVersion("1.0.0");
        reference.setGeneric("true");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(applicationConfig)
                .registry(registryConfig)
                .reference(reference)
                .start();

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);

        Object res = genericService.$invoke("hello", new String[]{"java.lang.String"}, new Object[]{"cxn"});
        System.out.println(res);
    }
}
