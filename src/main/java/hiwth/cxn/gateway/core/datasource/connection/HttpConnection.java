package hiwth.cxn.gateway.core.datasource.connection;

import hiwth.cxn.gateway.core.datasource.Connection;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

public class HttpConnection implements Connection {
    private HttpClient httpClient;

    private PostMethod postMethod;

    public HttpConnection(String uri) {
        httpClient = new HttpClient();

        postMethod = new PostMethod(uri);
        postMethod.addRequestHeader("accept", "*/*");
        postMethod.addRequestHeader("connection", "Keep-Alive");
        postMethod.addRequestHeader("Content-Type", "application/json;charset=GBK");
        postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
    }

    @Override
    public Object execute(String Method, String[] paramTypes, Object[] params) {
        Object res = null;
        try {

            int code = httpClient.executeMethod(postMethod);
            if(code == 200) {
                res = postMethod.getResponseBodyAsString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return res.toString();
    }
}
