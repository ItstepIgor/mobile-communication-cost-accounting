package com.calculateservice.service.impl;

import com.calculateservice.entity.IndividualResultPojo;
import com.calculateservice.entity.PhoneNumberPojo;
import com.calculateservice.entity.ResultPojo;
import com.calculateservice.service.IndividualResultService;
import com.calculateservice.service.PhoneNumberService;
import com.calculateservice.service.ReportService;
import com.calculateservice.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
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

    @Override
    @SneakyThrows
    public JasperPrint createReportResult(long mobileOperatorId, LocalDate localDate) {
        List<ResultPojo> resultPojo = resultService.getResult(mobileOperatorId);
        JRBeanCollectionDataSource dataSource;
        JasperReport jasperReport;

        InputStream reportResult = ReportService.class.getClassLoader().getResourceAsStream("result.jrxml");
        jasperReport = JasperCompileManager.compileReport(reportResult);
        dataSource = new JRBeanCollectionDataSource(resultPojo);
//
//        } else if (addPayTypeId == 2) {
//            System.out.println(addPayTypeId);
//            List<ComplicationAndMotivationPojo> complicationAndMotivationPojo =
//                    staffListRepository.findByAllComplication(addPayTypeId);
//            InputStream reportComplication = ReportService.class.getClassLoader().getResourceAsStream("ReportComplicationAndMotivation.jrxml");
//            jasperReport = JasperCompileManager.compileReport(reportComplication);
//            dataSource = new JRBeanCollectionDataSource(complicationAndMotivationPojo);
//        } else if (addPayTypeId == 3) {
//            System.out.println(addPayTypeId);
//            List<ComplicationAndMotivationPojo> complicationAndMotivationPojo =
//                    staffListRepository.findByAllMotivation(addPayTypeId);
//            InputStream reportComplication = ReportService.class.getClassLoader().getResourceAsStream("ReportComplicationAndMotivation.jrxml");
//            jasperReport = JasperCompileManager.compileReport(reportComplication);
//            dataSource = new JRBeanCollectionDataSource(complicationAndMotivationPojo);
//        }
        Map<String, Object> parameters = new HashMap<>();

        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        parameters.put("NumberDateOrder", addPayFundService.getAddPayFundNumberOrder(addPayTypeId));
//        parameters.put("NumberOrderTradeUnion", addPayFundService.getAddPayFundNumberOrderTradeUnion(addPayTypeId));
        parameters.put("CalcDate", date);
        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }

    @Override
    public JasperPrint createReportIndividualResult() {
        List<IndividualResultPojo> individualResult = individualResultService.getIndividualResult();
        return null;
    }

    @Override
    public JasperPrint createReportListPhoneNumber(long mobileOperatorId) {
        List<PhoneNumberPojo> listPhoneNumber = phoneNumberService.getListPhoneNumber(mobileOperatorId);
        return null;
    }
}
