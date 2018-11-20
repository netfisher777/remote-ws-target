package com.magicvalleyworks.remotewstarget.facadewsapi.clientproxy;

import com.magicvalleyworks.remotewstarget.wsfacadeapi.SimpleWebService;
import com.magicvalleyworks.remotewstarget.wsfacadeapi.SimpleWebService_Service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class SimpleWebServiceBeanFactory extends AbstractConsulBasedWebServiceFactory<SimpleWebService, SimpleWebService_Service> {
    @Produces
    @ApplicationScoped
    @Override
    public SimpleWebService createWebService() {
        return createPort(new SimpleWebService_Service(), SimpleWebService.class, "SimpleWebService");
    }
}
