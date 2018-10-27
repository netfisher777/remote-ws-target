package com.magicvalleyworks.remotewstarget.wsweb.services.simplews;

import com.magicvalleyworks.remotewstarget.service.api.ILocalSimpleEjb;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name = "SimpleWebService",
        serviceName = "SimpleWebService",
        endpointInterface = "com.magicvalleyworks.remotewstarget.wsweb.services.simplews.SimpleWebService",
        targetNamespace = "http://remotewstarget.magicvalleyworks.com/")
public class SimpleWebServiceImpl implements SimpleWebService {

    @EJB
    private ILocalSimpleEjb simpleEjb;

    @Override
    @WebMethod
    public String processData(String data) {
        return simpleEjb.processData(data);
    }
}
