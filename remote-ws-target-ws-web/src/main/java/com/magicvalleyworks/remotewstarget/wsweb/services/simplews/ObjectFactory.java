
package com.magicvalleyworks.remotewstarget.wsweb.services.simplews;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.magicvalleyworks.remotewstarget.web.services.simplews package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ProcessDataOutputType_QNAME = new QName("http://remotewstarget.magicvalleyworks.com/", "processDataOutputType");
    private final static QName _ProcessDataInputType_QNAME = new QName("http://remotewstarget.magicvalleyworks.com/", "processDataInputType");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.magicvalleyworks.remotewstarget.web.services.simplews
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://remotewstarget.magicvalleyworks.com/", name = "processDataOutputType")
    public JAXBElement<String> createProcessDataOutputType(String value) {
        return new JAXBElement<String>(_ProcessDataOutputType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://remotewstarget.magicvalleyworks.com/", name = "processDataInputType")
    public JAXBElement<String> createProcessDataInputType(String value) {
        return new JAXBElement<String>(_ProcessDataInputType_QNAME, String.class, null, value);
    }

}
