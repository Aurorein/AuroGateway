package hiwth.cxn.gateway.core.socket.agreement;

import com.alibaba.fastjson2.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder;
import org.jboss.netty.util.internal.ReusableIterator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestParser {

    private final FullHttpRequest request;

    public RequestParser(FullHttpRequest request) {
        this.request = request;
    }

    public String getUri() {
        String uri = request.getUri();
        int index = uri.indexOf("?");
        uri = (index > 0) ? uri.substring(0, index) : uri;

        return uri;
    }

    public Map<String, Object> parse() {

        String contentType = getContentType();
        HttpMethod method = request.getMethod();

        if(method == HttpMethod.GET) {
            Map<String, Object> params = new HashMap<>();
            String uri = request.getUri();
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
            queryStringDecoder.parameters().forEach( (k, v) -> params.put( k, v.get(0)));
            return params;
        } else if(method == HttpMethod.POST) {
            switch(contentType) {
                case "multipart/form-data": {
                    Map<String, Object> res = new HashMap<>();
                    HttpPostRequestDecoder postRequestDecoder = new HttpPostRequestDecoder(request);
                    InterfaceHttpPostRequestDecoder decoder = postRequestDecoder.offer(request);
                    postRequestDecoder.getBodyHttpDatas().forEach(
                            data -> {
                                try {
                                    res.put(data.getName(), ((Attribute)data).getValue());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    );
                    return res;
                }
                case "application/json": {
                    ByteBuf byteBuf = (request).content().copy();
                    String bufString = null;
                    if(byteBuf.isReadable()) {
                        bufString = byteBuf.toString(StandardCharsets.UTF_8);
                    }
                    return JSON.parseObject(bufString);

                }
                default:{
                    throw new RuntimeException("未定义的协议类型!");
                }
            }

        } else {
            throw new RuntimeException("未定义的方法类型!");
        }

    }

    public String getContentType() {
        Optional<Map.Entry<String, String>> mapEntry = request.headers().entries().stream().filter(
                entry -> entry.getKey().equals("Content-Type")
        ).findAny();

        assert mapEntry != null;
        Map.Entry<String, String> entry = mapEntry.orElse(null);
        if (null == entry) return "none";
        // application/json、multipart/form-data;
        String contentType = entry.getValue();
        int idx = contentType.indexOf(";");
        if (idx > 0) {
            return contentType.substring(0, idx);
        } else {
            return contentType;
        }
    }

}
