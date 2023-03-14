package com.importservice.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CallsFromNumberMTS {
    @XmlAttribute(name = "n")
    private String n;
    @XmlAttribute(name = "u")
    private String u;
    @XmlAttribute(name = "l")
    private String l;
    @XmlAttribute(name = "en")
    private String en;
    @XmlAttribute(name = "awt")
    private String awt;
    @XmlAttribute(name = "a")
    private String a;
    @XmlElement(name="i")
    private List<CallMTS> i = new ArrayList<>();
}
