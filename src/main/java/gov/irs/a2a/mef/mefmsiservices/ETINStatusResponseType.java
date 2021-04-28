//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.16 at 04:05:12 PM IST 
//


package gov.irs.a2a.mef.mefmsiservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ETINStatusResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ETINStatusResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ETIN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="StatusTxt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FormStatusGrp" type="{http://www.irs.gov/a2a/mef/MeFMSIServices}FormStatusGrpType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ETINStatusResponseType", propOrder = {
    "etin",
    "statusTxt",
    "formStatusGrp"
})
public class ETINStatusResponseType {

    @XmlElement(name = "ETIN", required = true)
    protected String etin;
    @XmlElement(name = "StatusTxt", required = true)
    protected String statusTxt;
    @XmlElement(name = "FormStatusGrp")
    protected List<FormStatusGrpType> formStatusGrp;

    /**
     * Gets the value of the etin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getETIN() {
        return etin;
    }

    /**
     * Sets the value of the etin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setETIN(String value) {
        this.etin = value;
    }

    /**
     * Gets the value of the statusTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusTxt() {
        return statusTxt;
    }

    /**
     * Sets the value of the statusTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusTxt(String value) {
        this.statusTxt = value;
    }

    /**
     * Gets the value of the formStatusGrp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formStatusGrp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormStatusGrp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FormStatusGrpType }
     * 
     * 
     */
    public List<FormStatusGrpType> getFormStatusGrp() {
        if (formStatusGrp == null) {
            formStatusGrp = new ArrayList<FormStatusGrpType>();
        }
        return this.formStatusGrp;
    }

}
