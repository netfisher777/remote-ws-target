package com.magicvalleyworks.remotewstarget.wfconsulregistrar.api;

public interface ConsulRegistrar {
    void registerWebService(String serviceName, String httpHealthCheckPath);
    void deregisterServices();
}
