package com.wunhwan.hawthorn.core.transfer;

import java.util.Map;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public class TargetDescribe {

    private String protocol;

    private String route;

    private Map<String, String> parameters;

    public TargetDescribe(String protocol, String route, Map<String, String> parameters) {
        this.protocol = protocol;
        this.route = route;
        this.parameters = parameters;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "TargetDescribe{" +
                "protocol='" + protocol + '\'' +
                ", route='" + route + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
