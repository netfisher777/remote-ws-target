package com.magicvalleyworks.remotewstarget.wfconsulregistrar.startup;

import com.magicvalleyworks.remotewstarget.wfconsulregistrar.api.ConsulRegistrar;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class ConsulRegistrarStartupBean {
    @Inject
    private ConsulRegistrar consulRegistrar;

    @PostConstruct
    public void initialize() {
        registerServices();
    }

    private void registerServices() {
        consulRegistrar.registerWebService("SimpleWebService", "rwst/SimpleWebService?wsdl");
    }


    @PreDestroy
    public void terminate() {
        consulRegistrar.deregisterServices();
    }
}
