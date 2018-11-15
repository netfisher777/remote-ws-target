
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
 *         &lt;element name="processDataResponse" type="{http://remotewstarget.magicvalleyworks.com/}data"/>
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
    "processDataResponse"
})
@XmlRootElement(name = "processDataResponse")
public class ProcessDataResponse {

    @XmlElement(required = true)
    protected Data processDataResponse;

    /**
     * Gets the value of the processDataResponse property.
     * 
     * @return
     *     possible object is
     *     {@link Data }
     *     
     */
    public Data getProcessDataResponse() {
        return processDataResponse;
    }

    /**
     * Sets the value of the processDataResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Data }
     *     
     */
    public void setProcessDataResponse(Data value) {
        this.processDataResponse = value;
    }

}
