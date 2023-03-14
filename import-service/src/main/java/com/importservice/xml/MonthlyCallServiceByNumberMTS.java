package com.importservice.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MonthlyCallServiceByNumberMTS {

    @XmlAttribute(name = "n")
    private String n;
    @XmlAttribute(name = "u")
    private String u;
    @XmlAttribute(name = "awt")
    private String awt;
    @XmlAttribute(name = "a")
    private String a;
    @XmlElementWrapper(name="ii")
    @XmlElement(name="i")
    private List<CostMonthlyCallServiceByNumberMTS> i;
}
