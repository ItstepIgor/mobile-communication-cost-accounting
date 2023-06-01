package com.calculateservice.controller;

import com.calculateservice.service.ReportService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

@RestController
@RequestMapping("report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    private static final String CONTENT_TYPE = "application/x-pdf";

    @GetMapping(value = "/result")
    @ResponseBody
    public void getReportResult(@RequestParam(value = "localDate")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                                        fallbackPatterns = {"dd/MM/yy", "dd.MM.yyyy", "dd-MM-yyyy"})
                                @Parameter(description = "Дата счета (последний день месяца): dd/MM/yy, dd.MM.yyyy, dd-MM-yyyy")
                                LocalDate localDate,
                                @Parameter(description = "Код оператора: 1 - А1, 2 - МТС")
                                @RequestParam long id, HttpServletResponse responseReportBonus) throws JRException, IOException {
        responseReportBonus.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=result.pdf");
        JasperPrint jasperPrint = reportService.createReportResult(id, localDate);
        responseReportBonus.setContentType(CONTENT_TYPE);
        final OutputStream outStream = responseReportBonus.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @GetMapping(value = "/individualresult")
    @ResponseBody
    public void getReportIndividualResult(HttpServletResponse responseReportBonus) throws JRException, IOException {
        responseReportBonus.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=individual_result.pdf");
        JasperPrint jasperPrint = reportService.createReportIndividualResult();
        responseReportBonus.setContentType(CONTENT_TYPE);
        final OutputStream outStream = responseReportBonus.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    @GetMapping(value = "/phone/{id}")
    @ResponseBody
    public void getReportPhone(@Parameter(description = "Код оператора: 1 - А1, 2 - МТС")
                               @PathVariable long id, HttpServletResponse responseReportBonus) throws JRException, IOException {
        responseReportBonus.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=phone.pdf");
        JasperPrint jasperPrint = reportService.createReportListPhoneNumber(id);
        responseReportBonus.setContentType(CONTENT_TYPE);
        final OutputStream outStream = responseReportBonus.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }
}
