package org.smartweb.web;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

/**
 * @PROJECT_NAME smartweb
 * @PACKAGE_NAME org.smartweb.web
 * @USER takou
 * @CREATE 2018/4/5
 **/
public class Request {

    private String method;

    private String resource;

    public Request(String method, String resource) {
        this.method = method;
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (method != null ? !method.equals(request.method) : request.method != null) return false;
        return resource != null ? resource.equals(request.resource) : request.resource == null;
    }

    @Override
    public int hashCode() {
        int result = method != null ? method.hashCode() : 0;
        result = 31 * result + (resource != null ? resource.hashCode() : 0);
        return result;
    }
}
