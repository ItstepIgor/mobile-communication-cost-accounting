package com.importservice.service.impl;

import com.importservice.dto.CallDTO;
import com.importservice.entity.*;
import com.importservice.reposiitory.*;
import com.importservice.service.mapper.CallListMapper;
import com.importservice.xml.*;
import com.importservice.service.CallServiceMTS;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
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

    private final CallRepository callRepository;
    private final AllExpensesByPhoneNumberRepository allExpensesByPhoneNumberRepository;
    private final AllCallServiceRepository allCallServiceRepository;
    private final TariffByNumberRepository tariffByNumberRepository;
    private final CallListMapper callListMapper;
    private final KafkaTemplate<String, List<CallDTO>> kafkaTemplate;

    @SneakyThrows
    @Override
    @Transactional
    public void saveToDataBaseFromFileMTS(ReportMTS reportMTS) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        LocalDate invoiceDate = LocalDate.parse(reportMTS.getB().get(0).getBd(), dateTimeFormatter);

        List<Call> callList = fillingCallList(reportMTS, dateTimeFormatter, invoiceDate);
        callRepository.saveAll(callList);
        kafkaTemplate.send("callMTS", callListMapper.listCallToListCallDto(callList));


        List<AllCallService> allCallServices = fillingCallService(reportMTS, invoiceDate);

        Map<String, Object> stringObjectMap = fillingTariffListAndAllExpenses(reportMTS, dateTimeFormatter, invoiceDate);

        @SuppressWarnings("unchecked")
        List<AllExpensesByPhoneNumber> allExpensesByPhoneNumbers =
                (List<AllExpensesByPhoneNumber>) stringObjectMap.get("AllExpensesByPhoneNumber");

        @SuppressWarnings("unchecked")
        List<TariffByNumber> tarriffList = (List<TariffByNumber>) stringObjectMap.get("TariffByNumber");

        allCallServiceRepository.saveAll(allCallServices);
        allExpensesByPhoneNumberRepository.saveAll(allExpensesByPhoneNumbers);
        tariffByNumberRepository.saveAll(tarriffList);
    }

    private List<AllCallService> fillingCallService(ReportMTS reportMTS, LocalDate invoiceDate) {
        List<AllCallService> allCallServices = new ArrayList<>();
        MonthlyCallServiceMTS monthlyCallServiceMTS = reportMTS.getPod().get(0);
        List<MonthlyCallServiceByNumberMTS> monthlyCallServiceByNumberMTS = monthlyCallServiceMTS.getDs();
        for (MonthlyCallServiceByNumberMTS monthlyCallServiceByNumber : monthlyCallServiceByNumberMTS) {
            long numberTemp = Long.parseLong(monthlyCallServiceByNumber.getN().substring(3, 12));
            String ownerNumberTemp = monthlyCallServiceByNumber.getU();
            List<CostMonthlyCallServiceByNumberMTS> costMonthlyCallServiceByNumberMTS = monthlyCallServiceByNumber.getI();
            for (CostMonthlyCallServiceByNumberMTS costMonthlyCallServiceByNumber : costMonthlyCallServiceByNumberMTS) {
                AllCallService allCallService = new AllCallService();
                allCallService.setNumber(numberTemp);
                allCallService.setOwnerNumber(ownerNumberTemp);
                allCallService.setCallTime("00:00");
                allCallService.setCallServiceName(costMonthlyCallServiceByNumber.getN());
                allCallService.setInvoiceDate(invoiceDate);
                allCallService.setSum(BigDecimal.valueOf(Double.parseDouble(costMonthlyCallServiceByNumber.getAwt())));
                allCallService.setSumWithNDS(BigDecimal.valueOf(Double.parseDouble(costMonthlyCallServiceByNumber.getA())));
                allCallService.setMobileOperator(2);
                allCallService.setOneTimeCallService(false);
                List<TaxMonthlyCallServiceByNumberMTS> taxMonthlyCallServiceByNumbersMTS = costMonthlyCallServiceByNumber.getT();
                for (TaxMonthlyCallServiceByNumberMTS taxMonthlyCallServiceByNumberMTS : taxMonthlyCallServiceByNumbersMTS) {
                    allCallService.setVatTax(taxMonthlyCallServiceByNumberMTS.getTr());
                    allCallServices.add(allCallService);
                }
            }
        }
        return allCallServices;
    }

    private Map<String, Object> fillingTariffListAndAllExpenses(ReportMTS reportMTS, DateTimeFormatter dateTimeFormatter, LocalDate invoiceDate) {
        List<TariffByNumber> tarriffList = new ArrayList<>();
        List<TariffPlanListMTS> reportMTSPc = reportMTS.getPc();
        List<AllExpensesByPhoneNumber> allExpensesByPhoneNumbers = new ArrayList<>();
        for (TariffPlanListMTS tariffPlanListMTS : reportMTSPc) {
            AllExpensesByPhoneNumber allExpensesByPhoneNumber = new AllExpensesByPhoneNumber();
            allExpensesByPhoneNumber.setNumber(tariffPlanListMTS.getN());
            allExpensesByPhoneNumber.setOwner(tariffPlanListMTS.getU());
            allExpensesByPhoneNumber.setInvoiceDate(invoiceDate);
            allExpensesByPhoneNumber.setSum(BigDecimal.valueOf(Double.parseDouble(tariffPlanListMTS.getC().get(0).getAwt())));
            allExpensesByPhoneNumber.setSumWithNDS(BigDecimal.valueOf(Double.parseDouble(tariffPlanListMTS.getC().get(0).getA())));
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
            allExpensesByPhoneNumbers.add(allExpensesByPhoneNumber);
        }
                Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("AllExpensesByPhoneNumber", allExpensesByPhoneNumbers);
        stringObjectMap.put("TariffByNumber", tarriffList);

        return stringObjectMap;
    }

    private List<Call> fillingCallList(ReportMTS reportMTS, DateTimeFormatter dateTimeFormatter, LocalDate invoiceDate) {
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
                call.setInvoiceDate(invoiceDate);
                call.setCallService(callMTS.getZv());
                call.setCode(callMTS.getS());
                call.setCallTime(callMTS.getDu());
                call.setNumber(callMTS.getN());
                call.setSum(BigDecimal.valueOf(Double.parseDouble(callMTS.getAwt())));
                call.setShortNumber(Long.parseLong(call.getOwnerNumber().substring(3, 12)));
                call.setDayOfWeek(call.getCallDateTime().getDayOfWeek().getValue());
                call.setMobileOperator(2);
                callList.add(call);
            }
        }
        return callList;
    }
}
