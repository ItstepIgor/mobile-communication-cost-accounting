package com.calculateservice.service;

import net.sf.jasperreports.engine.JasperPrint;

import java.time.LocalDate;

public interface ReportService {

    JasperPrint createReportResult(long mobileOperatorId, LocalDate localDate);

    JasperPrint createReportIndividualResult();

    JasperPrint createReportListPhoneNumber(long mobileOperatorId);
}
