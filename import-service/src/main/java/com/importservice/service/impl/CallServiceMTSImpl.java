package com.importservice.service.impl;

import com.importservice.xml.ReportMTS;
import com.importservice.service.CallServiceMTS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CallServiceMTSImpl implements CallServiceMTS {
    @Override
    public void createCall(ReportMTS reportMTS) {

        System.out.println();
    }

}
