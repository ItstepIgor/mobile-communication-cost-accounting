package com.importservice.service.impl;

import com.importservice.entity.AllExpensesByPhoneNumber;
import com.importservice.entity.Call;
import com.importservice.entity.MonthlyCallService;
import com.importservice.entity.TariffByNumber;
import com.importservice.reposiitory.AllExpensesByPhoneNumberRepository;
import com.importservice.reposiitory.CallRepository;
import com.importservice.reposiitory.MonthlyCallServiceRepository;
import com.importservice.reposiitory.TariffByNumberRepository;
import com.importservice.service.producer.Producer;
import com.importservice.xml.*;
import com.importservice.service.CallServiceMTS;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CallServiceMTSImpl implements CallServiceMTS {

    private final Producer producer;
    private final CallRepository callRepository;
    private final AllExpensesByPhoneNumberRepository allExpensesByPhoneNumberRepository;
    private final MonthlyCallServiceRepository monthlyCallServiceRepository;
    private final TariffByNumberRepository tariffByNumberRepository;

    @SneakyThrows
    @Override
    @Transactional
    public void saveToDataBaseFromFileMTS(ReportMTS reportMTS) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        List<Call> callList = fillingCallList(reportMTS, dateTimeFormatter);
        callRepository.saveAll(callList);
        producer.sendCall(callList);


        List<TariffByNumber> tarriffList = fillingTariffList(reportMTS, dateTimeFormatter);
        tariffByNumberRepository.saveAll(tarriffList);

        Map<String, Object> stringObjectMap = fillingExpensesByPhoneNumber(reportMTS, dateTimeFormatter);
        @SuppressWarnings("unchecked")
        List<AllExpensesByPhoneNumber> allExpensesByPhoneNumbers = (List<AllExpensesByPhoneNumber>) stringObjectMap.get("AllExpensesByPhoneNumber");
        allExpensesByPhoneNumberRepository.saveAll(allExpensesByPhoneNumbers);

        @SuppressWarnings("unchecked")
        List<MonthlyCallService> monthlyCallServices = (List<MonthlyCallService>) stringObjectMap.get("MonthlyCallService");
        monthlyCallServiceRepository.saveAll(monthlyCallServices);
    }

    private Map<String, Object> fillingExpensesByPhoneNumber(ReportMTS reportMTS,  DateTimeFormatter dateTimeFormatter) {
        List<MonthlyCallService> monthlyCallServices = new ArrayList<>();
        LocalDate date = LocalDate.parse(reportMTS.getB().get(0).getBd(), dateTimeFormatter);
        List<AllExpensesByPhoneNumber> allExpensesByPhoneNumbers = new ArrayList<>();
        MonthlyCallServiceMTS monthlyCallServiceMTS = reportMTS.getPod().get(0);
        List<MonthlyCallServiceByNumberMTS> monthlyCallServiceByNumberMTS = monthlyCallServiceMTS.getDs();
        for (MonthlyCallServiceByNumberMTS monthlyCallServiceByNumber : monthlyCallServiceByNumberMTS) {
            AllExpensesByPhoneNumber allExpensesByPhoneNumber = new AllExpensesByPhoneNumber();
            allExpensesByPhoneNumber.setNumber(monthlyCallServiceByNumber.getN());
            allExpensesByPhoneNumber.setOwner(monthlyCallServiceByNumber.getU());
            allExpensesByPhoneNumber.setInvoiceDate(date);
            allExpensesByPhoneNumber.setSum(BigDecimal.valueOf(Double.parseDouble(monthlyCallServiceByNumber.getAwt())));
            allExpensesByPhoneNumber.setSumWithNDS(BigDecimal.valueOf(Double.parseDouble(monthlyCallServiceByNumber.getA())));

            MonthlyCallService monthlyCallService = new MonthlyCallService();
            monthlyCallService.setNumber(monthlyCallServiceByNumber.getN());
            monthlyCallService.setOwner(monthlyCallServiceByNumber.getU());
            List<CostMonthlyCallServiceByNumberMTS> costMonthlyCallServiceByNumberMTS = monthlyCallServiceByNumber.getI();
            for (CostMonthlyCallServiceByNumberMTS costMonthlyCallServiceByNumber : costMonthlyCallServiceByNumberMTS) {
                monthlyCallService.setMonthlyCallServiceName(costMonthlyCallServiceByNumber.getN());
                monthlyCallService.setStartDate(LocalDate.parse(costMonthlyCallServiceByNumber.getD().substring(0, 10), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                monthlyCallService.setEndDate(LocalDate.parse(costMonthlyCallServiceByNumber.getD().substring(13, 23), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                monthlyCallService.setInvoiceDate(date);
                monthlyCallService.setSum(BigDecimal.valueOf(Double.parseDouble(costMonthlyCallServiceByNumber.getAwt())));
                monthlyCallService.setSumWithNDS(BigDecimal.valueOf(Double.parseDouble(costMonthlyCallServiceByNumber.getA())));
                monthlyCallServices.add(monthlyCallService);
            }
            allExpensesByPhoneNumbers.add(allExpensesByPhoneNumber);

        }
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("AllExpensesByPhoneNumber", allExpensesByPhoneNumbers);
        stringObjectMap.put("MonthlyCallService", monthlyCallServices);

        return stringObjectMap;
    }

    private List<TariffByNumber> fillingTariffList(ReportMTS reportMTS,  DateTimeFormatter dateTimeFormatter) {
        List<TariffByNumber> tarriffList = new ArrayList<>();
        List<TariffPlanListMTS> reportMTSPc = reportMTS.getPc();
        for (TariffPlanListMTS tariffPlanListMTS : reportMTSPc) {
            TariffByNumber tariffByNumber = new TariffByNumber();
            List<TariffPlanByNumber> fromNumberMTSI = tariffPlanListMTS.getI();
            tariffByNumber.setNumber(tariffPlanListMTS.getN());
            tariffByNumber.setOwner(tariffPlanListMTS.getU());
            for (TariffPlanByNumber tariffPlanByNumber : fromNumberMTSI) {
                tariffByNumber.setTariffName(tariffPlanByNumber.getN());
                tariffByNumber.setStartDateTime(LocalDateTime.parse(tariffPlanByNumber.getSd(), dateTimeFormatter));
                tariffByNumber.setEndDateTime(LocalDateTime.parse(tariffPlanByNumber.getEd(), dateTimeFormatter));
                tarriffList.add(tariffByNumber);
            }
        }
        return tarriffList;
    }

    private List<Call> fillingCallList(ReportMTS reportMTS,  DateTimeFormatter dateTimeFormatter) {
        List<Call> callList = new ArrayList<>();
        List<CallsFromNumberMTS> reportMTSNd = reportMTS.getNd();
        for (CallsFromNumberMTS callsFromNumberMTS : reportMTSNd) {
            List<CallMTS> fromNumberMTSI = callsFromNumberMTS.getI();
            for (CallMTS callMTS : fromNumberMTSI) {
                Call call = new Call();
                call.setOwnerNumber(callsFromNumberMTS.getN());
                if ((callMTS.getAwt().equals("0.0")) || (callMTS.getAwt().equals("0"))) {
                    continue;
                }
                call.setCallDateTime(LocalDateTime.parse(callMTS.getD(), dateTimeFormatter));
                call.setCallService(callMTS.getZv());
                call.setCode(callMTS.getS());
                call.setCallTime(callMTS.getDu());
                call.setNumber(callMTS.getN());
                call.setSum(BigDecimal.valueOf(Double.parseDouble(callMTS.getAwt())));
                call.setShortNumber(Long.parseLong(call.getOwnerNumber().substring(3, 12)));
                call.setDayOfWeek(call.getCallDateTime().getDayOfWeek().getValue());
                call.setMobileOperator("MTS");
                callList.add(call);
            }
        }
        return callList;
    }
}
