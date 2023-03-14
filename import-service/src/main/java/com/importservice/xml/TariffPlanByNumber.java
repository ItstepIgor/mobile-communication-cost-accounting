package com.importservice.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TariffPlanByNumber {

    @XmlAttribute(name = "n")
    private String n;
    @XmlAttribute(name = "sd")
    private String sd;
    @XmlAttribute(name = "ed")
    private String ed;

}
