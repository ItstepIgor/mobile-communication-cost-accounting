package com.calculateservice.service.impl;

import com.calculateservice.entity.*;
import com.calculateservice.repository.IndividualResultRepository;
import com.calculateservice.service.*;
import com.calculateservice.util.Filters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndividualResultServiceImpl implements IndividualResultService {

    private final IndividualResultRepository individualResultRepository;

    private final TransferWorkDayService transferWorkDayService;

    private final CallService callService;

    private final RuleService ruleService;

    private final MonthlyCallServiceService monthlyCallServiceService;

    private final PhoneNumberService phoneNumberService;

    private final LandlineNumberService landlineNumberService;

    private static final String PAID_CALL = "Платно";
    private static final String DAY_CALL = "Днем";
    private static final String FREE_CALL = "Бесплатно";

    @Override
    public void calcIndividualResult(LocalDate date, String number) {
        individualResultRepository.deleteAll();
        PhoneNumber phoneNumber = phoneNumberService.findPhoneNumberByNumber(Long.valueOf(number));
        long phoneGroupId = phoneNumber.getGroupNumber().getId();
        List<String> stringListLandlineNumber = landlineNumberService.findAllLandlineNumber()
                .stream().map(LandlineNumber::getNumber).toList();
        List<RuleOneTimeService> ruleOneTimeServices = ruleService.findRuleOneTimeService(Long.parseLong(number));
        List<IndividualResult> individualResults = new ArrayList<>();
        List<Call> allCallByNumber = callService.findAllCallByNumber(date, number);
        List<TransferWorkDay> transferWorkDays = transferWorkDayService.findAllTransferWorkDay(date);
        List<String> listMonthlyCallServiceName = ruleService
                .findRuleMonthlyService(phoneNumber.getId())
                .stream()
                .map(RuleMonthlyService::getMonthlyCallServiceName)
                .toList();
        List<MonthlyCallService> monthlyCallServiceByDate = monthlyCallServiceService.findAllByDate(date);

        monthlyCallServiceByDate.stream()
                .filter(monthlyCallService -> monthlyCallService.getPhoneNumber().getNumber() == (phoneNumber.getNumber()))
                .filter(monthlyCallService -> !listMonthlyCallServiceName
                        .contains(monthlyCallService.getMonthlyCallServiceName()))
                .forEach(monthlyCallService -> individualResults
                        .add(individualResultMonthlyBuilder(phoneNumber, monthlyCallService, PAID_CALL)));

        monthlyCallServiceByDate.stream()
                .filter(monthlyCallService -> monthlyCallService.getPhoneNumber().getNumber() == (phoneNumber.getNumber()))
                .filter(monthlyCallService -> listMonthlyCallServiceName
                        .contains(monthlyCallService.getMonthlyCallServiceName()))
                .forEach(monthlyCallService -> individualResults
                        .add(individualResultMonthlyBuilder(phoneNumber, monthlyCallService, FREE_CALL)));


        if ((!ruleOneTimeServices.isEmpty()) && (phoneGroupId == 1 || phoneGroupId == 5 || phoneGroupId == 8)) {
            for (RuleOneTimeService ruleOneTimeService : ruleOneTimeServices) {
                allCallByNumber.stream()
                        .filter(call -> Filters.getFilterByRule(ruleOneTimeService, call))
                        .filter(call -> !Filters.getFilterByDay(transferWorkDays, call))
                        .filter(call -> !stringListLandlineNumber.contains(call.getNumber()))
                        .filter(call -> (call.getCallDateTime().toLocalTime().isAfter(ruleOneTimeService.getStartPayment()))
                                && (call.getCallDateTime().toLocalTime().isBefore(ruleOneTimeService.getEndPayment())))
                        .forEach(call -> individualResults.add(individualResultBuilder(phoneNumber, call, DAY_CALL)));
                allCallByNumber.stream()
                        .filter(call -> Filters.getFilterByRule(ruleOneTimeService, call))
                        .filter(call -> !Filters.getFilterByDay(transferWorkDays, call))
                        .filter(call -> Filters.getFilterByTime(ruleOneTimeService, call))
                        .filter(call -> !stringListLandlineNumber.contains(call.getNumber()))
                        .forEach(call -> individualResults.add(individualResultBuilder(phoneNumber, call, PAID_CALL)));
                allCallByNumber.stream()
                        .filter(call -> Filters.getFilterByRule(ruleOneTimeService, call))
                        .filter(call -> Filters.getFilterByDay(transferWorkDays, call))
                        .filter(call -> !stringListLandlineNumber.contains(call.getNumber()))
                        .forEach(call -> individualResults.add(individualResultBuilder(phoneNumber, call, PAID_CALL)));
                allCallByNumber.stream()
                        .filter(call -> stringListLandlineNumber.contains(call.getNumber()))
                        .forEach(call -> individualResults.add(individualResultBuilder(phoneNumber, call, FREE_CALL)));
            }
        } else {
            if (phoneGroupId == 2 || phoneGroupId == 4 || phoneGroupId == 6 || phoneGroupId == 7) {
                allCallByNumber.stream()
                        .filter(call -> !stringListLandlineNumber.contains(call.getNumber()))
                        .forEach(call -> individualResults.add(individualResultBuilder(phoneNumber, call, PAID_CALL)));
                allCallByNumber.stream()
                        .filter(call -> stringListLandlineNumber.contains(call.getNumber()))
                        .forEach(call -> individualResults.add(individualResultBuilder(phoneNumber, call, FREE_CALL)));
            } else if (phoneGroupId == 3) {
                allCallByNumber.forEach(call -> individualResults.add(individualResultBuilder(phoneNumber, call, FREE_CALL)));
            }
        }
        individualResultRepository.saveAll(individualResults);
    }

    private IndividualResult individualResultBuilder(PhoneNumber phoneNumber, Call call, String callType) {
        return IndividualResult.builder()
                .ownerName(call.getOwnerNumber())
                .callService(call.getCallService())
                .phoneNumber(phoneNumber)
                .callDateTime(call.getCallDateTime())
                .sum(call.getSum())
                .callType(callType)
                .build();
    }

    private IndividualResult individualResultMonthlyBuilder(PhoneNumber phoneNumber, MonthlyCallService call, String callType) {
        return IndividualResult.builder()
                .ownerName(String.valueOf(call.getPhoneNumber().getNumber()))
                .callService(call.getMonthlyCallServiceName())
                .phoneNumber(phoneNumber)
                .callDateTime(call.getInvoiceDate().atStartOfDay())
                .sum(call.getSum())
                .callType(callType)
                .build();
    }
}
