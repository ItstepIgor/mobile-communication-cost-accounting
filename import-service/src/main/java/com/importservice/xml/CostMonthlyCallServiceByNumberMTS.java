package com.importservice.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CostMonthlyCallServiceByNumberMTS {
    @XmlAttribute(name = "n")
    private String n;
    @XmlAttribute(name = "d")
    private String d;
    @XmlAttribute(name = "awt")
    private String awt;
    @XmlAttribute(name = "a")
    private String a;
}
