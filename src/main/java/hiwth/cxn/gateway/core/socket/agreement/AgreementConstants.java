package hiwth.cxn.gateway.core.socket.agreement;

import hiwth.cxn.gateway.core.mapping.HttpStatement;
import io.netty.util.AttributeKey;

public class AgreementConstants {

    public static final AttributeKey<HttpStatement> HTTP_STATEMENT = AttributeKey.valueOf("HttpStatement");

    public enum ResponseCode {

        _200(200, "访问成功!"),

        _400(400, "请求格式无法正确识别!"),

        _403(403, "服务器拒绝请求"),

        _404(404, "资源不存在"),

        _500(500, "服务器内部错误!");

        private int code;

        private String msg;

        ResponseCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode(){return this.code;}

        public String getMsg(){return this.msg;}
    }
}
