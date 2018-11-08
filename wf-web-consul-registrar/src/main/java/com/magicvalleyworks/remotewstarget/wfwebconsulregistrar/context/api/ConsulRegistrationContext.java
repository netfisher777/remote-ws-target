package com.magicvalleyworks.remotewstarget.wfwebconsulregistrar.context.api;

public interface ConsulRegistrationContext {
    String getWebServicesConfigurationFileName();
    void setWebServicesConfigurationFileName(String fileName);
    String getConsulHostOverride();
    void setConsulHostOverride(String host);
    String getConsulPortOverride();
    void setConsulPortOverride(String port);
    String getWebApplicationContextPath();
    void setWebApplicationContextPath(String contextPath);
}