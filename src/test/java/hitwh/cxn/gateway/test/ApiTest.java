package hitwh.cxn.gateway.test;

import hiwth.cxn.gateway.core.bind.MapperRegistry;
import hiwth.cxn.gateway.core.mapping.HttpStatement;
import hiwth.cxn.gateway.core.mapping.HttpType;
import hiwth.cxn.gateway.core.session.Configuration;
import hiwth.cxn.gateway.core.socket.GatewayServer;
import io.netty.channel.Channel;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ApiTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        Configuration configuration = new Configuration();
        MapperRegistry mapperRegistry = new MapperRegistry(configuration);

        HttpStatement httpStatement1 = new HttpStatement("hello-service",
                "hitwh.cxn.rpc.provider.interfaces.HelloService",
                "hello", HttpType.GET,
                "java.lang.String", "/hello", true);

        HttpStatement httpStatement2 = new HttpStatement("hello-service",
                "hitwh.cxn.rpc.provider.interfaces.HelloService",
                "getName", HttpType.POST,
                "hitwh.cxn.rpc.provider.dao.Student", "/getName", true);

        mapperRegistry.addMapper(httpStatement1);
        mapperRegistry.addMapper(httpStatement2);

        configuration.setReferenceRegistry(mapperRegistry);

        GatewayServer gatewayServer = new GatewayServer(configuration);
        Future<Channel> future = Executors.newFixedThreadPool(2).submit(gatewayServer);
        Channel channel = future.get();

        while(!channel.isActive()) {
            System.out.println("正在监听...");
            Thread.sleep(5000);
        }

        Thread.sleep(Integer.MAX_VALUE);

    }
}
