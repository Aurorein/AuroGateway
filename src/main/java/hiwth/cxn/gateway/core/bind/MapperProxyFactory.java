package hiwth.cxn.gateway.core.bind;

import hiwth.cxn.gateway.core.session.IGatewaySession;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import org.objectweb.asm.Type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MapperProxyFactory {
    private Map<String, IGenericReference> genericReferenceCache = new ConcurrentHashMap<>();

    public MapperProxyFactory(String uri){
        this.uri = uri;
    }

    private String uri;

    public IGenericReference getInstance(IGatewaySession gatewaySession) {
        return genericReferenceCache.computeIfAbsent(uri, k -> {
            MapperProxy mapperProxy = new MapperProxy(gatewaySession, uri);

            String methodName = gatewaySession.getConfiguration().getHttpStatement(uri).getMethodName();
            InterfaceMaker interfaceMaker = new InterfaceMaker();
            interfaceMaker.add(new Signature(methodName, Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
            Class<?> interfaceClass = interfaceMaker.create();

            Enhancer enhancer = new Enhancer();
            enhancer.setInterfaces(new Class<?>[]{IGenericReference.class, interfaceClass});
            enhancer.setSuperclass(Object.class);
            enhancer.setCallback(mapperProxy);

            return (IGenericReference) enhancer.create();
        });

    }
}
