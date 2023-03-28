package com.importservice.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement(name = "Report")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportMTS {
    @XmlAttribute(name = "layerType")
    private String layerType;

    @XmlElement(name = "b")
    private List<ImportPeriodMTS> b = new ArrayList<>();
    @XmlElementWrapper(name="pcs")
    @XmlElement(name="pc")
    private List<TariffPlanListMTS> pc = new ArrayList<>();
    @XmlElement(name = "pod")
    private List<MonthlyCallServiceMTS> pod = new ArrayList<>();

    @XmlElement(name = "nd")
    private List<CallsFromNumberMTS> nd = new ArrayList<>();

}
