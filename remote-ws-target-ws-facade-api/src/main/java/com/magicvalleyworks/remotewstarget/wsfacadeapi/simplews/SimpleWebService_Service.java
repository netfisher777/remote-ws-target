
package com.magicvalleyworks.remotewstarget.wsfacadeapi.simplews;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "SimpleWebService", targetNamespace = "http://remotewstarget.magicvalleyworks.com/", wsdlLocation = "/wsdl/simpleWebService.wsdl")
public class SimpleWebService_Service
    extends Service
{

    private final static URL SIMPLEWEBSERVICE_WSDL_LOCATION;
    private final static WebServiceException SIMPLEWEBSERVICE_EXCEPTION;
    private final static QName SIMPLEWEBSERVICE_QNAME = new QName("http://remotewstarget.magicvalleyworks.com/", "SimpleWebService");

    static {
        SIMPLEWEBSERVICE_WSDL_LOCATION = com.magicvalleyworks.remotewstarget.wsfacadeapi.simplews.SimpleWebService_Service.class.getResource("/wsdl/simpleWebService.wsdl");
        WebServiceException e = null;
        if (SIMPLEWEBSERVICE_WSDL_LOCATION == null) {
            e = new WebServiceException("Cannot find '/wsdl/simpleWebService.wsdl' wsdl. Place the resource correctly in the classpath.");
        }
        SIMPLEWEBSERVICE_EXCEPTION = e;
    }

    public SimpleWebService_Service() {
        super(__getWsdlLocation(), SIMPLEWEBSERVICE_QNAME);
    }

    public SimpleWebService_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), SIMPLEWEBSERVICE_QNAME, features);
    }

    public SimpleWebService_Service(URL wsdlLocation) {
        super(wsdlLocation, SIMPLEWEBSERVICE_QNAME);
    }

    public SimpleWebService_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SIMPLEWEBSERVICE_QNAME, features);
    }

    public SimpleWebService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SimpleWebService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns SimpleWebService
     */
    @WebEndpoint(name = "SimpleWebServicePort")
    public SimpleWebService getSimpleWebServicePort() {
        return super.getPort(new QName("http://remotewstarget.magicvalleyworks.com/", "SimpleWebServicePort"), SimpleWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SimpleWebService
     */
    @WebEndpoint(name = "SimpleWebServicePort")
    public SimpleWebService getSimpleWebServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://remotewstarget.magicvalleyworks.com/", "SimpleWebServicePort"), SimpleWebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SIMPLEWEBSERVICE_EXCEPTION!= null) {
            throw SIMPLEWEBSERVICE_EXCEPTION;
        }
        return SIMPLEWEBSERVICE_WSDL_LOCATION;
    }

}
