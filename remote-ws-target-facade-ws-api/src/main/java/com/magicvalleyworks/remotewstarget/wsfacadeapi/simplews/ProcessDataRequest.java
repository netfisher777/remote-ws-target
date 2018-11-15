
package com.magicvalleyworks.remotewstarget.wsfacadeapi.simplews;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="processDataRequest" type="{http://remotewstarget.magicvalleyworks.com/}data"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "processDataRequest"
})
@XmlRootElement(name = "processDataRequest")
public class ProcessDataRequest {

    @XmlElement(required = true)
    protected Data processDataRequest;

    /**
     * Gets the value of the processDataRequest property.
     * 
     * @return
     *     possible object is
     *     {@link Data }
     *     
     */
    public Data getProcessDataRequest() {
        return processDataRequest;
    }

    /**
     * Sets the value of the processDataRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link Data }
     *     
     */
    public void setProcessDataRequest(Data value) {
        this.processDataRequest = value;
    }

}
