package com.importservice.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TariffPlanListMTS {

    @XmlAttribute(name = "n")
    private String n;
    @XmlAttribute(name = "u")
    private String u;
    @XmlElementWrapper(name="tp")
    @XmlElement(name="i")
    private List<TariffPlanByNumber> i = new ArrayList<>();
}
