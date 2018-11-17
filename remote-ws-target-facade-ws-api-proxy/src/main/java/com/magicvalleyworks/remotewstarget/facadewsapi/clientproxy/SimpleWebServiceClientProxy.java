package com.magicvalleyworks.remotewstarget.facadewsapi.clientproxy;

import com.magicvalleyworks.remotewstarget.wsfacadeapi.Data;
import com.magicvalleyworks.remotewstarget.wsfacadeapi.SimpleWebService;
import com.magicvalleyworks.remotewstarget.wsfacadeapi.SimpleWebService_Service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.xml.ws.BindingProvider;

@ApplicationScoped
public class SimpleWebServiceClientProxy implements SimpleWebService {
    private SimpleWebService simpleWebServicePort;

    @PostConstruct
    private void initialize() {
        SimpleWebService_Service service = new SimpleWebService_Service();
        this.simpleWebServicePort = service.getSimpleWebServicePort();
        BindingProvider bp = (BindingProvider)this.simpleWebServicePort;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://magicvalleyworks.com/rwst/SimpleWebService");
    }

    @Override
    public Data processDataRequest(Data processDataRequest) {
        return simpleWebServicePort.processDataRequest(processDataRequest);
    }
}
