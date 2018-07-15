package org.smartweb.aop;

public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
