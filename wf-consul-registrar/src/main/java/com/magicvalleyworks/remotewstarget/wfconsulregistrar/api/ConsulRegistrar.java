package com.magicvalleyworks.remotewstarget.wfconsulregistrar.api;

import com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.api.WebServiceRegConfig;

public interface ConsulRegistrar {
    void registerWebService(WebServiceRegConfig webServiceRegConfig);
    void deregisterServices();
}
