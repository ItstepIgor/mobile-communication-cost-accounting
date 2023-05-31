package com.calculateservice.service.impl;

import com.calculateservice.entity.IndividualResultPojo;
import com.calculateservice.entity.PhoneNumberPojo;
import com.calculateservice.entity.ResultPojo;
import com.calculateservice.service.IndividualResultService;
import com.calculateservice.service.PhoneNumberService;
import com.calculateservice.service.ReportService;
import com.calculateservice.service.ResultService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ResultService resultService;

    private final IndividualResultService individualResultService;

    private final PhoneNumberService phoneNumberService;

    @Override
    public JasperPrint createReportResult(long mobileOperatorId) {
        List<ResultPojo> result = resultService.getResult(mobileOperatorId);
        for (ResultPojo pojo : result) {
            System.out.println(pojo.getOwner() + ' ' + pojo.getNumber() + ' ' + pojo.getSum());
        }
//        JRBeanCollectionDataSource dataSource = null;
//        JasperReport jasperReport = null;
//
//        if (addPayTypeId == 1) {
//            System.out.println(addPayTypeId);
//            List<BonusPojo> bonusPojo = staffListRepository.findByAllBonus(addPayTypeId);
//            InputStream reportBonus = ReportService.class.getClassLoader().getResourceAsStream("ReportBonus.jrxml");
//            jasperReport = JasperCompileManager.compileReport(reportBonus);
//            dataSource = new JRBeanCollectionDataSource(bonusPojo);
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
//        Map<String, Object> parameters = new HashMap<>();
//        LocalDate localDate = calcSettingsService.getMaxDateCalcSettings().getCalcDate();
//        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        parameters.put("NumberDateOrder", addPayFundService.getAddPayFundNumberOrder(addPayTypeId));
//        parameters.put("NumberOrderTradeUnion", addPayFundService.getAddPayFundNumberOrderTradeUnion(addPayTypeId));
//        parameters.put("CalcDate", date);
//        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return null;
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
