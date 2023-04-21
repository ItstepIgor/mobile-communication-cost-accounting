package com.importservice.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TaxMonthlyCallServiceByNumberMTS {

    @XmlAttribute(name = "tr")
    private String tr;
    @XmlAttribute(name = "t")
    private String t;
}
