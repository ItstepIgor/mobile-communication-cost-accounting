package com.calculateservice.service.impl;

import com.calculateservice.entity.IndividualResultPojo;
import com.calculateservice.entity.PhoneNumberPojo;
import com.calculateservice.entity.ResultPojo;
import com.calculateservice.service.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ResultService resultService;

    private final IndividualResultService individualResultService;

    private final PhoneNumberService phoneNumberService;

    private final MobileOperatorService mobileOperatorService;


    private static final String PAID_CALL = "Платно";
    private static final String DAY_CALL = "Днем";
    private static final String FREE_CALL = "Бесплатно";

    @Override
    @SneakyThrows
    public JasperPrint createReportResult(long mobileOperatorId, LocalDate localDate) {
        List<ResultPojo> resultPojo = resultService.getResult(mobileOperatorId);
        JRBeanCollectionDataSource dataSource;
        JasperReport jasperReport;

        InputStream reportResult = ReportService.class.getClassLoader().getResourceAsStream("result.jrxml");
        dataSource = new JRBeanCollectionDataSource(resultPojo);
        jasperReport = JasperCompileManager.compileReport(reportResult);
        Map<String, Object> parameters = getMap();

        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        parameters.put("CalcDate", date);
        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }

    @Override
    @SneakyThrows
    public JasperPrint createReportIndividualResult() {
        List<IndividualResultPojo> individualResult = individualResultService.getIndividualResult();
        JRBeanCollectionDataSource dataSource;
        JasperReport jasperReport;
        String number = individualResult.stream().map(IndividualResultPojo::getNumber).distinct().findFirst().orElse(null);
        String owner = individualResult.stream().map(IndividualResultPojo::getOwner).findAny().orElse(null);
        BigDecimal paidCallSum = getSum(individualResult, PAID_CALL);
        BigDecimal dayCallSum = getSum(individualResult, DAY_CALL);
        BigDecimal freeCallSum = getSum(individualResult, FREE_CALL);
        InputStream reportResult = ReportService.class.getClassLoader().getResourceAsStream("indresult.jrxml");
        dataSource = new JRBeanCollectionDataSource(individualResult);
        jasperReport = JasperCompileManager.compileReport(reportResult);
        Map<String, Object> parameters = getMap();
        parameters.put("number", number);
        parameters.put("owner", owner);
        parameters.put("paidCallSum", paidCallSum);
        parameters.put("dayCallSum", dayCallSum);
        parameters.put("freeCallSum", freeCallSum);
        parameters.put("fullCallSum", paidCallSum.add(freeCallSum.add(dayCallSum)));
        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }

    @Override
    @SneakyThrows
    public JasperPrint createReportListPhoneNumber(long mobileOperatorId) {
        List<PhoneNumberPojo> listPhoneNumber = phoneNumberService.getListPhoneNumber(mobileOperatorId);
        JRBeanCollectionDataSource dataSource;
        JasperReport jasperReport;

        InputStream reportResult = ReportService.class.getClassLoader().getResourceAsStream("number.jrxml");
        dataSource = new JRBeanCollectionDataSource(listPhoneNumber);
        jasperReport = JasperCompileManager.compileReport(reportResult);
        Map<String, Object> parameters = getMap();
        parameters.put("MobileOperator", mobileOperatorService.findById(mobileOperatorId).getOperatorName());
        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }

    private static Map<String, Object> getMap() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("logo", ClassLoader.getSystemResource("img.jpg").getPath());
        parameters.put("logo1", ClassLoader.getSystemResourceAsStream("img.jpg"));
        return parameters;
    }

    private static BigDecimal getSum(List<IndividualResultPojo> individualResult, String paidCall) {
        BigDecimal sum = individualResult.stream()
                .filter(individualResultPojo -> individualResultPojo.getCallType().equals(paidCall))
                .map(IndividualResultPojo::getSum)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return  sum.multiply(BigDecimal.valueOf(0.25)).add(sum).setScale(2, RoundingMode.CEILING);
    }
}
