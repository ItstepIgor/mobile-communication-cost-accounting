package com.calculateservice.controller;

import com.calculateservice.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;


    @GetMapping(value = "/result/{id}")
    @ResponseBody
    public void getReportResult(@PathVariable long id, HttpServletResponse responseReportBonus) throws JRException, IOException {
        responseReportBonus.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=result.pdf");
        JasperPrint jasperPrint = reportService.createReportResult(id);
        responseReportBonus.setContentType("application/x-pdf");
        final OutputStream outStream = responseReportBonus.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @GetMapping(value = "/individualresult")
    @ResponseBody
    public void getReportIndividualResult(HttpServletResponse responseReportBonus) throws JRException, IOException {
        responseReportBonus.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=individual_result.pdf");
        JasperPrint jasperPrint = reportService.createReportIndividualResult();
        responseReportBonus.setContentType("application/x-pdf");
        final OutputStream outStream = responseReportBonus.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @GetMapping(value = "/phone/{id}")
    @ResponseBody
    public void getReportPhone(@PathVariable long id, HttpServletResponse responseReportBonus) throws JRException, IOException {
        responseReportBonus.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=phone.pdf");
        JasperPrint jasperPrint = reportService.createReportListPhoneNumber(id);
        responseReportBonus.setContentType("application/x-pdf");
        final OutputStream outStream = responseReportBonus.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }
}
