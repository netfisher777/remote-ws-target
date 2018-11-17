package com.magicvalleyworks.remotewstarget.wsfacade.services.simplews;

import com.magicvalleyworks.remotewstarget.wsfacadeapi.Data;
import com.magicvalleyworks.remotewstarget.wsfacadeapi.SimpleWebService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Date;

@WebService(name = "SimpleWebService",
        serviceName = "SimpleWebService", // Must be specified to override default
        endpointInterface = "com.magicvalleyworks.remotewstarget.wsfacadeapi.SimpleWebService",
        targetNamespace = "http://remotewstarget.magicvalleyworks.com/")
public class SimpleWebServiceImpl implements SimpleWebService {

    @Override
    @WebMethod
    public Data processDataRequest(Data data) {
        String changedData = String.format("Your data: %s; Current time in ms: %d", data.getData(), new Date().getTime());
        data.setData(changedData);
        return data;
    }
}
