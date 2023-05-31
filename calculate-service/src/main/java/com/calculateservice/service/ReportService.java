package com.calculateservice.service;

import net.sf.jasperreports.engine.JasperPrint;

public interface ReportService {

    JasperPrint createReportResult(long mobileOperatorId);

    JasperPrint createReportIndividualResult();

    JasperPrint createReportListPhoneNumber(long mobileOperatorId);
}
