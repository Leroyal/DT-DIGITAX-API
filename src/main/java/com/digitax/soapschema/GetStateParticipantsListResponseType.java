//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.15 at 08:09:43 PM IST 
//


package com.digitax.soapschema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetStateParticipantsListResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetStateParticipantsListResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Cnt" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="StateParticipantGrp" type="{http://www.irs.gov/a2a/mef/MeFMSIServices}StateParticipantGrpType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetStateParticipantsListResponseType", propOrder = {
    "cnt",
    "stateParticipantGrp"
})
public class GetStateParticipantsListResponseType {

    @XmlElement(name = "Cnt", defaultValue = "0")
    protected int cnt;
    @XmlElement(name = "StateParticipantGrp")
    protected List<StateParticipantGrpType> stateParticipantGrp;

    /**
     * Gets the value of the cnt property.
     * 
     */
    public int getCnt() {
        return cnt;
    }

    /**
     * Sets the value of the cnt property.
     * 
     */
    public void setCnt(int value) {
        this.cnt = value;
    }

    /**
     * Gets the value of the stateParticipantGrp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stateParticipantGrp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStateParticipantGrp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StateParticipantGrpType }
     * 
     * 
     */
    public List<StateParticipantGrpType> getStateParticipantGrp() {
        if (stateParticipantGrp == null) {
            stateParticipantGrp = new ArrayList<StateParticipantGrpType>();
        }
        return this.stateParticipantGrp;
    }

}
