package hiwth.cxn.gateway.core.executor.result;

public class GatewayResult {

    private int code;

    private String msg;

    private Object data;

    private GatewayResult(){}

    private GatewayResult(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    private GatewayResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    private GatewayResult setData(Object data) {
        this.data = data;
        return this;
    }

    public static GatewayResult buildSuccess(Object data) {
        return new GatewayResult(200).setData(data);
    }

    public static GatewayResult buildSuccess(String msg, Object data) {
        return new GatewayResult(200).setMsg(msg).setData(data);
    }

    public static GatewayResult buildFailure(String msg) {
        return new GatewayResult(201).setMsg(msg);
    }
}
