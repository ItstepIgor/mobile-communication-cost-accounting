package com.importservice.service.impl;

import com.importservice.entity.Call;
import com.importservice.reposiitory.CallRepository;
import com.importservice.xml.CallMTS;
import com.importservice.xml.CallsFromNumberMTS;
import com.importservice.xml.ReportMTS;
import com.importservice.service.CallServiceMTS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CallServiceMTSImpl implements CallServiceMTS {


    private final CallRepository callRepository;

    @Override
    public void createCall(ReportMTS reportMTS) {

        List<Call> callList = fillingCallList(reportMTS);

        System.out.println(callList);
    }

    private List<Call> fillingCallList(ReportMTS reportMTS) {
        List<Call> callList = new ArrayList<>();
        List<CallsFromNumberMTS> reportMTSNd = reportMTS.getNd();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        for (CallsFromNumberMTS callsFromNumberMTS : reportMTSNd) {
            Call call = new Call();
            call.setOwnerNumber(callsFromNumberMTS.getN());
            List<CallMTS> fromNumberMTSI = callsFromNumberMTS.getI();
            for (CallMTS callMTS : fromNumberMTSI) {
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
