package pos.api.teampixl.org.server;

import org.springframework.context.ApplicationContext;

public class URL {

    private final String address;
    private final String port;
    private final String contextPath;
    
    public URL(ApplicationContext context) {
        address = context.getEnvironment().getProperty("server.address", "localhost");
        port = context.getEnvironment().getProperty("server.port", "8080");
        contextPath = context.getEnvironment().getProperty("server.servlet.context-path", "/");
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getPort() {
        return port;
    }
    
    public String getContextPath() {
        return contextPath;
    }
    
    public String getUrl() {
        return "http://" + address + ":" + port + contextPath;
    }
    
    @Override
    public String toString() {
        return getUrl();
    }
    
}
