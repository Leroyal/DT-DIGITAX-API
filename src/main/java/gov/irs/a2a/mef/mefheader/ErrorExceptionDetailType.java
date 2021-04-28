//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.16 at 07:57:57 PM IST 
//


package gov.irs.a2a.mef.mefheader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErrorExceptionDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ErrorExceptionDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ErrorMessageTxt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ErrorClassificationCd" type="{http://www.irs.gov/a2a/mef/MeFHeader}ErrorClassificationCdType"/>
 *         &lt;element name="ErrorMessageCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HostNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorExceptionDetailType", propOrder = {
    "errorMessageTxt",
    "errorClassificationCd",
    "errorMessageCd",
    "hostNm"
})
public class ErrorExceptionDetailType {

    @XmlElement(name = "ErrorMessageTxt", required = true)
    protected String errorMessageTxt;
    @XmlElement(name = "ErrorClassificationCd", required = true)
    protected ErrorClassificationCdType errorClassificationCd;
    @XmlElement(name = "ErrorMessageCd")
    protected String errorMessageCd;
    @XmlElement(name = "HostNm")
    protected String hostNm;

    /**
     * Gets the value of the errorMessageTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessageTxt() {
        return errorMessageTxt;
    }

    /**
     * Sets the value of the errorMessageTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessageTxt(String value) {
        this.errorMessageTxt = value;
    }

    /**
     * Gets the value of the errorClassificationCd property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorClassificationCdType }
     *     
     */
    public ErrorClassificationCdType getErrorClassificationCd() {
        return errorClassificationCd;
    }

    /**
     * Sets the value of the errorClassificationCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorClassificationCdType }
     *     
     */
    public void setErrorClassificationCd(ErrorClassificationCdType value) {
        this.errorClassificationCd = value;
    }

    /**
     * Gets the value of the errorMessageCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessageCd() {
        return errorMessageCd;
    }

    /**
     * Sets the value of the errorMessageCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessageCd(String value) {
        this.errorMessageCd = value;
    }

    /**
     * Gets the value of the hostNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostNm() {
        return hostNm;
    }

    /**
     * Sets the value of the hostNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostNm(String value) {
        this.hostNm = value;
    }

}