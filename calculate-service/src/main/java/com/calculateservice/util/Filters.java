package com.calculateservice.util;

import com.calculateservice.entity.Call;
import com.calculateservice.entity.PhoneNumber;
import com.calculateservice.entity.RuleOneTimeService;
import com.calculateservice.entity.TransferWorkDay;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class Filters {

    public static boolean getFilterByRule(RuleOneTimeService ruleOneTimeService, Call call) {
        return call.getCallService().equals(ruleOneTimeService.getOneTimeCallServiceName());
    }

    public static boolean getFilterByTime(RuleOneTimeService ruleOneTimeService, Call call) {
        return (call.getCallDateTime().toLocalTime().isBefore(ruleOneTimeService.getStartPayment()))
                || (call.getCallDateTime().toLocalTime().isAfter(ruleOneTimeService.getEndPayment()));
    }

    public static boolean getFilterByDay(List<TransferWorkDay> transferWorkDays, Call call) {
        List<LocalDate> localDateDayOff = transferWorkDays.stream()
                .filter(transferWorkDay -> transferWorkDay.getTypeTransferWorkDay().getId() == 1)
                .map(TransferWorkDay::getTransferDate)
                .toList();

        List<LocalDate> localDateDayWork = transferWorkDays.stream()
                .filter(transferWorkDay -> transferWorkDay.getTypeTransferWorkDay().getId() == 2)
                .map(TransferWorkDay::getTransferDate)
                .toList();

        return localDateDayOff.contains(call.getCallDateTime().toLocalDate())
                || (!localDateDayWork.contains(call.getCallDateTime().toLocalDate())
                && (call.getCallDateTime().getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || call.getCallDateTime().getDayOfWeek().equals(DayOfWeek.SUNDAY)));
    }


    public static Stream<Call> getFilterNumberAndLandlineNumber(List<Call> allCalcByDate,
                                                                 List<String> stringListLandlineNumber,
                                                                 PhoneNumber phoneNumber) {
        return allCalcByDate.stream()
//                .filter(call -> call.getMobileOperator().equals("1"))
                .filter(call -> call.getShortNumber() == phoneNumber.getNumber())
                .filter(call -> !stringListLandlineNumber.contains(call.getNumber()));
    }
}
