package com.importservice.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MonthlyCallServiceMTS {

    @XmlElementWrapper(name="dss")
    @XmlElement(name="ds")
    private List<MonthlyCallServiceByNumberMTS> ds;

}
