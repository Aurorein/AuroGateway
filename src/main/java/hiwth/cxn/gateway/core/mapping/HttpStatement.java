package hiwth.cxn.gateway.core.mapping;

public class HttpStatement {

    private String applicationName;

    private String referenceName;

    private String methodName;

    private HttpType httpType;

    private String parameterType;

    private String uri;

    private boolean auth;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public HttpType getHttpType() {
        return httpType;
    }

    public void setHttpType(HttpType httpType) {
        this.httpType = httpType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public HttpStatement(String applicationName, String referenceName, String methodName, HttpType httpType, String parameterType , String uri, boolean auth) {
        this.applicationName = applicationName;
        this.referenceName = referenceName;
        this.methodName = methodName;
        this.parameterType = parameterType;
        this.httpType = httpType;
        this.uri = uri;
        this.auth = auth;
    }
}
