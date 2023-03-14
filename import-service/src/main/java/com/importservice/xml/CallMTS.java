package com.importservice.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CallMTS {

    @XmlAttribute(name = "d")
    private String d;
    @XmlAttribute(name = "n")
    private String n;
    @XmlAttribute(name = "zp")
    private String zp;
    @XmlAttribute(name = "zv")
    private String zv;
    @XmlAttribute(name = "s")
    private String s;
    @XmlAttribute(name = "du")
    private String du;
    @XmlAttribute(name = "awt")
    private String awt;
    @XmlAttribute(name = "a")
    private String a;

}
